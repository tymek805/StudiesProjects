import pandas as pd

# https://www.kaggle.com/datasets/START-UMD/gtd
csv_file = ""

def cleanup_text_column(df, column_name):
    df[column_name] = df[column_name].fillna('Unknown').astype(str)
    df[column_name] = df[column_name].str.replace('"', '').str.replace("'", '')
    df[column_name] = df[column_name].str.replace(',', ';')
    df[column_name] = df[column_name].str.strip()
    df[column_name] = df[column_name].str.replace('&', 'and')


def validate_row(row):
    errors = []

    if pd.isnull(row['eventid']):
        errors.append('Brak eventid')

    if not (1997 <= row['iyear'] <= 2025):
        errors.append('Niepoprawny rok')

    if not (0 <= row['imonth'] <= 12):
        errors.append('Niepoprawny miesiąc')

    if not (0 <= row['iday'] <= 31):
        errors.append('Niepoprawny dzień')

    if pd.isnull(row['country_txt']) or row['country_txt'].strip() == '':
        errors.append('Brak country_txt')

    if pd.isnull(row['city']) or row['city'].strip() == '':
        errors.append('Brak city')

    if pd.isnull(row['gname']) or row['gname'].strip() == '':
        errors.append('Brak gname')

    if row['nkill'] < 0:
        errors.append('Ujemna liczba ofiar (nkill)')

    if pd.isnull(row['suicide']):
        errors.append('Brak wartości suicide')

    return errors


def read_and_clean_data():
    columns = ["eventid", "iyear", "imonth", "iday", "country_txt", "region_txt", "city", "attacktype1_txt",
               "targtype1_txt", "gname", "weaptype1_txt", "nkill", "nwound", "success", "suicide"]

    df = pd.read_csv('etl_test_data.csv', encoding='ISO-8859-1', low_memory=False)
    # df = pd.read_csv('globalterrorismdb.csv', encoding='ISO-8859-1', low_memory=False)
    df = df[df['iyear'] >= 1997]
    df = df[columns]


    df['iyear'] = pd.to_numeric(df['iyear'], errors='coerce').fillna(0).astype(int)
    df['nkill'] = pd.to_numeric(df['nkill'], errors='coerce').fillna(0).astype(int)
    df['nwound'] = pd.to_numeric(df['nwound'], errors='coerce').fillna(0).astype(int)

    df['success'] = pd.to_numeric(df['success'], errors='coerce').fillna(0).astype(int)
    df['suicide'] = pd.to_numeric(df['suicide'], errors='coerce').fillna(0).astype(int)

    df['city'] = df['city'].fillna('Unknown')

    text_columns = ['city', 'country_txt', 'region_txt', 'attacktype1_txt', 'targtype1_txt', 'gname', 'weaptype1_txt']
    for col in text_columns:
        cleanup_text_column(df, col)

    df['weaptype1_txt'] = df['weaptype1_txt'].str.split().str[0]

    return df



def etl_process():
    df = read_and_clean_data()

    clean_rows = []
    error_log = []

    seen_eventids = set()

    for index, row in df.iterrows():
        row_errors = validate_row(row)
        eventid = row['eventid']

        if eventid in seen_eventids:
            row_errors.append('Duplikat eventid')

        if not row_errors:
            clean_rows.append(row)
            seen_eventids.add(eventid)
        else:
            error_log.append({'eventid': eventid, 'errors': '; '.join(row_errors)})

    clean_df = pd.DataFrame(clean_rows)
    error_df = pd.DataFrame(error_log)

    clean_df.to_csv('processed_data.csv', index=False)
    error_df.to_csv('error_log.csv', index=False)

    print(f'Liczba poprawnych rekordów: {len(clean_df)}')
    print(f'Liczba błędnych rekordów: {len(error_df)}')


def main():
    etl_process()


if __name__ == '__main__':
    main()

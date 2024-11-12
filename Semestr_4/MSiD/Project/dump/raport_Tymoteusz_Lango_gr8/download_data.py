from bs4 import BeautifulSoup
import requests
import os
import zipfile


def download_zip_file(url: str, local_filename: str) -> str:
    if not local_filename.endswith('.zip'):
        local_filename += '.zip'

    with requests.get(url, stream=True) as response:
        response.raise_for_status()
        with open(local_filename, 'wb') as file:
            for chunk in response.iter_content(chunk_size=8192):
                file.write(chunk)
    return local_filename


def unzip_file(zip_path: str, extract_to: str):
    if not os.path.isdir(extract_to):
        os.mkdir(extract_to)

    with zipfile.ZipFile(zip_path, 'r') as zip_ref:
        zip_ref.extractall(extract_to)
    os.remove(zip_path)


def remove_redundant(dir_path: str, parameters: list[str]):
    if not os.path.isdir(dir_path):
        return

    for file in os.listdir(dir_path):
        file_path = os.path.join(dir_path, file)
        if os.path.isfile(file_path) and not any([param in file for param in parameters]):
            os.remove(file_path)


def download_by_year(begin_year: int = 0, end_year: int = 0):
    base_url = "https://powietrze.gios.gov.pl"
    header = {"UserAgent": 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0'}

    page = requests.get(base_url + "/pjp/archives", headers=header)
    content = BeautifulSoup(page.content, 'html.parser').find_all('div', class_='row archive-row')[-1].find_all('li')[:-1]
    links = [(elem.find('p', class_='archive_file_name').get_text(), elem.find('a')['href']) for elem in content]
    for link in links:
        name, href = link
        year = [int(s) for s in name.split() if s.isdigit()][0]
        parameters = ['_SO2_', '_PM25_', '_PM2.5_', '_PM10_', '_O3_', '_CO_', '_NO2_']
        parameters = [str(year) + param for param in parameters]
        if begin_year <= year and (year <= end_year or end_year == 0):
            print(f"Downloading file \"{name}\"")
            downloaded_file = download_zip_file(base_url + href, name)
            print(f"File \"{downloaded_file}\" has been downloaded")
            extraction_dir = os.path.join('data', name)
            unzip_file(downloaded_file, extraction_dir)
            remove_redundant(extraction_dir, parameters)

    print("Finished")


download_by_year(begin_year=2006)

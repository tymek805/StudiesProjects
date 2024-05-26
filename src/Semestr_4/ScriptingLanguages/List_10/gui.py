import locale
import os.path
from datetime import datetime

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import *

from database_functions import DatabaseManager


# from read_logs import read_logs, log_to_dict


class LogDetailView(QWidget):
    def __init__(self):
        super().__init__()
        self.layout = QGridLayout()

        self.host = self.create_text_field('Remote host:', (0, 1, 1, 3))
        self.date = self.create_text_field('Date:', (1, 1, 1, 3))
        self.time = self.create_text_field('Time:', (2, 1))
        self.timezone = self.create_text_field('Timezone:', (2, 3))
        self.code = self.create_text_field('Status code:', (3, 1))
        self.method = self.create_text_field('Method:', (3, 3))
        self.resource = self.create_text_field('Resource:', (4, 1, 1, 3))
        self.size = self.create_text_field('Size:', (5, 1))

        self.setLayout(self.layout)

    def create_text_field(self, label_text: str, pos: tuple,
                          alignment=Qt.AlignmentFlag.AlignRight | Qt.AlignmentFlag.AlignVCenter) -> QLineEdit:
        text_field = QLineEdit()
        text_field.setEnabled(False)

        # self.layout.addWidget(QLabel(label_text, alignment=alignment), pos[0], pos[1] - 1)
        self.layout.addWidget(text_field, *pos)

        return text_field


class DatePicker(QWidget):
    def __init__(self, log_browser):
        super().__init__()
        self.log_browser = log_browser

        self.layout = QHBoxLayout()
        self.date_from = self.create_date_widget('From:', datetime(1900, 1, 1))
        self.date_to = self.create_date_widget('To:', datetime.today())
        self.setLayout(self.layout)

    def create_date_widget(self, label_text: str, date) -> QDateEdit:
        date_label = QLabel(label_text)
        date_picker = QDateEdit(date)

        date_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        date_picker.setCalendarPopup(True)
        # date_picker.dateChanged.connect(self.log_browser.filter)

        layout = QHBoxLayout()
        layout.addWidget(date_label, 25)
        layout.addWidget(date_picker, 75)

        button_widget = QWidget()
        button_widget.setLayout(layout)
        self.layout.addWidget(button_widget)
        return date_picker


class LogBrowser(QWidget):
    def __init__(self, log_detail_view: LogDetailView):
        super().__init__()
        self.logs = []
        self.log_detail_view = log_detail_view

        self.date_picker = DatePicker(self)
        self.log_list = QListWidget()
        self.log_list.itemSelectionChanged.connect(self.update_log_view)
        self.log_list.addItems(self.logs)

        layout = QVBoxLayout()
        layout.addWidget(self.date_picker)
        layout.addWidget(self.log_list)

        self.setLayout(layout)

    def update_log_view(self):
        selected_items = self.log_list.selectedItems()
        if selected_items:
            self.log_detail_view.update(selected_items[0].text())

    def previous(self):
        current_row = self.log_list.currentRow()
        if current_row > 0:
            self.log_list.setCurrentRow(current_row - 1)

    def next(self):
        current_row = self.log_list.currentRow()
        if current_row < self.log_list.count() - 1:
            self.log_list.setCurrentRow(current_row + 1)


class FileBrowser(QWidget):
    def __init__(self, log_browser: LogBrowser):
        super().__init__()
        self.log_browser = log_browser

        self.path_display = QLineEdit('')
        self.browser_btn = QPushButton('Browse')

        self.browser_btn.clicked.connect(self.click_browse)
        self.path_display.setEnabled(False)

        layout = QHBoxLayout()
        layout.addWidget(self.path_display)
        layout.addWidget(self.browser_btn)
        self.setLayout(layout)


class MainWindow(QMainWindow):
    def __init__(self, db_manager: DatabaseManager, window_width: int, window_height: int):
        super().__init__()
        self.db_manager: DatabaseManager = db_manager
        self.window_width: int = window_width
        self.window_height: int = window_height

        combobox = QComboBox()
        combobox.addItems(db_manager.get_stations())
        combobox.setCurrentIndex(-1)
        combobox.currentTextChanged.connect(self.calculate)

        self.queries: dict[str:QLabel] = {
            "Average rental time (starting): ": QLabel(),
            "Average rental time (ending): ": QLabel(),
            "Number of distinct bikes parked: ": QLabel(),
            "Number of paid rentals (longer than 20 minutes): ": QLabel(),
        }

        layout = QVBoxLayout()
        layout.addWidget(combobox)

        for idx, (query, label) in enumerate(self.queries.items()):
            child_layout = QGridLayout()
            child_layout.addWidget(QLabel(query, alignment=Qt.AlignmentFlag.AlignRight | Qt.AlignmentFlag.AlignVCenter),
                                   idx, 0, 1, 3)
            label.setStyleSheet("border: 1px solid black;")
            child_layout.addWidget(label, idx, 3, 1, 1)
            child_widget = QWidget()
            child_widget.setLayout(child_layout)
            layout.addWidget(child_widget)

        self.window = QWidget(self)
        self.window.setLayout(layout)
        self.setWindowTitle('WRM - Tymoteusz Lango')
        self.setCentralWidget(self.window)
        self.center()

    def center(self):
        screen_geometry = QApplication.primaryScreen().geometry()

        self.setGeometry(
            (screen_geometry.width() - self.window_width) // 2,
            (screen_geometry.height() - self.window_height) // 2,
            self.window_width,
            self.window_height
        )

    def calculate(self, station_name):
        for label, value in zip(self.queries.values(), self.db_manager.calculate(station_name)):
            label.setText(str(round(value, 2)))


def run(db_file):
    app = QApplication([])
    db_manager = DatabaseManager(db_file)
    window = MainWindow(db_manager, 500, 300)
    window.show()
    locale.setlocale(locale.LC_TIME, 'pl_PL.utf8')
    app.exec()


if __name__ == "__main__":
    run('rentals_db')

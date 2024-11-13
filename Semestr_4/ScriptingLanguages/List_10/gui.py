import locale

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import *

from database_functions import DatabaseManager


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

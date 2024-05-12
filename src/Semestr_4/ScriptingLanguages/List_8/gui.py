from PyQt6.QtCore import Qt, QRect
from PyQt6.QtGui import *
from PyQt6.QtWidgets import *


class FileBrowser(QWidget):
    def __init__(self):
        super().__init__()

        self.path_display = QLineEdit('CHOOSE FILE TO DISPLAY')
        self.browser_btn = QPushButton('Browse')

        self.browser_btn.clicked.connect(self.click_browse)
        self.path_display.setEnabled(False)

        layout = QHBoxLayout()
        layout.addWidget(self.path_display)
        layout.addWidget(self.browser_btn)
        self.setLayout(layout)

    def click_browse(self):
        file_name = QFileDialog.getOpenFileName(
            QFileDialog(self),
            'Choose log file',
            '',
            'ExistingFiles'
        )
        if file_name:
            self.path_display.setText(file_name[0])


class DatePicker(QWidget):
    def __init__(self):
        super().__init__()
        layout = QHBoxLayout()
        layout.addWidget(self._create_buttons('From:'))
        layout.addWidget(self._create_buttons('To:'))

        self.setLayout(layout)

    def _create_buttons(self, label_text: str) -> QWidget:
        date_label = QLabel(label_text)
        date_picker = QDateEdit()

        date_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        date_picker.setCalendarPopup(True)

        layout = QHBoxLayout()
        layout.addWidget(date_label, 25)
        layout.addWidget(date_picker, 75)

        button_widget = QWidget()
        button_widget.setLayout(layout)
        return button_widget


class LogBrowserTable(QWidget):
    def __init__(self):
        super().__init__()

        layout = QVBoxLayout()
        layout.addWidget(DatePicker())
        layout.addWidget(self._create_table())

        self.setLayout(layout)

    def _create_table(self):
        self.listWidget = QListWidget(self)

        data = ["Item 1", "Item 2", "Item 3", "Item 4", "Item 5"]

        for item in data:
            self.listWidget.addItem(item)

        # Fill table with data
        # self.tableWidget.populateTable()
        return self.listWidget


class MainWindow(QMainWindow):
    def __init__(self, window_width, window_height):
        super().__init__()
        self.window = QWidget(self)

        self.window_width = window_width
        self.window_height = window_height

        layout = QVBoxLayout()
        layout.addWidget(FileBrowser())
        layout.addWidget(LogBrowserTable())
        self.window.setLayout(layout)

        self.setWindowTitle('Log viewer - Tymoteusz Lango')
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


def run():
    app = QApplication([])
    window = MainWindow(500, 500)
    window.show()
    app.exec()


if __name__ == "__main__":
    run()

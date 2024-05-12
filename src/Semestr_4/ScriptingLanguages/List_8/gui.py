from PyQt6.QtCore import Qt, QRect
from PyQt6.QtGui import *
from PyQt6.QtWidgets import *


class MainWindow(QMainWindow):
    def __init__(self, window_width, window_height):
        super().__init__()
        self.window_width = window_width
        self.window_height = window_height

        self.setWindowTitle('Log viewer - Tymoteusz Lango')

        layout = QHBoxLayout()

        # layout.addWidget(Color('red'))
        # layout.addWidget(Color('green'))
        # layout.addWidget(Color('blue'))

        widget = QWidget()
        widget.setLayout(layout)
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

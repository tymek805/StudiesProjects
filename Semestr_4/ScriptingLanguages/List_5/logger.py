import logging
import sys


def define_logger(logger_level):
    logger = logging.getLogger()
    logging.basicConfig(
        format="%(levelname)s: %(message)s",
        level=logger_level,
        filemode="w",
        filename="logs.log",
    )

    fmt = logging.Formatter('%(levelname)s: %(message)s')

    stdout_handler = logging.StreamHandler(sys.stdout)
    stdout_handler.setLevel(logging.DEBUG)
    stdout_handler.addFilter(lambda record: record.levelno <= logging.INFO)
    stdout_handler.setFormatter(fmt)
    logger.addHandler(stdout_handler)

    stderr_handler = logging.StreamHandler(sys.stderr)
    stderr_handler.setLevel(logging.ERROR)
    stderr_handler.setFormatter(fmt)
    logger.addHandler(stderr_handler)

    return logger

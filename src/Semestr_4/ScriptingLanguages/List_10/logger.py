import logging
import os
import sys
from logging import handlers


def prepare_logger(logger_name: str, log_file: str = 'notify.log', log_level: str = 'INFO') -> logging.Logger:
    logger = logging.getLogger(logger_name)
    logger.setLevel(log_level)
    formatter = logging.Formatter(fmt='%(asctime)s - %(name)s:%(levelname)s - %(message)s', datefmt='%Y-%m-%d %H:%M:%S')

    # FILE handler
    handler = handlers.WatchedFileHandler(os.path.join(os.path.dirname(__file__), log_file))
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    # STDOUT handler
    handler = logging.StreamHandler(sys.stdout)
    handler.setLevel(logging.INFO)
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    return logger

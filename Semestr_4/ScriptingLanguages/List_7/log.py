import logging
from functools import wraps
from time import time


def log(level=logging.DEBUG):
    def decorator(func_or_class):
        logging.basicConfig(level=level)
        if isinstance(func_or_class, type):
            @wraps(func_or_class)
            def wrapper_class(*args, **kwargs):
                logging.log(level, f"Tworzenie instancji klasy {func_or_class.__name__}")
                return func_or_class(*args, **kwargs)

            return wrapper_class
        else:
            @wraps(func_or_class)
            def wrapper(*args, **kwargs):
                start_time = time()
                result = func_or_class(*args, **kwargs)
                duration = time() - start_time
                logging.log(level, f"Wywołano {func_or_class.__name__} z argumentami {args}, {kwargs}. "
                                   f"Czas wywołania: {start_time:.0f}s czas trwania: {duration:.4f}s, "
                                   f"wartość zwracana: {result}")
                return result
            return wrapper
    return decorator


@log(logging.DEBUG)
def example_fun(x, y):
    return x + y


@log()
class ExampleClass:
    def __init__(self, value):
        self.value = value


if __name__ == "__main__":
    example_fun(1, 2)
    ExampleClass(42)
    example_fun(3, 4)
    ExampleClass(1337)
    example_fun(5, 6)
    ExampleClass(9001)

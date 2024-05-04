from functools import cache


def make_generator(function):
    def generator():
        n = 1
        while True:
            yield function(n)
            n += 1
    return generator


def fibonacci(n: int):
    array = [0, 1]
    if n < 0:
        raise ValueError("n must be a positive number")
    elif n < 2:
        return n
    return fibonacci(n - 1) + fibonacci(n - 2)


def make_generator_mem(function):
    @cache
    def cached_f(n):
        return function(n)

    return make_generator(cached_f)


if __name__ == "__main__":
    # print("Generator with Fibonacci: ")
    # [print(value) for value in make_generator(fibonacci)()]
    # print("Power sequence generator: ")
    # [print(value) for value in make_generator(lambda x: x ** 2)()]
    print("Power sequence generator_mem: ")
    [print(value) for value in make_generator_mem(lambda x: x ** 2)()]

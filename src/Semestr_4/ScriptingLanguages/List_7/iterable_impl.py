def forall(pred, iterable) -> bool:
    return next((False for elem in iterable if not pred(elem)), True)


def exists(pred, iterable) -> bool:
    return next((True for elem in iterable if pred(elem)), False)


def atleast(n, pred, iterable) -> bool:
    return sum([pred(elem) for elem in iterable]) >= n


def atmost(n, pred, iterable) -> bool:
    return sum([pred(elem) for elem in iterable]) <= n


if __name__ == "__main__":
    print(forall(lambda x: x >= 0, [-7, 0, 11]))
    print(exists(lambda x: x >= 0, [-7, 0, 11]))
    print(atleast(2, lambda x: x >= 0, [-7, 0, -11]))
    print(atmost(2, lambda x: x >= 0, [-7, 0, -11]))

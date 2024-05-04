def odwr(string: str) -> str:
    return ' '.join(string.split()[::-1])


def most_common_element(array: list):
    return max(set(array), key=array.count)


def pierwiastek(x: int | float, epsilon: int | float, y=-1.0) -> int | float:
    y = y if y >= 0 else 0.5 * (x + 1)
    return pierwiastek(x, epsilon, (y + (x / y)) / 2) if abs(y ** 2 - x) >= epsilon else y


def make_alpha_dict(arg: str) -> dict:
    ans_dict = {}
    [[ans_dict.update({c: [word]}) if ans_dict.get(c) is None else ans_dict.get(c).append(word) for c in word] for word
     in arg.split() if word != '']
    return ans_dict


def flatten(array: list) -> list:
    arr = []
    [arr.append(elem) if type(elem) is not list else arr.extend(flatten(elem)) for elem in array]
    # return [elem if type(elem) is not list else flatten(elem) for elem in array]
    return arr


if __name__ == "__main__":
    print(odwr("To jest przykładowe zdanie do odwrócenia"))
    print(most_common_element([1, 1, 19, 2, 3, 4, 4, 5, 1]))
    print(pierwiastek(3, epsilon=0.1))
    print(make_alpha_dict("on i ona"))
    print(flatten([1, [2, 3], [[4, 5], 6]]))

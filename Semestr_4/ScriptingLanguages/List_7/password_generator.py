import secrets
import string


class PasswordGenerator:
    def __init__(self, length, charset, count):
        self.password_length = length
        self.charset = charset
        self.available_passwords = count

    def __iter__(self):
        return self

    def __next__(self):
        if self.available_passwords == 0:
            raise StopIteration("Maximum generated passwords reached")
        self.available_passwords -= 1
        return ''.join((secrets.choice(self.charset) for _ in range(self.password_length)))


if __name__ == "__main__":
    [print("For: ", password) for password in PasswordGenerator(10, string.ascii_letters, 10)]
    print("Next:", next(PasswordGenerator(10, string.ascii_letters, 10)))

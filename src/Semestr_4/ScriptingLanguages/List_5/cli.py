import argparse

import ssh_logs
from logger import define_logger


def cli():
    parser = argparse.ArgumentParser()
    parser.add_argument('logfile', help="Path to SSH log file")
    parser.add_argument(
        "--log-level",
        choices=["DEBUG", "INFO", "WARNING", "ERROR", "CRITICAL"],
        help="Set the logging level",
        default="ERROR"
    )
    subparsers = parser.add_subparsers(
        dest="command",
        title="subcommands",
        required=True,
    )

    subparsers.add_parser('ipv4', help="Get all IPv4 addresses from the log file")
    subparsers.add_parser('user', help="List all users from the log file")
    subparsers.add_parser('msg_type', help="List message types from the log file")
    random_parser = subparsers.add_parser('random', help="Get random logs from a random user")
    random_parser.add_argument(
        "-n",
        type=int,
        default=10,
        help="Number of logs of random user"
    )
    stat_parser = subparsers.add_parser('stats', help="Get the average session duration and standard deviation")
    stat_parser.add_argument(
        "--group-by-user",
        help="Group the stats by users",
        default=False,
    )
    subparsers.add_parser('frequent_users', help="Get the most and least frequent users")
    args = parser.parse_args()

    logger = define_logger(args.log_level)
    logs = ssh_logs.read_logs(args.logfile)
    match args.command:
        case 'ipv4':
            [print(ssh_logs.get_ipv4_from_log(log)) for log in logs]
        case 'user':
            [print(ssh_logs.get_user_from_log(log)) for log in logs]
        case 'msg_type':
            [print(ssh_logs.get_message_type_from_log(log).value) for log in logs]
        case 'random':
            [print(log) for log in ssh_logs.get_logs_from_random_user(args.n, logs)]
        case 'frequent_users':
            users = ssh_logs.most_least_user_login(logs)
            print(f"Lest frequent user: {users[0]}")
            print(f"Most frequent user: {users[1]}")
        case 'stats':
            if args.group_by_user:
                stats = ssh_logs.calculate_avg_and_deviation_by_users(logs)
                for user, (avg, dev) in stats.items():
                    print(f"{user}: {avg} {dev}")
            else:
                avg, dev = ssh_logs.calculate_avg_duration_and_deviation(logs)
                print(f"Average: {avg} Deviation: {dev}")


if __name__ == "__main__":
    cli()

import random

# board by its index
board = [0, 0, 0, 0, 0, 0, 0, 0, 0]
#player: -1 / bot: 1
def show_board():
    for index in range(len(board)):
        print("|", end="")
        if board[index] == 0:
            print(" ", end="")
        elif board[index] == -1:
            print("O", end="")
        else:
            print("X", end="")
        if index % 3 == 2:
            print("|")

def show_board_number():
    for i in range(3):
        for j in range(3):
            print(f"|{j+i*3}", end="")
        print("|")

def main():
    turn_num = 1
    turn = flip_coin()
    if turn:
        print("PLayer goes first (player: O / bot: X)")
    else:
        print("Bot goes first (player: O / bot: X)")
    #if turn = True -> player, else bot
    while True:
        #player
        if turn:
            show_board()
            show_board_number()
            while True:
                try:
                    position = int(input("Enter a position from 0 to 8 (inclusive): "))
                    if position >= 0 and position < 9:
                        if board[position] == 0:
                            board[position] = -1
                            break
                        else:
                            print("Input a number for an empty space")
                            show_board()
                    else:
                        print("Input a number between 0 and 8 (inclusive)")
                        show_board()
                except:
                    print("Input a valid number between 0 and 8 (inclusive)")
                    show_board()
            turn = False
        #bot
        else:
            show_board()
            position = 0
            if turn_num == 1:
                position = 4
            else:
                new_board = board.copy()
                dict = {"s":new_board}
                new_dict = recursion(dict, turn_num)
                position = int(pick_position(new_dict))
            board[position] = 1
            turn = True
            print("Bot has made its move")
        #2: tie / 0: in_progress / -1: player victory / 1: bot victory
        result = determine_board(board)
        if result == -1:
            show_board()
            print("Player has won")
            break
        elif result == 1:
            show_board()
            print("Bot has won")
            break
        elif result == 2:
            show_board()
            print("Tie")
            break
        turn_num += 1

def recursion(dict, turn_num):
    new_dict = {}
    if turn_num == 10:
        return dict
    for move in dict.keys():
        for i in range(9):
            if dict[move][i] == 0:
                new_board = dict[move].copy()
                if turn_num % 2 == 1:
                    new_board[i] = -1
                else:
                    new_board[i] = 1
                new_dict.setdefault(move+str(i), new_board)
    return recursion(new_dict, turn_num + 1)

def pick_position(dict):
    list_bot = []
    list_player = []
    list_tie = []
    for position in dict.keys():
        determine_num = determine_board(dict[position])
        if determine_num == -1:
            list_player.append(position[1])
        elif determine_num == 1:
            list_bot.append(position[1])
        else:
            list_tie.append(position[1])
    list_critical = critical_positions()
    list_success = success_positions()
    if len(list_success) > 0:
        return random.choice(list_success)
    elif len(list_critical) > 0:
        return random.choice(list_critical)
    elif len(list_bot) > 0:
        return random.choice(list_bot)
    elif len(list_tie) > 0:
        return random.choice(list_tie)
    else:
        return random.choice(list_player)

def success_positions():
    list_success = []
    for i in range(0, 9, 3):
        #i = 0,3,6 / row
        if board[i] == 1 and board[i+1] == 1 and board[i+2] == 0:
            list_success.append(i+2)
        if board[i] == 1 and board[i+1] == 0 and board[i+2] == 1:
            list_success.append(i+1)
        if board[i] == 0 and board[i+1] == 1 and board[i+2] == 1:
            list_success.append(i)
    for i in range(3):
        if board[i] == 1 and board[i+3] == 1 and board[i+6] == 0:
            list_success.append(i+6)
        if board[i] == 1 and board[i+3] == 0 and board[i+6] == 1:
            list_success.append(i+3)
        if board[i] == 0 and board[i+3] == 1 and board[i+6] == 1:
            list_success.append(i)
    for i in range(2, 5, 2):
        if board[4-i] == 1 and board[4] == 1 and board[4+i] == 0:
            list_success.append(4+i)
        if board[4-i] == 1 and board[4] == 0 and board[4+i] == 1:
            list_success.append(4)
        if board[4-i] == 0 and board[4] == 1 and board[4+i] == 1:
            list_success.append(4-i)
    return list_success

def critical_positions():
    list_critical = []
    for i in range(0, 9, 3):
        #i = 0,3,6 / row
        if board[i] == -1 and board[i+1] == -1 and board[i+2] == 0:
            list_critical.append(i+2)
        if board[i] == -1 and board[i+1] == 0 and board[i+2] == -1:
            list_critical.append(i+1)
        if board[i] == 0 and board[i+1] == -1 and board[i+2] == -1:
            list_critical.append(i)
    for i in range(3):
        if board[i] == -1 and board[i+3] == -1 and board[i+6] == 0:
            list_critical.append(i+6)
        if board[i] == -1 and board[i+3] == 0 and board[i+6] == -1:
            list_critical.append(i+3)
        if board[i] == 0 and board[i+3] == -1 and board[i+6] == -1:
            list_critical.append(i)
    for i in range(2, 5, 2):
        if board[4-i] == -1 and board[4] == -1 and board[4+i] == 0:
            list_critical.append(4+i)
        if board[4-i] == -1 and board[4] == 0 and board[4+i] == -1:
            list_critical.append(4)
        if board[4-i] == 0 and board[4] == -1 and board[4+i] == -1:
            list_critical.append(4-i)
    return list_critical

def determine_board(board):
    player_victory = False
    bot_victory = False
    for i in range(3):
        if board[i * 3] == board[i * 3 + 1] == board[i * 3 + 2] == -1:
            player_victory = True
        if board[i * 3] == board[i * 3 + 1] == board[i * 3 + 2] == 1:
            bot_victory = True
        if board[i] == board[i + 3] == board[i + 6] == -1:
            player_victory = True
        if board[i] == board[i + 3] == board[i + 6] == 1:
            bot_victory = True
    if board[0] == board[4] == board[8] == -1:
        player_victory = True
    if board[0] == board[4] == board[8] == 1:
        bot_victory = True
    if board[2] == board[4] == board[6] == -1:
        player_victory = True
    if board[2] == board[4] == board[6] == 1:
        bot_victory = True
    tie = True
    for i in range(9):
        if board[i] == 0:
            tie = False
    if player_victory:
        return -1
    elif bot_victory:
        return 1
    elif tie:
        return 2
    return 0

def flip_coin():
    return bool(random.getrandbits(1))

if __name__ == "__main__":
    main()
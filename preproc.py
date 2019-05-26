import os
# -*- coding: utf-8 -*-

def preproc():
    path = os.getcwd()
    try:
        flag = True
        with open(path + "/Script/Interstellar.txt", 'r') as ins:
            array = []
            for line in ins:
                li = line.strip()
                line = line.strip()
                if len(li) < 1:
                    continue
                array.append(line)
        movie_title = array[0]
        print("영화 제목 : ", movie_title)
        print(array)



    except FileNotFoundError:
        print("No such file or directory.")


if __name__ == "__main__":
    preproc()
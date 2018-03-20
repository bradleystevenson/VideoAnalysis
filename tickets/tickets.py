#!/usr/bin/python
import glob

def print_open_tickets(filename, open_tickets):
    file = open(filename, "w+")
    for ticket in open_tickets:
        file.write(ticket + "\n")
    file.close()

def print_closed_tickets(filename, new_tickets):
    already_closed_tickets = []
    try:
        file = open("closed/" + filename + "-closed", "r+")
        strings = file.read().split("\n")
        for string in strings:
            already_closed_tickets.append(string)
        file.close()
    except IOError:
        print "no such file"
    file = open("closed/" + filename + "-closed", "w+")
    for ticket in already_closed_tickets:
        file.write(ticket + "\n")
    for ticket in new_tickets:
        file.write(ticket + "\n")
    file.close()

def clean_file(filename):
    file = open(filename, "r")
    strings = file.read().split("\n")
    file.close()
    open_tickets = []
    closed_tickets = []
    for string in strings:
        if "CHECK" in string:
            closed_tickets.append(string)
        else:
            open_tickets.append(string)
    print_closed_tickets(filename, closed_tickets)
    print_open_tickets(filename, open_tickets)
                                
if __name__ == '__main__':
    files = glob.glob("*tickets*")
    for file in files:
        if ".py" not in file and "closed" not in file:
            clean_file(file)

    files = glob.glob("*version*")
    for file in files:
        if "closed" not in file:
            clean_file(file)

#!/usr/bin/python
# -*- coding: utf-8 -*-

### snippets
# dirs[:] = [d for d in dirs if d not in folders_to_exclude]
# local = [file for file in files if os.path.splitext(file)[1] in file_types]

### features:
# > create session
# enter button - session starts - first problem
# enter btn - starts timer
# button to exit exists always - click - session is removed
# 
# running timer
# display percentage
# > create plan
# use subjects that require more attention(more often / more problems)
# set program (set countdown to the interviews)
# > stats
# calculate weekly overall across all subjects
# display which subject requires more attention (bad results)

import os
import random
import npyscreen

folders_to_exclude = ['ds']
file_types = ['.java']


def read_from_file(file_path):
    file = open(file_path, 'r', encoding='utf-8')
    params = file.read().splitlines()
    file.close()
    return params


def create_files_map(from_dir):
    result = {}
    for dirpath, subdirs, files in os.walk(from_dir, topdown=True):
        for x in files:
            if x.endswith(".java"):
                path = os.path.join(dirpath, x)
                num = find_problem_number_in_file(path)
                result[num] = {'path': path, 'name': x}

    return result


def find_problem_number_in_file(file_path):
    file = open(file_path, 'r', encoding='utf-8')
    line = file.readline()
    next_line = False
    while line:
        if next_line:
            return line.split(" * ")[1].split('\n')[0]
        if line.startswith('/**'):
            next_line = True
        line = file.readline()
    file.close()


def random_select(n):
    return random.randint(0, n - 1)


def get_random_problem():
    params = read_from_file('parameters.txt')
    params_map = {x[0]: x[1] for x in [param.split(",") for param in params]}

    main_dir = params_map['main_dir']

    topics = read_from_file('input.txt')
    topics_map = {x[0]: x[1] for x in [topic.split(",") for topic in topics]}

    rand_topic_ind = random_select(len(topics_map))
    rand_topic_key = list(topics_map)[rand_topic_ind]
    rand_topic_dir = main_dir + topics_map[rand_topic_key]

    result_map = create_files_map(rand_topic_dir)

    rand_problem_ind = random_select(len(result_map))
    rand_problem = list(result_map)[rand_problem_ind]

    return rand_topic_key + ': ' + rand_problem


class InSessionBox(npyscreen.BoxTitle):
    _contained_widget = npyscreen.TitleText


class BoxWithSelects(npyscreen.BoxTitle):
    _contained_widget = npyscreen.SelectOne


class MainForm(npyscreen.FormBaseNew):
    text_controls_begin_session = 'begin session'
    text_controls_start_problem = 'start problem'
    text_controls_done = 'done'
    text_controls_exit = 'exit'

    controls = [text_controls_begin_session, text_controls_exit]
    controls_on_begin_session = [text_controls_start_problem, text_controls_exit]
    controls_on_start_problem = [text_controls_done, text_controls_exit]
    controls_on_done = controls_on_begin_session

    def create(self):
        self.y, self.x = self.useable_space()
        self.controls = self.add(BoxWithSelects, values=self.controls, max_height=5, max_width=25)
        self.controls.value_changed_callback = self.select_control

    def select_control(self, widget):
        selected_values = self.controls.entry_widget.get_selected_objects()
        if len(selected_values):
            if selected_values[0] == self.text_controls_begin_session:
                self.action_on_begin_session()
            if selected_values[0] == self.text_controls_start_problem:
                self.action_on_start_problem()
            if selected_values[0] == self.text_controls_done:
                self.action_on_done()
            if selected_values[0] == self.text_controls_exit:
                self.action_on_exit()

    def action_on_begin_session(self):
        self.session_box = self.add(npyscreen.BoxTitle, relx=40, rely=2, max_height=15, max_width=30)
        self.session_box.values = [get_random_problem()]
        self.session_box.display()

        # print(dir(self.controls.entry_widget._contained_widgets))
        self.controls.edit()
        self.controls.entry_widget.clear()
        self.controls.values = self.controls_on_begin_session
        # self.controls.update(clear=True)
        self.controls.display()

    def action_on_start_problem(self):
        # self.session_box.values = [get_random_problem()]
        # self.session_box.display()

        self.controls.values = self.controls_on_start_problem
        self.controls.display()

    def action_on_done(self):
        # self.session_box = self.add(npyscreen.BoxTitle, relx=20, rely=2, max_height=15, max_width=30)
        # self.session_box.values = [get_random_problem()]
        # self.session_box.display()

        self.controls.values = self.controls_on_done
        self.controls.display()

    def action_on_exit(self):
        self.parentApp.setNextForm(None)
        self.editing = False
        self.parentApp.switchFormNow()
        exit(0)


class App(npyscreen.StandardApp):
    def onStart(self):
        self.addForm("MAIN", MainForm, name="Checker")


def start():
    my_app = App()
    my_app.run()


if __name__ == "__main__":
    start()
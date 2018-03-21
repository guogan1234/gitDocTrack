#-------------------------------------------------
#
# Project created by QtCreator 2018-03-13T14:32:34
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = MutiThreadCallGlobalFunc
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    testthread.cpp

HEADERS  += widget.h \
    testthread.h

FORMS    += widget.ui

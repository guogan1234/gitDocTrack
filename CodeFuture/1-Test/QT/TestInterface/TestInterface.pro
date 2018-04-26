#-------------------------------------------------
#
# Project created by QtCreator 2018-03-27T14:47:35
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = TestInterface
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    interface.cpp \
    testclass.cpp

HEADERS  += widget.h \
    interface.h \
    testclass.h

FORMS    += widget.ui

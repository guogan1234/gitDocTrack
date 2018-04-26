#-------------------------------------------------
#
# Project created by QtCreator 2018-01-03T13:27:50
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = viewSceneItemDemo
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    eclipseitem.cpp \
    myscene.cpp

HEADERS  += widget.h \
    eclipseitem.h \
    myscene.h

FORMS    += widget.ui

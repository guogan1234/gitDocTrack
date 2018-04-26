#-------------------------------------------------
#
# Project created by QtCreator 2017-07-24T15:30:57
#
#-------------------------------------------------

QT       += core gui network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = webManagerTest
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    downloadnetworkmanager.cpp

HEADERS  += mainwindow.h \
    downloadnetworkmanager.h

FORMS    += mainwindow.ui

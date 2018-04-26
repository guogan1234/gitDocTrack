#-------------------------------------------------
#
# Project created by QtCreator 2018-03-21T16:47:37
#
#-------------------------------------------------

QT       += core network

QT       -= gui

TARGET = TestServer
CONFIG   += console
CONFIG   -= app_bundle

TEMPLATE = app


SOURCES += main.cpp \
    testtcpserver.cpp

HEADERS += \
    testtcpserver.h

#-------------------------------------------------
#
# Project created by QtCreator 2018-01-26T13:15:49
#
#-------------------------------------------------

QT       += core network

QT       -= gui

TARGET = HttpApp
CONFIG   += console
CONFIG   -= app_bundle

TEMPLATE = app


SOURCES += main.cpp \
    hellocontroller.cpp \
    listdatacontroller.cpp \
    requestmapper.cpp \
    logincontroller.cpp

HEADERS += \
    hellocontroller.h \
    listdatacontroller.h \
    requestmapper.h \
    logincontroller.h

include(../httpserver/httpserver.pri)

#-------------------------------------------------
#
# Project created by QtCreator 2018-01-23T16:16:36
#
#-------------------------------------------------

TARGET = shareDll
TEMPLATE = lib

DEFINES += SHAREDLL_LIBRARY

SOURCES += sharedll.cpp

HEADERS += sharedll.h\
        sharedll_global.h

DESTDIR = ../MyShareDll

unix {
    target.path = /usr/lib
    INSTALLS += target
}

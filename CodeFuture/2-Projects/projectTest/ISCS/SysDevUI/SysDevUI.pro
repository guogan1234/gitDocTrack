#-------------------------------------------------
#
# Project created by QtCreator 2018-03-27T08:32:19
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = SysDevUI
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    isysinfofinder.cpp \
    sysinfofinderwinimpl.cpp

HEADERS  += widget.h \
    isysinfofinder.h \
    sysinfofinderwinimpl.h \
    SysInfo.h

FORMS    += widget.ui

LIBS += "F:/testWinDlls/psapi.lib"

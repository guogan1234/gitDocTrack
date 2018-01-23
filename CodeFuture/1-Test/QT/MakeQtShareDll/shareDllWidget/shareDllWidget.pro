#-------------------------------------------------
#
# Project created by QtCreator 2018-01-23T17:21:17
#
#-------------------------------------------------

QT       += widgets

TARGET = shareDllWidget
TEMPLATE = lib

DEFINES += SHAREDLLWIDGET_LIBRARY

SOURCES += sharedllwidget.cpp \
    myqtsharedllwidget.cpp

HEADERS += sharedllwidget.h\
        sharedllwidget_global.h \
    myqtsharedllwidget.h

unix {
    target.path = /usr/lib
    INSTALLS += target
}

FORMS += \
    myqtsharedllwidget.ui

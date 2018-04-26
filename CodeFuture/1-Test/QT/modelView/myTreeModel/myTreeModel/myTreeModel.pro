#-------------------------------------------------
#
# Project created by QtCreator 2018-01-08T13:05:53
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = myTreeModel
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    mytreemodel.cpp \
    mytreemodel2.cpp \
    mytreeitem2.cpp

HEADERS  += widget.h \
    mytreemodel.h \
    info.h \
    mytreemodel2.h \
    mytreeitem2.h

FORMS    += widget.ui

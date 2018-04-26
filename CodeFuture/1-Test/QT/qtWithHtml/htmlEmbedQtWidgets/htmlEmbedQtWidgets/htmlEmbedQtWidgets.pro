#-------------------------------------------------
#
# Project created by QtCreator 2017-12-28T10:56:10
#
#-------------------------------------------------

QT       += core gui webkitwidgets

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = htmlEmbedQtWidgets
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    form.cpp \
    mywebpage.cpp

HEADERS  += widget.h \
    form.h \
    mywebpage.h

FORMS    += widget.ui \
    form.ui

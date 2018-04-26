#-------------------------------------------------
#
# Project created by QtCreator 2017-12-19T14:06:42
#
#-------------------------------------------------

QT       += core gui webkit

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = qtWithHtml
TEMPLATE = app

# The following define makes your compiler emit warnings if you use
# any feature of Qt which as been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0


SOURCES += main.cpp\
        widget.cpp\
        ui/ui.cpp
HEADERS  += widget.h \
    ui/ui.h

FORMS    += widget.ui

LIBS += D:\Qt-MinGW-OpenGL\Qt5.2.1\5.2.1\mingw48_32\lib\libQt5WebKitWidgetsd.a

RESOURCES += \
    res.qrc \
    res.qrc

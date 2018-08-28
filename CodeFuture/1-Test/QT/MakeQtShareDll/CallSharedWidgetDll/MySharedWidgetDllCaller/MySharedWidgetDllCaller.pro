#-------------------------------------------------
#
# Project created by QtCreator 2018-08-16T16:29:33
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = MySharedWidgetDllCaller
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
        widget.cpp

HEADERS  += widget.h

FORMS    += widget.ui

INCLUDEPATH += $$PWD/libs/MySharedWidgetDll/include

#MinGW生成动态库，会生成动态库导入库libxxx.a文件和xxx.dll文件(只添加xxx.dll的路径就可以成功运行)
LIBS += -L$$PWD/libs/MySharedWidgetDll/lib -lMySharedWidgetDll

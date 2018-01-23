#-------------------------------------------------
#
# Project created by QtCreator 2018-01-23T16:39:29
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = CallMyQtShareDll
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp

HEADERS  += widget.h

FORMS    += widget.ui

INCLUDEPATH += ./lib/lib2/1-test/include
#链接的是MinGW生成的动态库导入库libshareDll.a
#MinGW生成动态库，会生成动态库导入库dll.a文件和.dll文件
#qmake也能识别相对路径
#qmake也能识别带-test的易混淆的符号(与参数-l等混淆)
#qmake也能识别带关键字(如QT)的字符串路径
LIBS += -L./lib/lib2/1-test/libs/QT -lshareDllWidget

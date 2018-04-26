#-------------------------------------------------
#
# Project created by QtCreator 2018-03-24T16:23:19
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = SystemUI
TEMPLATE = app


SOURCES += main.cpp\
        widget.cpp \
    syswindowimpl.cpp

HEADERS  += widget.h \
    syswindowimpl.h

FORMS    += widget.ui

#Error
#LIBS += -L F:\testWinDlls -lPsapi.dll

#OK，加载QT改写的源码库文件libxxx.a
#LIBS += "F:/testWinDlls/libpsapi.a"
#OK
#LIBS += -LF:/testWinDlls -lpsapi
#Error
#LIBS += -LF:/testWinDlls -llibpsapi.a
#Error，静态库(-l链接)也不能带.a(xxx等同于libxxx.a)
#LIBS += -LF:/testWinDlls -lpsapi.a
#Error
#LIBS += "./libpsapi.a"


#OK，加载xxx.lib(windows动态导入库，非静态库)
#LIBS += -LF:/testWinDlls -lpsapi
#Error，双L链接中，-l链接不能带库文件后缀(猜测)
#LIBS += -LF:/testWinDlls -lpsapi.Lib

#OK，加载xxx.lib(windows动态导入库，非静态库)
#LIBS += "F:/testWinDlls/psapi.Lib"
#OK-为项目工程pro文件路径($$PWD)
#LIBS += $$PWD/psapi.Lib
#OK
#LIBS += F:/testWinDlls/psapi.Lib
#Error
#LIBS += ./psapi.Lib

win32:LIBS += F:/testWinDlls/psapi.Lib
unix:LIBS += $$PWD/libpsapi.so

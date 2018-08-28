#-------------------------------------------------
#
# Project created by QtCreator 2018-08-03T10:23:29
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = QtProFileAndQMake
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

RESOURCES += \
    resource.qrc

#编译生成的可执行文件的生成目录路径
#DESTDIR += ./run

#使用自定义变量存储字符串值,并用QT支持的$$调用变量
#MY_DESTDIR += ./destRun
#带空格的字符串值，需使用双引号""
MY_DESTDIR += "./run run"
DESTDIR += $$MY_DESTDIR

#测试获取环境变量的值，用$$()
#message($$(PWD))   #failed - 没有PWD这个系统环境变量，这是qmake的变量
#TEST_PWD = $$(path)   #ok - 在windows下测试的，有path环境变量，所有ok
message(TEST_PWD is $$TEST_PWD)
#获取qmake默认的变量 或 自定义变量的值
#message($$PWD)  #ok - PWD是qmake默认的变量
GUO = dmxy  #ok - 获取自定义变量的值
message($$GUO)
gan = abcd  #ok - 自定义的变量通常是用大写，但也可以用小写
message($$gan)

#把pro文件中的变量传递到Makefile中
PASS_PWD = $(PWD)   #failed

#测试使用qmake的域
win32{
    message(this is win32)
}
win32{
    CONFIG(debug,debug|release){
        message(this is win32 debug)
    }
}
win32|macx{
    message(this is win32 or macx)
}
#测试域的设置和使用
CONFIG += GG
GG{
    message(can config custom GG)
}else{
    message(can not config custom GG)
}

#测试install安装配置
place.path = D:/installQT
message($$DESTDIR)
#ok - 成功使多个路径生效
place.files = ./../build-QtProFileAndQMake-Desktop_Qt_5_8_0_MinGW_32bit-Debug/ui* $$DESTDIR/*
#ok - 成功使多个路径生效
#place.files += ./../build-QtProFileAndQMake-Desktop_Qt_5_8_0_MinGW_32bit-Debug/ui*
#place.files += $$DESTDIR/*
#ok - 用qmake换行符，成功使多个路径生效
#place.files += ./../build-QtProFileAndQMake-Desktop_Qt_5_8_0_MinGW_32bit-Debug/ui*\
#    $$DESTDIR/*
INSTALLS += place

message(pro file end!)

#获取qmake属性，用$$[]
message(Qt version: $$[QT_VERSION])
message(Qt is installed in $$[QT_INSTALL_PREFIX])
message(Qt resources can be found in the following locations:)
message(Documentation: $$[QT_INSTALL_DOCS])
message(Header files: $$[QT_INSTALL_HEADERS])
message(Libraries: $$[QT_INSTALL_LIBS])
message(Binary files (executables): $$[QT_INSTALL_BINS])
message(Plugins: $$[QT_INSTALL_PLUGINS])
message(Data files: $$[QT_INSTALL_DATA])
message(Translation files: $$[QT_INSTALL_TRANSLATIONS])
message(Settings: $$[QT_INSTALL_CONFIGURATION])
message(Examples: $$[QT_INSTALL_EXAMPLES])

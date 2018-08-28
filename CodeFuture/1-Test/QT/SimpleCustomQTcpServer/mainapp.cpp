#include "mainapp.h"
#include <QHostAddress>

MainApp::MainApp(QObject *parent) : QObject(parent)
{
    init();
}

void MainApp::start()
{
    bool bListen = false;
    bListen = server->listen(QHostAddress::Any,12345);
    if(bListen){
        qDebug("server listen ok");
    }else {
        qDebug("server listen failed");
    }
}

void MainApp::init()
{
    initParam();
}

void MainApp::initParam()
{
    server = new MyTcpServer();
}

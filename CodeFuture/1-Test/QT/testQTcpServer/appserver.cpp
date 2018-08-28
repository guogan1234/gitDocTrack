#include "appserver.h"
#include <QHostAddress>
#include <QTcpSocket>

AppServer::AppServer(QObject *parent) : QObject(parent)
{
    init();
}

void AppServer::start()
{
    qDebug("start");
    bool b = server->listen(QHostAddress::Any,12345);
    if(b){
        qDebug("tcpServer listen ok!");
    }else {
        qDebug("tcpServer listen failed!");
    }

    thread->start();
}

void AppServer::startMyServer()
{
    qDebug("AppServer::startMyServer");
    timer = new QTimer();
    connect(timer,SIGNAL(timeout()),this,SLOT(timeoutSlot()));
    timer->start(1000*60);
    myServer = new MyTcpServer();
    myServer->listen(QHostAddress::Any,12345);
    qDebug("AppServer::startMyServer normal exit!");
}

void AppServer::newConnectionSlot()
{
    qDebug("AppServer::newConnectionSlot");
    QTcpSocket* socket = NULL;
    if(server->hasPendingConnections()){
        socket = server->nextPendingConnection();
        socket->write("hello");
        QObject* parent = socket->parent();
        if(parent){
            qDebug("className - %s",parent->metaObject()->className());
        }else {
            qDebug("parent is NULL");
        }
//        socket->moveToThread(this->thread);//throw:Can not move objects with a parent
//        socket->setParent(this->thread);//can change object's parent
//        qDebug("change - %s",socket->parent()->metaObject()->className());
        socket->setParent(NULL);
        socket->moveToThread(this->thread);
    }else {
        qDebug("no pending connection");
    }
    qDebug("AppServer::newConnectionSlot normal exit.");
}

void AppServer::timeoutSlot()
{
    qDebug("timeout");
    myServer->getPendingConnection();
}

void AppServer::init()
{
    initParam();
}

void AppServer::initParam()
{
    server = new QTcpServer();
    connect(this->server,SIGNAL(newConnection()),this,SLOT(newConnectionSlot()));
    thread = new MyThread();
}

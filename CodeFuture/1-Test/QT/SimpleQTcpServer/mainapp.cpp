#include "mainapp.h"
#include <QHostAddress>
#include <QDebug>

MainApp::MainApp(QObject *parent) : QObject(parent)
{
    init();
}

void MainApp::start()
{
    thread->start();

    server = new QTcpServer();
    connect(server,SIGNAL(newConnection()),this,SLOT(newConnectionSlot()));
    bool b = server->listen(QHostAddress::Any,12345);
    if(b){
        qDebug("server listen ok!");
    }
}

void MainApp::acceptErrorSlot(QAbstractSocket::SocketError socketError)
{

}

void MainApp::newConnectionSlot()
{
    qDebug("MainApp::newConnectionSlot");
    if(server->hasPendingConnections()){
        QTcpSocket* socket = NULL;
        //nextPendingConnection()方法返回的QTcpSocket默认以监听QTcpServer为parent，故不能移动到其他线程
        socket = server->nextPendingConnection();
        qDebug()<<"peerAddr - "<<socket->peerAddress().toString()<<socket->peerPort();
        qDebug()<<"localAddr - "<<socket->localAddress().toString()<<socket->localPort();
        connect(socket,SIGNAL(connected()),this,SLOT(connectedSlot()));
        connect(socket,SIGNAL(bytesWritten(qint64)),this,SLOT(bytesWrittenSlot(qint64)));
        connect(socket,SIGNAL(disconnected()),this,SLOT(disconnectedSlot()));
        connect(socket,SIGNAL(error(QAbstractSocket::SocketError)),this,SLOT(errorSlot(QAbstractSocket::SocketError)));
        connect(socket,SIGNAL(readyRead()),this,SLOT(readyReadSlot()));
//        socket->moveToThread(thread);//若socket不移动到发送数据的线程中，第一次发送会出现警告(parent不在同一线程)，但不影响数据发送(数据仍然正常发送到接收端)

        socketList->append(socket);
    }
}

void MainApp::connectedSlot()
{
    qDebug("MainApp::connectedSlot");
    QObject* senderObject = sender();
    qDebug("sender - %s",senderObject->metaObject()->className());
    QTcpSocket* socket = static_cast<QTcpSocket*>(senderObject);
    qDebug()<<"peer - "<<socket->peerAddress().toString()<<socket->peerPort();
    qDebug("------------");
}

void MainApp::disconnectedSlot()
{

}

void MainApp::errorSlot(QAbstractSocket::SocketError socketError)
{

}

void MainApp::bytesWrittenSlot(qint64 bytes)
{
    qDebug("MainApp::bytesWrittenSlot - %ld",bytes);
    QObject* senderObject = sender();
    qDebug("sender - %s",senderObject->metaObject()->className());
    QTcpSocket* socket = static_cast<QTcpSocket*>(senderObject);
    qDebug()<<"peer - "<<socket->peerAddress().toString()<<socket->peerPort();
    qDebug("------------");
}

void MainApp::readyReadSlot()
{
    qDebug("MainApp::readyReadSlot");
    QObject* senderObject = sender();
    qDebug("sender - %s",senderObject->metaObject()->className());
    QTcpSocket* socket = static_cast<QTcpSocket*>(senderObject);
    qDebug()<<"peer - "<<socket->peerAddress().toString()<<socket->peerPort();
    qDebug("------------");
    QByteArray ba;
    ba = socket->readAll();
    qDebug()<<"recv data - "<<ba;
    CommandMsg* msg = new CommandMsg();
    msg->clientSocket = socket;
    msg->command = ba;
    commandMutex->lock();
    commandList->append(msg);
    commandMutex->unlock();
    qDebug("normal exit MainApp::readyReadSlot");
}

void MainApp::init()
{
    initParam();
}

void MainApp::initParam()
{
    socketList = new QList<QTcpSocket*>();
    commandList = new QList<CommandMsg*>();
    commandMutex = new QMutex();
    thread = new RecvDataHandleThread();
    thread->setDataPool(commandList,commandMutex);
}

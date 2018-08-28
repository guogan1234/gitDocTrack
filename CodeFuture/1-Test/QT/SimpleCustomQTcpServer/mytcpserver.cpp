#include "mytcpserver.h"
#include "mytcpthread.h"

MyTcpServer::MyTcpServer(QObject *parent) : QTcpServer(parent)
{
    init();
}

void MyTcpServer::incomingConnection(qintptr socketDescriptor)
{
    qDebug("MyTcpServer::incomingConnection");
    MyTcpThread* thread = new MyTcpThread();
    thread->setDataThread(dataThread);
    thread->setSocketDescriptor(socketDescriptor);
    thread->start();
}

void MyTcpServer::acceptErrorSlot(QAbstractSocket::SocketError socketError)
{

}

void MyTcpServer::newConnectionSlot()
{

}

void MyTcpServer::init()
{
    initParam();
}

void MyTcpServer::initParam()
{
    dataThread = new RecvDataHandleThread();
    dataThread->start();
}

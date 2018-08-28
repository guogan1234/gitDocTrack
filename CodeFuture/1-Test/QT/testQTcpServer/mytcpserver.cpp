#include "mytcpserver.h"
#include <QTcpSocket>
#include <QDebug>

MyTcpServer::MyTcpServer(QObject *parent) : QTcpServer(parent)
{

}

int count = 0;
void MyTcpServer::incomingConnection(qintptr socketDescriptor)
{
    qDebug("MyTcpServer::incomingConnection");
    QTcpSocket* socket = new QTcpSocket();
    QString name = QString("socket_%1").arg(count);
    qDebug()<<"name - "<<name;
    qDebug("addr - %p",socket);
    socket->setObjectName(name);
    socket->setSocketDescriptor(socketDescriptor);
    addPendingConnection(socket);

    count++;
    qDebug("MyTcpServer::incomingConnection normal exit!");
}

void MyTcpServer::getPendingConnection()
{
    qDebug("MyTcpServer::getPendingConnection");
    while (hasPendingConnections()) {
        QTcpSocket* socket = nextPendingConnection();
        qDebug()<<"socket name - "<<socket->objectName();
        qDebug("addr - %p",socket);
    }

    qDebug("MyTcpServer::getPendingConnection normal exit!");
}

#include "socketmanager.h"
#include <QHostAddress>
#include <QDebug>

SocketManager::SocketManager(QObject *parent) : QObject(parent)
{

}

SocketManager::SocketManager(qintptr descriptor, QObject *parent):QObject(parent)
{
    socket = new QTcpSocket();
    bool bSocket = false;
    bSocket = socket->setSocketDescriptor(descriptor);
    if(bSocket){
        connect(socket,SIGNAL(connected()),this,SLOT(connectedSlot()));
        connect(socket,SIGNAL(disconnected()),this,SLOT(disconnectedSlot()));
        connect(socket,SIGNAL(error(QAbstractSocket::SocketError)),this,SLOT(errorSlot(QAbstractSocket::SocketError)));
        connect(socket,SIGNAL(bytesWritten(qint64)),this,SLOT(bytesWrittenSlot(qint64)));
        connect(socket,SIGNAL(readyRead()),this,SLOT(readyReadSlot()));
        qDebug("socket create and setSocketDescriptor ok!");
    }else {
        qDebug("socket create,but setSocketDescriptor failed!");
    }
}

void SocketManager::connectedSlot()
{
    qDebug("SocketManager::connectedSlot");
}

void SocketManager::disconnectedSlot()
{
    qDebug("SocketManager::disconnectedSlot");
    emit socketDisconnectedSig();
}

void SocketManager::errorSlot(QAbstractSocket::SocketError socketError)
{

}

void SocketManager::hostFoundSlot()
{

}

void SocketManager::stateChangedSlot(QAbstractSocket::SocketState socketState)
{

}

void SocketManager::aboutToCloseSlot()
{

}

void SocketManager::bytesWrittenSlot(qint64 bytes)
{
    qDebug("SocketManager::bytesWrittenSlot - %ld ,%lld",bytes,bytes);
}

void SocketManager::readyReadSlot()
{
    qDebug("---+++---");
    qDebug("SocketManager::readyReadSlot");
    QObject* senderObject = this->sender();
    QTcpSocket* client = static_cast<QTcpSocket*>(senderObject);
    qDebug("socket - %p ,%p",this->socket,client);
    qDebug()<<"peer - "<<socket->peerAddress().toString()<<socket->peerPort();
    QByteArray msg;
    msg = client->readAll();
    //信号发送是异步触发，不会阻塞
    emit recvDataSignal(msg,client);
    qDebug("---SocketManager::readyReadSlot exit---");
    qDebug("---+++---");
}

void SocketManager::destroyedSlot(QObject *obj)
{

}

void SocketManager::objectNameChangedSlot(const QString &objectName)
{

}

void SocketManager::writeDataSlot(QByteArray data, QTcpSocket *client)
{
    qDebug("---+++---");
    qDebug("SocketManager::writeDataSlot - %p",client);
    qDebug("manage socket - %p",this->socket);
    if(client){
        QString clientAddr = client->peerAddress().toString();
        quint16 clientPort = client->peerPort();
        qDebug()<<"client - "<<client->peerAddress().toString()<<client->peerPort();
        QString socketAddr = socket->peerAddress().toString();
        quint16 socketPort = socket->peerPort();
        qDebug()<<"manage socket - "<<socket->peerAddress().toString()<<socket->peerPort();
        if(clientAddr == socketAddr && clientPort == socketPort){
            qDebug("find the same socket connection");
            socket->write(data);
        }else {
            qDebug("socket not matched");
        }
    }
    qDebug("---SocketManager::writeDataSlot exit---");
    qDebug("---+++---");
}

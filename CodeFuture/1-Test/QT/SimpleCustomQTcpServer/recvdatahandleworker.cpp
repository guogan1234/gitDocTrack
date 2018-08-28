#include "recvdatahandleworker.h"
#include <QHostAddress>
#include <QDebug>

RecvDataHandleWorker::RecvDataHandleWorker(QObject *parent) : QObject(parent)
{

}

void RecvDataHandleWorker::recvDataSlot(QByteArray msg, QTcpSocket *client)
{
    qDebug("---+++---");
    qDebug("RecvDataHandleWorker::recvDataSlot - %p",client);
    if(client){
        qDebug()<<"peer - "<<client->peerAddress().toString()<<client->peerPort();
        emit msgHandledSignal(msg,client);
    }
    qDebug("---RecvDataHandleWorker::recvDataSlot exit---");
    qDebug("---+++---");
}

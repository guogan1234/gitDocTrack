#include "recvdatahandlethread.h"

RecvDataHandleThread::RecvDataHandleThread(QObject *parent) : QThread(parent)
{

}

void RecvDataHandleThread::setDataPool(QList<CommandMsg *> *list, QMutex *mutex)
{
    dataList = list;
    dataMutex = mutex;
}

void RecvDataHandleThread::run()
{
    qDebug("RecvDataHandleThread::run");
    while (true) {
        CommandMsg* msg = NULL;
        dataMutex->lock();
//        qDebug("list len - %d",dataList->size());
        if(!dataList->isEmpty()){
           msg = dataList->takeFirst();
        }
        dataMutex->unlock();
        if(!msg){
            continue;
        }
        QTcpSocket* socket = msg->clientSocket;
        if(socket && socket->state()==QAbstractSocket::ConnectedState){
            socket->write(msg->command);
            socket->flush();
        }
    }
}

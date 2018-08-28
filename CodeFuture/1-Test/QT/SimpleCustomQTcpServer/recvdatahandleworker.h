#ifndef RECVDATAHANDLEWORKER_H
#define RECVDATAHANDLEWORKER_H

#include <QObject>
#include <QTcpSocket>

class RecvDataHandleWorker : public QObject
{
        Q_OBJECT
    public:
        explicit RecvDataHandleWorker(QObject *parent = 0);

    signals:
        void msgHandledSignal(QByteArray writeData,QTcpSocket* client);
    public slots:
        void recvDataSlot(QByteArray msg,QTcpSocket* client);
};

#endif // RECVDATAHANDLEWORKER_H

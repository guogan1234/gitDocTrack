#ifndef MAINAPP_H
#define MAINAPP_H

#include <QObject>
#include <QTcpServer>
#include <QTcpSocket>
#include <QMutex>
#include "commandmsg.h"
#include "recvdatahandlethread.h"

class MainApp : public QObject
{
        Q_OBJECT
    public:
        explicit MainApp(QObject *parent = 0);

        void start();
    signals:

    public slots:
        //QTcpServer
        void acceptErrorSlot(QAbstractSocket::SocketError socketError);
        void newConnectionSlot();

        //QTcpSocket
        void connectedSlot();
        void disconnectedSlot();
        void errorSlot(QAbstractSocket::SocketError socketError);
        void bytesWrittenSlot(qint64 bytes);
        void readyReadSlot();
    private:
        QTcpServer* server;
        RecvDataHandleThread* thread;

        QList<QTcpSocket*> * socketList;
        void init();
        void initParam();

        //
        QList<CommandMsg*> * commandList;
        QMutex* commandMutex;
};

#endif // MAINAPP_H

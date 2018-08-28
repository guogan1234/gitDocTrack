#ifndef MYTCPSERVER_H
#define MYTCPSERVER_H

#include <QTcpServer>
#include "recvdatahandlethread.h"

class MyTcpServer : public QTcpServer
{
        Q_OBJECT
    public:
        explicit MyTcpServer(QObject *parent = 0);

    protected:
        virtual void incomingConnection(qintptr socketDescriptor);
    signals:       

    public slots:
        void acceptErrorSlot(QAbstractSocket::SocketError socketError);
        void newConnectionSlot();

    private:
        RecvDataHandleThread* dataThread;

        void init();
        void initParam();
};

#endif // MYTCPSERVER_H

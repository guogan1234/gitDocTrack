#ifndef MYTCPTHREAD_H
#define MYTCPTHREAD_H

#include <QThread>
#include "socketmanager.h"
#include "recvdatahandlethread.h"

class MyTcpThread : public QThread
{
        Q_OBJECT
    public:
        explicit MyTcpThread(QObject *parent = 0);

        void setSocketDescriptor(qintptr descriptor);
        void setDataThread(RecvDataHandleThread* thread);
        void run();
    signals:

    public slots:
        void myDeleteLaterSlot();
    private:
        SocketManager* socketManager;
        RecvDataHandleThread* dataThread;
        RecvDataHandleWorker* dataWorker;

        qintptr socketDescriptor;

        bool getDataHandleWorker();
};

#endif // MYTCPTHREAD_H

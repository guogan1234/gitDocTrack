#ifndef RECVDATAHANDLETHREAD_H
#define RECVDATAHANDLETHREAD_H

#include <QThread>
#include "recvdatahandleworker.h"

class RecvDataHandleThread : public QThread
{
        Q_OBJECT
    public:
        explicit RecvDataHandleThread(QObject *parent = 0);

        RecvDataHandleWorker* getWorker();
        void run();
    signals:

    public slots:

    private:
        RecvDataHandleWorker* worker;
        bool bRun;

        void init();
        void initParam();
};

#endif // RECVDATAHANDLETHREAD_H

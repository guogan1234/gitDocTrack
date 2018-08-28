#ifndef RECVDATAHANDLETHREAD_H
#define RECVDATAHANDLETHREAD_H

#include <QThread>
#include <QMutex>
#include "commandmsg.h"

class RecvDataHandleThread : public QThread
{
        Q_OBJECT
    public:
        explicit RecvDataHandleThread(QObject *parent = 0);

        void setDataPool(QList<CommandMsg*> * list,QMutex* mutex);
        void run();
    signals:

    public slots:

    private:
        QList<CommandMsg*> * dataList;
        QMutex* dataMutex;
};

#endif // RECVDATAHANDLETHREAD_H

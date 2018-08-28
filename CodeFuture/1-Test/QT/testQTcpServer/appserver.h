#ifndef APPSERVER_H
#define APPSERVER_H

#include <QObject>
#include <QTcpServer>
#include <QTimer>
#include "mythread.h"
#include "mytcpserver.h"

class AppServer : public QObject
{
        Q_OBJECT
    public:
        explicit AppServer(QObject *parent = 0);

        void start();
        void startMyServer();
    signals:

    public slots:
        void newConnectionSlot();
        void timeoutSlot();
    private:
        QTcpServer* server;
        MyThread* thread;

        QTimer* timer;
        MyTcpServer* myServer;

        void init();
        void initParam();
};

#endif // APPSERVER_H

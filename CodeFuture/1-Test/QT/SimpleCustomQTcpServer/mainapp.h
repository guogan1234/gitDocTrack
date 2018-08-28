#ifndef MAINAPP_H
#define MAINAPP_H

#include <QObject>
#include "mytcpserver.h"

class MainApp : public QObject
{
        Q_OBJECT
    public:
        explicit MainApp(QObject *parent = 0);

        void start();
    signals:

    public slots:

    private:
        MyTcpServer* server;

        void init();
        void initParam();
};

#endif // MAINAPP_H

#ifndef MYTCPSERVER_H
#define MYTCPSERVER_H

#include <QObject>
#include <QTcpServer>

class MyTcpServer : public QTcpServer
{
        Q_OBJECT
    public:
        explicit MyTcpServer(QObject *parent = 0);

        void incomingConnection(qintptr socketDescriptor);
        void getPendingConnection();
    signals:

    public slots:
};

#endif // MYTCPSERVER_H

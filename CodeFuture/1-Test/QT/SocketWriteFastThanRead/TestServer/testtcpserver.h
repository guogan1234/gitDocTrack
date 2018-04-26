#ifndef TESTTCPSERVER_H
#define TESTTCPSERVER_H

#include <QTcpServer>
#include <QTcpSocket>

class TestTcpserver : public QTcpServer
{
        Q_OBJECT
    public:
        explicit TestTcpserver(QObject *parent = 0);

        void incomingConnection(qintptr sock);
    signals:

    public slots:
        void readMessage();
        void stateChangedSlot(QAbstractSocket::SocketState states);

    private:
        QTcpSocket* socket;

        void writeToFile(char* str);
};

#endif // TESTTCPSERVER_H

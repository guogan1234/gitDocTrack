#ifndef TESTCLASS_H
#define TESTCLASS_H

#include <QObject>
#include <QTcpSocket>

class TestClass : public QObject
{
        Q_OBJECT
    public:
        explicit TestClass(QObject *parent = 0);

        void Init();
    signals:

    public slots:
        void stateChangedSlot(QAbstractSocket::SocketState stat);
        void errorSlot(QAbstractSocket::SocketError socketError);
        void connectedSlot();
    private:
        QTcpSocket* socket;

        void writeToFile(char* str);
};

#endif // TESTCLASS_H

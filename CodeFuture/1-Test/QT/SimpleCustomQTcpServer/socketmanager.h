#ifndef SOCKETMANAGER_H
#define SOCKETMANAGER_H

#include <QObject>
#include <QTcpSocket>

class SocketManager : public QObject
{
        Q_OBJECT
    public:
        explicit SocketManager(QObject *parent = 0);
        SocketManager(qintptr descriptor,QObject *parent = 0);

    signals:
        void recvDataSignal(QByteArray msg,QTcpSocket* socket);
        void socketDisconnectedSig();
    public slots:
        //QAbstractSocket
        void connectedSlot();
        void disconnectedSlot();
        void errorSlot(QAbstractSocket::SocketError socketError);
        void hostFoundSlot();
        void stateChangedSlot(QAbstractSocket::SocketState socketState);
        //QIODevice
        void aboutToCloseSlot();
        void bytesWrittenSlot(qint64 bytes);
        void readyReadSlot();
        //QObject
        void destroyedSlot(QObject *obj = Q_NULLPTR);
        void objectNameChangedSlot(const QString &objectName);

        //
        void writeDataSlot(QByteArray data,QTcpSocket* client);
    private:
        QTcpSocket* socket;
};

#endif // SOCKETMANAGER_H

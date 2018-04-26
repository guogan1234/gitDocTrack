#include "testtcpserver.h"
#include <QThread>
#include <QTcpSocket>
#include <QFile>

TestTcpserver::TestTcpserver(QObject *parent) :
    QTcpServer(parent)
{
    qDebug("111");
    listen(QHostAddress::Any,11111);
}

void TestTcpserver::incomingConnection(qintptr sock)
{
    qDebug("222");
    //1.
    socket = new QTcpSocket();
    qDebug("state--%d\n",socket->state());
    connect(socket,SIGNAL(stateChanged(QAbstractSocket::SocketState)),this,SLOT(stateChangedSlot(QAbstractSocket::SocketState)));
    socket->setSocketDescriptor(sock);
    connect(socket,SIGNAL(readyRead()),this,SLOT(readMessage()));
//    QByteArray ba = socket->read(1);
//    char ch[4];
//    memset(ch,0,4);
//    qint64 len = socket->read(ch,1);
//    qDebug("len--%d\n",len);
//    qDebug("@--%s\n",ch);

    //2.
//    QThread* t = new QThread();
//    t->start();
    qDebug("incomingConnection end!");
}

int count = 0;
void TestTcpserver::readMessage()
{
    count++;
    qDebug("readMessage...%d",count);

    char ch[4];
    memset(ch,'0',4);
    qint64 all = socket->bytesAvailable();
    qint64 len = 0;
    len = socket->read(ch,1);
    qDebug("len--%d\n",len);
    qDebug("all--%d",all);
    qDebug("@--%s",ch);
    qDebug("readMessage end!");
    writeToFile(ch);
}

void TestTcpserver::stateChangedSlot(QAbstractSocket::SocketState states)
{
    qDebug("stateChangedSlot...");
    qDebug("stateChanged--%d",socket->state());
}

void TestTcpserver::writeToFile(char *str)
{
    QFile file("./temp.txt");
    if (!file.open(QIODevice::WriteOnly | QIODevice::Append))
    {
        qDebug("open file failed!!!");
        return;
    }
    file.write(str);
    qDebug("writeToFile end!!!");
}

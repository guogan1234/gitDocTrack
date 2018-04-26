#include "testclass.h"

#include <QFile>

TestClass::TestClass(QObject *parent) :
    QObject(parent)
{
    Init();
}

void TestClass::Init()
{
    qDebug("Init");
    socket = new QTcpSocket();
    connect(socket,SIGNAL(connected()),this,SLOT(connectedSlot()));
    connect(socket,SIGNAL(stateChanged(QAbstractSocket::SocketState)),this,SLOT(stateChangedSlot(QAbstractSocket::SocketState)));
    connect(socket,SIGNAL(error(QAbstractSocket::SocketError)),this,SLOT(errorSlot(QAbstractSocket::SocketError)));
    qDebug("Init--%d\n",socket->state());
    socket->connectToHost("127.0.0.1",11111);
    qDebug("Init--%d\n",socket->state());
}

void TestClass::stateChangedSlot(QAbstractSocket::SocketState stat)
{
    qDebug("stateChangedSlot");
    qDebug("state--%d\n",stat);
}

void TestClass::errorSlot(QAbstractSocket::SocketError socketError)
{
    qDebug("errorSlot");
    qDebug("error--%d\n",socketError);
}

void TestClass::connectedSlot()
{
    qDebug("connectedSlot");
    char ch[1024];
    memset(ch,'a',1024);
    for(int i = 0;i<100;i++){
        qDebug("#--%d\n",i);
        qint64 len = 0;
        qDebug("@2--%d\n",socket->state());
//        if(socket->state() == QTcpSocket::ConnectedState){
//        ch = "123";//Error--貌似栈内指针指向只读区(不确定，未深入研究)
        //或者指针常量不能赋值为只读区指针，char[]为指针常量
        char* str = "123";
        strcpy(ch,str);
        len = socket->write(ch,1024);
        bool b = socket->flush();
        qDebug("b--%d\n",b);
        qDebug("len--%d\n",len);
//        }

        if(len == -1){
            qDebug("Error!!!");
        }
    }

}

void TestClass::writeToFile(char *str)
{
    QFile file("./temp.txt");
    if (!file.open(QIODevice::WriteOnly | QIODevice::Append))
    {
        qDebug("open file failed!!!");
        return;
    }
    file.write(str);
}

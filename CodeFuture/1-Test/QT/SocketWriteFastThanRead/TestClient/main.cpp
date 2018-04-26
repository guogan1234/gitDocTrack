#include <QCoreApplication>
#include <QTcpSocket>

#include <testclass.h>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

//    QTcpSocket* socket = new QTcpSocket();
////    socket->connectToHost("127.0.0.1",11111);
//    socket->connectToHost("172.168.1.66",11111);
//    qDebug("@--%d\n",socket->state());

//    qDebug("Connected!!!");
//    char ch[1024];
//    memset(ch,'a',1024);
//    for(int i = 0;i<1000;i++){
//        qDebug("#--%d\n",i);
//        qint64 len = 0;
//        qDebug("@2--%d\n",socket->state());
////        if(socket->state() == QTcpSocket::ConnectedState){
//        len = socket->write(ch,1024);
//        bool b = socket->flush();
//        qDebug("b--%d\n",b);
//        qDebug("len--%d\n",len);
////        }

//        if(len == -1){
//            qDebug("Error!!!");
//        }
//    }

    TestClass* cls = new TestClass();

    return a.exec();
}

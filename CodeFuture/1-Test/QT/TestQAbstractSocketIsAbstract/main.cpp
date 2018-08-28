#include <QCoreApplication>
#include <QAbstractSocket>
#include <QDebug>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    QAbstractSocket* test = new QAbstractSocket(QAbstractSocket::TcpSocket,NULL);
    qDebug()<<"#:"<<test;

    int len_long = sizeof(long);
    int len_qint64 = sizeof(qint64);
    qDebug()<<"#:"<<len_long<<len_qint64;

    return a.exec();
}

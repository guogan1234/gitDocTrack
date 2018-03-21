#include "testthread.h"

#include <QDebug>

TestThread::TestThread(QObject *parent) :
    QThread(parent)
{
}

void globalFunc(QString t_name){
    qDebug()<<"strat--"<<t_name;
    int total = 0;
    for(int i = 0;i<10000;i++){
        total += i;
    }
    qDebug()<<"end--"<<t_name;
}

void TestThread::run()
{
    QString name = this->objectName();
    globalFunc(name);
}

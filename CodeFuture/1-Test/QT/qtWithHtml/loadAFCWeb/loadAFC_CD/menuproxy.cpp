#include "menuproxy.h"
#include <QDebug>

QList<QString> list;

menuProxy::menuProxy(QObject *parent) :
    QObject(parent)
{
}

void menuProxy::jsCallTest_2()
{
    qDebug()<<"jsCallTest_2...";
}

void menuProxy::jsCallTest_public(QString str)
{
    qDebug()<<"jsCallTest_public...(len:"<<list.size()<<")";
    list.append(str);

    for(int i = 0;i<list.size();i++){
        qDebug()<<"@: "<<i<<" "<<str;
    }
}

void menuProxy::jsCallTest(QString str)
{
    qDebug()<<"jsCallTest...(len:"<<list.size()<<")";
    list.append(str);

    for(int i = 0;i<list.size();i++){
        qDebug()<<"@: "<<i<<" "<<str;
    }
}

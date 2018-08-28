#include "combineobject.h"
#include <QThread>
#include <QDebug>

CombineObject::CombineObject(QObject *parent) : QObject(parent)
{

}

void CombineObject::setSimplyObject(SimplyObject *obj)
{
    sObject = obj;

    connect(this,SIGNAL(testSignal()),sObject,SLOT(signalFun()));
}

void CombineObject::simplyFun()
{
    Qt::HANDLE handle = QThread::currentThreadId();
    long id = (long)handle;
    qDebug("threadId(CombineObject::simplyFun) - %ld ,%s",id,name);

    emit testSignal();
}

void CombineObject::signalFun()
{
    Qt::HANDLE handle = QThread::currentThreadId();
    long id = (long)handle;
    qDebug("threadId(CombineObject::signalFun) - %ld ,%s",id,name);

    //直接调用成员的方法时(启用)
    sObject->simplyFun();
    qDebug("----------");
    sObject->signalFun();

//    //信号触发成员的槽方法时(启用)
//    emit testSignal();
    qDebug("exit CombineObject::signalFun");
}

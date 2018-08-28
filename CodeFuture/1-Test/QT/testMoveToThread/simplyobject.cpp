#include "simplyobject.h"
#include <QThread>
#include <QDebug>

SimplyObject::SimplyObject(QObject *parent) : QObject(parent)
{

}

void SimplyObject::simplyFun()
{
    Qt::HANDLE handle = QThread::currentThreadId();
    long id = (long)handle;
    qDebug("threadId(simplyFun) - %ld ,%s",id,name);
}

void SimplyObject::signalFun()
{
    Qt::HANDLE handle = QThread::currentThreadId();
    long id = (long)handle;
    qDebug("threadId(signalFun) - %ld ,%s",id,name);

    simplyFun();
    qDebug("exit SimplyObject::signalFun");
}

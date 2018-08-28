#include "mythread.h"

MyThread::MyThread(QObject *parent) : QThread(parent)
{

}

void MyThread::run()
{
    Qt::HANDLE handle = QThread::currentThreadId();
    long id = (long)handle;
    qDebug("threadId(run) - %ld ,%s",id,name);

    exec();
}

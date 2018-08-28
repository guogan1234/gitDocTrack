#include "recvdatahandlethread.h"
#include <QDebug>

RecvDataHandleThread::RecvDataHandleThread(QObject *parent) : QThread(parent)
{
    init();
}

RecvDataHandleWorker *RecvDataHandleThread::getWorker()
{
    if(bRun){
        return worker;
    }else {
        return NULL;
    }
}

void RecvDataHandleThread::run()
{
    qDebug("RecvDataHandleThread::run");
    worker = new RecvDataHandleWorker();
    bRun = true;
    qDebug("worker - %p",worker);
    //经测试，exec()函数为阻塞函数，会执行事件循环，阻塞所在线程执行(不会阻塞其他线程执行)，直到此函数返回
    exec();
    qDebug("---RecvDataHandleThread::run exit---");
}

void RecvDataHandleThread::init()
{
    initParam();
}

void RecvDataHandleThread::initParam()
{
    bRun = false;
}

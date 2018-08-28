#include "appserver.h"

AppServer::AppServer(QObject *parent) : QObject(parent)
{

}

void AppServer::start()
{
    Qt::HANDLE handle = QThread::currentThreadId();
    long id = (long)handle;
    qDebug("mainId - %ld",id);

    thread = new MyThread();
    thread->name = "thread";
    thread->start();

    cThread = new MyThread();
    cThread->name = "cThread";
    cThread->start();

    qDebug("\t--- ---\t");
//    startTest_1();
    startTest_2();
}

void AppServer::startTest_1()
{
    //测试简单的QObject子类
    obj_1 = new SimplyObject();
    obj_1->name = "obj_1";
    connect(this,SIGNAL(testSignal()),obj_1,SLOT(signalFun()));
    obj_2 = new SimplyObject();
    obj_2->name = "obj_2";
    connect(this,SIGNAL(testSignal()),obj_2,SLOT(signalFun()));
    //测试结果：移入线程的对象，只有被信号触发的槽函数，才是在线程中执行的
    obj_2->moveToThread(thread);
//    thread->start();

    qDebug("-------1-------");
    obj_1->simplyFun();
    qDebug("-------2-------");
    obj_2->simplyFun();
    qDebug("-------3-------");
    obj_1->signalFun();
    qDebug("-------4-------");
    obj_2->signalFun();
    qDebug("-------5-------");

    emit testSignal();
}

void AppServer::startTest_2()
{
    //测试组合的QObject子类
    //测试结果表明：
    //1.可通过moveToThread()改变对象所在线程。
    //2.通过信号触发执行的槽函数，槽函数执行线程 为 对象当前所在线程(可通过moveToThread()改变)
    //3.普通调用执行的函数(包括槽函数)，执行线程 为 调用该函数的执行函数所在线程
    obj_1 = new SimplyObject();
    obj_1->name = "obj_1";

    obj_2 = new SimplyObject();
    obj_2->name = "obj_2";
    obj_2->moveToThread(thread);

    cObj_1 = new CombineObject();
    cObj_1->name = "cObj_1";
    cObj_1->setSimplyObject(obj_1);

    cObj_2 = new CombineObject();
    cObj_2->name = "cObj_2";
    cObj_2->setSimplyObject(obj_2);

//    //移入线程前，执行普通方法
//    qDebug("-------1-------");
//    cObj_1->simplyFun();
//    qDebug("-------2-------");
//    cObj_2->simplyFun();
//    qDebug("-------3-------");

    //移入线程后，执行信号触发的槽方法，再直接调用成员的方法
    connect(this,SIGNAL(testSignal()),cObj_1,SLOT(signalFun()));
//    connect(this,SIGNAL(testSignal()),cObj_2,SLOT(signalFun()));
    cObj_1->moveToThread(cThread);
    cObj_2->moveToThread(cThread);
    emit testSignal();

//    //移入线程后，执行信号触发的槽方法，再通过信号触发成员的槽方法
//    connect(this,SIGNAL(testSignal()),cObj_1,SLOT(signalFun()));
//    connect(this,SIGNAL(testSignal()),cObj_2,SLOT(signalFun()));
//    cObj_1->moveToThread(cThread);
//    cObj_2->moveToThread(cThread);
//    emit testSignal();
}

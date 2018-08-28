#include "mytcpthread.h"

MyTcpThread::MyTcpThread(QObject *parent) : QThread(parent)
{

}

void MyTcpThread::setSocketDescriptor(qintptr descriptor)
{
    socketDescriptor = descriptor;
}

void MyTcpThread::setDataThread(RecvDataHandleThread *thread)
{
    dataThread = thread;
}

void MyTcpThread::run()
{
    if(getDataHandleWorker()){
        qDebug("getDataHandleWorker success - %p",dataWorker);
        socketManager = new SocketManager(socketDescriptor);
        connect(socketManager,SIGNAL(recvDataSignal(QByteArray,QTcpSocket*)),dataWorker,SLOT(recvDataSlot(QByteArray,QTcpSocket*)));
        connect(dataWorker,SIGNAL(msgHandledSignal(QByteArray,QTcpSocket*)),socketManager,SLOT(writeDataSlot(QByteArray,QTcpSocket*)));
        //socket disconnected
        connect(socketManager,SIGNAL(socketDisconnectedSig()),this,SIGNAL(finished()));
//        connect(this,SIGNAL(finished()),this,SLOT(deleteLater()));//测试结果表明--线程未执行结束(即线程也未退出和销毁)
        connect(this,SIGNAL(finished()),this,SLOT(myDeleteLaterSlot()));
    }
    //经测试，exec()函数为阻塞函数，会执行事件循环，阻塞所在线程执行(不会阻塞其他线程执行)，直到此函数返回
    exec();
    qDebug("MyTcpThread::run exit - %p",this);
}

void MyTcpThread::myDeleteLaterSlot()
{
    qDebug("MyTcpThread::myDeleteLaterSlot - %p",this);
    //error--错误的删除线程对象顺序()
    //测试结果(QThread:Destroyed while thread is still running)：
    //1.程序崩溃，同时输出警告到控制台--QThread:Destroyed while thread is still running
    //原因如下：
    //1.QThread对象实例若销毁了，再调用exit()方法退出线程事件循环(其实为空指针调用exit()方法，故程序崩溃，同时缓冲的警告数据输出到控制台)
    //2.QThread对象实例为主线程对象。应该先退出线程事件循环，结束线程，再销毁对应的线程控制对象QThread实例
//    this->deleteLater();//删除线程对象
//    this->exit();//结束线程事件循环，结束线程执行

    //error--销毁对象线程QThread实例时，线程仍未执行结束(未完全退出线程事件循环或未完全结束线程执行)
    //测试结果如下(QThread:Destroyed while thread is still running)：
    //1.接收和发送数据不频繁时，连接断开再重连，不怎么导致程序崩溃
    //2.接收和发送数据较频繁时，连接断开再重连，会导致程序崩溃
    //原因可能如下：
    //1.收发数据较多时，数据处理比较频繁，主线程中exit()方法未完全退出线程的执行(猜测exit()方法应该是异步的进行线程的退出)
//    this->exit();//先结束线程事件循环，退出线程执行
//    this->deleteLater();//再销毁线程控制对象QThread实例

    //ok--据此可确定出现上面错误确实为线程未完全结束执行，exit()方法应该是异步的进行线程的退出
    //测试结果如下:
    //1.线程确实能正常退出，网络连接断开再重连能正常执行，不会导致程序崩溃
    //2.但出现了执行两次此槽函数的问题，程序能正常运转功能
    //经测试，执行两次此槽函数的问题,原因如下：
    //两次执行此槽函数的QThread对象实例指针相同，故为同一个QThread对象实例
    //表明在线程完全退出执行时，系统会再次发送finished()信号，导致再次触发此槽函数
//    this->exit();
//    while (true) {
//        if(this->isFinished()){
//            qDebug("thread is finished,then delete QThread* object.");
//            this->deleteLater();
//            return;
//        }else{
//            qDebug("thread is not finished,is still running.");
//            QThread::msleep(100);
//        }
//    }

    //更好的处理方式
    if(!this->isFinished()){
        qDebug("thread is not finished.");
        this->exit();
        while (true) {
            if(this->isFinished()){
                qDebug("thread is finished,then delete QThread* object.");
                this->deleteLater();
                return;
            }else{
                qDebug("thread is not finished,is still running.");
                QThread::msleep(100);
            }
        }
    }else {
        qDebug("thread is finished.");
        return;
    }
}

bool MyTcpThread::getDataHandleWorker()
{
    while (true) {
        dataWorker = dataThread->getWorker();
        if(dataWorker){
            return true;
        }
        msleep(100);
    }
}

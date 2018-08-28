#ifndef APPSERVER_H
#define APPSERVER_H

#include <QObject>
#include "combineobject.h"
#include "mythread.h"

class AppServer : public QObject
{
        Q_OBJECT
    public:
        explicit AppServer(QObject *parent = 0);

        void start();
        void startTest_1();
        void startTest_2();
    signals:
        void testSignal();
    public slots:
    private:
        SimplyObject* obj_1;//不传入线程
        SimplyObject* obj_2;//传入线程

        CombineObject* cObj_1;//包含未传入线程的SimplyObject
        CombineObject* cObj_2;//包含已传入线程的SimplyObject

        MyThread* thread;//移入SimplyObject
        MyThread* cThread;//移入CombineObject
};

#endif // APPSERVER_H

#ifndef COMBINEOBJECT_H
#define COMBINEOBJECT_H

#include <QObject>
#include "simplyobject.h"

class CombineObject : public QObject
{
        Q_OBJECT
    public:
        explicit CombineObject(QObject *parent = 0);

        char* name;
        void setSimplyObject(SimplyObject* obj);
        void simplyFun();
    signals:
        void testSignal();
    public slots:
        void signalFun();
    private:
        SimplyObject* sObject;
};

#endif // COMBINEOBJECT_H

#ifndef TESTCLASS_H
#define TESTCLASS_H

#include "interface.h"

class TestClass : public Interface
{
        Q_OBJECT
    public:
        explicit TestClass(QObject *parent = 0);

        void func();
        void normal_func();
    signals:

    public slots:

};

#endif // TESTCLASS_H

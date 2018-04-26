#include "testclass.h"

TestClass::TestClass(QObject *parent) :
    Interface(parent)
{
}

void TestClass::func()
{
    qDebug("TestClass func...");
}

void TestClass::normal_func()
{
    qDebug("normal_func...");
}

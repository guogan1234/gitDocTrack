#include "childclass.h"
#include <QDebug>

ChildClass::ChildClass()
{

}

void ChildClass::testInheritValue()
{
    qDebug("ChildClass::testInheritValue");
    char* publicChar = this->publicValue;
    qDebug("publicChar - %s",publicChar);
    char* protectedChar = this->protectValue;
    qDebug("protectedChar - %s",protectedChar);
//    //error - 调用父类私有成员变量，编译不通过
//    char* privateChar = this->privateValue;
//    qDebug("privateChar - %s",privateChar);
}

void ChildClass::testInheritFunc()
{
    qDebug("ChildClass::testInheritFunc");
    qDebug("start publicFunc");
    this->publicFunc();
    qDebug("start protectedFunc");
    this->protectedFunc();
//    //error - 调用父类private私有成员函数，编译不通过
//    qDebug("start privateFunc");
//    this->privateFunc();

    qDebug("exit ChildClass::testInheritFunc");
}

void ChildClass::testInheritVirtualFunc()
{
    qDebug("ChildClass::testInheritVirtualFunc");
    this->protectedFunc();
    this->protectedVirtualFunc();
    qDebug("ChildClass::testInheritVirtualFunc exit!");
}

void ChildClass::publicOverFunc()
{
    qDebug("ChildClass::publicOverFunc");
}

void ChildClass::publicVirtualOverFunc()
{
    qDebug("ChildClass::publicVirtualOverFunc");
}

void ChildClass::publicVirtualPureFunc()
{
    qDebug("ChildClass::publicVirtualPureFunc");
}

void ChildClass::protectedVirtualPureFunc()
{
    qDebug("ChildClass::protectedVirtualPureFunc");
}

void ChildClass::privateVirtualPureFunc()
{
    qDebug("ChildClass::privateVirtualPureFunc");
}

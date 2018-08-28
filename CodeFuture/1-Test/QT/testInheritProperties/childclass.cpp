#include "childclass.h"
#include <QDebug>

ChildClass::ChildClass()
{

}

ChildClass::ChildClass(int a, int b, int c)
{
    publicProperty = a;
    protectedProperty = b;
//    privateProperty = c;//编译不通过--继承的类无法访问父类的private成员属性
}

void ChildClass::test()
{
    qDebug("ChildClass::test");
    //测试结果表明--继承的子类无法访问父类的private成员属性(即不继承父类的private成员属性)
    qDebug()<<"public - "<<this->publicProperty;
    qDebug()<<"protected - "<<this->protectedProperty;
//    qDebug()<<"private - "<<this->privateProperty;//编译不通过--继承的类无法访问父类的private成员属性
}

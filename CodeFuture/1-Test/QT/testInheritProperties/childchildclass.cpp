#include "childchildclass.h"
#include <QDebug>

ChildChildClass::ChildChildClass()
{

}

void ChildChildClass::test()
{
    qDebug("ChildChildClass::test");
    //测试结果表明:
    //孙子类和子类继承自父类的成员属性是一样的(都能继承public/protected成员属性，不能继承private成员属性)
    qDebug()<<"public - "<<this->publicProperty;
    qDebug()<<"protected - "<<this->protectedProperty;
//    qDebug()<<"private - "<<this->privateProperty;//
}

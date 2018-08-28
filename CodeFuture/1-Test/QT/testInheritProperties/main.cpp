#include <QCoreApplication>
#include <QDebug>
#include "childclass.h"
#include "childchildclass.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    //测试普通类
    BaseClass* base = new BaseClass();
    qDebug("---000---");
    qDebug()<<"public - "<<base->publicProperty;
//    qDebug()<<"protected - "<<base->protectedProperty;//编译不通过--类外只能访问类的public成员属性
//    qDebug()<<"private - "<<base->privateProperty;//编译不通过--类外只能访问类的public成员属性

    //测试继承的子类
    ChildClass* child = new ChildClass(11,22,33);
    qDebug("---111---");
    //1.测试类外访问继承的子类访问权限
    qDebug()<<"public - "<<child->publicProperty;
//    qDebug()<<"protected - "<<child->protectedProperty;//编译不通过--类外只能访问类的public成员属性
//    qDebug()<<"private - "<<child->privateProperty;//编译不通过--类外只能访问类的public成员属性

    qDebug("---222---");
    //2.测试继承的子类的类内访问权限
    child->test();

    //测试孙子类
    ChildChildClass* cChild = new ChildChildClass();
    //测试类外访问权限
    qDebug("---333---");
    qDebug()<<"public - "<<cChild->publicProperty;
//    qDebug()<<"protected - "<<cChild->protectedProperty;//编译不通过--类外只能访问类的public成员属性
//    qDebug()<<"private - "<<cChild->privateProperty;//编译不通过--类外只能访问类的public成员属性
    //测试类内访问权限
    qDebug("---444---");
    cChild->test();
    return a.exec();
}

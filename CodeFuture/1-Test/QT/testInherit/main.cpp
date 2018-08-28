#include <QCoreApplication>
#include "childclass.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    //测试简单继承
    ChildClass* child = new ChildClass();
    qDebug("testInheritValue");
    child->testInheritValue();
    qDebug("-------------------");
    qDebug("testInheritFunc");
    child->testInheritFunc();

    //测试虚函数继承
    qDebug("-------------------");
    qDebug("---test inherit virtual---");
    qDebug("---public---");
    child->publicVirtualFunc();

//    //不能在类外调用protected保护的方法
//    qDebug("---protected---");
//    child->protectedFunc();
//    child->protectedVirtualFunc();

    //测试多态的继承
    //测试结果：
    //1.隐藏现象 -> 同名的普通函数，不会被覆盖，调用还是基类的方法，不调用子类同名的方法
    //2.覆盖现象 -> 同名的virtual虚函数，会被覆盖，调用子类同名的函数方法(若子类未实现，调用基类virtual虚函数)
    qDebug("-------------------");
    qDebug("---test multi inherit---");
    BaseClass* test = new ChildClass();
    qDebug("---111---");
    test->publicFunc();
    qDebug("---222---");
    test->publicOverFunc();
    qDebug("---333---");
    test->publicVirtualFunc();
    qDebug("---444---");
    test->publicVirtualOverFunc();

    return a.exec();
}

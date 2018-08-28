#include <QCoreApplication>
#include "base.h"
#include "basechild.h"

void test_1(){
    Base* b = new Base();
    b->fun();
    b->hideFun();
    b->v_fun();
    b->v_fun2();

//    //私有方法，不能在类外调用
//    b->pFun();
//    b->v_pFun();
}

void test_2(){
    BaseChild* c = new BaseChild();
    c->fun();
    c->hideFun();
    c->v_fun();
    c->v_fun2();

    c->customFun();

//    //私有方法，不能在类外调用
//    c->pFun();
//    c->v_pFun();
}

//测试形成多态的 基类 继承特性
void test_3(){
    //测试形成多态的 基类 继承特性
    Base* bb = new BaseChild();
    //子类 未实现 此方法，但是基类方法 未被virtual修饰
    //结果：调用 基类 方法
    bb->fun();

    //子类 实现了 此方法，但是基类方法 未被virtual修饰
    //结果：调用 基类 方法
    bb->hideFun();

    //子类 实现了 此方法，但是基类方法 被virtual修饰
    //结果：调用 子类 方法
    bb->v_fun();

    //子类 未实现 此方法，但是基类方法 被virtual修饰
    //结果：调用 基类 方法
    bb->v_fun2();

//    bb->customFun();//Error
}

//测试抽象类 实例化
void test_4(){

}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
//    test_1();
//    test_2();
    test_3();

    test_4();

    return a.exec();
}

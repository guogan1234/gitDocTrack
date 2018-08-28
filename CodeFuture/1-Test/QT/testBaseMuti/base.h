#ifndef BASE_H
#define BASE_H


class Base
{
    public:
        Base();

        //测试 不被子类隐藏的 普通函数
        int fun();

        //测试 被子类隐藏的 普通函数
        int hideFun();

        //测试 被子类覆盖的 虚函数
        virtual int v_fun();

        //测试 不被子类覆盖的 虚函数
        virtual int v_fun2();
    private:
        int pFun();
        virtual int v_pFun();
};

#endif // BASE_H

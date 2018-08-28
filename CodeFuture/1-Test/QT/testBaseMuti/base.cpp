#include "base.h"
#include <QDebug>

Base::Base()
{

}

int Base::fun()
{
    qDebug()<<"Base:"<<"fun()";
    return 0;
}

int Base::hideFun()
{
    qDebug()<<"Base:"<<"hideFun()";
    return 0;
}

int Base::v_fun()
{
    qDebug()<<"Base:"<<"v_fun()";
    return 0;
}

int Base::v_fun2()
{
    qDebug()<<"Base:"<<"v_fun2()";
    return 0;
}

int Base::pFun()
{
    qDebug()<<"Base:"<<"pFun()";
    return 0;
}

int Base::v_pFun()
{
    qDebug()<<"Base:"<<"v_pFun()";
    return 0;
}

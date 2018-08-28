#include "basechild.h"
#include <QDebug>

BaseChild::BaseChild()
{

}

int BaseChild::hideFun()
{
    qDebug()<<"BaseChild:"<<"hideFun()";
    return 0;
}

int BaseChild::v_fun()
{
    qDebug()<<"BaseChild:"<<"v_fun()";
    return 0;
}

int BaseChild::customFun()
{
    qDebug()<<"BaseChild:"<<"customFun()";
    return 0;
}

int BaseChild::pFun()
{
    qDebug()<<"BaseChild:"<<"pFun()";
    return 0;
}

int BaseChild::v_pFun()
{
    qDebug()<<"BaseChild:"<<"v_pFun()";
    return 0;
}

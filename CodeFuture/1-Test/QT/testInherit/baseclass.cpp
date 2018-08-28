#include "baseclass.h"
#include <QDebug>

BaseClass::BaseClass()
{
    qDebug("BaseClass::BaseClass");
    publicValue = "public";
    protectValue = "protected";
    privateValue = "private";
}

void BaseClass::publicFunc()
{
    qDebug("BaseClass::publicFunc");
}

void BaseClass::publicOverFunc()
{
    qDebug("BaseClass::publicOverFunc");
}

void BaseClass::publicVirtualFunc()
{
    qDebug("BaseClass::publicVirtualFunc");
}

void BaseClass::publicVirtualOverFunc()
{
    qDebug("BaseClass::publicVirtualOverFunc");
}

void BaseClass::protectedFunc()
{
    qDebug("BaseClass::protectedFunc");
}

void BaseClass::protectedVirtualFunc()
{
    qDebug("BaseClass::protectedVirtualFunc");
}

void BaseClass::privateFunc()
{
    qDebug("BaseClass::privateFunc");
}

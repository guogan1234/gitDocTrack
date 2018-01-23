#include "sharedll.h"


ShareDll::ShareDll()
{
}

QString ShareDll::getStr()
{
    QString str = "Hello,it is my shareQtDll!!!";
    return str;
}

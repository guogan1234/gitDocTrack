#ifndef BASECHILD_H
#define BASECHILD_H

#include <QObject>
#include "base.h"

class BaseChild : public Base
{
    public:
        BaseChild();

        int hideFun();
        virtual int v_fun();

        int customFun();
    private:
        int pFun();
        virtual int v_pFun();
};

#endif // BASECHILD_H

#ifndef CHILDCLASS_H
#define CHILDCLASS_H

#include "baseclass.h"

class ChildClass : public BaseClass
{
    public:
        ChildClass();
        ChildClass(int a,int b,int c);

        void test();
};

#endif // CHILDCLASS_H

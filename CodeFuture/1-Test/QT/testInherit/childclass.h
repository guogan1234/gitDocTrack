#ifndef CHILDCLASS_H
#define CHILDCLASS_H

#include "baseclass.h"

class ChildClass : public BaseClass
{
    public:
        ChildClass();

        //测试函数
        void testInheritValue();
        void testInheritFunc();
        void testInheritVirtualFunc();

        //测试多态的继承
        //重实现的普通函数
        void publicOverFunc();
        //重实现的虚函数
        void publicVirtualOverFunc();

        //重实现的纯虚函数
        void publicVirtualPureFunc();
        void protectedVirtualPureFunc();
        void privateVirtualPureFunc();
};

#endif // CHILDCLASS_H

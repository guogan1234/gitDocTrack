#ifndef BASECLASS_H
#define BASECLASS_H


class BaseClass
{
    public:
        BaseClass();

        char* publicValue;
        void publicFunc();
        void publicOverFunc();//child over this func
        virtual void publicVirtualFunc();
        virtual void publicVirtualOverFunc();//child over this func
        virtual void publicVirtualPureFunc() = 0;

    protected:
        char* protectValue;
        void protectedFunc();
        virtual void protectedVirtualFunc();
        virtual void protectedVirtualPureFunc() = 0;

    private:
        char* privateValue;
        void privateFunc();
//        virtual void privateVirtualFunc();
//        virtual void privateVirtualPureFunc() = 0;
};

#endif // BASECLASS_H

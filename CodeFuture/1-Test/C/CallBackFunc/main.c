#include <stdio.h>

typedef void (*FUN_P)();

void print(){
    printf("This is print function.\n");
}

int print_i(){
    printf("This is print_i function.\n");
    return 0;
}

int add_func(int a,int b){
    return a+b;
}

int add(int a,int b,int (*cb_func)()){
    printf("this is add()\n");
    return (*cb_func)(a,b);
}

int main(void)
{
    printf("Hello World!\n");

    //0.
    void (*func)();
    func = print;//成功
//    (*func)();//调用方式一
    func();//调用方式二，和 (*func)() 效果一样

    //1.
//    void (*func_2)();
//    func_2 = print_i;//成功
//    (*func)();//可以调用 带返回值的print_i函数
//    int res = (*func)();//失败，定义的是 无返回值的函数指针
//    printf("#--%d\n",res);

//    //2.
//    int sum = 0;
//    sum = add(5,6,add_func);
//    printf("#--%d\n",sum);

    //3.使用typedef定义函数指针 为一个类型
    FUN_P pt = NULL;
    pt = print_i;
    (*pt)();
    return 0;
}


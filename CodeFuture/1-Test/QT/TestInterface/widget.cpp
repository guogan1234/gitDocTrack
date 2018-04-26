#include "widget.h"
#include "ui_widget.h"

#include "testclass.h"

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);
}

Widget::~Widget()
{
    delete ui;
}

void Widget::on_pushButton_clicked()
{
    //QT中如不覆盖基类的纯虚函数，仍然被编译器当做抽象类，不能实例化且报错
    TestClass* cls = new TestClass(this);
    cls->normal_func();
    cls->func();

    Interface* pureClass = new TestClass(this);
    pureClass->func();
}

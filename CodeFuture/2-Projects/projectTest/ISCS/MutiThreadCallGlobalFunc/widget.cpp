#include "widget.h"
#include "ui_widget.h"

#include "testthread.h"

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
    for(int i = 0;i<8;i++){
        TestThread* thread = new TestThread(this);
        thread->setObjectName(QString::number(i));
        thread->start();
    }
}

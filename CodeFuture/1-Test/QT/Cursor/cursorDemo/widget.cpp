#include "widget.h"
#include "ui_widget.h"
#include <QDebug>

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
    //setCursor(QCursor(Qt::BusyCursor));//没有成功
//    this->cursor().setShape(Qt::BusyCursor);//没有成功
}

void Widget::on_pushButton_2_clicked()
{
    QPixmap p(":/new/prefix1/res/1.jpg");
//    p.scaled(10,10);
//    setCursor(QCursor(p.scaled(500,500),5,5));
    setCursor(QCursor(p.scaled(20,20)));
    qDebug()<<"1111";
}

void Widget::on_pushButton_3_clicked()
{
    unsetCursor();
}

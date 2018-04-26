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

void Widget::mousePressEvent(QMouseEvent * e)
{
    //获取光标全局的位置
    QPoint cursorPos = QCursor::pos();
    qDebug()<<"cursorPos--"<<cursorPos;
    //全局坐标转成窗体工作区坐标
    QPoint widgetPos = this->mapFromGlobal(cursorPos);
    qDebug()<<"widgetPos--"<<widgetPos;
    //获取窗体左上角顶点相对于父窗体的位置(应用程序窗体的父窗体为桌面)
    QPoint thisWidget_pos = this->pos();
    qDebug()<<"thisWidget_pos--"<<thisWidget_pos;
    QPoint w2_pos = ui->w2->pos();
    qDebug()<<"w2_pos--"<<w2_pos;
    //获取窗体的工作区左上角顶点的位置
    int thisWidget_geo_x = this->geometry().x();
    qDebug()<<"thisWidget_geo_x--"<<thisWidget_geo_x;
}

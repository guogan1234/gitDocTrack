#include "widget.h"
#include "ui_widget.h"

//#include "sharedll.h"
#include "myqtsharedllwidget.h"

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
//    ShareDll* shareDllClass = new ShareDll();
//    QString str = "111";
//    str = shareDllClass->getStr();
//    qDebug("%s",qPrintable(str));

//    ui->lineEdit->setText(str);
}

void Widget::on_pushButton_2_clicked()
{
    myQtShareDllWidget* wid = new myQtShareDllWidget();
    wid->show();
}

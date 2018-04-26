#include "widget.h"
#include "ui_widget.h"

#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    Init();
}

Widget::~Widget()
{
    delete ui;
}

void Widget::Init()
{
    qDebug()<<"111";
//    myTreeModel = new MyTreeModel(this);
//    ui->treeView->setModel(myTreeModel);

    myTreeModel2 = new MyTreeModel2();
    ui->treeView->setModel(myTreeModel2);
    qDebug()<<"222";
}

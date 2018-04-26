#include "widget.h"
#include "ui_widget.h"

#include "mytablemodel.h"
#include "mytablemodel2.h"
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
    qDebug()<<"on_pushButton_clicked...[";
    //继承QAbstractItemModel的自定义表格模型
    MyTableModel* model = new MyTableModel();
    ui->tableView->setModel(model);
//    //继承QAbstractTableModel的自定义表格模型
//    MyTableModel2* model = new MyTableModel2();
//    ui->tableView->setModel(model);
    qDebug()<<"on_pushButton_clicked.]";
}

void Widget::on_pushButton_2_clicked()
{

}

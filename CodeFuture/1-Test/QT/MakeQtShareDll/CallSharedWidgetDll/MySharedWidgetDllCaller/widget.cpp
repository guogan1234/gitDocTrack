#include "widget.h"
#include "ui_widget.h"

#include <QLineEdit>
#include "mysharedwidget.h"

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
    QHBoxLayout* hLayout = new QHBoxLayout();
    QLineEdit* led = new QLineEdit();
    led->setText("I am left.");
    MySharedWidget* mySharedWidget = new MySharedWidget();

    hLayout->addWidget(led);
    hLayout->addWidget(mySharedWidget);
    //若不设置stretch，显示不全，详细参考API文档
    hLayout->setStretch(0,1);
    hLayout->setStretch(1,2);

    ui->widget_2->setLayout(hLayout);
}

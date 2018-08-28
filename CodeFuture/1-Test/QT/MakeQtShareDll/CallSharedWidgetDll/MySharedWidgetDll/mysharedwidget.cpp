#include "mysharedwidget.h"
#include "ui_mysharedwidget.h"

MySharedWidget::MySharedWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::MySharedWidget)
{
    ui->setupUi(this);
}

MySharedWidget::~MySharedWidget()
{
    delete ui;
}

void MySharedWidget::on_pushButton_clicked()
{
    ui->label->setText("hello boy!");
}

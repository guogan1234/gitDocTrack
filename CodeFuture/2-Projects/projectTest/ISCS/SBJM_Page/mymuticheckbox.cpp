#include "mymuticheckbox.h"
#include "ui_mymuticheckbox.h"

myMutiCheckBox::myMutiCheckBox(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::myMutiCheckBox)
{
    ui->setupUi(this);

    qDebug("myMutiCheckBox...");
    ui->widget_2->setVisible(false);
    qDebug("myMutiCheckBox!");
}

myMutiCheckBox::~myMutiCheckBox()
{
    delete ui;
}

void myMutiCheckBox::on_pushButton_clicked()
{
    ui->widget_2->setVisible(true);
}

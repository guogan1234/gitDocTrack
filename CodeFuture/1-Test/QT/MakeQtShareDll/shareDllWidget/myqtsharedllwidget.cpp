#include "myqtsharedllwidget.h"
#include "ui_myqtsharedllwidget.h"

myQtShareDllWidget::myQtShareDllWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::myQtShareDllWidget)
{
    ui->setupUi(this);
}

myQtShareDllWidget::~myQtShareDllWidget()
{
    delete ui;
}

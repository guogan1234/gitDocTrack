#include "form.h"
#include "ui_form.h"

Form::Form(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Form)
{
    ui->setupUi(this);

    setStyleSheet("border-color:green;border-style:solid;border-width:2px");
}

Form::~Form()
{
    delete ui;
}

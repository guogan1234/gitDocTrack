#include "widget.h"
#include "ui_widget.h"
#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

//    setMouseTracking(true);
    ui->comboBox->setMouseTracking(true);
    installEventFilter(ui->comboBox);
}

Widget::~Widget()
{
    delete ui;
}

bool Widget::event(QEvent *event)
{
//    qDebug()<<"event:type - "<<event->type();
}

bool Widget::eventFilter(QObject *watched, QEvent *event)
{
    qDebug()<<"event type - "<<event->type();
    if(watched == ui->comboBox){
        qDebug()<<"comboBox - "<<event->type();
    }
    return true;
}

void Widget::on_comboBox_activated(int index)
{
    qDebug("on_comboBox_activated - %d",index);
}

void Widget::on_comboBox_activated(const QString &arg1)
{
    qDebug()<<"on_comboBox_activated(QString)"<<arg1;
}

void Widget::on_comboBox_currentIndexChanged(int index)
{
    qDebug()<<"on_comboBox_currentIndexChanged(int)"<<index;
}

void Widget::on_comboBox_currentIndexChanged(const QString &arg1)
{
    qDebug()<<"on_comboBox_currentIndexChanged(QString)"<<arg1;
}

void Widget::on_comboBox_currentTextChanged(const QString &arg1)
{
    qDebug()<<"on_comboBox_currentTextChanged(QString)"<<arg1;
}

void Widget::on_comboBox_editTextChanged(const QString &arg1)
{
    qDebug()<<"on_comboBox_editTextChanged(QString)"<<arg1;
}

void Widget::on_comboBox_highlighted(int index)
{
    qDebug()<<"on_comboBox_highlighted(int)"<<index;
}

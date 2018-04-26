#include "widget.h"
#include "ui_widget.h"

#include <QSvgGenerator>
#include <QPainter>

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
    test1();
}

void Widget::test1()
{
    QSvgGenerator svg;
    svg.setFileName("./test.svg");

    QPainter p;
    p.begin(&svg);
    p.setPen(Qt::red);
    p.drawEllipse(0,0,100,100);
    p.end();
}

void Widget::on_pushButton_2_clicked()
{
    ui->widget->load(QString("./test.svg"));
}

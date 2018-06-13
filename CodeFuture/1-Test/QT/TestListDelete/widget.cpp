#include "widget.h"
#include "ui_widget.h"

#include "structData.h"
#include "classdata.h"
#include "stackTempData.h"

QList<SData*> list;
QList<ClassData*> list_2;

QList<StackTempData*> tempDataList;

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
    for(int i = 0;i<100000;i++){//100*10K = 1M 100000/100= 100
        SData* temp = new SData;
        memset(temp->c_1,'a',1024);
        memset(temp->c_2,'a',1024);
        memset(temp->c_3,'a',1024);
        memset(temp->c_4,'a',1024);
        memset(temp->c_5,'a',1024);
        memset(temp->c_6,'a',1024);
        memset(temp->c_7,'a',1024);
        memset(temp->c_8,'a',1024);
        memset(temp->c_9,'a',1024);
        memset(temp->c_10,'a',1024);

        list.append(temp);
        qDebug("i - %d\n",i);
    }
}

void Widget::on_pushButton_2_clicked()
{
//    list.clear();
//    qDebug("after clear!\n");
    int len = list.length();
    for(int i = 0;i<len;i++){
        list.removeAt(i);
        qDebug("len - %d,remove - %d\n",len,i);
    }
    qDebug("after remove!\n");
}

void Widget::on_pushButton_3_clicked()
{
    for(int i = 0;i<100000;i++){
        //ClassData* temp = new ClassData();
        ClassData* temp = new ClassData(this);
        memset(temp->c_1,'a',1024);
        memset(temp->c_2,'a',1024);
        memset(temp->c_3,'a',1024);
        memset(temp->c_4,'a',1024);
        memset(temp->c_5,'a',1024);
        memset(temp->c_6,'a',1024);
        memset(temp->c_7,'a',1024);
        memset(temp->c_8,'a',1024);
        memset(temp->c_9,'a',1024);
        memset(temp->c_10,'a',1024);

        list_2.append(temp);
        qDebug("i - %d\n",i);
    }
}

void Widget::on_pushButton_4_clicked()
{
    list_2.clear();
    qDebug("after clear!\n");
}

void Widget::on_pushButton_5_clicked()
{
    SData* data = list.at(0);
    qDebug("# - %s\n",data->c_1);
}

//tryChangeTempMemory
void Widget::on_pushButton_6_clicked()
{
    for(int i = 0;i<1000;i++){
        //ClassData* temp = new ClassData();
        ClassData* temp = new ClassData(this);
        memset(temp->c_1,'b',1024);
        memset(temp->c_2,'b',1024);
        memset(temp->c_3,'b',1024);
        memset(temp->c_4,'b',1024);
        memset(temp->c_5,'b',1024);
        memset(temp->c_6,'b',1024);
        memset(temp->c_7,'b',1024);
        memset(temp->c_8,'b',1024);
        memset(temp->c_9,'b',1024);
        memset(temp->c_10,'b',1024);

        list_2.append(temp);
        qDebug("i - %d\n",i);
    }
}

//load stack temp value
void Widget::on_pushButton_7_clicked()
{
    for(int i = 0;i<1000;i++){
        StackTempData tempData;
        tempData.data = i;

        tempDataList.append(&tempData);
        qDebug("i - %d\n",i);
    }
}

//get stack temp value
void Widget::on_pushButton_9_clicked()
{
    StackTempData* t = tempDataList.at(0);
    qDebug("# - %d\n",t->data);
}

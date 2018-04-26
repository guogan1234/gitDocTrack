#include "widget.h"
#include "ui_widget.h"
#include <QWebFrame>

#include <QJsonDocument>
#include <QJsonObject>
#include <QJsonArray>
#include <QJsonValue>

#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

//    testJson();

//    creatChartsDatas();

    //用调试器查看webview内部结构
    QWebPage* thePage;
    thePage = ui->webView->page();//加载新网页，webpage指针地址未改变
    QWebFrame* theFrame;
    theFrame = ui->webView->page()->mainFrame();
    connect(ui->webView->page()->mainFrame(),SIGNAL(javaScriptWindowObjectCleared()),this,SLOT(jsWindowCleared()));
    connect(ui->webView,SIGNAL(loadStarted()),this,SLOT(loadStartSlot()));
    connect(ui->webView,SIGNAL(loadFinished(bool)),this,SLOT(loadFinishedSlot(bool)));
}

Widget::~Widget()
{
    delete ui;
}

void Widget::on_pushButton_clicked()
{
    QString execPath = QApplication::applicationDirPath();
    QString htmlPath = "file:///" + execPath + "/html/qtLoadCharts.html";
    ui->webView->load(htmlPath);
}

void Widget::loadStartSlot()
{
    qDebug()<<"loadStartSlot...[";
}

void Widget::jsWindowCleared()
{
    qDebug()<<"jsWindowCleared...[";

}

void Widget::loadFinishedSlot(bool b)
{
    qDebug()<<"loadFinishedSlot...[";

    //向js传入简单字符串
//    ui->webView->page()->mainFrame()->evaluateJavaScript("qtSendDatasToJS('hello!')");

    //用调试器查看webview内部结构
    QWebPage* thePage;
    thePage = ui->webView->page();//加载新网页，webpage指针地址未改变
    QWebFrame* theFrame;
    theFrame = ui->webView->page()->mainFrame();
    QWebFrame* nowFrame;
    nowFrame = ui->webView->page()->currentFrame();
    QObjectList list = ui->webView->page()->children();

    qDebug()<<"loadFinishedSlot. ]";
}

void Widget::creatChartsDatas()
{

}

void Widget::testQStringArg()
{
    QString arg1 = "World";
    QString arg2 = "GG";
    int arg3 = 111;
//    QString testArgs = QString("Hello 1%,and 2% age is 3%").arg(arg1).arg(arg2).arg(arg3);//拼接arg参数失败，注意是%1，而不是1%
    QString testArgs = QString("Hello %1,and %2 age is %3").arg(arg1).arg(arg2).arg(arg3);//成功
//    QString str;
//    str = "Hello %1,and %2 age is %3";
//    QString testArgs = str.arg(arg1).arg(arg2).arg(arg3);
    qDebug()<<"testArgs -- "<<testArgs;
}

void Widget::testJson()
{
    QJsonObject obj1;
    obj1.insert("name", QJsonValue(QString("lily")));
    obj1.insert("age", QJsonValue(23));
    QJsonObject addr1;
    addr1.insert("city", QJsonValue(QString("guangzhou")));
    addr1.insert("province",QJsonValue(QString("guangdong")));
    obj1.insert("addr", addr1);
    qDebug() <<"obj1 -- "<< obj1;

    QJsonObject obj2;
    obj2.insert("name",QJsonValue(QString("tom")));
    obj2.insert("age",QJsonValue(24));
    QJsonObject addr2;
    addr2.insert("city",QJsonValue(QString("shenzhen")));
    addr2.insert("province",QJsonValue(QString("guangdong")));
    obj2.insert("addr", addr2);
    qDebug() <<"obj2 -- "<< obj2;

    QJsonArray array;
    array.push_back(obj1);
    array.push_back(obj2);
    qDebug() <<"array -- "<< array;
}

void Widget::on_pushButton_2_clicked()
{
    //向js传入json
    QJsonObject obj1;
//    obj1.insert("name", QJsonValue(QString("station1")));
    obj1.insert("name", QJsonValue(QString("车站1")));//使用项目默认的全局编码模式(UTF-8编码)，webview展示的highChart也可以正常显示中文
    QJsonArray station1_data;
    station1_data.append(11);
    station1_data.append(12);
    station1_data.append(13);
    station1_data.append(1);
    station1_data.append(22);
    station1_data.append(13);
    obj1.insert("data",station1_data);
    qDebug() <<"obj1 -- "<< obj1;

    QJsonObject obj2;
    obj2.insert("name",QJsonValue(QString("车站2")));
    QJsonArray station2_data;
    station2_data.append(21);
    station2_data.append(22);
    station2_data.append(23);
    station2_data.append(11);
    station2_data.append(13);
    obj2.insert("data", station2_data);
    qDebug() <<"obj2 -- "<< obj2;

    QJsonArray array;
    array.push_back(obj1);
    array.push_back(obj2);
    qDebug() <<"array -- "<< array;
    QJsonDocument doc(array);
    QString str(doc.toJson());
    QString data = "qtSendDatasToJS(%1)";
    QString jsData = data.arg(str);
    qDebug()<<"jsData -- "<<jsData;
    ui->webView->page()->mainFrame()->evaluateJavaScript(jsData);
}

#include "widget.h"
#include "ui_widget.h"
#include <QWebFrame>
#include <QFile>

#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    connect(ui->menuHtml_w->page()->mainFrame(),SIGNAL(javaScriptWindowObjectCleared()),this,SLOT(jsWindowClear()));
    connect(ui->menuHtml_w->page(),SIGNAL(loadFinished(bool)),this,SLOT(loadMenuHtmlFinished(bool)));
    connect(ui->menuHtml_w->page(),SIGNAL(loadStarted()),this,SLOT(loadStartSlot()));

    int flag = 1;//html菜单显示
    InitParams();
    InitShow(flag);
}

Widget::~Widget()
{
    delete ui;
}

void Widget::jsCallQObject()
{
    qDebug()<<"jsCallQObject...";
}

void Widget::on_pushButton_clicked()
{
    ui->webView->load(QUrl("http://127.0.0.1:4060/"));
}

void Widget::on_pushButton_2_clicked()
{
    ui->webView->load(QUrl("http://127.0.0.1:4060/passengerFlow/stationContrast/"));
}

void Widget::jsWindowClear()
{
    qDebug()<<"jsWindowClear..."<<__FILE__;
    ui->menuHtml_w->page()->mainFrame()->addToJavaScriptWindowObject("qtProxy",menuProxyClass);
//    ui->menuHtml_w->page()->mainFrame()->addToJavaScriptWindowObject("qtProxy",this);
    qDebug()<<"jsWindowClear exit!!!";
}

void Widget::loadMenuHtmlFinished(bool b)
{
//    //缓存web的js文件，与向js注入QT对象无关
    qDebug()<<"loadMenuHtmlFinished...";
//    if(!b){
//        qDebug()<<"load...";
//        return;
//    }
//    QString js_str;
//    QFile file(jsFilePath);
//    qDebug()<<"file:"<<jsFilePath;
//    if(!file.open(QIODevice::ReadOnly)){
//        qDebug()<<"load js file failed!!!";
//    }
//    js_str = QString::fromUtf8(file.readAll());
//    ui->menuHtml_w->page()->mainFrame()->evaluateJavaScript(js_str);
//    qDebug()<<"load js file ok!!!";
}

void Widget::jsCallQObjectSlot()
{
    qDebug()<<"jsCallQObjectSlot...";
}

void Widget::loadStartSlot()
{
    qDebug()<<"loadStartSlot...";
    QUrl url = ui->menuHtml_w->page()->mainFrame()->requestedUrl();
    qDebug()<<"URL:"<<url.toString();
}

void Widget::jsCallQObjectSlot_public()
{
    qDebug()<<"jsCallQObjectSlot_public...";
}

void Widget::InitShow(int flag)
{
    ui->widget->setVisible(false);

//    ui->menuHtml_w->load(QUrl("file:///F:/Work/QtMyTest/qtWithHtml/loadAFCWeb/build-loadAFC_CD-Desktop_Qt_5_2_1_MinGW_32bit-Debug/debug/html/DropDown.html"));

    menuHtmlPath = "file:///" + execPath + "/html/DropDown.html";
    jsFilePath = execPath + "/html/test.js";
    qDebug()<<"start load webPage...";
    ui->menuHtml_w->load(QUrl(menuHtmlPath));
}

void Widget::InitParams()
{
    execPath = QApplication::applicationDirPath();

    menuProxyClass = new menuProxy(this);
    menuProxyClass->jsCallTest_2();
}

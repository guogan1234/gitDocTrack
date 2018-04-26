#include "widget.h"
#include "ui_widget.h"
#include <QDir>
#include<QWebFrame>

#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    connect(ui->webView->page()->mainFrame(),SIGNAL(loadStarted()),this,SLOT(loadStartSlot()));
    connect(ui->webView->page()->mainFrame(),SIGNAL(loadFinished(bool)),this,SLOT(loadFinishedSlot(bool)));

//    //测试代码
//    QObjectList l = this->children();
//    QList<QObject*> ll = findChildren<QObject*>("pushButton");
//    QList<QObject*> lll = findChildren<QObject*>("webView");
//    int this_len = this->children().length();
//    int view_len = ui->webView->children().length();
//    qDebug()<<"len:"<<this_len<<" "<<view_len;
//    QObjectList list = ui->webView->children();
//    QObject* obj = list.first();
//    qDebug()<<"111:"<<ll.length()<<" "<<lll.length();
}

Widget::~Widget()
{
    delete ui;
}

void Widget::on_pushButton_clicked()
{
//    QString nowDir = QDir::currentPath();
//    QString htmlPath = nowDir + "/html/jsContainsQWidgets.html";
//    qDebug()<<htmlPath<<" "<<nowDir;
    QString htmlPath = "file:///" + QApplication::applicationDirPath() + "/html/jsContainsQWidgets.html";
    qDebug()<<htmlPath;
//    //1、直接用QWebView加载网页
//    ui->webView->load(QUrl(htmlPath));
    //2、使用QWebPage加载网页
    ui->webView->setPage(&webPage);
    connect(ui->webView->page()->mainFrame(),SIGNAL(loadStarted()),this,SLOT(loadStartSlot()));
    connect(ui->webView->page()->mainFrame(),SIGNAL(loadFinished(bool)),this,SLOT(loadFinishedSlot(bool)));

    webPage.mainFrame()->load(htmlPath);
//    //3、使用QWebPage的子类加载网页(可以向网页注入QT窗体插件)
//    ui->webView->setPage(&myPage);
//    connect(ui->webView->page()->mainFrame(),SIGNAL(loadStarted()),this,SLOT(loadStartSlot()));
//    connect(ui->webView->page()->mainFrame(),SIGNAL(loadFinished(bool)),this,SLOT(loadFinishedSlot(bool)));
//    myPage.mainFrame()->load(htmlPath);
}

void Widget::loadFinishedSlot(bool b)
{
    qDebug()<<"[ loadFinishedSlot! ]";
//    ui->webView->page()->mainFrame()->evaluateJavaScript("qtCallJs()");
    ui->webView->page()->mainFrame()->evaluateJavaScript("qtCallJsWithJson('{say:hello}')");
//    ui->webView->page()->mainFrame()->evaluateJavaScript("qtCallJsWithJson('{'say':'hello'}')");
}

void Widget::loadStartSlot()
{
    qDebug()<<"loadStartSlot...[";
    QString loadUrl = ui->webView->page()->mainFrame()->requestedUrl().toString();
    qDebug()<<loadUrl<<" "<<"loadStartSlot! ]";
}

void Widget::on_pushButton_2_clicked()
{
    QString htmlPath = "file:///" + QApplication::applicationDirPath() + "/html/jsContainsQWidgets.html";
    qDebug()<<htmlPath;
    //3、使用QWebPage的子类加载网页(可以向网页注入QT窗体插件)
    ui->webView->setPage(&myPage);
    connect(ui->webView->page()->mainFrame(),SIGNAL(loadStarted()),this,SLOT(loadStartSlot()));
    connect(ui->webView->page()->mainFrame(),SIGNAL(loadFinished(bool)),this,SLOT(loadFinishedSlot(bool)));
    myPage.mainFrame()->load(htmlPath);
}

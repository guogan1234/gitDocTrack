#include "widget.h"
#include "ui_widget.h"
#include <QVBoxLayout>
#include <QDebug>
#include <QDir>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    qDebug()<<"1111";
    ui->setupUi(this);

    qDebug()<<"2222";
    ui->widget->setVisible(false);
    QPixmap p(":/new/prefix1/res/1.jpg");
    setWindowIcon(QIcon(p.scaled(20,20)));
//    ui->webView->load(QUrl("file:///D:/DropDown.html"));//成功
//    //成功
//    QString str = "D:/";
//    ui->webView->load(QUrl("file:///"+str+"badge.html"));
//    //成功
//    QString appPath = QApplication::applicationDirPath();
//    qDebug()<<"appPath:"<<appPath;
//    appPath += "/html/";
//    ui->webView->load(QUrl("file:///"+appPath+"badge.html"));
    ui->webView->load(QUrl("http://127.0.0.1:4060/"));

//    ui->webView->load(QUrl(":/new/prefix1/html/DropDown.html"));//失败
//    //失败
//    QString str = "file:///:/new/prefix1/html/DropDown.html";
//    ui->webView->load(QUrl(str));

//    //失败
//    char* ch1 = "file:///";
//    char* ch2 = ":/new/prefix1/html/DropDown.html";
//    const char* ch3 = strcat(ch1,ch2);
//    const QString str = ch3;
//    ui->webView->load(QUrl(str));

//    ui->webView->load(QUrl("http://www.baidu.com"));
//    InitComponent();
//    InitLayout();
}

Widget::~Widget()
{
    delete ui;
}

void Widget::InitLayout()
{
    QVBoxLayout* main_lay = new QVBoxLayout(this);
    main_lay->addWidget(web);
    main_lay->addWidget(ui->widget);
}

void Widget::InitComponent()
{
//    web = new QWebView();
//    web->setUrl();
//    web->load(QUrl("http://www.baidu.com"));
}

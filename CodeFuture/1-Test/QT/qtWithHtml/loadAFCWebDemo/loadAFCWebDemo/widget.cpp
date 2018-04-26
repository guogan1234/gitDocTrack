#include "widget.h"
#include "ui_widget.h"
#include <QWebFrame>

#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    Init();
}

Widget::~Widget()
{
    delete ui;
}

bool bMenu = false;
void Widget::on_toolButton_clicked()
{
    ui->menuHtml->setVisible(bMenu);
    if(bMenu){
        bMenu = false;
    }else {
        bMenu = true;
    }
}

void Widget::changeLoad(QString subPath)
{
    qDebug()<<"changeLoad...[";
    qDebug()<<"subPath -- "<<subPath;
    QString reloadPath = baseWebUrl + subPath;
    qDebug()<<"reloadPath -- "<<reloadPath;
    qDebug()<<"start reload main page...";
    ui->mainHtml->load(reloadPath);
    qDebug()<<"changeLoad exit!!! ]";
}

void Widget::mainHtmlLoadStartSlot()
{
    qDebug()<<"mainHtmlLoadStartSlot...";
    QString requestURL = ui->mainHtml->page()->mainFrame()->requestedUrl().toString();
    qDebug()<<"requestURL -- "<<requestURL;
    qDebug()<<"mainHtmlLoadStartSlot exit!!!";
}

int count = 0;
void Widget::mainHtmlLoadFinishedSlot(bool b)
{
    count++;
    qDebug()<<"mainHtmlLoadFinishedSlot...["<<count;
    if(b){
        qDebug()<<"main page load finished!!!";
        QString mainURL = ui->mainHtml->url().toString();
        qDebug()<<"mainURL -- "<<mainURL;
    }
    qDebug()<<"mainHtmlLoadFinishedSlot exit!!! ]"<<count;
}

void Widget::addQObjectToJS()
{
    qDebug()<<"addQObjectToJS...";
    ui->menuHtml->page()->mainFrame()->addToJavaScriptWindowObject("qtProxy",this);
    qDebug()<<"addQObjectToJS exit!!!";
}

void Widget::Init()
{
    InitParams();
    InitConn();
    InitLoad();
}

void Widget::InitLoad()
{
    qDebug()<<"InitLoad...";
    ui->menuHtml->load(QUrl(menuHtmlPath));
    ui->mainHtml->load(QUrl(baseWebUrl));
    qDebug()<<"InitLoad exit!!!";
}

void Widget::InitConn()
{
    connect(ui->menuHtml->page()->mainFrame(),SIGNAL(javaScriptWindowObjectCleared()),this,SLOT(addQObjectToJS()));
    connect(ui->mainHtml->page()->mainFrame(),SIGNAL(loadStarted()),this,SLOT(mainHtmlLoadStartSlot()));
    connect(ui->mainHtml->page()->mainFrame(),SIGNAL(loadFinished(bool)),this,SLOT(mainHtmlLoadFinishedSlot(bool)));
}

void Widget::InitParams(){
    execPath = QApplication::applicationDirPath();
    menuHtmlPath = "file:///" + execPath +"/html/menu.html";

    baseWebUrl = "http://127.0.0.1:4060/";
}

#include "mywebpage.h"
#include "form.h"
#include <QPushButton>

#include <QDebug>

myWebPage::myWebPage(QWidget *parent) :
    QWebPage(parent)
{
}

QObject *myWebPage::createPlugin(const QString &classid, const QUrl &url, const QStringList &paramNames, const QStringList &paramValues)
{
    qDebug()<<"createPlugin...[ "<<classid;
    QWidget* widget;
    if(classid == "form"){
        widget = new Form();
        int index = paramNames.indexOf("width");
        if(index > -1){
            int width = paramValues[index].toInt();
            qDebug()<<"form.w:"<<width;
            widget->setMinimumWidth(width);
        }
        int index2 = paramNames.indexOf("height");
        if(index2 > -1){
            int h = paramValues[index2].toInt();
            qDebug()<<"form.h:"<<h;
            widget->setMinimumHeight(h);
        }
//        widget->setStyleSheet("border-color:green;border-style:solid;border-width:2px");
    }
    else if(classid == "GG-btn"){
        widget = new QPushButton();
        widget->setMaximumSize(100,25);
        widget->setStyleSheet("border-color:red;border-style:solid;border-width:3px");
    }
    return widget;
}

void myWebPage::javaScriptAlert(QWebFrame *frame, QString msg)
{

}

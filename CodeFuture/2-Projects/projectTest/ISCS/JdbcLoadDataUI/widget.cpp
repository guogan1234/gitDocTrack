#include "widget.h"
#include "ui_widget.h"
#include <QNetworkRequest>
#include <QFile>

#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    t1 = new QTimer(this);

    manager = new QNetworkAccessManager(this);
    connect(manager,SIGNAL(finished(QNetworkReply*)),this,SLOT(finishedSlot(QNetworkReply*)));
}

Widget::~Widget()
{
    delete ui;
}

QString url = "http://192.168.1.222:4068";

void Widget::on_loadPage_clicked()
{
    t.start();
    url += "/jdbcLoadLargeData?page=0&pageSize=3000";

    QNetworkRequest request;
    request.setUrl(QUrl(url));
    manager->get(request);
}

void Widget::on_loadAll_clicked()
{
    for(int i = 0;i<334;i++){
        QString url_base = "http://192.168.1.222:4068";
        QString url_all = url_base + "/jdbcLoadLargeData?page=" + QString::number(i) + "&pageSize=3000";
        qDebug()<<"url "<<url_all;

        QNetworkRequest request;
        request.setUrl(QUrl(url_all));
        manager->get(request);
    }
}

int count = 0;
int total = 0;
QByteArray byteAll;//测试把所有数据读入内存，一次性写入文件，报错
void Widget::finishedSlot(QNetworkReply * reply)
{
    qDebug()<<"count:"<<count;
    QByteArray ba = reply->readAll();
//    qDebug()<<"#:"<<ba;
    int ms = t.elapsed();
    int s = ms/1000;
//    qDebug()<<"@:"<<s<<" "<<ms;
    total += ms;
    t.restart();
//    byteAll += ba;//测试把所有数据读入内存，一次性写入文件，报错
    //多次写入文件，结果--运行成功
    //100W条数据使用JDBC查询，并使用Http协议获取，一页1000条数据，每次写入一页数据到txt文件--耗时222s
    QString fileName = "./" + QString::number(count) + ".txt";
    QFile file(fileName);
    if(file.open(QFile::WriteOnly)){
        qint64 len = file.write(ba);
        qDebug()<<"len:"<<len;
    }
    if(count == 333){
        int total_s = total/1000;
        //100W条数据使用JDBC查询，并使用Http协议获取，一页1000条数据--耗时204s
        qDebug()<<"total--"<<total<<" "<<total_s;
        ui->loadAllTime->setText(QString::number(total) + "ms");
    }
    count++;
}

void Widget::on_pushButton_clicked()
{
    QFile file("./a.txt");
    if(file.open(QFile::WriteOnly)){
        qint64 len = file.write(byteAll);
        qDebug()<<"len:"<<len;
    }
}

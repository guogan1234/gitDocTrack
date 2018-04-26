#include "widget.h"
#include "ui_widget.h"

#define GG

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
    qDebug("test operater system macro...");
#ifdef Q_OS_WIN
    qDebug("windows OS!");
    ui->pushButton->setText("windows");
#endif
#ifdef Q_OS_LINUX
    qDebug("linux OS!");
    ui->pushButton->setText("linux");
#endif

    qDebug("----------------------");
    //#ifdef必须有对应的结束#endef
    qDebug("test compiler macro...");
#ifdef Q_CC_GNU
    qDebug("GNU compiler");
#endif
#ifdef Q_CC_MINGW
    qDebug("MinGW complier");
#endif
#ifdef Q_CC_MSVC
    qDebug("MSVC compiler");
#endif

    qDebug("---------------------");
    qDebug("test custom macro...");
#ifdef GG
    qDebug("hello GG");
#elif HH
    qDebug("hello HH");
#else
    qDebug("hello it is nothing!");
#endif

    qDebug("---------------------");
    qDebug("test undef macro...");
#undef HH
    qDebug("undef HH");
#undef Q_OS_LINUX
    qDebug("undef Q_OS_LINUX");
}

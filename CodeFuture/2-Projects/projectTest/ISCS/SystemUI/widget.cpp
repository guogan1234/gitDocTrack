#include "widget.h"
#include "ui_widget.h"

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    impl = new SysWindowImpl();
}

Widget::~Widget()
{
    delete ui;
}

void Widget::on_pushButton_clicked()
{
    ui->LE_result->setText(impl->result);

    qDebug("on_pushButton_clicked...");
    test_getSysCpu();
}

void Widget::test_getSysCpu()
{
    qDebug("test_getSysCpu...");
//    impl->test_getSysCpu();//获取CPU使用率
//    impl->test_getSysFromRegist();//获取注册表信息
//    impl->test_getSysCpuBaseInfo();//获取CPU基本硬件信息
//    impl->test_getSysMemory();//获取内存信息
    impl->test_getSysProcess();

    ui->LE_procId->setText(QString("%1").arg(impl->res_procId));
    ui->LE_result->setText(impl->result);
    setWindowTitle("Finished!");
}

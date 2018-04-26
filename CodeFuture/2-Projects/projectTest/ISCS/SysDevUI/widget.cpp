#include "widget.h"
#include "ui_widget.h"

#include <QDebug>
#include <QStandardItemModel>

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

void Widget::Init()
{
    InitParams();
}

void Widget::InitParams()
{
    ui->widget->setVisible(false);

    t_msec = 1000;
    t = new QTimer();
    t->setInterval(t_msec);
    connect(t,SIGNAL(timeout()),this,SLOT(t_timeout_slot()));
    t->start();

    winFinder = new SysInfoFinderWinImpl(this);
}

void Widget::getViewDatas()
{
    proc_list.clear();
//    ui->TW_process->clear();
//    ui->TW_process->clearContents();
    disk_list.clear();
    ui->TW_disk->clearContents();

    //获取数据
    winFinder->getMemoryInfo();
    mem_data = winFinder->mem_data;

    winFinder->getCpuInfo();
    cpu_data = winFinder->cpu_data;

    winFinder->getPCInfo();
    pc_data = winFinder->pc_data;

    winFinder->getProcessInfo();
    proc_list = winFinder->proc_list;

    winFinder->getDiskInfo();
    disk_list = winFinder->disk_list;
}

void Widget::showViewDatas()
{
//    showProcess();
    showProcessView();
    showCpu();
    showMemory();
    showDisk();
    showPC();
}

//void Widget::showProcess()
//{
//    int len = proc_list.length();
//    ui->TW_process->setRowCount(len);
//    for(int i = 0;i < len;i++) {
//        qDebug()<<"#-"<<proc_list[i].id<<" "<<proc_list[i].t_num;
//        QTableWidgetItem* item_0 = new QTableWidgetItem();
//        item_0->setText(QString("%1").arg(proc_list[i].id));
//        ui->TW_process->setItem(i,0,item_0);

//        QTableWidgetItem* item_1 = new QTableWidgetItem();
//        item_1->setText(QString(proc_list[i].name));
//        ui->TW_process->setItem(i,1,item_1);

//        QTableWidgetItem* item_2 = new QTableWidgetItem();
//        item_2->setText(QString("%1").arg(proc_list[i].p_id));
//        ui->TW_process->setItem(i,2,item_2);

//        QTableWidgetItem* item_3 = new QTableWidgetItem();
//        item_3->setText(QString("%1").arg(proc_list[i].t_num));
//        ui->TW_process->setItem(i,3,item_3);

//        QTableWidgetItem* item_4 = new QTableWidgetItem();
//        item_4->setText(QString("%1 K").arg(proc_list[i].currentMemory/1024));
//        ui->TW_process->setItem(i,4,item_4);

////        QTableWidgetItem* item_5 = new QTableWidgetItem();
////        item_5->setText(QString("%1").arg(proc_list[i].memory_top));
////        ui->TW_process->setItem(i,5,item_5);
//    }
//}

void Widget::showProcessView()
{
    QStringList list;
    list<<"ID"<<"名称"<<"父进程ID"<<"线程数"<<"当前内存";

    QStandardItemModel* model = new QStandardItemModel();
    model->setHorizontalHeaderLabels(list);
    int len = proc_list.length();
    for(int i = 0;i < len;i++){
        QStandardItem* item_0 = new QStandardItem();
        item_0->setText(QString("%1").arg(proc_list[i].id));
        model->setItem(i,0,item_0);

        QStandardItem* item_1 = new QStandardItem();
        item_1->setText(QString(proc_list[i].name));
        model->setItem(i,1,item_1);

        QStandardItem* item_2 = new QStandardItem();
        item_2->setText(QString("%1").arg(proc_list[i].p_id));
        model->setItem(i,2,item_2);

        QStandardItem* item_3 = new QStandardItem();
        item_3->setText(QString("%1").arg(proc_list[i].t_num));
        model->setItem(i,3,item_3);

        QStandardItem* item_4 = new QStandardItem();
        item_4->setText(QString("%1 K").arg(proc_list[i].currentMemory/1024));
        model->setItem(i,4,item_4);
    }
    ui->TV_process->setModel(model);
}

void Widget::showCpu()
{   
    ui->LE_cpu_count->setText(QString("%1").arg(cpu_data.num));
    ui->LE_cpu_frequency->setText(QString("%1MHz").arg(cpu_data.frequency));
    ui->LE_cpu_level->setText(QString("%1").arg(cpu_data.level));
//    ui->LE_cpu_mask->setText(cpu_data.mask);
//    ui->LE_cpu_max_addr->setText(cpu_data.max_unit);
//    ui->LE_cpu_min_addr->setText(cpu_data.min_unit);
    ui->LE_cpu_name->setText(cpu_data.name);
    ui->LE_cpu_page_size->setText(QString("%1").arg(cpu_data.page_size));
    ui->LE_cpu_type_name->setText(QString("%1型号处理器").arg(cpu_data.type));
    ui->LE_cpu_used->setText(QString("%1").arg(cpu_data.used));
    ui->LE_cpu_version->setText(QString("%1").arg(cpu_data.version));
}

void Widget::showMemory()
{
    ui->LE_mem_used->setText(QString("%1%").arg(mem_data.used));
    ui->LE_mem_avail->setText(QString("%1 MB").arg(mem_data.remain));
    ui->LE_mem_total->setText(QString("%1 MB").arg(mem_data.total));
    ui->LE_page_avail->setText(QString("%1 MB").arg(mem_data.remain_page));
    ui->LE_page_size->setText(QString("%1 MB").arg(mem_data.page_size));
    ui->LE_virtual_avail->setText(QString("%1 MB").arg(mem_data.remain_virtual));
    ui->LE_virtual_total->setText(QString("%1 MB").arg(mem_data.virtual_total));
}

void Widget::showDisk()
{
    int len = disk_list.length();
    ui->TW_disk->setRowCount(len);
    for(int i = 0;i < len;i++){
        QTableWidgetItem* item_0 = new QTableWidgetItem();
        item_0->setText(QString(disk_list[i].location));
        ui->TW_disk->setItem(i,0,item_0);

        QTableWidgetItem* item_1 = new QTableWidgetItem();
        item_1->setText(QString(disk_list[i].type));
        ui->TW_disk->setItem(i,1,item_1);

        QTableWidgetItem* item_2 = new QTableWidgetItem();
        item_2->setText(QString("%1 MB").arg(disk_list[i].used));
        ui->TW_disk->setItem(i,2,item_2);

        QTableWidgetItem* item_3 = new QTableWidgetItem();
        item_3->setText(QString("%1 MB").arg(disk_list[i].remain));
        ui->TW_disk->setItem(i,3,item_3);

        QTableWidgetItem* item_4 = new QTableWidgetItem();
        item_4->setText(QString("%1 MB").arg(disk_list[i].total));
        ui->TW_disk->setItem(i,4,item_4);
    }
}

void Widget::showPC()
{
    ui->LE_pc_OS->setText(pc_data.OS_name);
    ui->LE_pc_os_version->setText(pc_data.OS_version);
}

void Widget::t_timeout_slot()
{
    getViewDatas();
    showViewDatas();

//    t->stop();
}

void Widget::on_test_clicked()
{
    winFinder->getCpuInfo();
}

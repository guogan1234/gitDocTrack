#include "widget.h"
#include "ui_widget.h"
#include <QComboBox>

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

void Widget::on_SBJM_Btn_clicked()
{
    ui->blankW->setVisible(false);

    ui->middle_w1->setVisible(true);
    ui->middle_w2->setVisible(true);
    ui->middle_w3->setVisible(true);

    //初始化设备建模页面
    InitLeft();
}

void Widget::specialtyComboChange(int index)
{
    qDebug("index--%d",index);
    if(index == -1){
        return;
    }
    //
    QTreeWidgetItem* item = ui->leftTree->currentItem();
    item->takeChildren();

    switch (index) {
    case 0:
        for(int i = 0;i<10;i++){
            QStringList list_1;
            if(i == 0){
                list_1<<"35KV出线";
            }else if(i == 1){
                list_1<<"35KV母联";
            }else {
                list_1<<"35KV整流变";
            }
            QTreeWidgetItem* top_1 = new QTreeWidgetItem(list_1);
            top_1->setData(0,Qt::WhatsThisRole,"1");
//            top_1->setSizeHint(0,QSize(200,25));
//            top_1->setSizeHint(1,QSize(100,25));

            QStringList boxList;
            boxList<<"林场"<<"星火路"<<"天润城";
            QComboBox* box_1 = new QComboBox(this);
            box_1->addItems(boxList);
            box_1->setCurrentIndex(-1);
            connect(box_1,SIGNAL(currentIndexChanged(int)),this,SLOT(deviceComboChange(int)));

            item->addChild(top_1);
            ui->leftTree->setItemWidget(top_1,1,box_1);
        }
        break;
    case 1:
        for(int i = 0;i<10;i++){
            QStringList list_1;
            if(i == 0){
                list_1<<"BAS出线";
            }else if(i == 1){
                list_1<<"BAS母联";
            }
            QTreeWidgetItem* top_1 = new QTreeWidgetItem(list_1);
            top_1->setData(0,Qt::WhatsThisRole,"1");
//            top_1->setSizeHint(0,QSize(200,25));
//            top_1->setSizeHint(1,QSize(100,25));

            QStringList boxList;
            boxList<<"林场"<<"星火路"<<"天润城";
            QComboBox* box_1 = new QComboBox(this);
            box_1->addItems(boxList);
            box_1->setCurrentIndex(-1);
            connect(box_1,SIGNAL(currentIndexChanged(int)),this,SLOT(deviceComboChange(int)));

            item->addChild(top_1);
            ui->leftTree->setItemWidget(top_1,1,box_1);
        }
        break;
    default:
        break;
    }

    //展开节点
    item->setExpanded(true);
}

void Widget::deviceComboChange(int index)
{
    qDebug("index--%d",index);
    if(index == -1){
        return;
    }
    //
    QTreeWidgetItem* item = ui->leftTree->currentItem();
    item->takeChildren();

    switch (index) {
    case 0:
        for(int i = 0;i<10;i++){
            QStringList list_1;
            if(i == 0){
                list_1<<"林场：103出线";
            }else if(i == 1){
                list_1<<"林场：母联";
            }else {
                list_1<<"林场：整流变";
            }
            QTreeWidgetItem* top_1 = new QTreeWidgetItem(list_1);
            top_1->setData(0,Qt::WhatsThisRole,"2");
//            top_1->setSizeHint(0,QSize(200,25));
//            top_1->setSizeHint(1,QSize(100,25));

            item->addChild(top_1);
        }
        break;
    case 1:
        for(int i = 0;i<10;i++){
            QStringList list_1;
            if(i == 0){
                list_1<<"星火路：103出线";
            }else if(i == 1){
                list_1<<"星火路：母联";
            }else {
                list_1<<"星火路：整流变";
            }
            QTreeWidgetItem* top_1 = new QTreeWidgetItem(list_1);
            top_1->setData(0,Qt::WhatsThisRole,"2");
//            top_1->setSizeHint(0,QSize(200,25));
//            top_1->setSizeHint(1,QSize(100,25));

            item->addChild(top_1);
        }
        break;
    default:
        break;
    }

    item->setExpanded(true);
}

void Widget::Init()
{
    ui->addSpecialPt->setVisible(false);

    //首次隐藏中间窗体
    ui->middle_w1->setVisible(false);
    ui->middle_w2->setVisible(false);
    ui->middle_w3->setVisible(false);

    InitRight();
}

void Widget::InitFlags()
{
    ui->leftTree->setAlternatingRowColors(true);
}

void Widget::InitLeft()
{
//    //局部变量初始化方式一
//    QStringList list_1;
//    list_1<<"建模专业";
//    QTreeWidgetItem* top_1 = new QTreeWidgetItem(list_1);
    //局部变量初始化方式二
    QTreeWidgetItem* top_1 = new QTreeWidgetItem(QStringList()<<"建模专业");
    top_1->setData(0,Qt::WhatsThisRole,"0");
//    top_1->setSizeHint(0,QSize(300,25));
//    top_1->setSizeHint(1,QSize(100,25));

    QStringList boxList;
    boxList<<"PSCADA"<<"BAS"<<"FAS"<<"ATS"<<"PA"<<"PIS"<<"CCTV"<<"AFC";
    QComboBox* box_1 = new QComboBox(this);
    box_1->addItems(boxList);
    box_1->setCurrentIndex(-1);
    connect(box_1,SIGNAL(currentIndexChanged(int)),this,SLOT(specialtyComboChange(int)));

    ui->leftTree->addTopLevelItem(top_1);
    ui->leftTree->setItemWidget(top_1,1,box_1);

    //设置tree当前节点
    ui->leftTree->setCurrentItem(top_1);

//    //test--使用相同的子部件指针，会抢走原来的子部件
//    QStringList list_1_1;
//    list_1_1<<"建模专业2";
//    QTreeWidgetItem* top_2 = new QTreeWidgetItem(list_1_1);
//    ui->leftTree->addTopLevelItem(top_2);
    //    ui->leftTree->setItemWidget(top_2,1,box_1);
}

void Widget::InitMiddle()
{
    ui->middleList->setSpacing(2);
    ui->middleList->clear();

    QListWidgetItem* item_1 = new QListWidgetItem("本侧电压信号");
    item_1->setBackgroundColor(Qt::yellow);

    QListWidgetItem* item_2 = new QListWidgetItem("过流保护");
    item_2->setBackgroundColor(Qt::yellow);

    QListWidgetItem* item_3 = new QListWidgetItem("保护装置闭锁");
    item_3->setBackgroundColor(Qt::yellow);

    QListWidgetItem* item_4 = new QListWidgetItem("速断保护");
    item_4->setBackgroundColor(Qt::yellow);

    QListWidgetItem* item_5 = new QListWidgetItem("充电保护");
    item_5->setBackgroundColor(Qt::yellow);

    QListWidgetItem* item_6 = new QListWidgetItem("事故总信号");
    item_6->setBackgroundColor(Qt::yellow);

    ui->middleList->addItem(item_1);
    ui->middleList->addItem(item_2);
    ui->middleList->addItem(item_3);
    ui->middleList->addItem(item_4);
    ui->middleList->addItem(item_5);
    ui->middleList->addItem(item_6);
}

void Widget::InitRight()
{
    ui->rightTree->expandAll();
}

void Widget::InitMiddle2()
{
    QListWidgetItem* item_1 = new QListWidgetItem("断路器位置");
    item_1->setBackgroundColor(Qt::darkYellow);

    QListWidgetItem* item_2 = new QListWidgetItem("A相电流");
    item_2->setBackgroundColor(Qt::darkYellow);

    QListWidgetItem* item_3 = new QListWidgetItem("频率");
    item_3->setBackgroundColor(Qt::darkYellow);

    QListWidgetItem* item_4 = new QListWidgetItem("有功功率");
    item_4->setBackgroundColor(Qt::darkYellow);

    QListWidgetItem* item_5 = new QListWidgetItem("无功功率");
    item_5->setBackgroundColor(Qt::darkYellow);

    QListWidgetItem* item_6 = new QListWidgetItem("功率因素");
    item_6->setBackgroundColor(Qt::darkYellow);

    ui->middleList->addItem(item_1);
    ui->middleList->addItem(item_2);
    ui->middleList->addItem(item_3);
    ui->middleList->addItem(item_4);
    ui->middleList->addItem(item_5);
    ui->middleList->addItem(item_6);
}

void Widget::on_leftTree_itemClicked(QTreeWidgetItem *item, int column)
{
    QString str = item->data(0,Qt::WhatsThisRole).toString();
    qDebug("WhatsThisRole--%s",qPrintable(str));
    if(str == "0"){
        return;
    }else if(str == "1"){
        InitMiddle();
    }else if(str == "2"){
        InitMiddle();
        InitMiddle2();
    }
}

void Widget::on_test_clicked()
{

}

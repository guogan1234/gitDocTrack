#include "widget.h"
#include "ui_widget.h"

#include "testform.h"

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
    InitLeft();
    InitMiddle();
    InitRight();
}

void Widget::InitLeft()
{
    ui->treeWidget->setAlternatingRowColors(true);

    QStringList list_1;
    list_1<<"35KV出线"<<"";
    QTreeWidgetItem* top_1 = new QTreeWidgetItem(list_1);
    top_1->setCheckState(0,Qt::PartiallyChecked);

    QComboBox* box_1 = new QComboBox(this);
    connect(box_1,SIGNAL(currentIndexChanged(int)),this,SLOT(fiterChange(int)));
    QStringList list_2;
    list_2<<"林场"<<"星火路"<<"天润城";
    box_1->addItems(list_2);

    QStringList list_3;
    list_3<<"fiter3"<<"fiter2"<<"fiter1";
    QComboBox* box_2 = new QComboBox();
    box_2->addItems(list_3);

    ui->treeWidget->addTopLevelItem(top_1);
    ui->treeWidget->setItemWidget(top_1,1,box_1);
//    ui->treeWidget->setItemWidget(top_1,0,box_2);

    //设置条项选中
    ui->treeWidget->setCurrentItem(top_1,0);
}

void Widget::InitMiddle()
{

}

void Widget::InitRight()
{
    ui->treeWidget_2->setAlternatingRowColors(true);
}

void Widget::fiterChange(int index)
{
    qDebug("fiterChange--%d",index);
    if(index == 0){
        return;
    }
    index++;
    QTreeWidgetItem* item = ui->treeWidget->currentItem();
    item->takeChildren();
    QStringList list;
    list<<"设备";
    for(int i = 0;i<index;i++){
        QTreeWidgetItem* temp = new QTreeWidgetItem(list);
        item->addChild(temp);
    }
    item->setExpanded(true);
}

void Widget::on_test_clicked()
{
    TestForm* form = new TestForm();
    form->show();
}

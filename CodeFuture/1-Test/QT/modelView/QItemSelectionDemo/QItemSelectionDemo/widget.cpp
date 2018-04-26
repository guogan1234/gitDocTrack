#include "widget.h"
#include "ui_widget.h"
#include <QStandardItemModel>

#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);
//    //tableView初始时没有itemModel(数据模型)，内置的selectModel(选择模型)初始的时候为null，不能建立连接
//    connect(ui->tableView->selectionModel(),SIGNAL(selectionChanged(QItemSelection,QItemSelection)),this,SLOT(selectionChangeSlot(QItemSelection,QItemSelection)));

    InitShow();
}

Widget::~Widget()
{
    delete ui;
}

void Widget::InitShow()
{
    QStandardItemModel* model = new QStandardItemModel(10,10);
    ui->tableView->setModel(model);
    connect(ui->tableView->selectionModel(),SIGNAL(selectionChanged(QItemSelection,QItemSelection)),this,SLOT(selectionChangeSlot(QItemSelection,QItemSelection)));
    connect(ui->tableView->selectionModel(),SIGNAL(currentChanged(QModelIndex,QModelIndex)),this,SLOT(currentChangeSlot(QModelIndex,QModelIndex)));

    //设置ItemSelection
//    QItemSelection* itemSelection = new QItemSelection();
    QItemSelection itemSelection;
    QModelIndex startIndex = model->index(0,0,QModelIndex());
    QModelIndex endIndex = model->index(7,7,QModelIndex());
    itemSelection.select(startIndex,endIndex);

    QItemSelection currentSelection;
    QModelIndex currentIndex = model->index(1,1);
    currentSelection.select(currentIndex,currentIndex);

    //QItemSelectionModel只能通过其他QItemSelectionModel初始化
//    QItemSelectionModel* selectModel = new QItemSelectionModel();
    QItemSelectionModel* selectModel = ui->tableView->selectionModel();
//    selectModel->select(currentIndex,QItemSelectionModel::Current);
    selectModel->select(itemSelection,QItemSelectionModel::Select);
    selectModel->setCurrentIndex(currentIndex,QItemSelectionModel::Select);
    //    ui->tableView->setSelectionModel(selectModel);
}

void Widget::clearModelData()
{
    for(int i = 0;i<ui->tableView->model()->rowCount();i++){
        for(int j = 0;j<ui->tableView->model()->columnCount();j++){
            QModelIndex itemIndex = ui->tableView->model()->index(i,j);
            ui->tableView->model()->setData(itemIndex,"0");
        }
    }
}

void Widget::on_test_clicked()
{
//    clearModelData();

    QItemSelectionModel* selectionModel = ui->tableView->selectionModel();
    QModelIndexList list = selectionModel->selectedIndexes();
    qDebug()<<"len:"<<list.length();
}

//第一个是新选择的项目，第二个是刚刚被取消选择的项目
void Widget::selectionChangeSlot(QItemSelection select, QItemSelection deselect)
{
    clearModelData();

    qDebug()<<"selectionChangeSlot...[";
    QModelIndexList list = select.indexes();
    QModelIndexList prevList = deselect.indexes();
    qDebug()<<"select:"<<list.length()<<"deselect:"<<prevList.length();
    foreach (QModelIndex index, prevList) {
        ui->tableView->model()->setData(index,"deselect");
    }
    foreach (QModelIndex index, list) {
        ui->tableView->model()->setData(index,"select");
    }

    qDebug()<<"selectionChangeSlot.]"<<endl;
}

void Widget::currentChangeSlot(QModelIndex current, QModelIndex prev)
{
    clearModelData();

    qDebug()<<"currentChangeSlot...[";
    qDebug()<<"prev:"<<prev.row()<<","<<prev.column();
    qDebug()<<"now:"<<current.row()<<","<<current.column();
    ui->tableView->model()->setData(current,"current");

    qDebug()<<"currentChangeSlot.]"<<endl;
}

void Widget::on_changeCurrent_clicked()
{
    qDebug()<<"on_changeCurrent_clicked...[";
    QModelIndex newIndex = ui->tableView->model()->index(8,8);//未被选择索引
//    QModelIndex newIndex = ui->tableView->model()->index(2,2);//已被选择索引
    qDebug()<<"#:"<<newIndex.row()<<","<<newIndex.column();
    QItemSelectionModel* selectionModel = ui->tableView->selectionModel();
    selectionModel->select(newIndex,QItemSelectionModel::SelectCurrent);//更新当前选择索引集，并将该索引设置到选择索引集中。已存在，选集不变；未存在，新增
//    selectionModel->select(newIndex,QItemSelectionModel::Current);//只是刷新选集，但并不会新增该索引
//    selectionModel->select(newIndex,QItemSelectionModel::Select);//若该索引已被选择，没有效果；未被选择，则新增到选择索引集
//    selectionModel->select(newIndex,QItemSelectionModel::Clear);//只会清空已选择索引
//    selectionModel->select(newIndex,QItemSelectionModel::ClearAndSelect);//清空已选择索引，并将该索引设置到选择索引集中
    qDebug()<<"on_changeCurrent_clicked.]"<<endl;
}

void Widget::on_changeSelecttion_clicked()
{
    qDebug()<<"on_changeSelecttion_clicked...[";
    QModelIndex startIndex = ui->tableView->model()->index(1,4);
    QModelIndex endIndex = ui->tableView->model()->index(2,5);
    QItemSelection itemSelection;
    itemSelection.select(startIndex,endIndex);

    QItemSelectionModel* selectionModel = ui->tableView->selectionModel();
    selectionModel->select(itemSelection,QItemSelectionModel::Toggle);

    qDebug()<<"on_changeSelecttion_clicked.]";
}

#include "mytreemodel.h"

MyTreeModel::MyTreeModel(QObject *parent) :
    QAbstractItemModel(parent)
{
    Init();
}

int MyTreeModel::columnCount(const QModelIndex &parent) const
{
    //name,parent,desc(3 column)
    return 3;
}

QVariant MyTreeModel::data(const QModelIndex &index, int role) const
{
    //获取父节点索引和name
    QModelIndex parent = index.parent();
    QString parentName = this->data(parent,Qt::DisplayRole).toString();

    //获取本节点在父节点的位置(行数)
    int row = index.row();
    QString name = "";
    if(parentName == "line1"){//父节点为line1
        name = stationList.at(row)->name;
    }else if(parentName == "station1"){
        name = deviceList1.at(row)->name;
    }else if(parentName == "station2"){
        name = deviceList2.at(row)->name;
    }
    return name;
}

QModelIndex MyTreeModel::index(int row, int column, const QModelIndex &parent) const
{
    //获取父节点的name
    QString key = this->data(parent,Qt::DisplayRole).toString();
}

QModelIndex MyTreeModel::parent(const QModelIndex &child) const
{

}

int MyTreeModel::rowCount(const QModelIndex &parent) const
{

}

void MyTreeModel::Init()
{
//    list = new QList<dev*>();
    InitTreeData();
}

void MyTreeModel::InitTreeData()
{
    //line
    dev* line = new dev();
    line->name = "line1";
    line->desc = "desc--line1";
//    list.append(line);
//    lineList = new QList<dev*>();
    lineList.append(line);

    //station
    dev* station1 = new dev();
    station1->name = "station1";
    station1->parent = "line1";
    station1->desc = "desc--station1";

    dev* station2 = new dev();
    station2->name = "station2";
    station2->parent = "line1";
    station2->desc = "desc--station2";

//    list.append(station1);
//    list.append(station2);
//    stationList = QList<dev*>();
    stationList.append(station1);
    stationList.append(station2);

    //device
    dev* device1_1 = new dev();
    device1_1->name = "device1_1";
    device1_1->parent = "station1";
    device1_1->desc = "desc--device1_1";

    dev* device1_2 = new dev();
    device1_2->name = "device1_2";
    device1_2->parent = "station1";
    device1_2->desc = "desc--device1_2";

//    list.append(device1_1);
//    list.append(device1_2);
//    deviceList1 = QList<dev*>();
    deviceList1.append(device1_1);
    deviceList1.append(device1_2);

    dev* device2_1 = new dev();
    device2_1->name = "device2_1";
    device2_1->parent = "station2";
    device2_1->desc = "desc--device2_1";

    dev* device2_2 = new dev();
    device2_2->name = "device2_2";
    device2_2->parent = "station2";
    device2_2->desc = "desc--device2_2";

    dev* device2_3 = new dev();
    device2_3->name = "device2_3";
    device2_3->parent = "station2";
    device2_3->desc = "desc--device2_3";

//    list.append(device2_1);
//    list.append(device2_2);
//    list.append(device2_3);
//    deviceList2 = new QList<dev*>();
    deviceList2.append(device2_1);
    deviceList2.append(device2_2);
    deviceList2.append(device2_3);
}

#include "mytreemodel2.h"

#include <QDebug>

MyTreeModel2::MyTreeModel2(QObject *parent) :
    QAbstractItemModel(parent)
{
    InitData();
}

int MyTreeModel2::columnCount(const QModelIndex &parent) const
{
    return 3;
}

QVariant MyTreeModel2::data(const QModelIndex &index, int role) const
{
    qDebug()<<"data...";
    MyTreeItem2* item = static_cast<MyTreeItem2*>(index.internalPointer());
    return item->name;
    qDebug()<<"data.";
}

QModelIndex MyTreeModel2::index(int row, int column, const QModelIndex &parent) const
{
    qDebug()<<"index..."<<row<<" "<<column;
//    return parent.child(row,0);
    if(parent.isValid()){
        qDebug()<<"isValid...";
        qDebug()<<"before get...";
        MyTreeItem2* item = static_cast<MyTreeItem2*>(parent.internalPointer());
        qDebug()<<"after get..."<<item->children.length();
        MyTreeItem2* theItem = item->children.at(row);
        return createIndex(row,0,theItem);
    }
    qDebug()<<"index.";
}

QModelIndex MyTreeModel2::parent(const QModelIndex &child) const
{
    qDebug()<<"parent...";
    MyTreeItem2* item = static_cast<MyTreeItem2*>(child.internalPointer());
    MyTreeItem2* parentItem = item->parent;
    if(parentItem == NULL){//item为root
        return QModelIndex();
    }
    MyTreeItem2* gp_item = parentItem->parent;
    if(gp_item == NULL){//item的父节点为root
        int row = parentItem->children.indexOf(item);
        return createIndex(row,0,parentItem);
    }else {//item的父节点不为root，是其他有效节点，一样的处理
        int row = parentItem->children.indexOf(item);
        return createIndex(row,0,parentItem);
    }
    qDebug()<<"parent.";
}

int MyTreeModel2::rowCount(const QModelIndex &parent) const
{
    MyTreeItem2* item = static_cast<MyTreeItem2*>(parent.internalPointer());
    return item->children.length();
}

void MyTreeModel2::InitData()
{
    qDebug()<<"InitData...";
    root = new MyTreeItem2();
    root->parent = NULL;

    MyTreeItem2* line = new MyTreeItem2();
    line->parent = root;
    line->name = "line";
    line->shortName = "line_1";
    line->desc = "desc--line";

    MyTreeItem2* station_1 = new MyTreeItem2();
    station_1->parent = line;
    station_1->name = "station1";
    station_1->shortName = "station_1";
    station_1->desc = "desc--station1";

    MyTreeItem2* station_2 = new MyTreeItem2();
    station_2->parent = line;
    station_2->name = "station2";
    station_2->shortName = "station_2";
    station_2->desc = "desc--station2";

    line->children.append(station_1);
    line->children.append(station_2);

    root->children.append(line);

    MyTreeItem2* device_1 = new MyTreeItem2();
    device_1->parent = station_1;
    device_1->name = "device1";
    device_1->shortName = "device_1";
    device_1->desc = "desc--device1";

    MyTreeItem2* device_2 = new MyTreeItem2();
    device_2->parent = station_1;
    device_2->name = "device2";
    device_2->shortName = "device_2";
    device_2->desc = "desc--device2";

    station_1->children.append(device_1);
    station_1->children.append(device_2);

    MyTreeItem2* device_3 = new MyTreeItem2();
    device_3->parent = station_2;
    device_3->name = "device3";
    device_3->shortName = "device_3";
    device_3->desc = "desc--device3";

    MyTreeItem2* device_4 = new MyTreeItem2();
    device_4->parent = station_2;
    device_4->name = "device4";
    device_4->shortName = "device_4";
    device_4->desc = "desc--device4";

    station_2->children.append(device_3);
    station_2->children.append(device_4);

    qDebug()<<"InitData.";
}

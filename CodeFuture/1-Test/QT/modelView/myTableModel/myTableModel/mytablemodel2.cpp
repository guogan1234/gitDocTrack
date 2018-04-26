#include "mytablemodel2.h"

#include <QDebug>

MyTableModel2::MyTableModel2(QObject *parent) :
    QAbstractTableModel(parent)
{
    InitTableData();
}

int MyTableModel2::columnCount(const QModelIndex &parent) const
{
    return 3;
}

QVariant MyTableModel2::data(const QModelIndex &index, int role) const
{
    qDebug()<<"data...";

    if(role == Qt::DisplayRole){
    int row = index.row();
    int col = index.column();
    dev* d = list.at(row);
    if(col >= 3){
        qDebug()<<"col is out of 3.";
    }else {
        if(col == 0)
            return d->name;
        if(col == 1)
            return d->parent;
        if(col == 2)
            return d->desc;
    }
    }else if(role == Qt::CheckStateRole){
        //去掉条项中默认的自带复选框，测试成功
        qDebug()<<"data:CheckStateRole";
        return QVariant();
    }
}

int MyTableModel2::rowCount(const QModelIndex &parent) const
{
    return list.length();
}

void MyTableModel2::InitTableData()
{
    for(int i = 0;i<10;i++){
        dev* temp = new dev();
        QString name = "name_%1";
        QString parent = "parent_%1";
        QString desc = "desc2_%1";
        temp->name = name.arg(i);
        temp->parent = parent.arg(i);
        temp->desc = desc.arg(i);

        list.append(temp);
    }
}

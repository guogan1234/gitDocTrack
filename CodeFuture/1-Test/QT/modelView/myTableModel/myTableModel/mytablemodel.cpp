#include "mytablemodel.h"

#include <QDebug>

MyTableModel::MyTableModel(QObject *parent) :
    QAbstractItemModel(parent)
{
    InitTableData();
}

int MyTableModel::columnCount(const QModelIndex &parent) const
{
    return 3;
}

QVariant MyTableModel::data(const QModelIndex &index, int role) const
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

//若自定义的表格模型继承QAbstractTableModel，则可以不用实现此index()方法
QModelIndex MyTableModel::index(int row, int column, const QModelIndex &parent) const
{
    qDebug()<<"index...";

    //根据QT源码QAbstractTableModel的index()方法实现的
    //因为是从QAbstractItemModel继承去实现的自定义表格模型，类似于自己实现QAbstractTableModel
    return createIndex(row,column);
}

QModelIndex MyTableModel::parent(const QModelIndex &child) const
{
    //根据QT源码QAbstractTableModel的parent()方法实现的
    //因为是从QAbstractItemModel继承去实现的自定义表格模型，类似于自己实现QAbstractTableModel
    return QModelIndex();
}

int MyTableModel::rowCount(const QModelIndex &parent) const
{
    return list.length();
}

Qt::ItemFlags MyTableModel::flags(const QModelIndex &index) const
{
    qDebug()<<"flags...";

    //改变每个条项的ItemFlags
    Qt::ItemFlags theFlags = Qt::ItemIsSelectable|Qt::ItemIsEditable|Qt::ItemIsEnabled;
    return theFlags;

//    //不做任何处理，调用QT源码的处理方法
//    return QAbstractItemModel::flags(index);
}

bool MyTableModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    qDebug()<<"setData...";

    QAbstractItemModel::setData(index,value,role);

    if(role == Qt::CheckStateRole){
        qDebug()<<"setData...";
        QAbstractItemModel::setData(index,QVariant(),role);
    }
    return true;
}

void MyTableModel::InitTableData()
{
//    list = new QList<dev*>();

    for(int i = 0;i<10;i++){
        dev* temp = new dev();
        QString name = "name_%1";
        QString parent = "parent_%1";
        QString desc = "desc_%1";
        temp->name = name.arg(i);
        temp->parent = parent.arg(i);
        temp->desc = desc.arg(i);

        list.append(temp);
    }
}

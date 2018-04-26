#ifndef MYTABLEMODEL2_H
#define MYTABLEMODEL2_H

#include <QAbstractTableModel>
#include "info.h"

class MyTableModel2 : public QAbstractTableModel
{
        Q_OBJECT
    public:
        explicit MyTableModel2(QObject *parent = 0);
        //QAbstractItemModel所有的纯虚函数
        int columnCount(const QModelIndex &parent) const;
        QVariant data(const QModelIndex &index, int role) const;
        int rowCount(const QModelIndex &parent) const;
//        //QT文档记载有误，QAbstractItemModel的parent()纯虚函数也被QAbstractTableModel重新实现了(已查看QT源码证实)
//        QModelIndex parent(const QModelIndex &child) const;

    signals:

    public slots:

    private:
        QList<dev*> list;

        void InitTableData();
};

#endif // MYTABLEMODEL2_H

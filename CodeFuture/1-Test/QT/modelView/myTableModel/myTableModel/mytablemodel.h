#ifndef MYTABLEMODEL_H
#define MYTABLEMODEL_H

#include <QAbstractItemModel>
#include "info.h"

class MyTableModel : public QAbstractItemModel
{
        Q_OBJECT
    public:
        explicit MyTableModel(QObject *parent = 0);

        //QAbstractItemModel所有的纯虚函数
        int columnCount(const QModelIndex &parent) const;
        QVariant data(const QModelIndex &index, int role) const;
        QModelIndex index(int row, int column, const QModelIndex &parent) const;
        QModelIndex parent(const QModelIndex &child) const;
        int rowCount(const QModelIndex &parent) const;

        //尝试去掉条项中默认的自带复选框，没有成功(已经成功，并非重载以下虚函数实现，而是在上面data纯虚函数中)
        //重实现(重载)的虚函数
        Qt::ItemFlags flags(const QModelIndex &index) const;
        bool setData(const QModelIndex &index, const QVariant &value, int role);

    signals:

    public slots:

    private:
        QList<dev*> list;

        void InitTableData();
};

#endif // MYTABLEMODEL_H

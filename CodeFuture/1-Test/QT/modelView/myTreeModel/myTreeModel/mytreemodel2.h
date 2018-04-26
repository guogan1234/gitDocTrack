#ifndef MYTREEMODEL2_H
#define MYTREEMODEL2_H

#include <QAbstractItemModel>
#include "mytreeitem2.h"

class MyTreeModel2 : public QAbstractItemModel
{
        Q_OBJECT
    public:
        explicit MyTreeModel2(QObject *parent = 0);

        //pure virtual
        int columnCount(const QModelIndex &parent) const;
        QVariant data(const QModelIndex &index, int role) const;
        QModelIndex index(int row, int column, const QModelIndex &parent) const;
        QModelIndex parent(const QModelIndex &child) const;
        int rowCount(const QModelIndex &parent) const;

    signals:

    public slots:

    private:
        void InitData();

        MyTreeItem2* root;
};

#endif // MYTREEMODEL2_H

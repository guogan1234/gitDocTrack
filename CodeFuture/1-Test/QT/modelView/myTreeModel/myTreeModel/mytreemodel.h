#ifndef MYTREEMODEL_H
#define MYTREEMODEL_H

#include <QAbstractItemModel>
#include "info.h"

//自定义树模型未实现成功，自己凭空想的，方法不对
class MyTreeModel : public QAbstractItemModel
{
        Q_OBJECT
    public:
        explicit MyTreeModel(QObject *parent = 0);

        int columnCount(const QModelIndex &parent) const;
        QVariant data(const QModelIndex &index, int role) const;
        QModelIndex index(int row, int column, const QModelIndex &parent) const;
        QModelIndex parent(const QModelIndex &child) const;
        int rowCount(const QModelIndex &parent) const;

    signals:

    public slots:

    private:
        //用一个list存放数据
        QList<dev*> list;
        //用3个list存放数据
        QList<dev*> lineList;
        QList<dev*> stationList;
        QList<dev*> deviceList1;
        QList<dev*> deviceList2;

        void Init();
        void InitTreeData();
};

#endif // MYTREEMODEL_H

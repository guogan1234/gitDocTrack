#ifndef MYTREEITEM2_H
#define MYTREEITEM2_H

#include <QString>
#include <QList>

class MyTreeItem2
{
    public:
        MyTreeItem2();

        //简化，属性全为public，不做封装
        QString name;
        QString shortName;
        QString desc;

        MyTreeItem2* parent;
        QList<MyTreeItem2*> children;
};

#endif // MYTREEITEM2_H

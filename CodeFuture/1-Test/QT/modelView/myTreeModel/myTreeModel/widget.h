#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include "mytreemodel.h"
#include "mytreemodel2.h"

namespace Ui {
class Widget;
}

class Widget : public QWidget
{
        Q_OBJECT

    public:
        explicit Widget(QWidget *parent = 0);
        ~Widget();

    private:
        Ui::Widget *ui;

        MyTreeModel* myTreeModel;
        MyTreeModel2* myTreeModel2;

        void Init();
};

#endif // WIDGET_H

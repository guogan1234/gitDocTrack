#ifndef MYSHAREDWIDGET_H
#define MYSHAREDWIDGET_H

#include <QWidget>
#include "mysharedwidgetdll_global.h"

namespace Ui {
class MySharedWidget;
}

class MYSHAREDWIDGETDLLSHARED_EXPORT MySharedWidget : public QWidget
{
        Q_OBJECT

    public:
        explicit MySharedWidget(QWidget *parent = 0);
        ~MySharedWidget();

    private slots:
        void on_pushButton_clicked();

    private:
        Ui::MySharedWidget *ui;
};

#endif // MYSHAREDWIDGET_H

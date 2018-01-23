#ifndef MYQTSHAREDLLWIDGET_H
#define MYQTSHAREDLLWIDGET_H

#include <QWidget>
#include "sharedllwidget_global.h"

namespace Ui {
class myQtShareDllWidget;
}

class SHAREDLLWIDGETSHARED_EXPORT myQtShareDllWidget : public QWidget
{
        Q_OBJECT

    public:
        explicit myQtShareDllWidget(QWidget *parent = 0);
        ~myQtShareDllWidget();

    private:
        Ui::myQtShareDllWidget *ui;
};

#endif // MYQTSHAREDLLWIDGET_H

#ifndef MYQTSHAREDLLWIDGET_H
#define MYQTSHAREDLLWIDGET_H

#include <QWidget>

namespace Ui {
class myQtShareDllWidget;
}

class myQtShareDllWidget : public QWidget
{
        Q_OBJECT

    public:
        explicit myQtShareDllWidget(QWidget *parent = 0);
        ~myQtShareDllWidget();

    private:
        Ui::myQtShareDllWidget *ui;
};

#endif // MYQTSHAREDLLWIDGET_H

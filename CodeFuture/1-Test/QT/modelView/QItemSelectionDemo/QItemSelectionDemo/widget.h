#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QItemSelection>

namespace Ui {
class Widget;
}

class Widget : public QWidget
{
        Q_OBJECT

    public:
        explicit Widget(QWidget *parent = 0);
        ~Widget();

    private slots:
        void on_test_clicked();

        void selectionChangeSlot(QItemSelection select,QItemSelection deselect);
        void currentChangeSlot(QModelIndex current,QModelIndex prev);

        void on_changeCurrent_clicked();

        void on_changeSelecttion_clicked();

    private:
        Ui::Widget *ui;

        void InitShow();

        void clearModelData();
};

#endif // WIDGET_H

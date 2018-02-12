#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QTreeWidgetItem>

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
        void on_SBJM_Btn_clicked();

        void specialtyComboChange(int index);
        void deviceComboChange(int index);

        void on_leftTree_itemClicked(QTreeWidgetItem *item, int column);

        void on_test_clicked();

    private:
        Ui::Widget *ui;

        void Init();
        void InitFlags();
        void InitLeft();
        void InitMiddle();
        void InitRight();

        void InitMiddle2();
};

#endif // WIDGET_H

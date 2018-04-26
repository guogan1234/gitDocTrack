#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>

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
        void on_pushButton_clicked();

        void loadStartSlot();
        void jsWindowCleared();
        void loadFinishedSlot(bool b);

        void on_pushButton_2_clicked();

    private:
        Ui::Widget *ui;

        void creatChartsDatas();

        void testQStringArg();
        void testJson();
};

#endif // WIDGET_H

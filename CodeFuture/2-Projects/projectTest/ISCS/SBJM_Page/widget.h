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

    private:
        Ui::Widget *ui;

        void Init();
        void InitLeft();
        void InitMiddle();
        void InitRight();

    private slots:
        void fiterChange(int index);
        void on_test_clicked();
};

#endif // WIDGET_H

#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QWebPage>
#include "mywebpage.h"

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

        void loadFinishedSlot(bool b);
        void loadStartSlot();

        void on_pushButton_2_clicked();

    private:
        Ui::Widget *ui;

        QWebPage webPage;
        myWebPage myPage;
};

#endif // WIDGET_H

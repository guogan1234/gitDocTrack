#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include "menuproxy.h"

namespace Ui {
class Widget;
}

class Widget : public QWidget
{
        Q_OBJECT

    public:
        explicit Widget(QWidget *parent = 0);
        ~Widget();

        void jsCallQObject();

    private slots:
        void on_pushButton_clicked();

        void on_pushButton_2_clicked();

        void jsWindowClear();
        void loadMenuHtmlFinished(bool b);

        void jsCallQObjectSlot();

        void loadStartSlot();

    public slots:
        void jsCallQObjectSlot_public();

    private:
        Ui::Widget *ui;

        void InitShow(int flag);

        QString execPath;
        QString menuHtmlPath;
        QString jsFilePath;

        void InitParams();

        menuProxy* menuProxyClass;
};

#endif // WIDGET_H

#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QtWebKitWidgets/QWebView>

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

        QWebView* web;

        void InitLayout();
        void InitComponent();
};

#endif // WIDGET_H

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
        void on_toolButton_clicked();

        void mainHtmlLoadStartSlot();
        void mainHtmlLoadFinishedSlot(bool b);
        void addQObjectToJS();

    public slots:
        void changeLoad(QString subPath);


    private:
        Ui::Widget *ui;

        void Init();
        void InitParams();
        void InitLoad();
        void InitConn();

        QString execPath;
        QString menuHtmlPath;
        QString mainHtmlPath;

        QString baseWebUrl;
};

#endif // WIDGET_H

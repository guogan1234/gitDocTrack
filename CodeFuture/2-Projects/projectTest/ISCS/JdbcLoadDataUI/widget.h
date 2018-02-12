#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QTimer>
#include <QTime>

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
        void on_loadPage_clicked();

        void on_loadAll_clicked();

        void finishedSlot(QNetworkReply* reply);

        void on_pushButton_clicked();

    private:
        Ui::Widget *ui;

        QTimer* t1;
        QTime t;
        QNetworkAccessManager* manager;
};

#endif // WIDGET_H

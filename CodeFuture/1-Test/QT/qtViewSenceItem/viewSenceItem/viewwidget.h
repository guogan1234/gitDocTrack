#ifndef VIEWWIDGET_H
#define VIEWWIDGET_H

#include <QWidget>
#include <QGraphicsScene>
#include <QGraphicsView>

class viewWidget : public QWidget
{
        Q_OBJECT
    public:
        explicit viewWidget(QWidget *parent = 0);

    signals:

    public slots:

    private:
        QGraphicsScene* sence;
        QGraphicsView* view;

        void Init();
};

#endif // VIEWWIDGET_H

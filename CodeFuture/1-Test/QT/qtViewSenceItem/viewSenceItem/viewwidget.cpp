#include "viewwidget.h"
#include <QHBoxLayout>

#include <QGraphicsLinearLayout>
#include <QGraphicsWidget>
#include "eclipseitem.h"

#include <QDebug>

viewWidget::viewWidget(QWidget *parent) :
    QWidget(parent)
{
    Init();
}

void viewWidget::Init()
{
//    //给图形项增加布局，失败
    EclipseItem* item1 = new EclipseItem(1);
    EclipseItem* item2 = new EclipseItem(2);
//    QGraphicsLinearLayout* item_lay = new QGraphicsLinearLayout();
//    //QGraphicsLinearLayout只能添加 QGraphicsLayoutItem，只有QGraphicsWidget继承自QGraphicsLayoutItem和QGraphicsItem
//    //所以无法增加布局
//    item_lay->addItem(item1);
//    item_lay->addItem(item2);
//    QGraphicsWidget* widget = new QGraphicsWidget();
//    widget->setLayout(item_lay);
    item1->setPos(10,10);

    QRectF rect(0,0,800,800);
    sence = new QGraphicsScene(rect,this);
//    sence = new QGraphicsScene(this);
    sence->setBackgroundBrush(QBrush(Qt::black));
//    sence->addItem(widget);
    sence->addItem(item1);
    sence->addItem(item2);

    qDebug()<<"#:"<<sence->sceneRect();

    //默认QT会把view(视图)中心映射到scene(场景)中心，而item(图形项)和scene(场景)对应左上角都为(0,0),视图和场景大小可以不一样
    view = new QGraphicsView(sence,this);
    view->setBackgroundBrush(QBrush(Qt::gray));//同样是设置场景背景色
//    view->setSceneRect(50,50,400,400);//设置view的可视场景大小，以场景左上角为(0,0)
    view->setSceneRect(0,0,800,800);
//    QHBoxLayout* lay = new QHBoxLayout();
//    lay->addWidget(view);

//    setLayout(lay);
}

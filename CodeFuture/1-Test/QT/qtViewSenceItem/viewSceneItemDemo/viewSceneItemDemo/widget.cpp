#include "widget.h"
#include "ui_widget.h"
#include "myscene.h"
#include "eclipseitem.h"

#include <QGraphicsView>
#include <QDebug>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    Init();
}

Widget::~Widget()
{
    delete ui;
}

void Widget::Init()
{
    EclipseItem* item_1 = new EclipseItem(1);//大矩形
    EclipseItem* item_2 = new EclipseItem(2);//小矩形(在底层)
    EclipseItem* item_3 = new EclipseItem(2);//小矩形(在顶层)
    EclipseItem* item_4 = new EclipseItem(2);
    item_4->setPos(400,400);//将item的左上角顶点放到场景中心
    //Z值大，绘制在顶层
    item_1->setZValue(1.1);
    item_2->setZValue(0.5);
    item_3->setZValue(1.3);

    myScene* scene = new myScene();
    scene->setSceneRect(0,0,800,800);
    scene->setBackgroundBrush(Qt::gray);

    scene->addItem(item_1);
    scene->addItem(item_2);
    scene->addItem(item_3);
    scene->addItem(item_4);

    ui->graphicsView->setScene(scene);
    //把场景的矩形(0,0,200,200)的中心放到view的中心
    //如未设置，把整个场景的矩形中心放到view的中心
    ui->graphicsView->setSceneRect(0,0,200,200);
    //获取view对应的scene位置
//    QPoint pt_1 = ui->graphicsView->viewport()->rect().topLeft();
    QPoint pt_1 = ui->graphicsView->rect().topLeft();
//    QPoint pt_1 = ui->graphicsView->pos();
    QPointF pt_2 = ui->graphicsView->mapToScene(pt_1);
    qDebug()<<"#"<<pt_1<<" "<<pt_2;
    //一个场景可以被多个视图使用
    ui->graphicsView_2->setScene(scene);
}

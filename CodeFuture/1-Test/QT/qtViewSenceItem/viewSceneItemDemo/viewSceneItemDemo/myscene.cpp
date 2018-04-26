#include "myscene.h"
#include <QGraphicsSceneMouseEvent>

#include <QDebug>

myScene::myScene(QObject *parent) :
    QGraphicsScene(parent)
{

}

//void myScene::mouseMoveEvent(QGraphicsSceneMouseEvent *event)
//{
//    qDebug()<<"mouseMoveEvent...";
//}

//void myScene::mousePressEvent(QGraphicsSceneMouseEvent *mouseEvent)
//{
//    qDebug()<<"mousePressEvent...";
//}

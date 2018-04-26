#include "eclipseitem.h"
#include <QGraphicsSceneMouseEvent>
//#include <QFlags>
#include <QCursor>

#include <QDebug>

EclipseItem::EclipseItem(int type)
{
    m_type = type;
//    setFlag(QGraphicsItem::ItemIsMovable|QGraphicsItem::ItemIsSelectable);
//    setFlags(QGraphicsItem::ItemIsMovable|QGraphicsItem::ItemIsSelectable);
}

QRectF EclipseItem::boundingRect() const
{
    QRectF rect;
    if(m_type == 1){
        QRectF r1(0,0,100,100);
        rect = r1;
    }else {
        QRectF r2(0,0,50,50);
        rect = r2;
    }
    return rect;
}

bool bEdge = false;//是否鼠标点击到item1的边缘
int paintCount = 0;
int allPaintCount = 0;
void EclipseItem::paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget)
{
    qDebug()<<"paint--"<<allPaintCount++<<" "<<m_type;
    if(m_type == 1){
        if(bEdge){
//            //模拟QT Creator的设计器实现
//            painter->setPen(Qt::blue);
//            painter->drawRect(0,0,100,100);
//            bEdge = false;//避免多次重绘
//            return;
        }
        qDebug()<<"paint..."<<paintCount++;
        painter->setBrush(QBrush(Qt::blue));
        painter->drawRect(0,0,100,100);
        painter->setPen(Qt::red);
        painter->drawEllipse(0,0,100,100);
        painter->setPen(QPen(QBrush(Qt::red),3));
        painter->drawPoint(90,10);
    }else{
        painter->setBrush(QBrush(Qt::yellow));
        painter->drawRect(10,10,40,40);
        painter->setPen(Qt::green);
        painter->drawEllipse(10,10,40,40);      
    }
}

int moveCount = 0;

QPointF startPt;
QPointF endPt;
void EclipseItem::mouseMoveEvent(QGraphicsSceneMouseEvent *event)
{
//    qDebug()<<"item:mouseMoveEvent..."<<m_type<<" "<<moveCount++;

}

void EclipseItem::mousePressEvent(QGraphicsSceneMouseEvent *event)
{
    qDebug()<<"item:mousePressEvent..."<<m_type;
    //获取光标(热点)相对于桌面的位置
    startPt = QCursor::pos();
    qDebug()<<"item:mousePressEvent "<<startPt<<" "<<event->pos();
    QPointF eventPt = event->pos();
    if(eventPt.x()>= 80 && eventPt.x()<=100){
        bEdge = true;
        qDebug()<<"update...";
        update();
    }
}

void EclipseItem::mouseReleaseEvent(QGraphicsSceneMouseEvent *event)
{
    qDebug()<<"item:mouseReleaseEvent..."<<m_type;
    endPt = QCursor::pos();
    //鼠标未移动，不做任何处理，提升处理效率
    if(startPt == endPt){
        return;
    }
    qDebug()<<"item:mouseReleaseEvent "<<endPt<<" "<<event->pos();
    QPointF addPt = endPt - startPt;
    //获取item在父节点的位置，若无父节点，场景为父节点
    QPointF prevPt = this->pos();
    qDebug()<<"item:mouseReleaseEvent "<<prevPt;
    this->setPos(prevPt + addPt);
    //获取item在本item的鼠标位置，可以超过item的boundingRect或为负值
    QPointF eventPt = event->pos();
    qDebug()<<"item:mouseReleaseEvent "<<"eventPt--"<<eventPt;
}

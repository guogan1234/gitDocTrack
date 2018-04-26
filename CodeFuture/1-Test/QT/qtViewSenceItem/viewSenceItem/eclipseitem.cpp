#include "eclipseitem.h"

EclipseItem::EclipseItem(int type)
{
    m_type = type;
}

QRectF EclipseItem::boundingRect() const
{
    QRectF rect(0,0,200,200);
    return rect;
}

void EclipseItem::paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget)
{
    if(m_type == 1){
        painter->setPen(Qt::red);
        painter->drawEllipse(0,0,100,100);
        painter->drawRect(0,0,100,100);
    }else{
        painter->setPen(Qt::green);
        painter->drawEllipse(10,10,50,50);
        painter->drawRect(10,10,50,50);
    }
}

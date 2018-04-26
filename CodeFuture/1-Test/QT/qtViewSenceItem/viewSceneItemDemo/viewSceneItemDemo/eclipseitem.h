#ifndef ECLIPSEITEM_H
#define ECLIPSEITEM_H

#include <QGraphicsItem>
#include <QPainter>

class EclipseItem : public QGraphicsItem
{
    public:
        EclipseItem(int type);

        QRectF boundingRect() const;
        void paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget);

        void mouseMoveEvent(QGraphicsSceneMouseEvent *event);
        void mousePressEvent(QGraphicsSceneMouseEvent *event);
        void mouseReleaseEvent(QGraphicsSceneMouseEvent *event);

    private:
        QPainterPath path;

        int m_type;
};

#endif // ECLIPSEITEM_H

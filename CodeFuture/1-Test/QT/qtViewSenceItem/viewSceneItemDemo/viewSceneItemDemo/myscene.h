#ifndef MYSCENE_H
#define MYSCENE_H

#include <QGraphicsScene>

class myScene : public QGraphicsScene
{
        Q_OBJECT
    public:
        explicit myScene(QObject *parent = 0);

//        void mouseMoveEvent(QGraphicsSceneMouseEvent *event);
//        void mousePressEvent(QGraphicsSceneMouseEvent * mouseEvent);
    signals:

    public slots:

};

#endif // MYSCENE_H

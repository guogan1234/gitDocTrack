#include "widget.h"
#include "ui_widget.h"
#include "./global/global.h"
#include <QMouseEvent>
#include <QDrag>
#include <QMimeData>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    setCursor(Qt::OpenHandCursor);
    ui->w1->setCursor(Qt::BusyCursor);//事件处理忙碌，无法切换光标
    ui->w2->setCursor(Qt::PointingHandCursor);

    ui->w1->installEventFilter(this);
    ui->w2->installEventFilter(this);
    setAcceptDrops(true);
    ui->w1->setAcceptDrops(true);
    ui->w2->setAcceptDrops(true);
}

Widget::~Widget()
{
    delete ui;
}

int startPos = 0;
QPointF startPt;

bool Widget::eventFilter(QObject * obj, QEvent * e)
{
    if(obj == ui->w1){
        qDebug()<<"type--"<<e->type();
        if(e->type() == QEvent::MouseButtonPress){
            qDebug()<<"MouseButtonPress...";
            QMouseEvent* m_e = static_cast<QMouseEvent*>(e);
            if(m_e->button() == Qt::LeftButton){
                qDebug()<<"LeftButton...";
                startPt = m_e->pos();
                qDebug()<<"#:"<<startPt;
            }
            QPixmap pix(":/new/prefix1/res/2.jpg");
            ui->w1->setCursor(QCursor(pix.scaled(30,30)));
            qDebug()<<"w1->cursor...";

        }
        if(e->type() == QEvent::MouseMove){
            qDebug()<<"MouseMove...";
            QMouseEvent* m_e = static_cast<QMouseEvent*>(e);
            QPointF endPt = m_e->pos();
            qreal q = (endPt-startPt).manhattanLength();
            qDebug()<<"$:"<<startPt<<" - "<<endPt<<" d: "<<q;
            if(q > QApplication::startDragDistance()){
                qDebug()<<"start drag...";
                //改变光标，都未成功
//                ui->w1->setCursor(QCursor(Qt::BusyCursor));
//                ui->w1->cursor().setShape(Qt::BusyCursor);
//                this->setCursor(QCursor(Qt::BusyCursor));
                QDrag* drag = new QDrag(this);
                QMimeData* mimeData = new QMimeData;
                mimeData->setText("Hello drag!");
                drag->setMimeData(mimeData);
                drag->exec();
            }
        }
        if(e->type() == QEvent::DragMove){
            qDebug()<<"DragMove...";
        }
    }
    else if(obj == ui->w2){
        qDebug()<<"w2(type):"<<e->type();
        if(e->type() == QEvent::Drop){
            qDebug()<<"w2(type):Drop...";
            QDropEvent* drop_e = static_cast<QDropEvent*>(e);
            QString str = drop_e->mimeData()->text();
            qDebug()<<"drop's mimeData is:"+str;

            QPixmap p(":/new/prefix1/res/1.jpg");
            ui->w2->setCursor(QCursor(p.scaled(30,30)));
            qDebug()<<"cursor...";
        }
        if(e->type() == QEvent::DragEnter){
            qDebug()<<"w2(type):DragEnter...";
//            QDragEnterEvent* dragEnter_e = static_cast<QDragEnterEvent*>(e);
//            //1、直接在DragEnter时，处理拖拽操作
//            QString str = dragEnter_e->mimeData()->text();
//            qDebug()<<"drag's mimeData is:"+str;
            //2、在DragEnter时，将数据转到拽入事件中
//            dragEnter_e->setDropAction(Qt::MoveAction);
//            dragEnter_e->accept();
            e->accept();
        }
        if(e->type() == QEvent::DragMove){
            qDebug()<<"w2(type):DragEnter...";
            QDragMoveEvent* dragMove_e = static_cast<QDragMoveEvent*>(e);
            QString str = dragMove_e->mimeData()->text();
            qDebug()<<"dragMove's mimeData is:"+str;
        }
    }
}

void Widget::on_pushButton_2_clicked()
{
    setCursor(Qt::BusyCursor);
}

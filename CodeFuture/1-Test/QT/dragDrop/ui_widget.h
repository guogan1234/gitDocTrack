/********************************************************************************
** Form generated from reading UI file 'widget.ui'
**
** Created by: Qt User Interface Compiler version 5.2.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_WIDGET_H
#define UI_WIDGET_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Widget
{
public:
    QWidget *w1;
    QWidget *w2;
    QPushButton *pushButton;
    QPushButton *pushButton_2;

    void setupUi(QWidget *Widget)
    {
        if (Widget->objectName().isEmpty())
            Widget->setObjectName(QStringLiteral("Widget"));
        Widget->resize(708, 541);
        w1 = new QWidget(Widget);
        w1->setObjectName(QStringLiteral("w1"));
        w1->setGeometry(QRect(100, 140, 191, 231));
        w1->setCursor(QCursor(Qt::CrossCursor));
        w1->setStyleSheet(QStringLiteral("border-color: rgb(255, 0, 0);border-style:inset;border-top-style:solid;border-bottom-style:solid;border-width:2px"));
        w2 = new QWidget(Widget);
        w2->setObjectName(QStringLiteral("w2"));
        w2->setGeometry(QRect(410, 140, 221, 231));
        w2->setStyleSheet(QStringLiteral("border-color: rgb(0, 255, 0);border-style:outset;border-top-style:dotted;border-bottom-style:dotted;border-width:2px"));
        pushButton = new QPushButton(w2);
        pushButton->setObjectName(QStringLiteral("pushButton"));
        pushButton->setGeometry(QRect(40, 50, 75, 23));
        pushButton_2 = new QPushButton(Widget);
        pushButton_2->setObjectName(QStringLiteral("pushButton_2"));
        pushButton_2->setGeometry(QRect(120, 40, 75, 23));

        retranslateUi(Widget);

        QMetaObject::connectSlotsByName(Widget);
    } // setupUi

    void retranslateUi(QWidget *Widget)
    {
        Widget->setWindowTitle(QApplication::translate("Widget", "Widget", 0));
        pushButton->setText(QApplication::translate("Widget", "PushButton", 0));
        pushButton_2->setText(QApplication::translate("Widget", "changeCursor", 0));
    } // retranslateUi

};

namespace Ui {
    class Widget: public Ui_Widget {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_WIDGET_H

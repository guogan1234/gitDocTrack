/********************************************************************************
** Form generated from reading UI file 'mysharedwidget.ui'
**
** Created by: Qt User Interface Compiler version 5.8.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MYSHAREDWIDGET_H
#define UI_MYSHAREDWIDGET_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MySharedWidget
{
public:
    QLabel *label;
    QPushButton *pushButton;

    void setupUi(QWidget *MySharedWidget)
    {
        if (MySharedWidget->objectName().isEmpty())
            MySharedWidget->setObjectName(QStringLiteral("MySharedWidget"));
        MySharedWidget->resize(400, 300);
        label = new QLabel(MySharedWidget);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(30, 50, 311, 31));
        pushButton = new QPushButton(MySharedWidget);
        pushButton->setObjectName(QStringLiteral("pushButton"));
        pushButton->setGeometry(QRect(30, 100, 75, 23));

        retranslateUi(MySharedWidget);

        QMetaObject::connectSlotsByName(MySharedWidget);
    } // setupUi

    void retranslateUi(QWidget *MySharedWidget)
    {
        MySharedWidget->setWindowTitle(QApplication::translate("MySharedWidget", "Form", Q_NULLPTR));
        label->setText(QApplication::translate("MySharedWidget", "This is a widget from sharedWidgetDll.", Q_NULLPTR));
        pushButton->setText(QApplication::translate("MySharedWidget", "PushButton", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class MySharedWidget: public Ui_MySharedWidget {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MYSHAREDWIDGET_H

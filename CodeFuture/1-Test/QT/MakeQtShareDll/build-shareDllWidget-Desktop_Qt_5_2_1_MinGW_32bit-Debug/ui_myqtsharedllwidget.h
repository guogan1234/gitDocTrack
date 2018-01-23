/********************************************************************************
** Form generated from reading UI file 'myqtsharedllwidget.ui'
**
** Created by: Qt User Interface Compiler version 5.2.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MYQTSHAREDLLWIDGET_H
#define UI_MYQTSHAREDLLWIDGET_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_myQtShareDllWidget
{
public:
    QLabel *label;

    void setupUi(QWidget *myQtShareDllWidget)
    {
        if (myQtShareDllWidget->objectName().isEmpty())
            myQtShareDllWidget->setObjectName(QStringLiteral("myQtShareDllWidget"));
        myQtShareDllWidget->resize(346, 333);
        label = new QLabel(myQtShareDllWidget);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(40, 50, 251, 21));

        retranslateUi(myQtShareDllWidget);

        QMetaObject::connectSlotsByName(myQtShareDllWidget);
    } // setupUi

    void retranslateUi(QWidget *myQtShareDllWidget)
    {
        myQtShareDllWidget->setWindowTitle(QApplication::translate("myQtShareDllWidget", "Form", 0));
        label->setText(QApplication::translate("myQtShareDllWidget", "Hello,i am from Dll!!!", 0));
    } // retranslateUi

};

namespace Ui {
    class myQtShareDllWidget: public Ui_myQtShareDllWidget {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MYQTSHAREDLLWIDGET_H

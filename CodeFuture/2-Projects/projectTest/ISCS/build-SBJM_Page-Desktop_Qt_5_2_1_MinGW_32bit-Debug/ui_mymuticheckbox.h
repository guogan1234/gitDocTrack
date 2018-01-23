/********************************************************************************
** Form generated from reading UI file 'mymuticheckbox.ui'
**
** Created by: Qt User Interface Compiler version 5.2.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MYMUTICHECKBOX_H
#define UI_MYMUTICHECKBOX_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QCheckBox>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_myMutiCheckBox
{
public:
    QVBoxLayout *verticalLayout_2;
    QWidget *widget;
    QHBoxLayout *horizontalLayout;
    QLineEdit *lineEdit;
    QPushButton *pushButton;
    QWidget *widget_2;
    QVBoxLayout *verticalLayout;
    QCheckBox *checkBox;
    QCheckBox *checkBox_2;
    QCheckBox *checkBox_3;

    void setupUi(QWidget *myMutiCheckBox)
    {
        if (myMutiCheckBox->objectName().isEmpty())
            myMutiCheckBox->setObjectName(QStringLiteral("myMutiCheckBox"));
        myMutiCheckBox->resize(293, 143);
        myMutiCheckBox->setMaximumSize(QSize(999, 999));
        verticalLayout_2 = new QVBoxLayout(myMutiCheckBox);
        verticalLayout_2->setObjectName(QStringLiteral("verticalLayout_2"));
        widget = new QWidget(myMutiCheckBox);
        widget->setObjectName(QStringLiteral("widget"));
        horizontalLayout = new QHBoxLayout(widget);
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        lineEdit = new QLineEdit(widget);
        lineEdit->setObjectName(QStringLiteral("lineEdit"));

        horizontalLayout->addWidget(lineEdit);

        pushButton = new QPushButton(widget);
        pushButton->setObjectName(QStringLiteral("pushButton"));

        horizontalLayout->addWidget(pushButton);


        verticalLayout_2->addWidget(widget);

        widget_2 = new QWidget(myMutiCheckBox);
        widget_2->setObjectName(QStringLiteral("widget_2"));
        verticalLayout = new QVBoxLayout(widget_2);
        verticalLayout->setObjectName(QStringLiteral("verticalLayout"));
        checkBox = new QCheckBox(widget_2);
        checkBox->setObjectName(QStringLiteral("checkBox"));

        verticalLayout->addWidget(checkBox);

        checkBox_2 = new QCheckBox(widget_2);
        checkBox_2->setObjectName(QStringLiteral("checkBox_2"));

        verticalLayout->addWidget(checkBox_2);

        checkBox_3 = new QCheckBox(widget_2);
        checkBox_3->setObjectName(QStringLiteral("checkBox_3"));

        verticalLayout->addWidget(checkBox_3);


        verticalLayout_2->addWidget(widget_2);


        retranslateUi(myMutiCheckBox);

        QMetaObject::connectSlotsByName(myMutiCheckBox);
    } // setupUi

    void retranslateUi(QWidget *myMutiCheckBox)
    {
        myMutiCheckBox->setWindowTitle(QApplication::translate("myMutiCheckBox", "Form", 0));
        pushButton->setText(QApplication::translate("myMutiCheckBox", "PushButton", 0));
        checkBox->setText(QApplication::translate("myMutiCheckBox", "CheckBox", 0));
        checkBox_2->setText(QApplication::translate("myMutiCheckBox", "CheckBox", 0));
        checkBox_3->setText(QApplication::translate("myMutiCheckBox", "CheckBox", 0));
    } // retranslateUi

};

namespace Ui {
    class myMutiCheckBox: public Ui_myMutiCheckBox {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MYMUTICHECKBOX_H

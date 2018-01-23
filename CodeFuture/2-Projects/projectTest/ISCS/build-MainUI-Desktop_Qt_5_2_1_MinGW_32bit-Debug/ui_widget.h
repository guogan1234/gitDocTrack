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
#include <QtWidgets/QGroupBox>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/QTreeWidget>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Widget
{
public:
    QVBoxLayout *verticalLayout;
    QWidget *topW;
    QHBoxLayout *horizontalLayout_4;
    QPushButton *SBJM_Btn;
    QPushButton *QXDY_Btn;
    QPushButton *test;
    QSpacerItem *horizontalSpacer_4;
    QWidget *middleW;
    QHBoxLayout *horizontalLayout_3;
    QWidget *middle_w1;
    QVBoxLayout *verticalLayout_4;
    QTreeWidget *leftTree;
    QTextEdit *textEdit;
    QGroupBox *middle_w2;
    QVBoxLayout *verticalLayout_2;
    QWidget *widget_5;
    QHBoxLayout *horizontalLayout;
    QPushButton *addPtTemplate;
    QPushButton *addSpecialPt;
    QPushButton *del;
    QSpacerItem *horizontalSpacer;
    QListWidget *middleList;
    QWidget *blankW;
    QWidget *middle_w3;
    QVBoxLayout *verticalLayout_3;
    QTreeWidget *rightTree;
    QWidget *widget_7;
    QHBoxLayout *horizontalLayout_2;
    QSpacerItem *horizontalSpacer_2;
    QPushButton *save;
    QSpacerItem *horizontalSpacer_3;
    QWidget *bottomW;
    QHBoxLayout *horizontalLayout_5;
    QLabel *label;
    QLabel *label_2;
    QLabel *label_3;
    QLabel *label_4;
    QSpacerItem *horizontalSpacer_5;

    void setupUi(QWidget *Widget)
    {
        if (Widget->objectName().isEmpty())
            Widget->setObjectName(QStringLiteral("Widget"));
        Widget->resize(848, 593);
        verticalLayout = new QVBoxLayout(Widget);
        verticalLayout->setSpacing(6);
        verticalLayout->setContentsMargins(11, 11, 11, 11);
        verticalLayout->setObjectName(QStringLiteral("verticalLayout"));
        topW = new QWidget(Widget);
        topW->setObjectName(QStringLiteral("topW"));
        topW->setMaximumSize(QSize(16777215, 50));
        topW->setStyleSheet(QStringLiteral("border-color:blue;border-style:solid;border-width:1px"));
        horizontalLayout_4 = new QHBoxLayout(topW);
        horizontalLayout_4->setSpacing(6);
        horizontalLayout_4->setContentsMargins(11, 11, 11, 11);
        horizontalLayout_4->setObjectName(QStringLiteral("horizontalLayout_4"));
        SBJM_Btn = new QPushButton(topW);
        SBJM_Btn->setObjectName(QStringLiteral("SBJM_Btn"));

        horizontalLayout_4->addWidget(SBJM_Btn);

        QXDY_Btn = new QPushButton(topW);
        QXDY_Btn->setObjectName(QStringLiteral("QXDY_Btn"));

        horizontalLayout_4->addWidget(QXDY_Btn);

        test = new QPushButton(topW);
        test->setObjectName(QStringLiteral("test"));
        test->setStyleSheet(QStringLiteral("border-color:blue;border-style:solid;border-width:1px"));

        horizontalLayout_4->addWidget(test);

        horizontalSpacer_4 = new QSpacerItem(659, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout_4->addItem(horizontalSpacer_4);


        verticalLayout->addWidget(topW);

        middleW = new QWidget(Widget);
        middleW->setObjectName(QStringLiteral("middleW"));
        middleW->setStyleSheet(QStringLiteral("border-color:blue;border-style:solid;border-width:1px"));
        horizontalLayout_3 = new QHBoxLayout(middleW);
        horizontalLayout_3->setSpacing(6);
        horizontalLayout_3->setContentsMargins(11, 11, 11, 11);
        horizontalLayout_3->setObjectName(QStringLiteral("horizontalLayout_3"));
        horizontalLayout_3->setContentsMargins(0, 0, 0, 0);
        middle_w1 = new QWidget(middleW);
        middle_w1->setObjectName(QStringLiteral("middle_w1"));
        verticalLayout_4 = new QVBoxLayout(middle_w1);
        verticalLayout_4->setSpacing(6);
        verticalLayout_4->setContentsMargins(11, 11, 11, 11);
        verticalLayout_4->setObjectName(QStringLiteral("verticalLayout_4"));
        leftTree = new QTreeWidget(middle_w1);
        leftTree->setObjectName(QStringLiteral("leftTree"));
        QPalette palette;
        QBrush brush(QColor(170, 170, 255, 255));
        brush.setStyle(Qt::SolidPattern);
        palette.setBrush(QPalette::Active, QPalette::AlternateBase, brush);
        palette.setBrush(QPalette::Inactive, QPalette::AlternateBase, brush);
        palette.setBrush(QPalette::Disabled, QPalette::AlternateBase, brush);
        leftTree->setPalette(palette);
        leftTree->setAlternatingRowColors(true);
        leftTree->header()->setDefaultSectionSize(200);

        verticalLayout_4->addWidget(leftTree);

        textEdit = new QTextEdit(middle_w1);
        textEdit->setObjectName(QStringLiteral("textEdit"));

        verticalLayout_4->addWidget(textEdit);


        horizontalLayout_3->addWidget(middle_w1);

        middle_w2 = new QGroupBox(middleW);
        middle_w2->setObjectName(QStringLiteral("middle_w2"));
        verticalLayout_2 = new QVBoxLayout(middle_w2);
        verticalLayout_2->setSpacing(6);
        verticalLayout_2->setContentsMargins(11, 11, 11, 11);
        verticalLayout_2->setObjectName(QStringLiteral("verticalLayout_2"));
        widget_5 = new QWidget(middle_w2);
        widget_5->setObjectName(QStringLiteral("widget_5"));
        horizontalLayout = new QHBoxLayout(widget_5);
        horizontalLayout->setSpacing(6);
        horizontalLayout->setContentsMargins(11, 11, 11, 11);
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        addPtTemplate = new QPushButton(widget_5);
        addPtTemplate->setObjectName(QStringLiteral("addPtTemplate"));

        horizontalLayout->addWidget(addPtTemplate);

        addSpecialPt = new QPushButton(widget_5);
        addSpecialPt->setObjectName(QStringLiteral("addSpecialPt"));

        horizontalLayout->addWidget(addSpecialPt);

        del = new QPushButton(widget_5);
        del->setObjectName(QStringLiteral("del"));

        horizontalLayout->addWidget(del);

        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);


        verticalLayout_2->addWidget(widget_5);

        middleList = new QListWidget(middle_w2);
        middleList->setObjectName(QStringLiteral("middleList"));

        verticalLayout_2->addWidget(middleList);


        horizontalLayout_3->addWidget(middle_w2);

        blankW = new QWidget(middleW);
        blankW->setObjectName(QStringLiteral("blankW"));

        horizontalLayout_3->addWidget(blankW);

        middle_w3 = new QWidget(middleW);
        middle_w3->setObjectName(QStringLiteral("middle_w3"));
        verticalLayout_3 = new QVBoxLayout(middle_w3);
        verticalLayout_3->setSpacing(6);
        verticalLayout_3->setContentsMargins(11, 11, 11, 11);
        verticalLayout_3->setObjectName(QStringLiteral("verticalLayout_3"));
        rightTree = new QTreeWidget(middle_w3);
        QTreeWidgetItem *__qtreewidgetitem = new QTreeWidgetItem();
        __qtreewidgetitem->setText(0, QString::fromUtf8("\345\261\236\346\200\247"));
        rightTree->setHeaderItem(__qtreewidgetitem);
        QTreeWidgetItem *__qtreewidgetitem1 = new QTreeWidgetItem(rightTree);
        new QTreeWidgetItem(__qtreewidgetitem1);
        new QTreeWidgetItem(__qtreewidgetitem1);
        QTreeWidgetItem *__qtreewidgetitem2 = new QTreeWidgetItem(__qtreewidgetitem1);
        new QTreeWidgetItem(__qtreewidgetitem2);
        new QTreeWidgetItem(__qtreewidgetitem2);
        new QTreeWidgetItem(__qtreewidgetitem2);
        QTreeWidgetItem *__qtreewidgetitem3 = new QTreeWidgetItem(rightTree);
        new QTreeWidgetItem(__qtreewidgetitem3);
        new QTreeWidgetItem(__qtreewidgetitem3);
        rightTree->setObjectName(QStringLiteral("rightTree"));
        QPalette palette1;
        palette1.setBrush(QPalette::Active, QPalette::AlternateBase, brush);
        palette1.setBrush(QPalette::Inactive, QPalette::AlternateBase, brush);
        palette1.setBrush(QPalette::Disabled, QPalette::AlternateBase, brush);
        rightTree->setPalette(palette1);
        rightTree->setAlternatingRowColors(true);
        rightTree->header()->setDefaultSectionSize(250);

        verticalLayout_3->addWidget(rightTree);

        widget_7 = new QWidget(middle_w3);
        widget_7->setObjectName(QStringLiteral("widget_7"));
        horizontalLayout_2 = new QHBoxLayout(widget_7);
        horizontalLayout_2->setSpacing(6);
        horizontalLayout_2->setContentsMargins(11, 11, 11, 11);
        horizontalLayout_2->setObjectName(QStringLiteral("horizontalLayout_2"));
        horizontalSpacer_2 = new QSpacerItem(165, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout_2->addItem(horizontalSpacer_2);

        save = new QPushButton(widget_7);
        save->setObjectName(QStringLiteral("save"));

        horizontalLayout_2->addWidget(save);

        horizontalSpacer_3 = new QSpacerItem(164, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout_2->addItem(horizontalSpacer_3);


        verticalLayout_3->addWidget(widget_7);

        verticalLayout_3->setStretch(0, 18);
        verticalLayout_3->setStretch(1, 1);

        horizontalLayout_3->addWidget(middle_w3);

        horizontalLayout_3->setStretch(0, 2);
        horizontalLayout_3->setStretch(1, 2);
        horizontalLayout_3->setStretch(2, 2);
        horizontalLayout_3->setStretch(3, 5);

        verticalLayout->addWidget(middleW);

        bottomW = new QWidget(Widget);
        bottomW->setObjectName(QStringLiteral("bottomW"));
        bottomW->setMaximumSize(QSize(16777215, 50));
        bottomW->setStyleSheet(QStringLiteral("border-color:blue;border-style:solid;border-width:1px"));
        horizontalLayout_5 = new QHBoxLayout(bottomW);
        horizontalLayout_5->setSpacing(6);
        horizontalLayout_5->setContentsMargins(11, 11, 11, 11);
        horizontalLayout_5->setObjectName(QStringLiteral("horizontalLayout_5"));
        label = new QLabel(bottomW);
        label->setObjectName(QStringLiteral("label"));
        label->setAlignment(Qt::AlignRight|Qt::AlignTrailing|Qt::AlignVCenter);

        horizontalLayout_5->addWidget(label);

        label_2 = new QLabel(bottomW);
        label_2->setObjectName(QStringLiteral("label_2"));

        horizontalLayout_5->addWidget(label_2);

        label_3 = new QLabel(bottomW);
        label_3->setObjectName(QStringLiteral("label_3"));

        horizontalLayout_5->addWidget(label_3);

        label_4 = new QLabel(bottomW);
        label_4->setObjectName(QStringLiteral("label_4"));

        horizontalLayout_5->addWidget(label_4);

        horizontalSpacer_5 = new QSpacerItem(451, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout_5->addItem(horizontalSpacer_5);


        verticalLayout->addWidget(bottomW);

        verticalLayout->setStretch(0, 1);
        verticalLayout->setStretch(1, 10);
        verticalLayout->setStretch(2, 1);

        retranslateUi(Widget);

        QMetaObject::connectSlotsByName(Widget);
    } // setupUi

    void retranslateUi(QWidget *Widget)
    {
        Widget->setWindowTitle(QApplication::translate("Widget", "Widget", 0));
        SBJM_Btn->setText(QApplication::translate("Widget", "\350\256\276\345\244\207\345\273\272\346\250\241", 0));
        QXDY_Btn->setText(QApplication::translate("Widget", "\346\235\203\351\231\220\345\256\232\344\271\211", 0));
        test->setText(QApplication::translate("Widget", "\346\265\213\350\257\225", 0));
        QTreeWidgetItem *___qtreewidgetitem = leftTree->headerItem();
        ___qtreewidgetitem->setText(1, QApplication::translate("Widget", "\350\277\207\346\273\244\346\235\241\344\273\266", 0));
        ___qtreewidgetitem->setText(0, QApplication::translate("Widget", "\350\256\276\345\244\207\345\273\272\346\250\241", 0));
        middle_w2->setTitle(QApplication::translate("Widget", "\346\250\241\346\235\277", 0));
        addPtTemplate->setText(QApplication::translate("Widget", "\345\242\236\345\212\240\347\202\271\346\250\241\346\235\277", 0));
        addSpecialPt->setText(QApplication::translate("Widget", "\345\242\236\345\212\240\347\211\271\346\256\212\347\202\271", 0));
        del->setText(QApplication::translate("Widget", "\345\210\240\351\231\244", 0));
        QTreeWidgetItem *___qtreewidgetitem1 = rightTree->headerItem();
        ___qtreewidgetitem1->setText(1, QApplication::translate("Widget", "\345\200\274", 0));

        const bool __sortingEnabled = rightTree->isSortingEnabled();
        rightTree->setSortingEnabled(false);
        QTreeWidgetItem *___qtreewidgetitem2 = rightTree->topLevelItem(0);
        ___qtreewidgetitem2->setText(0, QApplication::translate("Widget", "\347\274\226\350\276\221\345\261\236\346\200\247", 0));
        QTreeWidgetItem *___qtreewidgetitem3 = ___qtreewidgetitem2->child(0);
        ___qtreewidgetitem3->setText(0, QApplication::translate("Widget", "\346\240\207\347\255\276\345\220\215", 0));
        QTreeWidgetItem *___qtreewidgetitem4 = ___qtreewidgetitem2->child(1);
        ___qtreewidgetitem4->setText(0, QApplication::translate("Widget", "\346\217\217\350\277\260", 0));
        QTreeWidgetItem *___qtreewidgetitem5 = ___qtreewidgetitem2->child(2);
        ___qtreewidgetitem5->setText(0, QApplication::translate("Widget", "\346\230\257\345\220\246\346\216\247\345\210\266", 0));
        QTreeWidgetItem *___qtreewidgetitem6 = ___qtreewidgetitem5->child(0);
        ___qtreewidgetitem6->setText(0, QApplication::translate("Widget", "\346\216\247\345\210\266\345\212\250\344\275\234\347\273\204\345\220\215\347\247\260", 0));
        QTreeWidgetItem *___qtreewidgetitem7 = ___qtreewidgetitem5->child(1);
        ___qtreewidgetitem7->setText(0, QApplication::translate("Widget", "\346\216\247\345\210\266\345\212\250\344\275\2341", 0));
        QTreeWidgetItem *___qtreewidgetitem8 = ___qtreewidgetitem5->child(2);
        ___qtreewidgetitem8->setText(0, QApplication::translate("Widget", "\346\216\247\345\210\266\345\212\250\344\275\2342", 0));
        QTreeWidgetItem *___qtreewidgetitem9 = rightTree->topLevelItem(1);
        ___qtreewidgetitem9->setText(0, QApplication::translate("Widget", "\346\265\217\350\247\210\345\261\236\346\200\247", 0));
        QTreeWidgetItem *___qtreewidgetitem10 = ___qtreewidgetitem9->child(0);
        ___qtreewidgetitem10->setText(0, QApplication::translate("Widget", "\346\211\200\345\261\236\344\270\223\344\270\232", 0));
        QTreeWidgetItem *___qtreewidgetitem11 = ___qtreewidgetitem9->child(1);
        ___qtreewidgetitem11->setText(0, QApplication::translate("Widget", "\346\211\200\345\261\236\346\250\241\346\235\277", 0));
        rightTree->setSortingEnabled(__sortingEnabled);

        save->setText(QApplication::translate("Widget", "\344\277\235\345\255\230", 0));
        label->setText(QApplication::translate("Widget", "\345\275\223\345\211\215\346\250\241\345\235\227\357\274\232", 0));
        label_2->setText(QApplication::translate("Widget", "\350\256\276\345\244\207\345\273\272\346\250\241", 0));
        label_3->setText(QApplication::translate("Widget", "\346\225\260\346\215\256\345\272\223\357\274\232127.0.0.1", 0));
        label_4->setText(QApplication::translate("Widget", "\347\231\273\345\275\225\347\224\250\346\210\267\357\274\232iscs", 0));
    } // retranslateUi

};

namespace Ui {
    class Widget: public Ui_Widget {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_WIDGET_H

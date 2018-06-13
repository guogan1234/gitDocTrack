/********************************************************************************
** Form generated from reading UI file 'widget.ui'
**
** Created by: Qt User Interface Compiler version 5.8.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_WIDGET_H
#define UI_WIDGET_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTabWidget>
#include <QtWidgets/QTableView>
#include <QtWidgets/QTableWidget>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Widget
{
public:
    QVBoxLayout *verticalLayout_3;
    QTabWidget *tabWidget;
    QWidget *tab_6;
    QVBoxLayout *verticalLayout_5;
    QTableView *TV_process;
    QWidget *tab_2;
    QGridLayout *gridLayout;
    QLabel *label_7;
    QLineEdit *LE_cpu_page_size;
    QLineEdit *LE_cpu_count;
    QLabel *label_2;
    QLineEdit *LE_cpu_level;
    QLabel *label_3;
    QLabel *label_8;
    QLineEdit *LE_cpu_name;
    QLabel *label_9;
    QLineEdit *LE_cpu_frequency;
    QLabel *label_19;
    QLineEdit *LE_cpu_type_name;
    QLabel *label_10;
    QLineEdit *LE_cpu_version;
    QLabel *label_11;
    QLineEdit *LE_cpu_used;
    QWidget *tab_3;
    QGridLayout *gridLayout_2;
    QLabel *label_12;
    QLabel *label_14;
    QLabel *label_16;
    QLineEdit *LE_page_avail;
    QLineEdit *LE_mem_avail;
    QLineEdit *LE_mem_used;
    QLabel *label_18;
    QLabel *label_13;
    QLineEdit *LE_virtual_avail;
    QLineEdit *LE_mem_total;
    QLabel *label_15;
    QLineEdit *LE_page_size;
    QLabel *label_17;
    QLineEdit *LE_virtual_total;
    QWidget *tab_4;
    QVBoxLayout *verticalLayout;
    QTableWidget *TW_disk;
    QWidget *tab_5;
    QVBoxLayout *verticalLayout_4;
    QWidget *widget_2;
    QGridLayout *gridLayout_3;
    QLabel *label_4;
    QLineEdit *LE_pc_OS;
    QLabel *label_20;
    QLineEdit *LE_pc_os_version;
    QWidget *widget;
    QLineEdit *LE_cpu_mask;
    QLabel *label;
    QLabel *label_6;
    QLabel *label_5;
    QLineEdit *LE_cpu_min_addr;
    QLineEdit *LE_cpu_max_addr;
    QPushButton *test;

    void setupUi(QWidget *Widget)
    {
        if (Widget->objectName().isEmpty())
            Widget->setObjectName(QStringLiteral("Widget"));
        Widget->resize(800, 600);
        verticalLayout_3 = new QVBoxLayout(Widget);
        verticalLayout_3->setSpacing(6);
        verticalLayout_3->setContentsMargins(11, 11, 11, 11);
        verticalLayout_3->setObjectName(QStringLiteral("verticalLayout_3"));
        tabWidget = new QTabWidget(Widget);
        tabWidget->setObjectName(QStringLiteral("tabWidget"));
        tab_6 = new QWidget();
        tab_6->setObjectName(QStringLiteral("tab_6"));
        verticalLayout_5 = new QVBoxLayout(tab_6);
        verticalLayout_5->setSpacing(6);
        verticalLayout_5->setContentsMargins(11, 11, 11, 11);
        verticalLayout_5->setObjectName(QStringLiteral("verticalLayout_5"));
        TV_process = new QTableView(tab_6);
        TV_process->setObjectName(QStringLiteral("TV_process"));

        verticalLayout_5->addWidget(TV_process);

        tabWidget->addTab(tab_6, QString());
        tab_2 = new QWidget();
        tab_2->setObjectName(QStringLiteral("tab_2"));
        gridLayout = new QGridLayout(tab_2);
        gridLayout->setSpacing(6);
        gridLayout->setContentsMargins(11, 11, 11, 11);
        gridLayout->setObjectName(QStringLiteral("gridLayout"));
        label_7 = new QLabel(tab_2);
        label_7->setObjectName(QStringLiteral("label_7"));

        gridLayout->addWidget(label_7, 4, 1, 1, 1);

        LE_cpu_page_size = new QLineEdit(tab_2);
        LE_cpu_page_size->setObjectName(QStringLiteral("LE_cpu_page_size"));
        LE_cpu_page_size->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_page_size, 3, 2, 1, 1);

        LE_cpu_count = new QLineEdit(tab_2);
        LE_cpu_count->setObjectName(QStringLiteral("LE_cpu_count"));
        LE_cpu_count->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_count, 1, 6, 1, 1);

        label_2 = new QLabel(tab_2);
        label_2->setObjectName(QStringLiteral("label_2"));

        gridLayout->addWidget(label_2, 1, 4, 1, 1);

        LE_cpu_level = new QLineEdit(tab_2);
        LE_cpu_level->setObjectName(QStringLiteral("LE_cpu_level"));
        LE_cpu_level->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_level, 4, 2, 1, 1);

        label_3 = new QLabel(tab_2);
        label_3->setObjectName(QStringLiteral("label_3"));

        gridLayout->addWidget(label_3, 3, 1, 1, 1);

        label_8 = new QLabel(tab_2);
        label_8->setObjectName(QStringLiteral("label_8"));

        gridLayout->addWidget(label_8, 0, 1, 1, 1);

        LE_cpu_name = new QLineEdit(tab_2);
        LE_cpu_name->setObjectName(QStringLiteral("LE_cpu_name"));
        LE_cpu_name->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_name, 0, 2, 1, 1);

        label_9 = new QLabel(tab_2);
        label_9->setObjectName(QStringLiteral("label_9"));

        gridLayout->addWidget(label_9, 0, 4, 1, 1);

        LE_cpu_frequency = new QLineEdit(tab_2);
        LE_cpu_frequency->setObjectName(QStringLiteral("LE_cpu_frequency"));
        LE_cpu_frequency->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_frequency, 0, 6, 1, 1);

        label_19 = new QLabel(tab_2);
        label_19->setObjectName(QStringLiteral("label_19"));

        gridLayout->addWidget(label_19, 1, 1, 1, 1);

        LE_cpu_type_name = new QLineEdit(tab_2);
        LE_cpu_type_name->setObjectName(QStringLiteral("LE_cpu_type_name"));
        LE_cpu_type_name->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_type_name, 1, 2, 1, 1);

        label_10 = new QLabel(tab_2);
        label_10->setObjectName(QStringLiteral("label_10"));

        gridLayout->addWidget(label_10, 3, 4, 1, 1);

        LE_cpu_version = new QLineEdit(tab_2);
        LE_cpu_version->setObjectName(QStringLiteral("LE_cpu_version"));
        LE_cpu_version->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_version, 3, 6, 1, 1);

        label_11 = new QLabel(tab_2);
        label_11->setObjectName(QStringLiteral("label_11"));

        gridLayout->addWidget(label_11, 4, 4, 1, 1);

        LE_cpu_used = new QLineEdit(tab_2);
        LE_cpu_used->setObjectName(QStringLiteral("LE_cpu_used"));
        LE_cpu_used->setReadOnly(true);

        gridLayout->addWidget(LE_cpu_used, 4, 6, 1, 1);

        tabWidget->addTab(tab_2, QString());
        tab_3 = new QWidget();
        tab_3->setObjectName(QStringLiteral("tab_3"));
        gridLayout_2 = new QGridLayout(tab_3);
        gridLayout_2->setSpacing(6);
        gridLayout_2->setContentsMargins(11, 11, 11, 11);
        gridLayout_2->setObjectName(QStringLiteral("gridLayout_2"));
        label_12 = new QLabel(tab_3);
        label_12->setObjectName(QStringLiteral("label_12"));

        gridLayout_2->addWidget(label_12, 0, 0, 1, 1);

        label_14 = new QLabel(tab_3);
        label_14->setObjectName(QStringLiteral("label_14"));

        gridLayout_2->addWidget(label_14, 2, 0, 1, 1);

        label_16 = new QLabel(tab_3);
        label_16->setObjectName(QStringLiteral("label_16"));

        gridLayout_2->addWidget(label_16, 4, 0, 1, 1);

        LE_page_avail = new QLineEdit(tab_3);
        LE_page_avail->setObjectName(QStringLiteral("LE_page_avail"));
        LE_page_avail->setReadOnly(true);

        gridLayout_2->addWidget(LE_page_avail, 4, 1, 1, 1);

        LE_mem_avail = new QLineEdit(tab_3);
        LE_mem_avail->setObjectName(QStringLiteral("LE_mem_avail"));
        LE_mem_avail->setReadOnly(true);

        gridLayout_2->addWidget(LE_mem_avail, 2, 1, 1, 1);

        LE_mem_used = new QLineEdit(tab_3);
        LE_mem_used->setObjectName(QStringLiteral("LE_mem_used"));
        LE_mem_used->setReadOnly(true);

        gridLayout_2->addWidget(LE_mem_used, 0, 1, 1, 1);

        label_18 = new QLabel(tab_3);
        label_18->setObjectName(QStringLiteral("label_18"));

        gridLayout_2->addWidget(label_18, 6, 0, 1, 1);

        label_13 = new QLabel(tab_3);
        label_13->setObjectName(QStringLiteral("label_13"));

        gridLayout_2->addWidget(label_13, 0, 2, 1, 1);

        LE_virtual_avail = new QLineEdit(tab_3);
        LE_virtual_avail->setObjectName(QStringLiteral("LE_virtual_avail"));
        LE_virtual_avail->setReadOnly(true);

        gridLayout_2->addWidget(LE_virtual_avail, 6, 1, 1, 1);

        LE_mem_total = new QLineEdit(tab_3);
        LE_mem_total->setObjectName(QStringLiteral("LE_mem_total"));
        LE_mem_total->setReadOnly(true);

        gridLayout_2->addWidget(LE_mem_total, 0, 3, 1, 1);

        label_15 = new QLabel(tab_3);
        label_15->setObjectName(QStringLiteral("label_15"));

        gridLayout_2->addWidget(label_15, 2, 2, 1, 1);

        LE_page_size = new QLineEdit(tab_3);
        LE_page_size->setObjectName(QStringLiteral("LE_page_size"));
        LE_page_size->setReadOnly(true);

        gridLayout_2->addWidget(LE_page_size, 2, 3, 1, 1);

        label_17 = new QLabel(tab_3);
        label_17->setObjectName(QStringLiteral("label_17"));

        gridLayout_2->addWidget(label_17, 4, 2, 1, 1);

        LE_virtual_total = new QLineEdit(tab_3);
        LE_virtual_total->setObjectName(QStringLiteral("LE_virtual_total"));
        LE_virtual_total->setReadOnly(true);

        gridLayout_2->addWidget(LE_virtual_total, 4, 3, 1, 1);

        tabWidget->addTab(tab_3, QString());
        tab_4 = new QWidget();
        tab_4->setObjectName(QStringLiteral("tab_4"));
        verticalLayout = new QVBoxLayout(tab_4);
        verticalLayout->setSpacing(6);
        verticalLayout->setContentsMargins(11, 11, 11, 11);
        verticalLayout->setObjectName(QStringLiteral("verticalLayout"));
        TW_disk = new QTableWidget(tab_4);
        if (TW_disk->columnCount() < 5)
            TW_disk->setColumnCount(5);
        QTableWidgetItem *__qtablewidgetitem = new QTableWidgetItem();
        TW_disk->setHorizontalHeaderItem(0, __qtablewidgetitem);
        QTableWidgetItem *__qtablewidgetitem1 = new QTableWidgetItem();
        TW_disk->setHorizontalHeaderItem(1, __qtablewidgetitem1);
        QTableWidgetItem *__qtablewidgetitem2 = new QTableWidgetItem();
        TW_disk->setHorizontalHeaderItem(2, __qtablewidgetitem2);
        QTableWidgetItem *__qtablewidgetitem3 = new QTableWidgetItem();
        TW_disk->setHorizontalHeaderItem(3, __qtablewidgetitem3);
        QTableWidgetItem *__qtablewidgetitem4 = new QTableWidgetItem();
        TW_disk->setHorizontalHeaderItem(4, __qtablewidgetitem4);
        TW_disk->setObjectName(QStringLiteral("TW_disk"));

        verticalLayout->addWidget(TW_disk);

        tabWidget->addTab(tab_4, QString());
        tab_5 = new QWidget();
        tab_5->setObjectName(QStringLiteral("tab_5"));
        verticalLayout_4 = new QVBoxLayout(tab_5);
        verticalLayout_4->setSpacing(6);
        verticalLayout_4->setContentsMargins(11, 11, 11, 11);
        verticalLayout_4->setObjectName(QStringLiteral("verticalLayout_4"));
        widget_2 = new QWidget(tab_5);
        widget_2->setObjectName(QStringLiteral("widget_2"));
        gridLayout_3 = new QGridLayout(widget_2);
        gridLayout_3->setSpacing(6);
        gridLayout_3->setContentsMargins(11, 11, 11, 11);
        gridLayout_3->setObjectName(QStringLiteral("gridLayout_3"));
        label_4 = new QLabel(widget_2);
        label_4->setObjectName(QStringLiteral("label_4"));

        gridLayout_3->addWidget(label_4, 0, 0, 1, 1);

        LE_pc_OS = new QLineEdit(widget_2);
        LE_pc_OS->setObjectName(QStringLiteral("LE_pc_OS"));
        LE_pc_OS->setReadOnly(true);

        gridLayout_3->addWidget(LE_pc_OS, 0, 1, 1, 1);

        label_20 = new QLabel(widget_2);
        label_20->setObjectName(QStringLiteral("label_20"));

        gridLayout_3->addWidget(label_20, 0, 2, 1, 1);

        LE_pc_os_version = new QLineEdit(widget_2);
        LE_pc_os_version->setObjectName(QStringLiteral("LE_pc_os_version"));
        LE_pc_os_version->setReadOnly(true);

        gridLayout_3->addWidget(LE_pc_os_version, 0, 3, 1, 1);


        verticalLayout_4->addWidget(widget_2);

        widget = new QWidget(tab_5);
        widget->setObjectName(QStringLiteral("widget"));
        LE_cpu_mask = new QLineEdit(widget);
        LE_cpu_mask->setObjectName(QStringLiteral("LE_cpu_mask"));
        LE_cpu_mask->setGeometry(QRect(120, 40, 286, 20));
        label = new QLabel(widget);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(30, 40, 84, 20));
        label_6 = new QLabel(widget);
        label_6->setObjectName(QStringLiteral("label_6"));
        label_6->setGeometry(QRect(30, 100, 84, 20));
        label_5 = new QLabel(widget);
        label_5->setObjectName(QStringLiteral("label_5"));
        label_5->setGeometry(QRect(30, 70, 84, 20));
        LE_cpu_min_addr = new QLineEdit(widget);
        LE_cpu_min_addr->setObjectName(QStringLiteral("LE_cpu_min_addr"));
        LE_cpu_min_addr->setGeometry(QRect(120, 100, 286, 20));
        LE_cpu_max_addr = new QLineEdit(widget);
        LE_cpu_max_addr->setObjectName(QStringLiteral("LE_cpu_max_addr"));
        LE_cpu_max_addr->setGeometry(QRect(120, 70, 286, 20));
        test = new QPushButton(widget);
        test->setObjectName(QStringLiteral("test"));
        test->setGeometry(QRect(470, 60, 75, 23));

        verticalLayout_4->addWidget(widget);

        tabWidget->addTab(tab_5, QString());

        verticalLayout_3->addWidget(tabWidget);


        retranslateUi(Widget);

        tabWidget->setCurrentIndex(0);


        QMetaObject::connectSlotsByName(Widget);
    } // setupUi

    void retranslateUi(QWidget *Widget)
    {
        Widget->setWindowTitle(QApplication::translate("Widget", "Widget", Q_NULLPTR));
        tabWidget->setTabText(tabWidget->indexOf(tab_6), QApplication::translate("Widget", "\350\277\233\347\250\213", Q_NULLPTR));
        label_7->setText(QApplication::translate("Widget", "CPU\347\255\211\347\272\247\357\274\232", Q_NULLPTR));
        label_2->setText(QApplication::translate("Widget", "CPU\344\270\252\346\225\260\357\274\232", Q_NULLPTR));
        label_3->setText(QApplication::translate("Widget", "CPU\345\210\206\351\241\265\345\244\247\345\260\217\357\274\232", Q_NULLPTR));
        label_8->setText(QApplication::translate("Widget", "CPU\345\220\215\347\247\260\357\274\232", Q_NULLPTR));
        label_9->setText(QApplication::translate("Widget", "CPU\344\270\273\351\242\221\357\274\232", Q_NULLPTR));
        label_19->setText(QApplication::translate("Widget", "CPU\345\236\213\345\217\267\357\274\232", Q_NULLPTR));
        label_10->setText(QApplication::translate("Widget", "CPU\347\211\210\346\234\254\357\274\232", Q_NULLPTR));
        label_11->setText(QApplication::translate("Widget", "CPU\344\275\277\347\224\250\347\216\207", Q_NULLPTR));
        tabWidget->setTabText(tabWidget->indexOf(tab_2), QApplication::translate("Widget", "CPU", Q_NULLPTR));
        label_12->setText(QApplication::translate("Widget", "\347\211\251\347\220\206\345\206\205\345\255\230\344\275\277\347\224\250\347\216\207\357\274\232", Q_NULLPTR));
        label_14->setText(QApplication::translate("Widget", "\345\217\257\347\224\250\347\211\251\347\220\206\345\206\205\345\255\230\357\274\232", Q_NULLPTR));
        label_16->setText(QApplication::translate("Widget", "\345\217\257\347\224\250\351\241\265\351\235\242\346\226\207\344\273\266\345\244\247\345\260\217\357\274\232", Q_NULLPTR));
        label_18->setText(QApplication::translate("Widget", "\345\217\257\347\224\250\350\231\232\346\213\237\345\206\205\345\255\230\357\274\232", Q_NULLPTR));
        label_13->setText(QApplication::translate("Widget", "\347\211\251\347\220\206\345\206\205\345\255\230\346\200\273\351\207\217\357\274\232", Q_NULLPTR));
        label_15->setText(QApplication::translate("Widget", "\347\263\273\347\273\237\351\241\265\351\235\242\346\226\207\344\273\266\345\244\247\345\260\217\357\274\232", Q_NULLPTR));
        label_17->setText(QApplication::translate("Widget", "\350\231\232\346\213\237\345\206\205\345\255\230\346\200\273\351\207\217\357\274\232", Q_NULLPTR));
        tabWidget->setTabText(tabWidget->indexOf(tab_3), QApplication::translate("Widget", "\345\206\205\345\255\230", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem = TW_disk->horizontalHeaderItem(0);
        ___qtablewidgetitem->setText(QApplication::translate("Widget", "\346\211\200\345\234\250\347\233\230\347\254\246", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem1 = TW_disk->horizontalHeaderItem(1);
        ___qtablewidgetitem1->setText(QApplication::translate("Widget", "\347\243\201\347\233\230\347\261\273\345\236\213", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem2 = TW_disk->horizontalHeaderItem(2);
        ___qtablewidgetitem2->setText(QApplication::translate("Widget", "\345\211\251\344\275\231\351\207\217", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem3 = TW_disk->horizontalHeaderItem(3);
        ___qtablewidgetitem3->setText(QApplication::translate("Widget", "\344\275\277\347\224\250\351\207\217", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem4 = TW_disk->horizontalHeaderItem(4);
        ___qtablewidgetitem4->setText(QApplication::translate("Widget", "\346\200\273\351\207\217", Q_NULLPTR));
        tabWidget->setTabText(tabWidget->indexOf(tab_4), QApplication::translate("Widget", "\347\243\201\347\233\230", Q_NULLPTR));
        label_4->setText(QApplication::translate("Widget", "\346\223\215\344\275\234\347\263\273\347\273\237\357\274\232", Q_NULLPTR));
        label_20->setText(QApplication::translate("Widget", "\346\223\215\344\275\234\347\263\273\347\273\237\347\211\210\346\234\254\357\274\232", Q_NULLPTR));
        label->setText(QApplication::translate("Widget", "CPU\346\216\251\347\240\201\357\274\232", Q_NULLPTR));
        label_6->setText(QApplication::translate("Widget", "\346\234\200\345\260\217\345\257\273\345\235\200\345\215\225\345\205\203\357\274\232", Q_NULLPTR));
        label_5->setText(QApplication::translate("Widget", "\346\234\200\345\244\247\345\257\273\345\235\200\345\215\225\345\205\203\357\274\232", Q_NULLPTR));
        test->setText(QApplication::translate("Widget", "Test", Q_NULLPTR));
        tabWidget->setTabText(tabWidget->indexOf(tab_5), QApplication::translate("Widget", "\344\270\273\346\234\272", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class Widget: public Ui_Widget {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_WIDGET_H

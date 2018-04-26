#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QTimer>
#include "SysInfo.h"
#include "sysinfofinderwinimpl.h"

namespace Ui {
class Widget;
}

class Widget : public QWidget
{
        Q_OBJECT

    public:
        explicit Widget(QWidget *parent = 0);
        ~Widget();

    private:
        Ui::Widget *ui;

        QTimer* t;
        int t_msec;
        SysInfoFinderWinImpl* winFinder;

        QList<proc_info> proc_list;
        cpu_info cpu_data;
        mem_info mem_data;
        pc_info pc_data;
        QList<disk_info> disk_list;

        void Init();
        void InitParams();

        void getViewDatas();
        void showViewDatas();
//        void showProcess();
        void showProcessView();
        void showCpu();   
        void showMemory();
        void showDisk();
        void showPC();
    private slots:
        void t_timeout_slot();

        //
        void on_test_clicked();
};

#endif // WIDGET_H

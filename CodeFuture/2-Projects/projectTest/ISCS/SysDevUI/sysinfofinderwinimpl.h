#ifndef SYSINFOFINDERWINIMPL_H
#define SYSINFOFINDERWINIMPL_H

#include "SysInfo.h"
#include "windows.h"

#include "isysinfofinder.h"

class SysInfoFinderWinImpl : public ISysInfoFinder
{
        Q_OBJECT
    public:
        explicit SysInfoFinderWinImpl(QObject *parent = 0);

        void getProcessInfo();
        void getCpuInfo();
        void getMemoryInfo();
        void getDiskInfo();
        void getPCInfo();

        QList<proc_info> proc_list;
        cpu_info cpu_data;
        mem_info mem_data;
        pc_info pc_data;
        QList<disk_info> disk_list;
    private:
        void getSysProcess();
        void getSysProcessMemory(int nPid, int &mem);
        void getSysCpu();
        void getSysCpuBaseInfo();
        void getSysCpuByRegist();
        void getSysMemory();
        void getSysDisk();
        void getPCBaseInfo();

        FILETIME prev_idle_t;
        FILETIME prev_kernel_t;
        FILETIME prev_user_t;

        void Init();
        void InitDatas();
    signals:

    public slots:

};

#endif // SYSINFOFINDERWINIMPL_H

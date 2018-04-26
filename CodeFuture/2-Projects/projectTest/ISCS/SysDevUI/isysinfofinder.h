#ifndef ISYSINFOFINDER_H
#define ISYSINFOFINDER_H

#include <QObject>

class ISysInfoFinder : public QObject
{
        Q_OBJECT
    public:
        explicit ISysInfoFinder(QObject *parent = 0);

        //获取进程信息
        virtual void getSysProcess() = 0;
        //获取进程相关内存信息
        virtual void getSysProcessMemory(int nPid,int& mem) = 0;
        //获取CPU信息
        virtual void getSysCpu() = 0;
        //获取CPU基本信息
        virtual void getSysCpuBaseInfo() = 0;
        //获取内存信息
        virtual void getSysMemory() = 0;
        //获取磁盘信息
        virtual void getSysDisk() = 0;
    signals:

    public slots:

};

#endif // ISYSINFOFINDER_H

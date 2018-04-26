#ifndef SYSWINDOWIMPL_H
#define SYSWINDOWIMPL_H

#include <QObject>
#include <QTimer>

class SysWindowImpl : public QObject
{
        Q_OBJECT
    public:
        explicit SysWindowImpl(QObject *parent = 0);

        void test_getSysCpu();
        void test_getSysCpuBaseInfo();
        void test_getSysMemory();
        void test_getSysProcess();
        bool test_getSysProcessMemory(int nPid, int& mem);
        bool test_getSysFromRegist();

        int res_procId;
        QString result;
    signals:

    public slots:
        void timeoutSlot();
    private:
        QTimer* t;    
};

#endif // SYSWINDOWIMPL_H

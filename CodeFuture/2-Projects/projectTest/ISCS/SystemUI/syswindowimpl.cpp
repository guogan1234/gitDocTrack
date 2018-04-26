#include "syswindowimpl.h"

#pragma once
#pragma comment(lib,"Psapi.lib")

#include "iostream"
#include "windows.h"
//PROCESSENTRY32、MODULEENTRY32等结构体头文件
#include "tlhelp32.h"
#include "psapi.h"

//windows基本类型定义头文件
//#include "windef.h"

#include <QDebug>

#define MB (1024*1024)

struct process_ele{
        int proc_id;
        QString proc_name;
        int proc_memory;
};

SysWindowImpl::SysWindowImpl(QObject *parent) :
    QObject(parent)
{
    t = new QTimer();
    t->setInterval(1000);
    connect(t,SIGNAL(timeout()),this,SLOT(timeoutSlot()));
//    t->start();

    result = "start";
}

__int64 CompareFileTime ( FILETIME time1, FILETIME time2 )
{
    __int64 a = time1.dwHighDateTime << 32 | time1.dwLowDateTime ;
    __int64 b = time2.dwHighDateTime << 32 | time2.dwLowDateTime ;
    return   (b - a);
}

//将宽字节wchar_t*转化为单字节char*
inline char* UnicodeToAnsi( const wchar_t* szStr )
{
    int nLen = WideCharToMultiByte( CP_ACP, 0, szStr, -1, NULL, 0, NULL, NULL );
    if (nLen == 0)
    {
        return NULL;
    }
    char* pResult = new char[nLen];
    WideCharToMultiByte( CP_ACP, 0, szStr, -1, pResult, nLen, NULL, NULL );
    return pResult;
}

void test_YiWei(){
    int i = 1;
    int a = i<<32;
    int b = i<<2;
    qDebug("@:%d\n",a);
    qDebug("#:%d\n",b);

    int c = 0x80000000;
    int d = c<<33;
    qDebug("c1:%d c2:%d",c,d);
}

void SysWindowImpl::test_getSysCpu()
{
    qDebug("333");
    test_YiWei();

    HANDLE hEvent;

    FILETIME preidleTime;
    FILETIME prekernelTime;
    FILETIME preuserTime;
    GetSystemTimes( &preidleTime, &prekernelTime, &preuserTime );
    hEvent = CreateEvent (NULL,FALSE,FALSE,NULL); // 初始值为 nonsignaled ，并且每次触发后自动设置为nonsignaled
//    for(int i = 0;i<50;i++){
    while(true){
        WaitForSingleObject( hEvent,1000 ); //等待500毫秒

        FILETIME idleTime;
        FILETIME kernelTime;
        FILETIME userTime;
        GetSystemTimes( &idleTime, &kernelTime, &userTime );

        int idle = CompareFileTime( preidleTime,idleTime);
        int kernel = CompareFileTime( prekernelTime, kernelTime);
        int user = CompareFileTime(preuserTime, userTime);

        int cpu = (kernel +user - idle) *100/(kernel+user);
//        cout << "CPU利用率:" << cpu << "%" <<endl;
        std::cout<<"CPU利用率:" << cpu << "%"<<std::endl;
//        qDebug("CPU:%d%%\n",cpu);

        preidleTime = idleTime;
        prekernelTime = kernelTime;
        preuserTime = userTime ;
    }
}

void SysWindowImpl::test_getSysCpuBaseInfo()
{
    SYSTEM_INFO systemInfo;
    GetSystemInfo(&systemInfo);
    qDebug() << QStringLiteral("处理器掩码:") << systemInfo.dwActiveProcessorMask;
    qDebug() << QStringLiteral("处理器个数:") << systemInfo.dwNumberOfProcessors;
    qDebug() << QStringLiteral("处理器分页大小:") << systemInfo.dwPageSize;
    qDebug() << QStringLiteral("处理器类型:") << systemInfo.dwProcessorType;
    qDebug() << QStringLiteral("最大寻址单元:") << systemInfo.lpMaximumApplicationAddress;
    qDebug() << QStringLiteral("最小寻址单元:") << systemInfo.lpMinimumApplicationAddress;
    qDebug() << QStringLiteral("处理器等级:") << systemInfo.wProcessorLevel;
    qDebug() << QStringLiteral("处理器版本:") << systemInfo.wProcessorRevision;
    qDebug() << QStringLiteral("AllocationGranularity:") <<systemInfo.dwAllocationGranularity;

    qDebug("addr:%p",systemInfo.lpMaximumApplicationAddress);
}

void SysWindowImpl::test_getSysMemory()
{
    MEMORYSTATUSEX statex;
    statex.dwLength = sizeof (statex);
    GlobalMemoryStatusEx (&statex);
    qDebug() << QStringLiteral("物理内存使用率:") << statex.dwMemoryLoad;
    qDebug() << QStringLiteral("物理内存总量:") << statex.ullTotalPhys/MB;
    qDebug() << QStringLiteral("可用的物理内存:") << statex.ullAvailPhys/MB;
    qDebug() << QStringLiteral("系统页面文件大小:") << statex.ullTotalPageFile/MB;
    qDebug() << QStringLiteral("系统可用页面文件大小:") << statex.ullAvailPageFile/MB;
    qDebug() << QStringLiteral("虚拟内存总量:") << statex.ullTotalVirtual/MB;
    qDebug() << QStringLiteral("可用的虚拟内存:") << statex.ullAvailVirtual/MB;
    qDebug() << QStringLiteral("保留（值为0）:") << statex.ullAvailExtendedVirtual/MB;
}

void SysWindowImpl::test_getSysProcess()
{
    HANDLE hProcessSnap;    //进程快照的句柄
    HANDLE hProcess;    //用于获取进程的句柄
    PROCESSENTRY32 pe32;//进程信息的结构体
//    QMap<int,QString> pidMap;//保存所有进程的PID
    // 获取系统进程信息的快照
    hProcessSnap = CreateToolhelp32Snapshot( TH32CS_SNAPPROCESS, 0 );
    if( hProcessSnap == INVALID_HANDLE_VALUE )
    {
        qDebug()<<"CreateToolhelp32Snapshot Failed!";// 打印错误信息
        if(NULL !=hProcessSnap)
        {
            CloseHandle( hProcessSnap );          // 关闭进程快照信息
            hProcessSnap = NULL;
        }
//        return pidMap;
    }
    // 在使用之前设置PROCESSENTRY32结构体的初始大小值,如果不初始化dwSize, Process32First 调用会失败.
    pe32.dwSize = sizeof( PROCESSENTRY32 );
    if( !Process32First( hProcessSnap, &pe32 ) )// 开始获取第一个进程的信息，如果获取失败就返回
    {
        qDebug()<<"Process32First Failed!"; // 打印错误信息
        if(NULL !=hProcessSnap)
        {
            CloseHandle( hProcessSnap );          // 关闭进程快照信息
            hProcessSnap = NULL;
        }
//        return pidMap;
    }
    //开始遍历所有进程
//    pidMap.clear();
    do
    {
        QString strProcessName =UnicodeToAnsi(pe32.szExeFile);
        int p_id = pe32.th32ProcessID;
        qDebug()<<"list--"<<strProcessName;
        int memory = 0;
        test_getSysProcessMemory(p_id,memory);
        qDebug()<<"list2--"<<memory;

//        //加入PID
//        if(!pidMap.contains((int)pe32.th32ProcessID))
//        {
//            QString strProcessName =UnicodeToAnsi(pe32.szExeFile);
//            pidMap.insert((int)pe32.th32ProcessID,strProcessName);
//        }

        //当然还可以获取到很多其他信息，例如进程名字(szExeFile[MAX_PATH])、父进程PPID(th32ParentProcessID)。。。
        /* 附上该结构体信息
            typedef struct tagPROCESSENTRY32 {
            DWORD     dwSize;
            DWORD     cntUsage;
            DWORD     th32ProcessID;
            ULONG_PTR th32DefaultHeapID;
            DWORD     th32ModuleID;
            DWORD     cntThreads;
            DWORD     th32ParentProcessID;
            LONG      pcPriClassBase;
            DWORD     dwFlags;
            TCHAR     szExeFile[MAX_PATH];
            } PROCESSENTRY32, *PPROCESSENTRY32;
            */
    }
    while( Process32Next( hProcessSnap, &pe32 ) );// 获取下一个进程的信息
    if(NULL !=hProcessSnap)//最后关闭快照句柄
    {
        CloseHandle( hProcessSnap );
        hProcessSnap = NULL;
    }
    //    return pidMap;
}

bool SysWindowImpl::test_getSysProcessMemory(int nPid, int &mem)
{
    HANDLE hProcess;//该线程的句柄
    PROCESS_MEMORY_COUNTERS pmc;//该线程的内存信息结构体，需要psapi.h
    hProcess = OpenProcess( PROCESS_ALL_ACCESS ,FALSE, nPid );//利用最大权限打开该线程并获得句柄
    if (NULL == hProcess)
    {
        qDebug()<<"OpenProcess Failed!";
        return false;
    }
    qDebug("before psapi.dll call...");
    res_procId = mem;
    result = "before psapi.dll call...";
    if ( !GetProcessMemoryInfo( hProcess, &pmc, sizeof(pmc)) )//需要psapi.lib和psapi.dll
    {
        qDebug()<<"GetProcessMemoryInfo Failed!";
        result = "GetProcessMemoryInfo Failed!";
        return false;
    }
    mem = (int)pmc.WorkingSetSize;
    qDebug("mem--%d",mem);
    result = QString("%1").arg(mem);

//    int nMemTotal = 0;
//    int nMemUsed = 0;
//    this->GetSysMemory(nMemTotal,nMemUsed);
//    double tep = ((int)pmc.WorkingSetSize*1.0);
//    mem = (int)ceil(100*(tep/(1024.0*1024.0)) / nMemTotal) ;

    CloseHandle(hProcess);

    return true;
}

bool SysWindowImpl::test_getSysFromRegist()
{
    qDebug("test_getSysFromRegist");
    //打开注册表
    HKEY  hKey;
    LONG  nRet = RegOpenKeyExW(HKEY_LOCAL_MACHINE,
                                L"SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
                                0,
                                KEY_ALL_ACCESS,
                                &hKey);
    if(nRet != ERROR_SUCCESS)
        return false;
    //获取操作系统名称  “Windows 7 Ultimate”

    DWORD type;
    WCHAR szProductName[100] = {0};
    DWORD dwSize = 100;
    nRet = RegQueryValueExW(hKey, L"ProductName", NULL, &type, (BYTE *)szProductName, &dwSize);
    if(nRet != ERROR_SUCCESS){
        return false;
    }else{
//        std::cout<<"1--"<<szProductName<<std::endl;
        qDebug("qt--%s",szProductName);
        printf("1--%s\n",szProductName);
    }

    //获取CurrentVersion  “6.1”
    WCHAR szCurrentVersion[100] = {0};
    dwSize = 100;
    nRet = RegQueryValueExW(hKey, L"CurrentVersion", NULL,&type, (BYTE *)szCurrentVersion, &dwSize);
    if(nRet != ERROR_SUCCESS)
        return false;
}

void SysWindowImpl::timeoutSlot()
{
    qDebug("----------------");
    test_getSysMemory();
}

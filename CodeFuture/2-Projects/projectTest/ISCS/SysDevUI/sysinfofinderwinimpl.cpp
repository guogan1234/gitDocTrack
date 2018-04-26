#include "sysinfofinderwinimpl.h"

#define MB (1024*1024)

#include "windows.h"
#include "tlhelp32.h"
#include "psapi.h"
#include <QDebug>

SysInfoFinderWinImpl::SysInfoFinderWinImpl(QObject *parent) :
    ISysInfoFinder(parent)
{
}

__int64 CompareFileTime ( FILETIME time1, FILETIME time2 )
{
    __int64 a = time1.dwHighDateTime << 32 | time1.dwLowDateTime ;
    __int64 b = time2.dwHighDateTime << 32 | time2.dwLowDateTime ;
    return   (b - a);
}

//将单字节char*转化为宽字节wchar_t*
wchar_t* AnsiToUnicode( const char* szStr )
{
    int nLen = MultiByteToWideChar( CP_ACP, MB_PRECOMPOSED, szStr, -1, NULL, 0 );
    if (nLen == 0)
    {
        return NULL;
    }
    wchar_t* pResult = new wchar_t[nLen];
    MultiByteToWideChar( CP_ACP, MB_PRECOMPOSED, szStr, -1, pResult, nLen );
    return pResult;
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

void SysInfoFinderWinImpl::getProcessInfo()
{
    getSysProcess();
}

void SysInfoFinderWinImpl::getCpuInfo()
{
    getSysCpuBaseInfo();
    getSysCpu();
    getSysCpuByRegist();
}

void SysInfoFinderWinImpl::getMemoryInfo()
{
    getSysMemory();
}

void SysInfoFinderWinImpl::getDiskInfo()
{
    getSysDisk();
}

void SysInfoFinderWinImpl::getPCInfo()
{
    getPCBaseInfo();
}

void SysInfoFinderWinImpl::getSysProcess()
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
    }
    //开始遍历所有进程
    proc_list.clear();
    do
    {
        proc_info temp;

        char* szFile = NULL;
        szFile = UnicodeToAnsi(pe32.szExeFile);
        int p_id = pe32.th32ProcessID;
//        qDebug()<<"list--"<<strProcessName;
        int memory = 0;
        getSysProcessMemory(p_id,memory);
        qDebug()<<"list2--"<<memory;

        temp.id = p_id;
        temp.name = szFile;
        temp.p_id = pe32.th32ParentProcessID;
        temp.t_num = pe32.cntThreads;
        temp.currentMemory = memory;
        qDebug()<<"list3--"<<pe32.cntThreads<<"--"<<temp.t_num;

        proc_list.append(temp);

//        //加入PID
//        if(!pidMap.contains((int)pe32.th32ProcessID))
//        {
//            QString strProcessName =UnicodeToAnsi(pe32.szExeFile);
//            pidMap.insert((int)pe32.th32ProcessID,strProcessName);
//        }

        //当然还可以获取到很多其他信息，例如进程名字(szExeFile[MAX_PATH])、父进程PPID(th32ParentProcessID)。。。
    }
    while( Process32Next( hProcessSnap, &pe32 ) );// 获取下一个进程的信息
    if(NULL !=hProcessSnap)//最后关闭快照句柄
    {
        CloseHandle( hProcessSnap );
        hProcessSnap = NULL;
    }
}

void SysInfoFinderWinImpl::getSysProcessMemory(int nPid, int &mem)
{
    HANDLE hProcess;//该线程的句柄
    PROCESS_MEMORY_COUNTERS pmc;//该线程的内存信息结构体
    hProcess = OpenProcess( PROCESS_ALL_ACCESS ,FALSE, nPid );//利用最大权限打开该线程并获得句柄
    if (NULL == hProcess)
    {
        qDebug()<<"OpenProcess Failed!";
        return ;
    }
    if ( !GetProcessMemoryInfo( hProcess, &pmc, sizeof(pmc)) )
    {
        qDebug()<<"GetProcessMemoryInfo Failed!";
        return ;
    }
    mem = (int)pmc.WorkingSetSize;
    qDebug("mem--%d",mem);

    CloseHandle(hProcess);
}

void SysInfoFinderWinImpl::getSysCpu()
{
    FILETIME idleTime;
    FILETIME kernelTime;
    FILETIME userTime;
    GetSystemTimes( &idleTime, &kernelTime, &userTime );

    int idle = CompareFileTime( prev_idle_t,idleTime);
    int kernel = CompareFileTime( prev_kernel_t, kernelTime);
    int user = CompareFileTime(prev_user_t, userTime);

    int cpu = (kernel +user - idle) *100/(kernel+user);
    cpu_data.used = cpu;

    prev_idle_t = idleTime;
    prev_kernel_t = kernelTime;
    prev_user_t = userTime ;
}

void SysInfoFinderWinImpl::getSysCpuBaseInfo()
{
    SYSTEM_INFO systemInfo;
    GetSystemInfo(&systemInfo);

    QString str = "";
    str = systemInfo.dwActiveProcessorMask;
    qDebug()<<"111--"<<str;
//    cpu_data.mask = str;
    cpu_data.num = systemInfo.dwNumberOfProcessors;
    cpu_data.page_size = systemInfo.dwPageSize;
    cpu_data.type = systemInfo.dwProcessorType;
//    cpu_data.max_unit = systemInfo.lpMaximumApplicationAddress;
//    cpu_data.min_unit = systemInfo.lpMinimumApplicationAddress;
    cpu_data.level = systemInfo.wProcessorLevel;
    cpu_data.version = systemInfo.wProcessorRevision;
}

void SysInfoFinderWinImpl::getSysCpuByRegist()
{
    HKEY  hKey;
    LONG lReturn;
    DWORD type;
    DWORD dwSize = 100;
    DWORD dwMHz;
    WCHAR szCPUName[100] = {0};

    //打开注册表
    lReturn = RegOpenKeyExW(HKEY_LOCAL_MACHINE,
                            L"HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0",
                            0,
                            KEY_READ,
                            &hKey);
    if(lReturn != ERROR_SUCCESS)
        return ;
    //获取CPU名称
    lReturn = RegQueryValueExW(hKey, L"ProcessorNameString", NULL, &type, (BYTE *)szCPUName, &dwSize);
    if(lReturn != ERROR_SUCCESS)
        return ;
    //获取CPU主频
    lReturn = RegQueryValueExW(hKey, L"~MHz", NULL, NULL, (LPBYTE)&dwMHz, &dwSize);
    if(lReturn != ERROR_SUCCESS)
        return ;
//    qDebug("#--%s",szCPUName);
//    qDebug("@--%d",dwMHz);
    char *ch = NULL;
    ch = UnicodeToAnsi(szCPUName);
//    qDebug("##--%s",ch);
    cpu_data.frequency = dwMHz;
//    cpu_data.name = QString(ch);
    cpu_data.name = ch;
    RegCloseKey(hKey);
}

void SysInfoFinderWinImpl::getSysMemory()
{
    MEMORYSTATUSEX statex;
    statex.dwLength = sizeof (statex);
    GlobalMemoryStatusEx (&statex);

    mem_data.used = statex.dwMemoryLoad;
    mem_data.total = statex.ullTotalPhys/MB;
    mem_data.remain = statex.ullAvailPhys/MB;
    mem_data.page_size = statex.ullTotalPageFile/MB;
    mem_data.remain_page = statex.ullAvailPageFile/MB;
    mem_data.virtual_total = statex.ullTotalVirtual/MB;
    mem_data.remain_virtual = statex.ullAvailVirtual/MB;
}

void SysInfoFinderWinImpl::getSysDisk()
{
    DWORD dwSize = MAX_PATH;
    WCHAR szLogicalDrives[MAX_PATH] = {0};
    //获取逻辑驱动器号字符串
    DWORD dwResult = GetLogicalDriveStrings(dwSize,szLogicalDrives);
    //处理获取到的结果
    if (dwResult > 0 && dwResult <= MAX_PATH) {
        WCHAR* szSingleDrive = szLogicalDrives;
//        char* szSingleDrive = szLogicalDrives;  //从缓冲区起始地址开始
        disk_list.clear();
        while(*szSingleDrive) {
            qDebug("drive:%s",szSingleDrive);
//            printf("Drive: %s\n", szSingleDrive);   //输出单个驱动器的驱动器号
//            putDrivesType(szSingleDrive);           //输出逻辑驱动器类型
//            putDrivesFreeSpace(szSingleDrive);

            disk_info temp;

            //获取磁盘类型
            UINT uDriverType = GetDriveType(szSingleDrive);
            char* type = NULL;

               switch(uDriverType) {
               case DRIVE_UNKNOWN  :{
                   type = "未知的磁盘类型";
//                   puts("未知的磁盘类型");
                   break;
               }
               case DRIVE_NO_ROOT_DIR: {qDebug("路径无效");type = "路径无效"; break;}
               case DRIVE_REMOVABLE: {qDebug("可移动磁盘");type = "可移动磁盘"; break;}
               case DRIVE_FIXED: {qDebug("固定磁盘");type = "固定磁盘"; break;}
               case DRIVE_REMOTE: {qDebug("网络磁盘");type = "网络磁盘"; break;}
               case DRIVE_CDROM: {qDebug("光驱");type = "光驱"; break;}
               case DRIVE_RAMDISK: {qDebug("内存映射盘");type = "内存映射盘"; break;}
               default:
                   break;
               }

            //获取磁盘容量
            unsigned long long available,total,free;
            if(GetDiskFreeSpaceEx(szSingleDrive,(ULARGE_INTEGER*)&available,(ULARGE_INTEGER*)&total,(ULARGE_INTEGER*)&free)){
                qDebug()<<"res--"<<*szSingleDrive<<" "<<available<<" "<<total<<" "<<free;
                printf("Drives %s | total = %lld MB,available = %lld MB,free = %lld MB\n",
                       szSingleDrive,total>>20,available>>20,free>>20);
            }else{
                qDebug("获取容量信息失败");
            }
            char* ch = UnicodeToAnsi(szSingleDrive);
            szSingleDrive += strlen(ch) + 1;
//            // 获取下一个驱动器号起始地址
//            szSingleDrive += strlen(szSingleDrive) + 1;

            temp.location = ch;
            temp.remain = free/MB;
            temp.total = total/MB;
            temp.type = type;
            temp.used = (total - free)/MB;

            disk_list.append(temp);
        }
    }
}

void SysInfoFinderWinImpl::getPCBaseInfo()
{
    //打开注册表
    HKEY  hKey;
    LONG  nRet = RegOpenKeyExW(HKEY_LOCAL_MACHINE,
                               L"SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
                               0,
                               KEY_ALL_ACCESS,
                               &hKey);
    if(nRet != ERROR_SUCCESS)
        return ;
    //获取操作系统名称  “Windows 7 Ultimate”

    DWORD type;
    WCHAR szProductName[100] = {0};
    DWORD dwSize = 100;
    nRet = RegQueryValueExW(hKey, L"ProductName", NULL, &type, (BYTE *)szProductName, &dwSize);
    if(nRet != ERROR_SUCCESS)
        return ;

    //获取CurrentVersion  “6.1”
    WCHAR szCurrentVersion[100] = {0};
    dwSize = 100;
    nRet = RegQueryValueExW(hKey, L"CurrentVersion", NULL,&type, (BYTE *)szCurrentVersion, &dwSize);
    if(nRet != ERROR_SUCCESS)
        return ;

    char* ch_1 = NULL;
    ch_1 = UnicodeToAnsi(szProductName);
    char* ch_2 = NULL;
    ch_2 = UnicodeToAnsi(szCurrentVersion);

    pc_data.OS_name = ch_1;
    pc_data.OS_version = ch_2;
}

void SysInfoFinderWinImpl::Init()
{
    InitDatas();
}

void SysInfoFinderWinImpl::InitDatas()
{
    cpu_data.used = 0;
    getSysCpuBaseInfo();
}

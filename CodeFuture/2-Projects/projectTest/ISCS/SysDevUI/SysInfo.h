#ifndef SYSINFO_H
#define SYSINFO_H

#include <QString>

struct proc_info{
       int id;
       char* name;
       int p_id;
       int t_num;
       int currentMemory;
       int memory_top;
       int break_num;
};

struct cpu_info{
        int used;
        char* mask;
        int num;
        int page_size;
        int type;
        char* max_unit;
        char* min_unit;
        int level;
        int version;
        char* type_name;
        int frequency;
        char* name;
};

struct mem_info{
        int used;
        int total;
        int remain;
        int page_size;
        int remain_page;
        int virtual_total;
        int remain_virtual;
};

struct disk_info{
        char* location;
        char* type;
        int used;
        int remain;
        int total;
};

struct pc_info{
        char* OS_name;
        char* OS_version;
};

#endif // SYSINFO_H

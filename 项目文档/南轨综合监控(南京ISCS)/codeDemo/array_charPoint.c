#include <stdio.h>

int main()
{
    int i, size;
    const char *tables[] = {
        "table_001",
        "table_002",
        "table_003",
        "table_004",
        "table_005",
    };

    size = sizeof(tables)/sizeof(char*);
    for (i = 0; i < size; i++)
    {
        printf("table[%d]=%s\n", i, tables[i]);
    }
    return 0;
}

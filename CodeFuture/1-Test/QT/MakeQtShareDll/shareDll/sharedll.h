#ifndef SHAREDLL_H
#define SHAREDLL_H

#include "sharedll_global.h"
#include <QString>

class SHAREDLLSHARED_EXPORT ShareDll
{

    public:
        ShareDll();

        QString getStr();
};

#endif // SHAREDLL_H

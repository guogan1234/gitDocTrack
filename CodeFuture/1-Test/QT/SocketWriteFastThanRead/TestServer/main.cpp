#include <QCoreApplication>

#include "testtcpserver.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    TestTcpserver* server = new TestTcpserver();

    return a.exec();
}

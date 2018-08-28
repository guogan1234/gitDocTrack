#include <QCoreApplication>
#include "appserver.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    AppServer server;
//    server.start();
    server.startMyServer();

    return a.exec();
}

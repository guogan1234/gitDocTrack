#include <QCoreApplication>
#include "appserver.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    AppServer* app = new AppServer();
    app->start();

    return a.exec();
}

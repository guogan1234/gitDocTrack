#include <QCoreApplication>
#include "mainapp.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    MainApp app;
    app.start();

    return a.exec();
}

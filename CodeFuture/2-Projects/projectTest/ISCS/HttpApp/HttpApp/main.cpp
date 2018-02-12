#include <QCoreApplication>
#include <QSettings>
#include "httplistener.h"
#include "httprequesthandler.h"

#include "hellocontroller.h"
#include "listdatacontroller.h"
#include "requestmapper.h"

using namespace stefanfrings;

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    QString iniPath = QCoreApplication::applicationDirPath() + "/httpConfig.ini";
    QSettings* settings = new QSettings(iniPath,QSettings::IniFormat,&a);
    settings->beginGroup("listener");

//    HttpListener* listener = new HttpListener(settings, new HttpRequestHandler(&a), &a);
//    new HttpListener(settings,new HelloController(&a),&a);
//    new HttpListener(settings,new ListDataController(&a),&a);
    new HttpListener(settings,new requestMapper(&a),&a);

    return a.exec();
}

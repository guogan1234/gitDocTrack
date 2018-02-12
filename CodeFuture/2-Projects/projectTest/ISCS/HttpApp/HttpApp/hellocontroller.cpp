#include "hellocontroller.h"

HelloController::HelloController()
{
}

HelloController::HelloController(QObject *parent)
    :HttpRequestHandler(parent)
{

}

void HelloController::service(HttpRequest &request, HttpResponse &response)
{
    response.write("Http--hello!",true);
//    response.write("Http--你好！",true);
}

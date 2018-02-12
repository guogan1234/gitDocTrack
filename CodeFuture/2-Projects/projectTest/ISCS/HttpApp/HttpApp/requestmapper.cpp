#include "requestmapper.h"

#include "hellocontroller.h"
#include "listdatacontroller.h"
#include "logincontroller.h"

requestMapper::requestMapper()
{
}

requestMapper::requestMapper(QObject *parent)
    :HttpRequestHandler(parent)
{

}

int count = 0;
void requestMapper::service(HttpRequest &request, HttpResponse &response)
{
    count++;
    QByteArray path = request.getPath();
    qDebug("Request path is %s.[%d]",path.data(),count);

    if (path=="/" || path=="/hello") {
        HelloController(0).service(request, response);
    }
    else if (path=="/list") {
        ListDataController(0).service(request, response);
    }
    else if(path == "/login"){
        loginController(0).service(request,response);
    }
    else {
        response.setStatus(404,"Not found");
        response.write("The URL is wrong, no such document.",true);
    }

    qDebug("RequestMapper: finished request");
}

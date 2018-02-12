#ifndef HELLOCONTROLLER_H
#define HELLOCONTROLLER_H

#include "httprequesthandler.h"

using namespace stefanfrings;

class HelloController : public HttpRequestHandler
{
    public:
        HelloController();
        HelloController(QObject* parent = 0);

        void service(HttpRequest &request, HttpResponse &response);
};

#endif // HELLOCONTROLLER_H

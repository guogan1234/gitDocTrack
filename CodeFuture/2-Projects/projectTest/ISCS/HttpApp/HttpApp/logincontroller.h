#ifndef LOGINCONTROLLER_H
#define LOGINCONTROLLER_H

#include "httprequesthandler.h"
using namespace stefanfrings;

class loginController : public HttpRequestHandler
{
    public:
        loginController();
        loginController(QObject* parent = 0);
        void service(HttpRequest &request, HttpResponse &response);
};

#endif // LOGINCONTROLLER_H

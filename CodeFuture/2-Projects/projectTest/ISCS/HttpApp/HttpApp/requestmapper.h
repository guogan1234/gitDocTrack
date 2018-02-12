#ifndef REQUESTMAPPER_H
#define REQUESTMAPPER_H

#include "httprequesthandler.h"
using namespace stefanfrings;

class requestMapper : public HttpRequestHandler
{
    public:
        requestMapper();
        requestMapper(QObject* parent = 0);

        void service(HttpRequest &request, HttpResponse &response);
};

#endif // REQUESTMAPPER_H

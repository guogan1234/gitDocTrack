#ifndef LISTDATACONTROLLER_H
#define LISTDATACONTROLLER_H

#include "httprequesthandler.h"

using namespace stefanfrings;

class ListDataController : public HttpRequestHandler
{
    public:
        ListDataController();
        ListDataController(QObject* parent = 0);

        void service(HttpRequest &request, HttpResponse &response);
};

#endif // LISTDATACONTROLLER_H

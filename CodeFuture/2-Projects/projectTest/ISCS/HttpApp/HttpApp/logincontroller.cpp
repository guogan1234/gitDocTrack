#include "logincontroller.h"

loginController::loginController()
{
}

loginController::loginController(QObject *parent)
    :HttpRequestHandler(parent)
{

}

void loginController::service(HttpRequest &request, HttpResponse &response)
{
    QByteArray username=request.getParameter("username");
    QByteArray password=request.getParameter("password");

    qDebug("username=%s",username.constData());
    qDebug("password=%s",password.constData());

    response.setHeader("Content-Type", "text/html; charset=UTF-8");
    response.write("<html><body>");

    if (username=="test" and password=="hello") {
        response.write("Yes, correct");
    }
    else {
        response.write("<form method='POST' action='/login'>");
        if (!username.isEmpty()) {
            response.write("No, that was wrong!<br><br>");
        }
        response.write("Please login:<br>");
        response.write("Name:  <input type='text' name='username'><br>");
        response.write("Password: <input type='password' name='password'><br>");
        response.write("<input type='submit'>");
        response.write("</form>");
    }

    response.write("</body></html>",true);
}

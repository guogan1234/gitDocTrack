#ifndef MYWEBPAGE_H
#define MYWEBPAGE_H

#include <QWebPage>

class myWebPage : public QWebPage
{
        Q_OBJECT
    public:
        explicit myWebPage(QWidget *parent = 0);

        QObject* createPlugin(const QString &classid, const QUrl &url, const QStringList &paramNames, const QStringList &paramValues);
        void javaScriptAlert(QWebFrame* frame,QString msg);
    signals:

    public slots:

};

#endif // MYWEBPAGE_H

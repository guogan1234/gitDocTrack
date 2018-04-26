#ifndef DOWNLOADNETWORKMANAGER_H
#define DOWNLOADNETWORKMANAGER_H

#include <QNetworkAccessManager>

class DownloadNetworkManager : public QNetworkAccessManager
{
        Q_OBJECT
    public:
        explicit DownloadNetworkManager(QObject *parent = 0);
        ~DownloadNetworkManager();

        void execute();
        void replyFinished(QNetworkReply *reply);
        void onAuthenticationRequest(QNetworkReply *reply, QAuthenticator *authenticator);
        void readyRead();
    signals:

    public slots:

    private:
        QString m_strFileName;
};

#endif // DOWNLOADNETWORKMANAGER_H

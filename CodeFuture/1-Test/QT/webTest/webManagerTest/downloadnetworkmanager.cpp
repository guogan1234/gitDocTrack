#include "downloadnetworkmanager.h"

#include <QFileInfo>

DownloadNetworkManager::DownloadNetworkManager(QObject *parent) :
    QNetworkAccessManager(parent)
{
    // 获取当前的时间戳，设置下载的临时文件名称
        QDateTime dateTime = QDateTime::currentDateTime();
        QString date = dateTime.toString("yyyy-MM-dd-hh-mm-ss-zzz");
        m_strFileName = QString("E:/%1.tmp").arg(date);

        connect(this, SIGNAL(finished(QNetworkReply *)), this, SLOT(replyFinished(QNetworkReply *)));
}

DownloadNetworkManager::~DownloadNetworkManager()
{
    // 终止下载
        if (m_pReply != NULL)
        {
            m_pReply->abort();
            m_pReply->deleteLater();
        }
}

void DownloadNetworkManager::execute()
{
    m_url = QUrl("http://192.168.*.*/download/2.0.0.zip");

        QNetworkRequest request;
        request.setUrl(m_url);
        request.setHeader(QNetworkRequest::ContentTypeHeader, "application/zip");

        connect(this, SIGNAL(authenticationRequired(QNetworkReply *, QAuthenticator *)), this, SLOT(onAuthenticationRequest(QNetworkReply *, QAuthenticator *)));

        m_pReply = get(request);
        connect(m_pReply, SIGNAL(downloadProgress(qint64, qint64)), this, SIGNAL(downloadProgress(qint64, qint64)));
        connect(m_pReply, SIGNAL(readyRead()), this, SLOT(readyRead()));
}

void DownloadNetworkManager::replyFinished(QNetworkReply *reply)
{
    // 获取响应的信息，状态码为200表示正常
        QVariant statusCode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute);

        // 无错误返回
        if (reply->error() == QNetworkReply::NoError)
        {
            // 重命名临时文件
            QFileInfo fileInfo(m_strFileName);
            QFileInfo newFileInfo = fileInfo.absolutePath() + m_url.fileName();
            QDir dir;
            if (dir.exists(fileInfo.absolutePath()))
            {
                if (newFileInfo.exists())
                    newFileInfo.dir().remove(newFileInfo.fileName());
                QFile::rename(m_strFileName, newFileInfo.absoluteFilePath());
            }
        }
        else
        {
            QString strError = reply->errorString();
            qDebug() << "Error:" << strError;
        }

        emit replyFinished(statusCode.toInt());
}

void DownloadNetworkManager::onAuthenticationRequest(QNetworkReply *reply, QAuthenticator *authenticator)
{
    QByteArray password;
        password.append("123456");
        password = QByteArray::fromBase64(password);

        QString strPassword(password);

        authenticator->setUser("wang");
        authenticator->setPassword(strPassword);
}

void DownloadNetworkManager::readyRead()
{
    QFileInfo fileInfo(m_strFileName);
        QFileInfo newFileInfo = fileInfo.absolutePath() + m_url.fileName();
        QString strFileName = newFileInfo.absoluteFilePath();

        emit fileName(strFileName);

        // 写文件-形式为追加
        QFile file(m_strFileName);
        if (file.open(QIODevice::Append))
            file.write(m_pReply->readAll());
        file.close();
}

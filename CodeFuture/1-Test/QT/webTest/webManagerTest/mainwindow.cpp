#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::download()
{
    if (m_pNetworkManager == NULL)
        {
            m_pNetworkManager = new DownloadNetworkManager(this);
            connect(m_pNetworkManager, SIGNAL(downloadProgress(qint64, qint64)), this, SLOT(downloadProgress(qint64, qint64)), Qt::QueuedConnection);
            connect(m_pNetworkManager, SIGNAL(replyFinished(int)), this, SLOT(replyFinished(int)), Qt::QueuedConnection);
            connect(m_pNetworkManager, SIGNAL(fileName(QString)), m_pFileInfoLabel, SLOT(setText(QString)), Qt::QueuedConnection);
        }
        m_pNetworkManager->execute();
        downloadTime.start();
}

void MainWindow::downloadProgress(qint64 bytesReceived, qint64 bytesTotal)
{
    // 总时间
        int nTime = downloadTime.elapsed();

        // 本次下载所用时间
        nTime -= m_nTime;

        // 下载速度
        double dBytesSpeed = (bytesReceived * 1000.0) / nTime;
        double dSpeed = dBytesSpeed;

        //剩余时间
        qint64 leftBytes = (bytesTotal - bytesReceived);
        double dLeftTime = (leftBytes * 1.0) / dBytesSpeed;

        m_pSpeedInfoLabel->setText(speed(dSpeed));
        m_pLeftTimeInfoLabel->setText(timeFormat(qCeil(dLeftTime)));
        m_pFileSizeInfoLabel->setText(size(bytesTotal));
        m_pDownloadInfoLabel->setText(size(bytesReceived));
        m_pProgressBar->setMaximum(bytesTotal);
        m_pProgressBar->setValue(bytesReceived);

        // 获取上一次的时间
        m_nTime = nTime;
}

void MainWindow::replyFinished(int statusCode)
{
    m_nStatusCode = statusCode;
        QString strStatus = (statusCode == 200) ? QStringLiteral("下载成功") : QStringLiteral("下载失败");
        m_pStatusLabel->setText(strStatus);
}

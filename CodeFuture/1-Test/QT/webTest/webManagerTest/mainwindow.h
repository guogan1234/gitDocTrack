#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QLabel>
#include <QProgressBar>

#include "downloadnetworkmanager.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
        Q_OBJECT

    public:
        explicit MainWindow(QWidget *parent = 0);
        ~MainWindow();

    private:
        Ui::MainWindow *ui;

        void download();

        DownloadNetworkManager* m_pNetworkManager;

        void downloadProgress(qint64 bytesReceived, qint64 bytesTotal);

        void replyFinished(int statusCode);

        QLabel* m_pSpeedInfoLabel;
        QLabel* m_pLeftTimeInfoLabel;
        QLabel* m_pFileSizeInfoLabel;
        QLabel* m_pDownloadInfoLabel;
        QLabel* m_pStatusLabel;
        QProgressBar* m_pProgressBar;
        int m_nStatusCode;
        int m_nTime;
};

#endif // MAINWINDOW_H

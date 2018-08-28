#ifndef MAINAPP_H
#define MAINAPP_H

#include <QObject>
#include <QProcess>

class MainApp : public QObject
{
        Q_OBJECT
    public:
        explicit MainApp(QObject *parent = 0);

        void start();
    signals:

    public slots:
        void errorOccurredSlot(QProcess::ProcessError error);
        void finishedSlot(int exitCode, QProcess::ExitStatus exitStatus);
        void readyReadStandardErrorSlot();
        void readyReadStandardOutputSlot();
        void startedSlot();
        void stateChangedSlot(QProcess::ProcessState newState);

    private:
        QProcess* m_process;
};

#endif // MAINAPP_H

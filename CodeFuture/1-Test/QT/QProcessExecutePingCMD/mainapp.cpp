#include "mainapp.h"
#include <QDebug>

MainApp::MainApp(QObject *parent) : QObject(parent)
{

}

void MainApp::start()
{
    qDebug("start...\n");
    QProcess* process = new QProcess();
    m_process = process;
    connect(process,SIGNAL(errorOccurred(QProcess::ProcessError)),this,SLOT(errorOccurredSlot(QProcess::ProcessError)));
    connect(process,SIGNAL(finished(int,QProcess::ExitStatus)),this,SLOT(finishedSlot(int,QProcess::ExitStatus)));
    connect(process,SIGNAL(readyReadStandardError()),this,SLOT(readyReadStandardErrorSlot()));
    connect(process,SIGNAL(readyReadStandardOutput()),this,SLOT(readyReadStandardOutputSlot()));
    connect(process,SIGNAL(started()),this,SLOT(startedSlot()));
    connect(process,SIGNAL(stateChanged(QProcess::ProcessState)),this,SLOT(stateChangedSlot(QProcess::ProcessState)));
//    process->execute("ping -n 10 www.baidu.com");//静态函数，发现无法触发连接的槽函数
//    process->start("ping -n 10 www.baidu.com");//可以触发连接的槽函数
    process->start("ping -n 10 123.123.123.123");
    qDebug("process is started...");

    bool bFinished = process->waitForFinished(-1);
    qDebug("----------------------");
    qDebug()<<"bFinished - "<<bFinished;
    qDebug()<<"exitStatus - "<<process->exitStatus();
    qDebug()<<"exitCode - "<<process->exitCode();
    qDebug()<<"state - "<<process->state();

    qDebug("start exit normally!\n");
}

void MainApp::errorOccurredSlot(QProcess::ProcessError error)
{
    qDebug()<<"errorOccurredSlot..."<<error;
}

void MainApp::finishedSlot(int exitCode, QProcess::ExitStatus exitStatus)
{
    qDebug()<<"finishedSlot...";
    qDebug()<<"exitCode - "<<exitCode<<"exitStatus - "<<exitStatus;
}

void MainApp::readyReadStandardErrorSlot()
{
    qDebug()<<"readyReadStandardErrorSlot...";
}

void MainApp::readyReadStandardOutputSlot()
{
    qDebug()<<"readyReadStandardOutputSlot...";
    //数据一次被读完，以下3种方式都能获取数据
//    QByteArray b1 = m_process->readAll();
//    qDebug()<<"b1 - "<<b1;
//    QByteArray b2 = m_process->readAllStandardOutput();
//    qDebug()<<"b2 - "<<b2;
    QByteArray b3 = m_process->readLine();
    qDebug()<<"b3 - "<<b3;
}

void MainApp::startedSlot()
{
    qDebug()<<"startedSlot...";
}

void MainApp::stateChangedSlot(QProcess::ProcessState newState)
{
    qDebug()<<"stateChangedSlot..."<<newState;
}

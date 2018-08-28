#include <QCoreApplication>
#include <QDebug>

void test(){
    qDebug()<<"char - "<<sizeof(char);
    qDebug()<<"short - "<<sizeof(short);
    qDebug()<<"int - "<<sizeof(int);
    qDebug()<<"long int - "<<sizeof(long int);
    qDebug()<<"long - "<<sizeof(long);
    qDebug()<<"long long - "<<sizeof(long long);

    qDebug()<<"float - "<<sizeof(float);
    qDebug()<<"double - "<<sizeof(double);

    qDebug()<<"void* - "<<sizeof(void*);
    qDebug()<<"char* - "<<sizeof(char*);
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    test();

    return a.exec();
}

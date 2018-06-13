#ifndef CLASSDATA_H
#define CLASSDATA_H

#include <QObject>

class ClassData : public QObject
{
        Q_OBJECT
    public:
        explicit ClassData(QObject *parent = 0);

        char c_1[1024];
        char c_2[1024];
        char c_3[1024];
        char c_4[1024];
        char c_5[1024];
        char c_6[1024];
        char c_7[1024];
        char c_8[1024];
        char c_9[1024];
        char c_10[1024];
    signals:

    public slots: 
};

#endif // CLASSDATA_H

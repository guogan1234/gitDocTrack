#ifndef SIMPLYOBJECT_H
#define SIMPLYOBJECT_H

#include <QObject>

class SimplyObject : public QObject
{
        Q_OBJECT
    public:
        explicit SimplyObject(QObject *parent = 0);

        char* name;
        void simplyFun();
    signals:

    public slots:
        void signalFun();
};

#endif // SIMPLYOBJECT_H

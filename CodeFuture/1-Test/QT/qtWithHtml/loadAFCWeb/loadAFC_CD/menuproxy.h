#ifndef MENUPROXY_H
#define MENUPROXY_H

#include <QObject>

class menuProxy : public QObject
{
        Q_OBJECT
    public:
        explicit menuProxy(QObject *parent = 0);

        void jsCallTest_2();

    signals:

    public slots:
        void jsCallTest_public(QString str);

    private:
        void jsCallTest(QString str);
};

#endif // MENUPROXY_H

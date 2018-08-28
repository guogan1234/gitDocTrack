#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>

namespace Ui {
class Widget;
}

class Widget : public QWidget
{
        Q_OBJECT

    public:
        explicit Widget(QWidget *parent = 0);
        ~Widget();

        bool event(QEvent *event);
        bool eventFilter(QObject *watched, QEvent *event);

    private slots:
        void on_comboBox_activated(int index);

        void on_comboBox_activated(const QString &arg1);

        void on_comboBox_currentIndexChanged(int index);

        void on_comboBox_currentIndexChanged(const QString &arg1);

        void on_comboBox_currentTextChanged(const QString &arg1);

        void on_comboBox_editTextChanged(const QString &arg1);

        void on_comboBox_highlighted(int index);

    private:
        Ui::Widget *ui;
};

#endif // WIDGET_H

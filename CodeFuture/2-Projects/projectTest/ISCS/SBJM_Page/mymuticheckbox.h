#ifndef MYMUTICHECKBOX_H
#define MYMUTICHECKBOX_H

#include <QWidget>

namespace Ui {
class myMutiCheckBox;
}

class myMutiCheckBox : public QWidget
{
        Q_OBJECT

    public:
        explicit myMutiCheckBox(QWidget *parent = 0);
        ~myMutiCheckBox();

    private slots:
        void on_pushButton_clicked();

    private:
        Ui::myMutiCheckBox *ui;
};

#endif // MYMUTICHECKBOX_H

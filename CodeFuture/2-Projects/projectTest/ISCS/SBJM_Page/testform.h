#ifndef TESTFORM_H
#define TESTFORM_H

#include <QWidget>

namespace Ui {
class TestForm;
}

class TestForm : public QWidget
{
        Q_OBJECT

    public:
        explicit TestForm(QWidget *parent = 0);
        ~TestForm();

    private:
        Ui::TestForm *ui;

        void Init();
        void InitComboBox();
};

#endif // TESTFORM_H

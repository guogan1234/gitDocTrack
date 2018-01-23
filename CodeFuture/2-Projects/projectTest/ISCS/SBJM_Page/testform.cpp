#include "testform.h"
#include "ui_testform.h"
#include <QListWidget>
#include <QCheckBox>

//#include "mymuticheckbox.h"

TestForm::TestForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::TestForm)
{
    ui->setupUi(this);

    Init();
}

TestForm::~TestForm()
{
    delete ui;
}

void TestForm::Init()
{
    qCritical("Critical--临界的，危险的！");
//    qFatal("Fatal--致命的，毁灭性的!!!");//此方法会请求运行系统结束本程序
    InitComboBox();
}

void TestForm::InitComboBox()
{
    qDebug("InitComboBox...");
    QListWidget* pListWidget = new QListWidget(this);
    for (int i = 0; i < 5; ++i)
    {
        QListWidgetItem *pItem = new QListWidgetItem(pListWidget);
        pListWidget->addItem(pItem);
//        pItem->setData(Qt::UserRole, i);
        QCheckBox *pCheckBox = new QCheckBox(this);
//        pCheckBox->setText(QStringLiteral("Qter%1").arg(i));
        pCheckBox->setText("hello");
        pListWidget->addItem(pItem);
        pListWidget->setItemWidget(pItem, pCheckBox);
//        connect(pCheckBox, SIGNAL(stateChanged(int)), this, SLOT(stateChanged(int)));
    }
//    ui.comboBox->setModel(pListWidget->model());
//    ui.comboBox->setView(pListWidget);
    ui->comboBox->setModel(pListWidget->model());
    ui->comboBox->setView(pListWidget);
    qDebug("InitComboBox!");
}

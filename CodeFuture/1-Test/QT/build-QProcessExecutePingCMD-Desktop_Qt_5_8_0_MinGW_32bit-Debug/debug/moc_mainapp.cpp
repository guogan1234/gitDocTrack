/****************************************************************************
** Meta object code from reading C++ file 'mainapp.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.8.0)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../QProcessExecutePingCMD/mainapp.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'mainapp.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.8.0. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_MainApp_t {
    QByteArrayData data[15];
    char stringdata0[226];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_MainApp_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_MainApp_t qt_meta_stringdata_MainApp = {
    {
QT_MOC_LITERAL(0, 0, 7), // "MainApp"
QT_MOC_LITERAL(1, 8, 17), // "errorOccurredSlot"
QT_MOC_LITERAL(2, 26, 0), // ""
QT_MOC_LITERAL(3, 27, 22), // "QProcess::ProcessError"
QT_MOC_LITERAL(4, 50, 5), // "error"
QT_MOC_LITERAL(5, 56, 12), // "finishedSlot"
QT_MOC_LITERAL(6, 69, 8), // "exitCode"
QT_MOC_LITERAL(7, 78, 20), // "QProcess::ExitStatus"
QT_MOC_LITERAL(8, 99, 10), // "exitStatus"
QT_MOC_LITERAL(9, 110, 26), // "readyReadStandardErrorSlot"
QT_MOC_LITERAL(10, 137, 27), // "readyReadStandardOutputSlot"
QT_MOC_LITERAL(11, 165, 11), // "startedSlot"
QT_MOC_LITERAL(12, 177, 16), // "stateChangedSlot"
QT_MOC_LITERAL(13, 194, 22), // "QProcess::ProcessState"
QT_MOC_LITERAL(14, 217, 8) // "newState"

    },
    "MainApp\0errorOccurredSlot\0\0"
    "QProcess::ProcessError\0error\0finishedSlot\0"
    "exitCode\0QProcess::ExitStatus\0exitStatus\0"
    "readyReadStandardErrorSlot\0"
    "readyReadStandardOutputSlot\0startedSlot\0"
    "stateChangedSlot\0QProcess::ProcessState\0"
    "newState"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_MainApp[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       6,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: name, argc, parameters, tag, flags
       1,    1,   44,    2, 0x0a /* Public */,
       5,    2,   47,    2, 0x0a /* Public */,
       9,    0,   52,    2, 0x0a /* Public */,
      10,    0,   53,    2, 0x0a /* Public */,
      11,    0,   54,    2, 0x0a /* Public */,
      12,    1,   55,    2, 0x0a /* Public */,

 // slots: parameters
    QMetaType::Void, 0x80000000 | 3,    4,
    QMetaType::Void, QMetaType::Int, 0x80000000 | 7,    6,    8,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, 0x80000000 | 13,   14,

       0        // eod
};

void MainApp::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        MainApp *_t = static_cast<MainApp *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->errorOccurredSlot((*reinterpret_cast< QProcess::ProcessError(*)>(_a[1]))); break;
        case 1: _t->finishedSlot((*reinterpret_cast< int(*)>(_a[1])),(*reinterpret_cast< QProcess::ExitStatus(*)>(_a[2]))); break;
        case 2: _t->readyReadStandardErrorSlot(); break;
        case 3: _t->readyReadStandardOutputSlot(); break;
        case 4: _t->startedSlot(); break;
        case 5: _t->stateChangedSlot((*reinterpret_cast< QProcess::ProcessState(*)>(_a[1]))); break;
        default: ;
        }
    }
}

const QMetaObject MainApp::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_MainApp.data,
      qt_meta_data_MainApp,  qt_static_metacall, Q_NULLPTR, Q_NULLPTR}
};


const QMetaObject *MainApp::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *MainApp::qt_metacast(const char *_clname)
{
    if (!_clname) return Q_NULLPTR;
    if (!strcmp(_clname, qt_meta_stringdata_MainApp.stringdata0))
        return static_cast<void*>(const_cast< MainApp*>(this));
    return QObject::qt_metacast(_clname);
}

int MainApp::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 6)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 6;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 6)
            *reinterpret_cast<int*>(_a[0]) = -1;
        _id -= 6;
    }
    return _id;
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE

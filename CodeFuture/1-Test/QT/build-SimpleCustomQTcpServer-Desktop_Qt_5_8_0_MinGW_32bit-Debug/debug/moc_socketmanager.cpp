/****************************************************************************
** Meta object code from reading C++ file 'socketmanager.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.8.0)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../SimpleCustomQTcpServer/socketmanager.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'socketmanager.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.8.0. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_SocketManager_t {
    QByteArrayData data[27];
    char stringdata0[360];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_SocketManager_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_SocketManager_t qt_meta_stringdata_SocketManager = {
    {
QT_MOC_LITERAL(0, 0, 13), // "SocketManager"
QT_MOC_LITERAL(1, 14, 14), // "recvDataSignal"
QT_MOC_LITERAL(2, 29, 0), // ""
QT_MOC_LITERAL(3, 30, 3), // "msg"
QT_MOC_LITERAL(4, 34, 11), // "QTcpSocket*"
QT_MOC_LITERAL(5, 46, 6), // "socket"
QT_MOC_LITERAL(6, 53, 21), // "socketDisconnectedSig"
QT_MOC_LITERAL(7, 75, 13), // "connectedSlot"
QT_MOC_LITERAL(8, 89, 16), // "disconnectedSlot"
QT_MOC_LITERAL(9, 106, 9), // "errorSlot"
QT_MOC_LITERAL(10, 116, 28), // "QAbstractSocket::SocketError"
QT_MOC_LITERAL(11, 145, 11), // "socketError"
QT_MOC_LITERAL(12, 157, 13), // "hostFoundSlot"
QT_MOC_LITERAL(13, 171, 16), // "stateChangedSlot"
QT_MOC_LITERAL(14, 188, 28), // "QAbstractSocket::SocketState"
QT_MOC_LITERAL(15, 217, 11), // "socketState"
QT_MOC_LITERAL(16, 229, 16), // "aboutToCloseSlot"
QT_MOC_LITERAL(17, 246, 16), // "bytesWrittenSlot"
QT_MOC_LITERAL(18, 263, 5), // "bytes"
QT_MOC_LITERAL(19, 269, 13), // "readyReadSlot"
QT_MOC_LITERAL(20, 283, 13), // "destroyedSlot"
QT_MOC_LITERAL(21, 297, 3), // "obj"
QT_MOC_LITERAL(22, 301, 21), // "objectNameChangedSlot"
QT_MOC_LITERAL(23, 323, 10), // "objectName"
QT_MOC_LITERAL(24, 334, 13), // "writeDataSlot"
QT_MOC_LITERAL(25, 348, 4), // "data"
QT_MOC_LITERAL(26, 353, 6) // "client"

    },
    "SocketManager\0recvDataSignal\0\0msg\0"
    "QTcpSocket*\0socket\0socketDisconnectedSig\0"
    "connectedSlot\0disconnectedSlot\0errorSlot\0"
    "QAbstractSocket::SocketError\0socketError\0"
    "hostFoundSlot\0stateChangedSlot\0"
    "QAbstractSocket::SocketState\0socketState\0"
    "aboutToCloseSlot\0bytesWrittenSlot\0"
    "bytes\0readyReadSlot\0destroyedSlot\0obj\0"
    "objectNameChangedSlot\0objectName\0"
    "writeDataSlot\0data\0client"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_SocketManager[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
      14,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       2,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    2,   84,    2, 0x06 /* Public */,
       6,    0,   89,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
       7,    0,   90,    2, 0x0a /* Public */,
       8,    0,   91,    2, 0x0a /* Public */,
       9,    1,   92,    2, 0x0a /* Public */,
      12,    0,   95,    2, 0x0a /* Public */,
      13,    1,   96,    2, 0x0a /* Public */,
      16,    0,   99,    2, 0x0a /* Public */,
      17,    1,  100,    2, 0x0a /* Public */,
      19,    0,  103,    2, 0x0a /* Public */,
      20,    1,  104,    2, 0x0a /* Public */,
      20,    0,  107,    2, 0x2a /* Public | MethodCloned */,
      22,    1,  108,    2, 0x0a /* Public */,
      24,    2,  111,    2, 0x0a /* Public */,

 // signals: parameters
    QMetaType::Void, QMetaType::QByteArray, 0x80000000 | 4,    3,    5,
    QMetaType::Void,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, 0x80000000 | 10,   11,
    QMetaType::Void,
    QMetaType::Void, 0x80000000 | 14,   15,
    QMetaType::Void,
    QMetaType::Void, QMetaType::LongLong,   18,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QObjectStar,   21,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString,   23,
    QMetaType::Void, QMetaType::QByteArray, 0x80000000 | 4,   25,   26,

       0        // eod
};

void SocketManager::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        SocketManager *_t = static_cast<SocketManager *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->recvDataSignal((*reinterpret_cast< QByteArray(*)>(_a[1])),(*reinterpret_cast< QTcpSocket*(*)>(_a[2]))); break;
        case 1: _t->socketDisconnectedSig(); break;
        case 2: _t->connectedSlot(); break;
        case 3: _t->disconnectedSlot(); break;
        case 4: _t->errorSlot((*reinterpret_cast< QAbstractSocket::SocketError(*)>(_a[1]))); break;
        case 5: _t->hostFoundSlot(); break;
        case 6: _t->stateChangedSlot((*reinterpret_cast< QAbstractSocket::SocketState(*)>(_a[1]))); break;
        case 7: _t->aboutToCloseSlot(); break;
        case 8: _t->bytesWrittenSlot((*reinterpret_cast< qint64(*)>(_a[1]))); break;
        case 9: _t->readyReadSlot(); break;
        case 10: _t->destroyedSlot((*reinterpret_cast< QObject*(*)>(_a[1]))); break;
        case 11: _t->destroyedSlot(); break;
        case 12: _t->objectNameChangedSlot((*reinterpret_cast< const QString(*)>(_a[1]))); break;
        case 13: _t->writeDataSlot((*reinterpret_cast< QByteArray(*)>(_a[1])),(*reinterpret_cast< QTcpSocket*(*)>(_a[2]))); break;
        default: ;
        }
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 0:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 1:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QTcpSocket* >(); break;
            }
            break;
        case 4:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QAbstractSocket::SocketError >(); break;
            }
            break;
        case 6:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QAbstractSocket::SocketState >(); break;
            }
            break;
        case 13:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 1:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< QTcpSocket* >(); break;
            }
            break;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        void **func = reinterpret_cast<void **>(_a[1]);
        {
            typedef void (SocketManager::*_t)(QByteArray , QTcpSocket * );
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&SocketManager::recvDataSignal)) {
                *result = 0;
                return;
            }
        }
        {
            typedef void (SocketManager::*_t)();
            if (*reinterpret_cast<_t *>(func) == static_cast<_t>(&SocketManager::socketDisconnectedSig)) {
                *result = 1;
                return;
            }
        }
    }
}

const QMetaObject SocketManager::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_SocketManager.data,
      qt_meta_data_SocketManager,  qt_static_metacall, Q_NULLPTR, Q_NULLPTR}
};


const QMetaObject *SocketManager::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *SocketManager::qt_metacast(const char *_clname)
{
    if (!_clname) return Q_NULLPTR;
    if (!strcmp(_clname, qt_meta_stringdata_SocketManager.stringdata0))
        return static_cast<void*>(const_cast< SocketManager*>(this));
    return QObject::qt_metacast(_clname);
}

int SocketManager::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 14)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 14;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 14)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 14;
    }
    return _id;
}

// SIGNAL 0
void SocketManager::recvDataSignal(QByteArray _t1, QTcpSocket * _t2)
{
    void *_a[] = { Q_NULLPTR, const_cast<void*>(reinterpret_cast<const void*>(&_t1)), const_cast<void*>(reinterpret_cast<const void*>(&_t2)) };
    QMetaObject::activate(this, &staticMetaObject, 0, _a);
}

// SIGNAL 1
void SocketManager::socketDisconnectedSig()
{
    QMetaObject::activate(this, &staticMetaObject, 1, Q_NULLPTR);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE

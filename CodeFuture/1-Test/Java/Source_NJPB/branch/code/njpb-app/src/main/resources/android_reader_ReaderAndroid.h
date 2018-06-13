/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class android_reader_ReaderAndroid */

#ifndef _Included_android_reader_ReaderAndroid
#define _Included_android_reader_ReaderAndroid
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     android_reader_ReaderAndroid
 * Method:    open
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_open
  (JNIEnv *, jclass, jstring, jint);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    close
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_android_reader_ReaderAndroid_close
  (JNIEnv *, jobject, jint);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    getCardSN
 * Signature: (IIBB[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_getCardSN
  (JNIEnv *, jobject, jint, jint, jbyte, jbyte, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    MFRead
 * Signature: (IIBBB[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_MFRead
  (JNIEnv *, jobject, jint, jint, jbyte, jbyte, jbyte, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    MFWrite
 * Signature: (IIBBB[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_MFWrite
  (JNIEnv *, jobject, jint, jint, jbyte, jbyte, jbyte, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    MFGetVersion
 * Signature: (II[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_MFGetVersion
  (JNIEnv *, jobject, jint, jint, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    MFRstAnt
 * Signature: (II[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_MFRstAnt
  (JNIEnv *, jobject, jint, jint, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    MFCloseAnt
 * Signature: (II[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_MFCloseAnt
  (JNIEnv *, jobject, jint, jint, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    CPU_RATS
 * Signature: (II[B[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_CPU_1RATS
  (JNIEnv *, jobject, jint, jint, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    CPU_APDU
 * Signature: (II[BB[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_CPU_1APDU
  (JNIEnv *, jobject, jint, jint, jbyteArray, jbyte, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    CPU_RST_Ant
 * Signature: (II[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_CPU_1RST_1Ant
  (JNIEnv *, jobject, jint, jint, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    Inventory_15693
 * Signature: (IIBBB[B[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_Inventory_115693
  (JNIEnv *, jobject, jint, jint, jbyte, jbyte, jbyte, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    ReadBlk_15693
 * Signature: (IIBBB[B[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_ReadBlk_115693
  (JNIEnv *, jobject, jint, jint, jbyte, jbyte, jbyte, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    WriteBlk_15693
 * Signature: (IIBBB[B[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_WriteBlk_115693
  (JNIEnv *, jobject, jint, jint, jbyte, jbyte, jbyte, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    LockBlk_15693
 * Signature: (IIBB[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_LockBlk_115693
  (JNIEnv *, jobject, jint, jint, jbyte, jbyte, jbyteArray, jbyteArray);

/*
 * Class:     android_reader_ReaderAndroid
 * Method:    getCardUID
 * Signature: (II[B[B)I
 */
JNIEXPORT jint JNICALL Java_android_reader_ReaderAndroid_getCardUID
  (JNIEnv *, jobject, jint, jint, jbyteArray, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif
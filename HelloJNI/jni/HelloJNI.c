#include <jni.h>
#include <stdio.h>
#include "demo_HelloJNI.h"

JNIEXPORT void JNICALL Java_demo_HelloJNI_sayHello(JNIEnv *env, jobject thisObj) {
   printf("Hello World!\n");
   return;
}

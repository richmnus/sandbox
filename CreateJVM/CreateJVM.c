/*
 * Copyright (c) 1995-1997 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
#include <jni.h>


int main() {
    JNIEnv *env;
    JavaVM *jvm;
    JDK1_1InitArgs vm_args;
    jint res;
    jclass cls;
    jmethodID mid;
    jstring jstr;
    jobjectArray args;


    /* IMPORTANT: specify vm_args version # if you use JDK1.1.2 and beyond */
    vm_args.version = 0x00010001;

    JNI_GetDefaultJavaVMInitArgs(&vm_args);

    res = JNI_CreateJavaVM(&jvm,&env,&vm_args);
    if (res < 0) {
        fprintf(stderr, "Can't create Java VM\n");
        exit(1);
    }

    cls = (*env)->FindClass(env, "Prog");
    if (cls == 0) {
        fprintf(stderr, "Can't find Prog class\n");
        exit(1);
    }

    mid = (*env)->GetStaticMethodID(env, cls, "main", "([Ljava/lang/String;)V");
    if (mid == 0) {
        fprintf(stderr, "Can't find Prog.main\n");
        exit(1);
    }

    jstr = (*env)->NewStringUTF(env, " from C!");
    if (jstr == 0) {
        fprintf(stderr, "Out of memory\n");
        exit(1);
    }
    args = (*env)->NewObjectArray(env, 1,
                        (*env)->FindClass(env, "java/lang/String"), jstr);
    if (args == 0) {
        fprintf(stderr, "Out of memory\n");
        exit(1);
    }
    (*env)->CallStaticVoidMethod(env, cls, mid, args);

    (*jvm)->DestroyJavaVM(jvm);

    return 0;
}


/*
 ============================================================================
 Name        : LaunchJVM.c
 Author      :
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <jni.h>

JNIEnv* create_vm() {
	JavaVM* jvm;
	JNIEnv* env;
	JavaVMInitArgs args;
	JavaVMOption options[1];

	/* There is a new JNI_VERSION_1_4, but it doesn't add anything for the purposes of our example. */
	args.version = JNI_VERSION_1_2;
//	args.version = JNI_VERSION_1_4;
	args.nOptions = 1;
//	options[0].optionString = "-Djava.class.path=c:\\projects\\local\\inonit\\classes";
	options[0].optionString = "-Djava.class.path=C:\\Work\\workspaces\\eclipse_juno4.2_jee_sandbox\\HelloWorldClass\\bin";
	args.options = options;
	args.ignoreUnrecognized = JNI_FALSE;

	JNI_CreateJavaVM(&jvm, (void **)&env, &args);
	return env;
}

void invoke_class(JNIEnv* env) {
	jclass helloWorldClass;
	jmethodID mainMethod;
	jobjectArray applicationArgs;
	jstring applicationArg0;

	// "pkgname/classname"
//	helloWorldClass = (*env)->FindClass(env, "example/jni/InvocationHelloWorld");
	helloWorldClass = (*env)->FindClass(env, "demo/HelloWorldClass");

	mainMethod = (*env)->GetStaticMethodID(env, helloWorldClass, "main", "([Ljava/lang/String;)V");

	applicationArgs = (*env)->NewObjectArray(env, 1, (*env)->FindClass(env, "java/lang/String"), NULL);
	applicationArg0 = (*env)->NewStringUTF(env, "From-C-program");
	(*env)->SetObjectArrayElement(env, applicationArgs, 0, applicationArg0);

	(*env)->CallStaticVoidMethod(env, helloWorldClass, mainMethod, applicationArgs);
}


int main(int argc, char **argv) {
	JNIEnv* env = create_vm();
	invoke_class(env);
	return 0;
}

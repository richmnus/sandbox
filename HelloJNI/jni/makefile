# Define a variable for classpath
CLASS_PATH = ../bin

# Define a virtual path for .class in the bin directory
vpath %.class $(CLASS_PATH)

all: hello.dll

# $@ matches the target, $< matches the first dependency
hello.dll: HelloJNI.o
	gcc -Wl,--add-stdcall-alias -shared -o $@ $<

# $@ matches the target, $< matches the first dependency
HelloJNI.o: HelloJNI.c demo_HelloJNI.h
	gcc -I"C:\Program Files\IBM\Java71\include" -I"C:\Program Files\IBM\Java71\include\win32" -c $< -o $@

# $* matches the target filename without the extension
demo_HelloJNI.h: HelloJNI.class
	javah -classpath $(CLASS_PATH) demo.HelloJNI

HelloJNI.class:

clean :
	rm demo_HelloJNI.h HelloJNI.o hello.dll

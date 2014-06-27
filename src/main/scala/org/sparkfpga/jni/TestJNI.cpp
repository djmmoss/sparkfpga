#include "org_sparkfpga_jni_TestJNI.h"
#include <ctype.h>
#include <string.h>

JNIEXPORT jstring JNICALL Java_org_sparkfpga_jni_TestJNI_passString (JNIEnv* env, jobject obj, jstring text) {
    const char* str = env->GetStringUTFChars(text, 0);
    return env->NewStringUTF(str);
}

package com.yeferic.holaflymarvel.commons

import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

fun <V, T : Any> setPrivatePropertyValue(
    objectClass: T,
    fieldName: String,
    value: V,
) {
    objectClass::class.java.getDeclaredField(fieldName).apply {
        isAccessible = true
        set(objectClass, value)
    }
}

fun <T> getPrivatePropertyValue(
    objectClass: Any,
    fieldName: String,
): T {
    return objectClass::class.java.getDeclaredField(fieldName).apply {
        isAccessible = true
    }.get(objectClass) as T
}

inline fun <reified T> T.callToPrivateFunction(
    nameFunction: String,
    vararg args: Any?,
): Any? =
    T::class.declaredFunctions.firstOrNull {
        it.name == nameFunction
    }?.apply { isAccessible = true }?.call(this, *args)

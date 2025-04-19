package com.javaweb.Utils;

import java.lang.reflect.UndeclaredThrowableException;

public class ExceptionUtils {

    /**
     * Tái ném ngoại lệ kiểu RuntimeException hoặc Error.
     * Nếu không phải, ném UndeclaredThrowableException.
     * @param ex ngoại lệ cần tái ném
     */
    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        // Nếu không phải RuntimeException hoặc Error, ném UndeclaredThrowableException
        throw new UndeclaredThrowableException(ex);
    }
}

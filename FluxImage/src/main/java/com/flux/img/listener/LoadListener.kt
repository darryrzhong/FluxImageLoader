package com.flux.img.listener

/**
 * <pre>
 *     类描述  :
 *
 *
 *     @author : never
 *     @since   : 2025/2/19
 * </pre>
 */

interface LoadListener<T> {
    fun onSuccess(bitmap: T) {}
    fun onStart() {}
    fun onCancel() {}
    fun onError(throwable: Throwable) {}
}


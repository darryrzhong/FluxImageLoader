package com.flux.img.okhttp

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.KeyManager
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * <pre>
 *     类描述  : https配置帮助类
 *     Https如果使用的CA证书, 不需要任何配置可以直接访问
 *     如果使用私有证书代理https(抓包),则需要配置https
 *
 *
 *     @author : never
 *     @since   : 2024/11/25
 * </pre>
 */
object HttpsHelper {

    /**
     * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
     * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
     * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
     */
    var UnSafeHostnameVerifier = HostnameVerifier { _, _ -> true }

    /**
     * 为了解决客户端不信任服务器数字证书的问题，网络上大部分的解决方案都是让客户端不对证书做任何检查，
     * 这是一种有很大安全漏洞的办法
     */
    var UnSafeTrustManager: X509TrustManager = object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }
}

internal fun prepareKeyManager(bksFile: InputStream?, password: String?): Array<KeyManager>? {
    try {
        if (bksFile == null || password == null) return null
        val clientKeyStore = KeyStore.getInstance("BKS")
        clientKeyStore.load(bksFile, password.toCharArray())
        val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        kmf.init(clientKeyStore, password.toCharArray())
        return kmf.keyManagers
    } catch (e: Exception) {
        Log.e("prepareKeyManager", "Exception", e)
    }
    return null
}





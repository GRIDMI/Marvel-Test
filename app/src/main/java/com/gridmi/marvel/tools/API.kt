package com.gridmi.marvel.tools

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.gridmi.marvel.Constant
import com.gridmi.marvel.dto.CharacterDto
import okhttp3.*
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest

open class API {

    companion object {

        abstract class Callback(
                private val failed: ((Throwable) -> Unit)? = null
        ) : okhttp3.Callback {

            abstract fun onSuccess(success: Rsp)
            open fun onFailure(throwable: Throwable) {
                failed?.invoke(throwable)
            }

            private val handler = Handler(Looper.getMainLooper())

            override fun onFailure(call: Call, e: IOException) {
                onPreFinal(failed = e)
            }

            @Suppress("UNCHECKED_CAST")
            override fun onResponse(call: Call, response: Response) {
                response.body()?.string()?.let { json ->
                    exchange(json)?.let {
                        onPreFinal(success = it)
                        return
                    }
                    onPreFinal(failed = Throwable("Can't create data"))
                }
                onPreFinal(failed = Throwable("Can't get json"))
            }

            private fun onPreFinal(success: Rsp? = null, failed: Throwable? = null) {
                handler.post {
                    try {
                        if (success != null) {
                            onSuccess(success)
                            return@post
                        }
                        throw failed ?: Throwable("Error!")
                    } catch (t:Throwable) {
                        onFailure(t)
                    }
                }
            }

            companion object {
                val gson = Gson()
                fun exchange(json:String) : Rsp? {
                    return gson.fromJson(json, Rsp::class.java)
                }
            }

        }

        private val client = OkHttpClient.Builder().addInterceptor(object : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {

                val request:Request = chain.request()

                val url:HttpUrl = withAuth(request.url())

                return chain.proceed(request.newBuilder().url(url).build())

            }

            fun withAuth(url:HttpUrl) : HttpUrl {

                val ts = (System.currentTimeMillis() / 1000L).toInt()

                val c = "${ts}${Constant.PRIVATE_KEY}${Constant.PUBLIC_KEY}"

                val builder:HttpUrl.Builder = url.newBuilder()
                builder.addQueryParameter("ts", ts.toString())
                builder.addQueryParameter("hash", md5(c))
                builder.addQueryParameter("apikey", Constant.PUBLIC_KEY)

                return builder.build()

            }

            fun md5(input:String): String {
                val md = MessageDigest.getInstance("MD5")
                return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
            }

        }).build()

        /**
         * Return list of whole characters
         * */
        fun characters(success:((List<CharacterDto>) -> Unit), failed:((Throwable) -> Unit)? = null) {

            val builder = Request.Builder().url(Constant.getHandlerPoint("characters"))

            client.newCall(builder.build()).enqueue(object : Callback(failed = failed) {
                override fun onSuccess(success: Rsp) {
                    success(success.data?.getResultsList(CharacterDto::class.java) ?: emptyList())
                }
            })

        }

    }

}
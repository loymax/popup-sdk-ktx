package loymax.smartcom.sdk.infrastructure

import com.google.gson.GsonBuilder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import loymax.smartcom.sdk.auth.HttpBearerAuth
import okhttp3.*
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.CallAdapter
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import retrofit2.converter.gson.GsonConverterFactory
class ApiClient(
    private var baseUrl: String = defaultBasePath,
    private val okHttpClientBuilder: OkHttpClient.Builder? = null,
    private val serializerBuilder: Moshi.Builder = Serializer.moshiBuilder,
    private val callFactory: Call.Factory? = null,
    private val callAdapterFactories: List<CallAdapter.Factory> = listOf(),
    private val converterFactories: List<Converter.Factory> = listOf(
        GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    ),
    private var bearerToken: String? = null
) {
    private val apiAuthorizations = mutableMapOf<String, Interceptor>()
    var logger: ((String) -> Unit)? = null

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .apply { callAdapterFactories.forEach { addCallAdapterFactory(it) } }
            .apply { converterFactories.forEach { addConverterFactory(it) } }
    }

    private val clientBuilder: OkHttpClient.Builder by lazy {
        okHttpClientBuilder ?: defaultClientBuilder
    }

    private val defaultClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message -> logger?.invoke(message) }
                .apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                bearerToken?.let {
                    request.addHeader("Authorization", "$it")
                }
                chain.proceed(request.build())
            }
    }

    init {
        normalizeBaseUrl()
    }

    /**
     * Устанавливает `Bearer Token` для всех последующих запросов
     */
    fun setBearerToken(token: String): ApiClient {
        this.bearerToken = token
        return this
    }

    /**
     * Создаёт сервисный интерфейс API
     */
    fun <S> createService(serviceClass: Class<S>): S {
        val usedCallFactory = this.callFactory ?: clientBuilder.build()
        return retrofitBuilder.callFactory(usedCallFactory).build().create(serviceClass)
    }

    private fun normalizeBaseUrl() {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/"
        }
    }

    companion object {
        @JvmStatic
        protected val baseUrlKey: String = "com.smartcommunications.sdk.baseUrl"

        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(baseUrlKey, "https://smcmaster-api.smc.nsk-k8s.loymax.net/api")
        }
    }
}
package com.example.data.di

import com.example.data.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val PRINT_LOG = BuildConfig.DEV       // 로그 출력 여부

    private const val CONNECT_TIMEOUT = 3000L // 커넥션 타임
    private const val WRITE_TIMEOUT = 3000L // 쓰기 타임
    private const val READ_TIMEOUT = 3000L // 읽기 타임

    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        if (PRINT_LOG) {
            OkHttpClient.Builder()
//                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))   // https 관련 보안 옵션
                .cookieJar(JavaNetCookieJar(CookieManager()))               // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)          // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)              // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)                // 연결 타임아웃 시간 설정
                .cache(null)                                                // 캐시사용 안함
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .header("Authorization", BuildConfig.REST_API_KET)
//                        .header("User-Agent", "")
//                        .header("devicemodel", Build.MODEL)
//                                    .header("key", "value")
                            .build()
                    )
                }
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()

        } else {
            OkHttpClient.Builder()
                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))     // https 관련 보안 옵션
                .cookieJar(JavaNetCookieJar(CookieManager()))               // 쿠키 매니저 연결
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)          // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)              // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)                // 연결 타임아웃 시간 설정
                .cache(null)                                                // 캐시사용 안함
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .header("Authorization", BuildConfig.REST_API_KET)
//                        .header("User-Agent", "")
//                        .header("devicemodel", Build.MODEL)
//                                    .header("key", "value")
                            .build()
                    )
                }
                .build()

        }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory() : MoshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    )


    @Singleton
    @Provides
    fun provideWeatherRetrofitInstance(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())      // ScalarConverter 적용
        .addConverterFactory(moshiConverterFactory)                 // MoshiConverter 적용
        .build()
}
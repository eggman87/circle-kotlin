package com.eggman.circleciandroid.injection

import android.app.Application
import android.content.Context
import com.eggman.circleciandroid.service.CircleApi
import com.eggman.circleciandroid.session.JsonSharedPreferencesSesson
import com.eggman.circleciandroid.session.Session
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import com.squareup.otto.Bus

import dagger.Module
import dagger.Provides
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import javax.inject.Singleton

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
@Module
class AppModule(private val app:Application) {


    @Provides
    @Singleton
    fun provideContext():Context {
        return app;
    }

    @Provides
    @Singleton
    fun provideCircleApi(client: OkHttpClient):CircleApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://circleci.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()


        return retrofit.create(CircleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(session: Session):OkHttpClient {
        val client = OkHttpClient()

        val interceptor = Interceptor { chain ->
            if (chain != null) {
                var request = chain.request();

                val token = session.getCircleToken();

                val httpUrl = request.httpUrl().newBuilder()
                if (token != null) {
                    httpUrl.addQueryParameter("circle-token", token)
                }

                request = request.newBuilder()
                        .header("Accept", "application/json")
                        .url(httpUrl.build())
                        .build()

                return@Interceptor chain.proceed(request)
            }

            null
        }

        client.interceptors().add(interceptor)

        val log = HttpLoggingInterceptor()
        log.level = HttpLoggingInterceptor.Level.BODY
        client.interceptors().add(log)

        return client
    }

    @Provides
    @Singleton
    fun provideSession(context:Context):Session {
        return JsonSharedPreferencesSesson(context)
    }

    @Provides
    @Singleton
    fun provideBus(): Bus {
        return Bus()
    }

}
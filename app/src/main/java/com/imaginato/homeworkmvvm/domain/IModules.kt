package com.imaginato.homeworkmvvm.domain

import android.app.Application
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imaginato.homeworkmvvm.data.local.login.AppDatabase
import com.imaginato.homeworkmvvm.data.local.login.LoginDao
import com.imaginato.homeworkmvvm.data.remote.login.LoginDataRepository
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.data.remote.login.loginapi.LoginApi
import com.imaginato.homeworkmvvm.ui.demo.MainActivityViewModel
import com.imaginato.homeworkmvvm.ui.login.LoginViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://private-222d3-homework5.apiary-mock.com"

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val netModules = module {
    single { provideInterceptors() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideGson() }
}

val apiModules = module {
    single { provideDemoApi(get()) }
}

val repositoryModules = module {
    single { provideDemoRepo(get(), loginDao = provideDao(provideDatabase(androidApplication()))) }
}

val viewModelModules = module {
    viewModel {
        MainActivityViewModel()
    }
    viewModel {
        LoginViewModel()
    }
}

private fun provideDemoRepo(api: LoginApi,loginDao: LoginDao): LoginRepository {
    return LoginDataRepository(api,loginDao)
}

private fun provideDemoApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

private fun provideDatabase(application: Application):AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "I-Database")
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideDao(database: AppDatabase): LoginDao {
    return database.loginDao
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.readTimeout(2, TimeUnit.MINUTES)
    clientBuilder.connectTimeout(2, TimeUnit.MINUTES)
    interceptors.forEach { clientBuilder.addInterceptor(it) }
    return clientBuilder.build()
}

private fun provideInterceptors(): ArrayList<Interceptor> {
    val interceptors = arrayListOf<Interceptor>()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    interceptors.add(loggingInterceptor)
    return interceptors
}

fun provideGson(): Gson {
    return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
}

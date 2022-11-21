package me.project.wswork.ui

import android.app.Application
import android.content.ContentProvider
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import me.project.wswork.data.workmanager.LeadWork
import me.project.wswork.data.workmanager.MyWorkweFactory
import me.project.wswork.data.repositories.CarsRepository
import me.project.wswork.di.dataBaseModule
import me.project.wswork.di.repositoryModule
import me.project.wswork.di.viewModelModule
import me.project.wswork.di.webServiceModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MyApp : Application(), Configuration.Provider{


    override fun onCreate() {
        super.onCreate()



        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    webServiceModule,
                    dataBaseModule,
                )
            )
        }


        val periodicWorkRequest = PeriodicWorkRequestBuilder<LeadWork>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "LeadWork",
            ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest
        )



    }

    override fun getWorkManagerConfiguration() = Configuration.Builder().setMinimumLoggingLevel(android.util.Log.DEBUG)
        .setWorkerFactory(MyWorkweFactory(repository = get() as CarsRepository)).build()



}
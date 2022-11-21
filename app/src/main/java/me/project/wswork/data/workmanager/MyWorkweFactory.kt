package me.project.wswork.data.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import me.project.wswork.data.repositories.CarsRepository

//Classe criada para poder passar parametros que desejamos utilizar
//pos o workmanager ja vem com seus parametros automaticos.
//Então precisamos de um factory para poder utilizar de outros parametros

class MyWorkweFactory(
    private val repository: CarsRepository
) : WorkerFactory() {


    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {

        return LeadWork(appContext,workerParameters,repository)
    }
}
package me.project.wswork.data.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.project.wswork.data.repositories.CarsRepository

class LeadWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object{
        const val TAG = "TLEADWork"
    }


    override  fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            val leadList = repository.getLeads()
            repository.inviteLeadsApi(leadList)
            Log.i(TAG, "DEU CERTO")
        }
        return Result.success()
        }

    }


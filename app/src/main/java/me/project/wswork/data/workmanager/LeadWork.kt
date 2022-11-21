package me.project.wswork.data.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.project.wswork.data.repositories.CarsRepository

class LeadWork(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: CarsRepository
) : Worker(context, workerParams) {


    // WorkManager ferramenta de agendamento de tarefas em background
    // nesta feita a cada 15 minutos está tarefa é executada
    //nos dando a rotina necessaria para o envio dos leads da api.
    // as configuraçoes e inicialização esta na classe MyAPp
    override fun doWork(): Result {
        return try {
            inviteLead()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    }

    private fun inviteLead() {
        CoroutineScope(Dispatchers.Default).launch {
            val request = repository.getLeads()
            if (request.isNotEmpty()) {
                repository.inviteLeadsApi(request)

            }
        }
    }
}
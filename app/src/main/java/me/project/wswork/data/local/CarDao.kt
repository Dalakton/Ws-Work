package me.project.wswork.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CarDao {

    //funções de inserir o usuario ao banco de dados, e pegar os usuarios do banco de dados.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leadEntity: LeadEntity)

    @Query("SELECT * FROM leadEntity ORDER BY id ASC")
    suspend fun getLeads(): List<LeadEntity>
}
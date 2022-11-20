package me.project.wswork.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leadEntity: LeadEntity)

    @Query("SELECT * FROM leadEntity")
    suspend fun getLeads(): List<LeadEntity>
}
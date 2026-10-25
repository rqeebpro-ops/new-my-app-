package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivationCodeDao {
    @Query("SELECT * FROM activation_codes ORDER BY createdAt DESC")
    fun getAllCodes(): Flow<List<ActivationCodeEntity>>

    @Query("SELECT * FROM activation_codes WHERE code = :code LIMIT 1")
    suspend fun getCodeByValue(code: String): ActivationCodeEntity?

    @Query("SELECT * FROM activation_codes WHERE isUsed = 0 ORDER BY createdAt DESC")
    fun getAvailableCodes(): Flow<List<ActivationCodeEntity>>

    @Query("SELECT COUNT(*) FROM activation_codes")
    fun getCodesCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM activation_codes WHERE isUsed = 1")
    fun getUsedCodesCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCode(code: ActivationCodeEntity): Long

    @Update
    suspend fun updateCode(code: ActivationCodeEntity)

    @Query("UPDATE activation_codes SET isUsed = :isUsed, status = :status, usedAt = :usedAt WHERE code = :code")
    suspend fun markCodeUsage(code: String, isUsed: Boolean, status: String, usedAt: Long)

    @Query("UPDATE activation_codes SET status = :status WHERE id = :id")
    suspend fun updateCodeStatus(id: Long, status: String)

    @Query("DELETE FROM activation_codes WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Delete
    suspend fun deleteCode(code: ActivationCodeEntity)

    @Query("DELETE FROM activation_codes")
    suspend fun clearAll()
}

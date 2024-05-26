package com.example.core.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "habits", indices = [Index(value = ["id"])])
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long? = null,
    val title : String,
    val description : String,
    val type : Int,
    val habitPriority : Int,
    val numberExecutions : Int,
    val period : Int,
    val color : Int,
    val dateCreation : Int,
    val uid : String,
    val syncStatus: SyncStatus
)
enum class SyncStatus {
    SYNCED,
    PENDING_UPLOAD,
    PENDING_DOWNLOAD
}

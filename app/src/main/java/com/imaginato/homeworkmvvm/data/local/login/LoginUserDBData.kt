package com.imaginato.homeworkmvvm.data.local.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LoginUserData")
data class LoginUserDBData(
    @PrimaryKey
    val userId: String,
    @ColumnInfo(name = "name")
    val userName: String,
    @ColumnInfo(name = "isDeleted")
    val isDeleted: Boolean,
    @ColumnInfo(name = "XAcc")
    val xAcc: String
)
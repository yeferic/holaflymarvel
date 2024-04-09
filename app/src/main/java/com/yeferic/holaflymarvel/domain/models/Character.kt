package com.yeferic.holaflymarvel.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yeferic.holaflymarvel.core.commons.Constants.EMPTY_STRING

@Entity
data class Character(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0,
    @ColumnInfo(name = "name")
    var name: String = EMPTY_STRING,
    @ColumnInfo(name = "description")
    var description: String = EMPTY_STRING,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String = EMPTY_STRING,
)

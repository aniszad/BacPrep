package com.tp.bacprep.data.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tp.bacprep.domain.models.User

@Dao
interface CurrentUserDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentUser(user : User)

    @Query("SELECT * FROM current_user_table WHERE id = :id")
    fun getCurrentUser(id : String) : User?
}
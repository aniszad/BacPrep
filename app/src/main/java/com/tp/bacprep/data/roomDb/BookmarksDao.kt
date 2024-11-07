package com.tp.bacprep.data.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tp.bacprep.domain.models.Bookmark


@Dao
interface BookmarksDao {

    @Insert
    fun addBookmark(bookmark: Bookmark)

    @Query("SELECT COUNT(*) FROM bookmarks WHERE id = :bookmarkId")
    fun doesItemExist(bookmarkId: String): Int

    @Query("DELETE FROM bookmarks WHERE id = :bookmarkId")
    fun deleteItemById(bookmarkId: String)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks() : List<Bookmark>
}
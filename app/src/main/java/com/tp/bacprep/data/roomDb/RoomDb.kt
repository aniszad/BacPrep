package com.tp.bacprep.data.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.FileSystemItem
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.domain.models.QuizScore
import com.tp.bacprep.domain.models.RecentOpenedFile
import com.tp.bacprep.domain.models.StudyTime
import com.tp.bacprep.domain.models.User

@Database(entities = [User::class ,Bookmark::class, QuizScore::class, RecentOpenedFile::class, ModuleProgress::class, StudyTime::class], version = 19)
@TypeConverters(
    ListStringConverter::class,
    AttachmentsListConverter::class,
)
abstract class RoomDb : RoomDatabase() {
    abstract val dao : BookmarksDao
    abstract val scoresDao : QuizScoresDao
    abstract val recentFilesDao : RecentFilesDao
    abstract val moduleProgressDao : ModulesProgressDao
    abstract val studyTimeDao : StudyTimeDao
    abstract val currentUserDao : CurrentUserDao
    }

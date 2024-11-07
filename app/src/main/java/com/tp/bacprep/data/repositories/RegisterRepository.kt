package com.tp.bacprep.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.domain.models.User

class RegisterRepository(private val fbAuth : FbAuth) {

    suspend fun registerUser(user : User, psw : String) : Pair<String?, Boolean>{
        return fbAuth.registerUser(user, psw)
    }

    suspend fun sendVerificationEmail() : Pair<String?, Boolean>{
        return fbAuth.sendVerificationEmail()
    }

    suspend fun isCurrentUserVerified() : Pair<String?, Boolean>{
        return fbAuth.isCurrentUserVerified()
    }

    suspend fun setUserProfileImage(id : String, selectedImage:Int){
        fbAuth.setUserProfileImage(id, selectedImage)
    }

    fun getCurrentUser(): FirebaseUser?{
        return fbAuth.getCurrentUser()
    }

    suspend fun registerAdmin(user: User, psw: String): Pair<String?, Boolean>? {
        return fbAuth.registerAdmin(user, psw)
    }
}
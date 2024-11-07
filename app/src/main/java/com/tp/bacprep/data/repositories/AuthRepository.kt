package com.tp.bacprep.data.repositories

import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.domain.models.User
import javax.inject.Inject

class AuthRepository @Inject constructor(private val fbAuth : FbAuth) {

    suspend fun sendPswResetEmail(email : String?) : Pair<String?, Boolean>{
        return fbAuth.sendPswResetEmail(email)
    }

    suspend fun signIn(email : String, psw : String) : Pair<String?, User?> {
        return fbAuth.signIn(email, psw)
    }

    fun getUserId(): String {
        return fbAuth.getUserId()
    }

    suspend fun getUserName(callback : (String) -> Unit){
        fbAuth.getUserName { result ->
            callback(result.second!!)
        }
    }

    fun logout(): Boolean {
        return fbAuth.logout()
    }

    suspend fun isUserSignedIn(): Pair<String, Boolean>? {
        return fbAuth.isUserSignedIn()
    }

    suspend fun getMyBranch(): User? {
        return fbAuth.getUser()
    }

    suspend fun sendVerificationEmail(){
        fbAuth.sendVerificationEmail()
    }
}
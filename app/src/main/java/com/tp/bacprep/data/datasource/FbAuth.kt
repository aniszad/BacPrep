package com.tp.bacprep.data.datasource

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp.bacprep.R
import com.tp.bacprep.domain.models.User
import com.tp.bacprep.util.Constants
import kotlinx.coroutines.tasks.await


class FbAuth(private val context:Context) {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    /* try {
        auth.createUserWithEmailAndPassword(user.email, user.password).await()
    }catch (e2 : FirebaseAuthException){

        callback(false)
    }catch (e : Exception){
        callback(false)
    }

    */

    suspend fun registerUser(user: User, psw : String) : Pair<String?, Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(user.email, psw).await()
            user.id = authResult.user!!.uid
            db.collection(Constants.USERS).document(user.id).set(user, SetOptions.merge()).await()
            Pair(user.role, true)
        } catch (e : FirebaseFirestoreException){
            Log.e("AUTH ERROR -----", e.message.toString())
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)
        }catch (e : KotlinNullPointerException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 53")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)

        } catch (e : FirebaseAuthUserCollisionException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 56")
            Pair(Constants.EMAIL_COLLISION_ERROR, false)
        }catch (e : FirebaseAuthWeakPasswordException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 56")
            Pair(Constants.WEAK_PSW, false)
        }catch (e : FirebaseAuthException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 40")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)
        }catch (e : FirebaseException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 50")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)

        }catch (e : Exception){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 56")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)

        }

        /* .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    try {
                        val userId = task.result.user?.uid!!
                        user.id = userId
                       db.collection(Constants.USERS).document(user.id)
                           .set(user, SetOptions.merge())
                           .addOnCompleteListener {
                               if (task.isSuccessful){
                                   callback(true)
                               }else {
                                   try {
                                   auth.currentUser?.delete()?.addOnCompleteListener {
                                       if (!task.isSuccessful) {
                                           Log.e(
                                               "User auth success - User DB failed -- critical !",
                                               "the user was saved in auth but failed to save in db FbAuth class line 32"
                                           )
                                           callback(false)
                                       }
                                   }
                               }catch (e : NullPointerException){
                                       Log.e(
                                           "null pointer !",
                                           "41"
                                       )
                                   callback(false)
                               }catch (e : Exception){
                                       Log.e(
                                           "null pointer !",
                                           "48"
                                       )
                                   callback(false)
                               }
                                   callback(false)
                               }
                           }
                    } catch (e: NullPointerException) {
                        callback(false)
                    } catch (e: Exception) {
                        callback(false)
                    }
                } else {
                    callback(false)
                }
            }
    }

            */


    }
    suspend fun registerAdmin(user: User, psw : String) : Pair<String?, Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(user.email, psw).await()
            user.id = authResult.user!!.uid
            db.collection(Constants.ADMINS).document(user.id).set(user, SetOptions.merge()).await()
            Pair(user.role, true)
        } catch (e : FirebaseFirestoreException){
            Log.e("AUTH ERROR -----", e.message.toString())
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)
        }catch (e : KotlinNullPointerException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 53")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)

        } catch (e : FirebaseAuthUserCollisionException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 56")
            Pair(Constants.EMAIL_COLLISION_ERROR, false)
        }catch (e : FirebaseAuthWeakPasswordException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 56")
            Pair(Constants.WEAK_PSW, false)
        }catch (e : FirebaseAuthException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 40")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)
        }catch (e : FirebaseException){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 50")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)

        }catch (e : Exception){
            Log.e("AUTH ERROR -----", "failed to save user in auth, FbAuth class line 56")
            if (auth.currentUser != null){
                auth.currentUser!!.delete()
            }
            Pair(e.message.toString(), false)

        }

        /* .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    try {
                        val userId = task.result.user?.uid!!
                        user.id = userId
                       db.collection(Constants.USERS).document(user.id)
                           .set(user, SetOptions.merge())
                           .addOnCompleteListener {
                               if (task.isSuccessful){
                                   callback(true)
                               }else {
                                   try {
                                   auth.currentUser?.delete()?.addOnCompleteListener {
                                       if (!task.isSuccessful) {
                                           Log.e(
                                               "User auth success - User DB failed -- critical !",
                                               "the user was saved in auth but failed to save in db FbAuth class line 32"
                                           )
                                           callback(false)
                                       }
                                   }
                               }catch (e : NullPointerException){
                                       Log.e(
                                           "null pointer !",
                                           "41"
                                       )
                                   callback(false)
                               }catch (e : Exception){
                                       Log.e(
                                           "null pointer !",
                                           "48"
                                       )
                                   callback(false)
                               }
                                   callback(false)
                               }
                           }
                    } catch (e: NullPointerException) {
                        callback(false)
                    } catch (e: Exception) {
                        callback(false)
                    }
                } else {
                    callback(false)
                }
            }
    }

            */


    }

    suspend fun sendVerificationEmail() : Pair<String?, Boolean>{
        val currentUser = auth.currentUser
        return try {
            currentUser?.sendEmailVerification()?.await()
            Pair(null, true)
        }catch (e : NullPointerException){
            Pair(e.message.toString(), false)
        }catch(e : FirebaseTooManyRequestsException){
            Pair(e.message.toString(), false)
        }catch(e : FirebaseAuthException){
            Pair(e.message.toString(), false)
        }catch(e : FirebaseAuthException){
            Pair(e.message.toString(), false)
        }catch (e : Exception){
            Pair(e.message.toString(), false)
        }
    }

    suspend fun isCurrentUserVerified() : Pair<String?, Boolean>{
        return try {
            val currentUser = auth.currentUser
            currentUser!!.reload().await()
            if(currentUser.isEmailVerified){
                Pair(null, true)
            }else{
                Pair(null, false)
            }
        }catch (e : NullPointerException){
            Pair(e.message.toString(), false)
        }catch (e : FirebaseException){
            Pair(e.message.toString(), false)
        }catch (e : Exception){
            Pair(e.message.toString(), false)
        }
    }

    suspend fun setUserProfileImage(id : String, selectedImage:Int){
        try {
            db.collection(Constants.USERS).document(id).update("image", selectedImage).await()
        }catch (e : Exception){
            // TODO -- when there's been an issue setting the selected image
        }
    }

    fun getCurrentUser(): FirebaseUser?{
        return try {
            auth.currentUser
        }catch (e : Exception){
            null
        }
    }

    suspend fun sendPswResetEmail(email : String?) : Pair<String?, Boolean>{
        return try {
            if (email == null){
                val userEmail = auth.currentUser?.email ?:""
                auth.sendPasswordResetEmail(userEmail).await()
            }else{
                auth.sendPasswordResetEmail(email).await()
            }

            Pair(null, true)
        }catch (e : FirebaseAuthException){
            Pair(e.message.toString(), false)
        }catch (e : Exception){
            Pair(e.message.toString(), false)
        }
    }

    suspend fun signIn(email : String, psw : String) : Pair<String?, User?>{
        return try {
            auth.signInWithEmailAndPassword(email,psw).await()
            val user = getUser()
            val fbUser = auth.currentUser
            if (fbUser != null){
                if (fbUser.isEmailVerified){
                    if (user != null){
                        if (user.role == Constants.TEACHER_ROLE || user.role == Constants.CREATOR_ROLE || user.role == Constants.STUDENT_ROLE){
                            user.emailVerified= true
                            return Pair(null, user)
                        }else{
                            return Pair(null, null)
                        }
                    }else{
                        logout()
                        return Pair(context.getString(R.string.erreur_veuillez_r_essayez), null)
                    }
                }else{
                    fbUser.sendEmailVerification().await()
                    logout()
                    return Pair(context.getString(R.string.votre_email_n_est_pas_encore_v_rifi_veuillez_consulter_votre_boite_email), null)
                }
            }else{
                logout()
                return Pair(context.getString(R.string.erreur_veuillez_r_essayez), null)
            }
        }catch (e : FirebaseAuthException){
            Pair(e.message.toString(), null)
        }catch (e : Exception){
             Pair(e.message.toString(), null)
        }
    }

    suspend fun getUser(): User? {
        return try {
            val uid = auth.currentUser?.uid
            if(uid != null) {
                val docSnap = db.collection(Constants.USERS).document(uid).get().await()
                val data = docSnap.data
                if (data != null) {
                    val role = data["role"] as String
                    User(
                        id = data["id"] as String,
                        name = data["name"] as String,
                        lastName = data["lastName"] as String,
                        email = data["email"]as String,
                        image = (data["image"] as Long).toInt(),
                        branch = data["branch"]as String,
                        role = role,
                        blocked = data["blocked"] as Boolean,
                    )
                }else{
                    val docAdminSnap = db.collection(Constants.ADMINS).document(uid).get().await()
                    val dataAdmin = docAdminSnap.data
                    if (dataAdmin != null) {
                        val role = dataAdmin["role"] as String
                        User(
                            id = dataAdmin["id"] as String,
                            name = dataAdmin["name"] as String,
                            lastName = dataAdmin["lastName"] as String,
                            email = dataAdmin["email"]as String,
                            image = (dataAdmin["image"] as Long).toInt(),
                            branch = dataAdmin["branch"]as String,
                            role = role,
                            blocked = dataAdmin["blocked"] as Boolean,
                        )
                    }else{
                        null
                    }
                }
            }else{
                null
            }
        }catch (e : Exception){
            null
        }
    }



    fun getUserId(): String {
        return auth.currentUser!!.uid
    }

    suspend fun getAdminName(callback : (Pair<Boolean, String?>) -> Unit) {
         try {
            val doc = db.collection(Constants.ADMINS).document(auth.currentUser!!.uid).get().await()
            val name = doc.get("name") as String
            val lastName = doc.get("lastName") as String
            callback(Pair(true, "$lastName $name"))
        }catch (e : FirebaseException){
            callback(Pair(false, e.message.toString()))
        }catch (e : Exception){
            callback(Pair(false, e.message.toString()))
         }
    }
    suspend fun getUserName(callback : (Pair<Boolean, String?>) -> Unit) {
        try {
            val doc = db.collection(Constants.USERS).document(auth.currentUser!!.uid).get().await()
                ?: db.collection(Constants.ADMINS).document(auth.currentUser!!.uid).get().await()
            val name = doc.get("name") as String
            val lastName = doc.get("lastName") as String
            callback(Pair(true, "$lastName $name"))
        }catch (e : FirebaseException){
            callback(Pair(false, e.message.toString()))
        }catch (e : Exception){
            callback(Pair(false, e.message.toString()))
        }
    }

    fun logout() : Boolean {
        return try {
            auth.signOut()
            true
        }catch (e : Exception){
            false
        }
    }

    suspend fun isUserSignedIn(): Pair<String, Boolean>? {
        return try {
            val currentUser = auth.currentUser
            if (currentUser!=null) {
                val docSnap = db.collection(Constants.USERS).document(currentUser.uid).get().await()
                val data = docSnap.data
                if (data != null) {
                    val role = data["role"] as String
                    Pair(role, currentUser.isEmailVerified)
                } else {
                    val docSnapAdmin = db.collection(Constants.ADMINS).document(currentUser.uid).get().await()
                    val dataAdmin = docSnapAdmin.data
                    if (dataAdmin != null) {
                        val roleAdmin = dataAdmin["role"] as String
                        val isEmailVerifiedAdmin = dataAdmin["emailVerified"] as Boolean
                        Pair(roleAdmin, isEmailVerifiedAdmin&&currentUser.isEmailVerified)
                    } else {
                        null
                    }
                }
            }else{
                    null
                }
            } catch (e : Exception){
            null
        }
    }

}
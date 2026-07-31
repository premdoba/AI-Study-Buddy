package com.nextgendevs.quicknotes.data.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsageRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
)  {

    private fun uid(): String {
        val userId = auth.currentUser?.uid
        Log.d("UsageDebug", "UID = $userId")
        return userId ?: ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun today(): String {
        return java.time.LocalDate.now().toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun isUsageAllowed(): Boolean {

        val userId = uid()
        if (userId.isEmpty()) return false

        return try {

            val docRef = firestore.collection("users").document(userId)
            val snapshot = docRef.get().await()

            val today = java.time.LocalDate.now().toString()

            val lastReset = snapshot.getString("lastReset") ?: ""
            val maxUsage = snapshot.getLong("maxUsage") ?: 3

            var usageCount = snapshot.getLong("usageCount") ?: 0

            // 🔥 STEP 1: handle reset FIRST
            if (lastReset != today) {

                usageCount = 0

                docRef.update(
                    mapOf(
                        "usageCount" to 0,
                        "lastReset" to today
                    )
                )
            }

            // 🔥 STEP 2: IMPORTANT → decide using corrected value
            return usageCount < maxUsage

        } catch (e: Exception) {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun incrementUsage() {
        val userId = uid()
        if (userId.isEmpty()) return

        val docRef = firestore.collection("users").document(userId)

        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(docRef)

            val today = java.time.LocalDate.now().toString()
            val lastReset = snapshot.getString("lastReset") ?: ""

            var usage = snapshot.getLong("usageCount") ?: 0

            if (lastReset != today) {
                usage = 0
                transaction.update(docRef, "lastReset", today)
            }

            transaction.update(docRef, "usageCount", usage + 1)
        }.await()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getRemainingUsage(): Long {
        val userId = uid()
        if (userId.isEmpty()) return 0

        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()


            val today = java.time.LocalDate.now().toString()
            val lastReset = snapshot.getString("lastReset") ?: ""

            val usage = if (lastReset != today) 0 else snapshot.getLong("usageCount") ?: 0
            val max = snapshot.getLong("maxUsage") ?: 3

            (max - usage).coerceAtLeast(0)
        } catch (e: Exception) {
            0
        }
    }
}
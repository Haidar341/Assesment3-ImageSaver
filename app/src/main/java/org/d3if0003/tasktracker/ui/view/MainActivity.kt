package org.d3if0003.tasktracker.ui.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.d3if0003.tasktracker.R
import org.d3if0003.tasktracker.model.DataClass
import org.d3if0003.tasktracker.ui.screen.MyApp
import org.d3if0003.tasktracker.util.NetworkUtils

class MainActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventListener: ValueEventListener
    private val dataList = mutableStateListOf<DataClass>()
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val isLoading = mutableStateOf(true)
    private val isNetworkAvailable = mutableStateOf(true)

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Fetch user name and email
        val userName = currentUser.displayName ?: "User Name"
        val userEmail = currentUser.email ?: "user@example.com"

        // Initialize GoogleSignInClient
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            var isNetworkAvailable by remember { mutableStateOf(NetworkUtils.isNetworkAvailable(this)) }

            LaunchedEffect(Unit) {
                registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }

            DisposableEffect(Unit) {
                onDispose {
                    unregisterReceiver(connectivityReceiver)
                }
            }

            MyApp(
                tasks = dataList,
                onSignOut = { signOut() },
                onFabClick = {
                    if (isNetworkAvailable) {
                        if (auth.currentUser != null) {
                            val intent = Intent(this@MainActivity, UploadActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Please log in to upload tasks", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "No internet connection. Cannot upload tasks.", Toast.LENGTH_SHORT).show()
                    }
                },
                onProfileImageClick = { /* Handle profile click */ },
                onSearchQueryChanged = { query -> searchList(query) },
                onSignInClick = {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                },
                onDeleteTask = { data -> confirmDeleteData(data) },
                onTaskClick = { data -> openDetailActivity(data) },
                userName = userName,
                userEmail = userEmail,
                isLoading = isLoading.value,
                isNetworkAvailable = isNetworkAvailable // Pass the parameter
            )
        }

        fetchData()
    }

    private val connectivityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val isConnected = NetworkUtils.isNetworkAvailable(context ?: return)
            isNetworkAvailable.value = isConnected
            if (!isConnected) {
                Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchData() {
        databaseReference =
            FirebaseDatabase.getInstance("https://tasktracker-ecb59-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Todo List")
        eventListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                isLoading.value = false
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading.value = false
                showErrorDialog(error.message)
            }
        })
    }

    private fun confirmDeleteData(data: DataClass) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah Anda yakin ingin menghapus ini?")
            .setPositiveButton("Ya") { _, _ -> deleteData(data) }
            .setNegativeButton("Tidak", null)
        builder.show()
    }

    private fun deleteData(data: DataClass) {
        val key = data.id
        if (key != null) {
            databaseReference.child(key).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    showErrorDialog(task.exception?.message ?: "Unknown error occurred")
                }
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(this) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.show()
    }

    private fun searchList(text: String) {
        val searchList = ArrayList<DataClass>()
        for (dataClass in dataList) {
            if (dataClass.dataTitle?.lowercase()?.contains(text.lowercase()) == true) {
                searchList.add(dataClass)
            }
        }
        dataList.clear()
        dataList.addAll(searchList)
    }

    private fun openDetailActivity(data: DataClass) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("Title", data.dataTitle)
            putExtra("Description", data.dataDesc)
            putExtra("Purpose", data.dataPurpose) // Change from "Priority" to "Purpose"
            putExtra("Image", data.dataImage)
        }
        startActivity(intent)
    }
}

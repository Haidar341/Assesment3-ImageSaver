package org.d3if0003.tasktracker.ui.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.d3if0003.tasktracker.model.DataClass
import org.d3if0003.tasktracker.ui.screen.Upload
import java.io.IOException
import java.util.*

class UploadActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var filePath: Uri
    private lateinit var progressDialog: ProgressDialog
    private var bitmap: Bitmap? = null
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val PICK_IMAGE_REQUEST = 71
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            Toast.makeText(this, "Please log in to upload tasks", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        progressDialog = ProgressDialog(this)
        databaseReference = FirebaseDatabase.getInstance("https://tasktracker-ecb59-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Todo List")
        storageReference = FirebaseStorage.getInstance().reference

        setContent {
            var title by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            var priority by remember { mutableStateOf("") }
            var isUploading by remember { mutableStateOf(false) }

            Upload(
                lavender = Color(0xFFB388FF),
                title = title,
                description = description,
                priority = priority,
                isUploading = isUploading,
                onTitleChange = { title = it },
                onDescriptionChange = { description = it },
                onPriorityChange = { priority = it },
                onUploadClick = {
                    isUploading = true
                    uploadImageToFirebase(title, description, priority) { isUploading = false }
                },
                onCancelClick = { finish() },
                onSelectImageClick = { selectImageFromGallery() },
                imageBitmap = bitmap
            )
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data!!
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                // Recompose the UI to display the selected image
                setContent {
                    var title by remember { mutableStateOf("") }
                    var description by remember { mutableStateOf("") }
                    var priority by remember { mutableStateOf("") }
                    var isUploading by remember { mutableStateOf(false) }

                    Upload(
                        lavender = Color(0xFFB388FF),
                        title = title,
                        description = description,
                        priority = priority,
                        isUploading = isUploading,
                        onTitleChange = { title = it },
                        onDescriptionChange = { description = it },
                        onPriorityChange = { priority = it },
                        onUploadClick = {
                            isUploading = true
                            uploadImageToFirebase(title, description, priority) { isUploading = false }
                        },
                        onCancelClick = { finish() },
                        onSelectImageClick = { selectImageFromGallery() },
                        imageBitmap = bitmap
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImageToFirebase(title: String, description: String, priority: String, onUploadComplete: () -> Unit) {
        if (title.isEmpty() || description.isEmpty() || priority.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            onUploadComplete()
            return
        }

        if (::filePath.isInitialized) {
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val dataClass = DataClass(
                            databaseReference.push().key,
                            title,
                            description,
                            priority,
                            uri.toString()
                        )
                        databaseReference.child(dataClass.id!!).setValue(dataClass)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Task Uploaded", Toast.LENGTH_SHORT).show()

                                    // Start DetailActivity and pass data
                                    val intent = Intent(this, DetailActivity::class.java)
                                    intent.putExtra("Title", title)
                                    intent.putExtra("Description", description)
                                    intent.putExtra("Purpose", priority)
                                    intent.putExtra("Image", uri.toString())
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed to upload task", Toast.LENGTH_SHORT).show()
                                }
                                onUploadComplete()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                    onUploadComplete()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressDialog.setMessage("Uploaded $progress%")
                }
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            onUploadComplete()
        }
    }
}

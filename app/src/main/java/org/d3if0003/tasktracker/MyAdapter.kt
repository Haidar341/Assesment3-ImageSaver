package org.d3if0003.tasktracker

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.DataSource
import org.d3if0003.tasktracker.model.DataClass
import org.d3if0003.tasktracker.ui.view.DetailActivity

class MyAdapter(private val context: Context, private var dataList: List<DataClass>) : RecyclerView.Adapter<MyViewHolder>() {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://tasktracker-ecb59-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Todo List")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.progressBar.visibility = View.VISIBLE
        Glide.with(context)
            .load(dataList[position].dataImage)
            .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(holder.recImage)
        holder.recTitle.text = dataList[position].dataTitle
        holder.recDesc.text = dataList[position].dataDesc
        holder.recPriority.text = dataList[position].dataPurpose
        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Image", dataList[holder.adapterPosition].dataImage)
            intent.putExtra("Description", dataList[holder.adapterPosition].dataDesc)
            intent.putExtra("Title", dataList[holder.adapterPosition].dataTitle)
            intent.putExtra("Purpose", dataList[holder.adapterPosition].dataPurpose)
            context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener {
            showDeleteConfirmationDialog(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun searchDataList(searchList: List<DataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete this task?")
        builder.setPositiveButton("Yes") { _, _ ->
            deleteTask(dataList[position].id)
        }
        builder.setNegativeButton("No", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteTask(id: String?) {
        id?.let {
            databaseReference.child(it).removeValue().addOnSuccessListener {
                Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recImage: ImageView = itemView.findViewById(R.id.recImage)
    var recTitle: TextView = itemView.findViewById(R.id.recTitle)
    var recDesc: TextView = itemView.findViewById(R.id.recDesc)
    var recPriority: TextView = itemView.findViewById(R.id.recPriority)
    var recCard: CardView = itemView.findViewById(R.id.recCard)
    var btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
}

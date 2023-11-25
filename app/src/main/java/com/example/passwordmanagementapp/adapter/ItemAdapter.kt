package com.example.passwordmanagementapp.adapter

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanagementapp.R

class ItemAdapter(
    private val items: MutableList<String>,
    private val context: Context,
    private val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemFrame: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.item_frame)
        val itemName: EditText = itemView.findViewById<EditText>(R.id.item_name)
        val optionIcon: ImageView = itemView.findViewById<ImageView>(R.id.icon)
        val optionArea: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.option_area)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = Editable.Factory.getInstance().newEditable("文字列")
        holder.itemFrame.setOnClickListener {
            onItemClick(items[position])
        }
        holder.optionIcon.setOnClickListener {
            if (holder.optionArea.visibility == View.GONE) {
                holder.optionArea.visibility = View.VISIBLE
            } else if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}

package com.example.passwordmanagementapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanagementapp.R
import com.example.passwordmanagementapp.adapter.ViewModel.MainViewModel

class ItemAdapter(
    private val items: MutableList<String>,
    private val context: Context,
    private val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemFrame: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.item_frame)
        val itemName: EditText = itemView.findViewById<EditText>(R.id.item_name)
        val itemPassword: EditText = itemView.findViewById<EditText>(R.id.item_password)
        val optionIcon: ImageView = itemView.findViewById<ImageView>(R.id.icon)
        val optionArea: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.option_area)

        val changeNameText: TextView = itemView.findViewById<TextView>(R.id.change_name_text)
        val changePasswordText: TextView =
            itemView.findViewById<TextView>(R.id.change_password_text)
        val itemDeleteText: TextView = itemView.findViewById<TextView>(R.id.item_delete_text)
        val changeColorText: TextView = itemView.findViewById<TextView>(R.id.change_color_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemFrame.setOnClickListener {
            holder.itemName.requestFocus()
            MainViewModel().showKeyboard(holder.itemName, context)
        }
        holder.optionIcon.setOnClickListener {
            if (holder.optionArea.visibility == View.GONE) {
                holder.optionArea.visibility = View.VISIBLE
            } else if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
        }


        //名前を変更
        holder.changeNameText.setOnClickListener {
            holder.optionArea.visibility = View.GONE
            holder.itemName.requestFocus()
            MainViewModel().showKeyboard(holder.itemName, context)
        }

        //パスワード入力
        holder.changePasswordText.setOnClickListener {
            holder.optionArea.visibility = View.GONE
            holder.itemPassword.requestFocus()
            MainViewModel().showKeyboard(holder.itemPassword, context)

        }

        //アイテム削除
        holder.itemDeleteText.setOnClickListener {
        }

        //色の変更
        holder.changeColorText.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

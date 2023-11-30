package com.example.passwordmanagementapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanagementapp.R
import com.example.passwordmanagementapp.adapter.ViewModel.MainViewModel
import com.example.passwordmanagementapp.model.ItemDataModel

class ItemAdapter(
    private val items: MutableList<String>,
    private val context: Context,
    private val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemFrame: ConstraintLayout = itemView.findViewById(R.id.item_frame)
        val itemName: EditText = itemView.findViewById(R.id.item_name)
        val itemId: EditText = itemView.findViewById(R.id.item_id)
        val itemPassword: EditText = itemView.findViewById(R.id.item_password)
        val optionIcon: ImageView = itemView.findViewById(R.id.icon)
        val optionArea: ConstraintLayout = itemView.findViewById(R.id.option_area)

        val changeNameText: TextView = itemView.findViewById(R.id.change_name_text)
        val changePasswordText: TextView = itemView.findViewById(R.id.change_password_text)
        val itemDeleteText: TextView = itemView.findViewById(R.id.item_delete_text)
        val changeColorText: TextView = itemView.findViewById(R.id.change_color_text)

        val confirmButton: Button = itemView.findViewById(R.id.confirm_button)
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
            MainViewModel().getSharedPreferences(context)

        }

        holder.confirmButton.setOnClickListener {
            val itemName = holder.itemName.text.toString()
            val itemId = holder.itemId.text.toString()
            val itemPassword = holder.itemPassword.text.toString()

            if (itemName.isNotEmpty() && itemId.isNotEmpty() && itemPassword.isNotEmpty()) {
                val item = ItemDataModel(position, itemName, itemId, itemPassword)
                MainViewModel().saveSharedPreferences(context, item)
                Toast.makeText(context, "${itemName}の保存が完了しました", Toast.LENGTH_SHORT).show()
                Log.d("test_log", "${item}")
            } else {
                Toast.makeText(context, "すべての領域に値を入力してください", Toast.LENGTH_SHORT).show()
            }
            MainViewModel().hideKeyboard(it, context)
        }


    }


    override fun getItemCount(): Int {
        return items.size
    }
}

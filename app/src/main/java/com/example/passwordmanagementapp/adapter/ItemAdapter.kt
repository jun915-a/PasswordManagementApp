package com.example.passwordmanagementapp.adapter

import android.app.Application
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanagementapp.R
import com.example.passwordmanagementapp.adapter.ViewModel.MainViewModel
import com.example.passwordmanagementapp.model.ItemDataModel
import com.example.passwordmanagementapp.ui.MainFragment

class ItemAdapter(
    private val count: Int,
    private val items: MutableList<ItemDataModel>,
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

    private val viewModel: MainViewModel =
        ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
            .create(MainViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.optionArea.visibility == View.VISIBLE) {
            holder.optionArea.visibility = View.GONE
        }
        if (position < count - 1) {
            setItem(items.get(position), holder)
        }
        holder.itemFrame.setOnClickListener {
            if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
            holder.itemName.requestFocus()
            viewModel.showKeyboard(holder.itemName, context)
        }
        holder.itemName.setOnClickListener{
            if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
        }
        holder.itemId.setOnClickListener{
            if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
        }
        holder.itemPassword.setOnClickListener {
            if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
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
            viewModel.showKeyboard(holder.itemName, context)
        }

        //パスワード入力
        holder.changePasswordText.setOnClickListener {
            holder.optionArea.visibility = View.GONE
            holder.itemPassword.requestFocus()
            viewModel.showKeyboard(holder.itemPassword, context)

        }

        //アイテム削除
        holder.itemDeleteText.setOnClickListener {
            //            viewModel.postImageView(1)
        }

        //色の変更
        holder.changeColorText.setOnClickListener {
            viewModel.getSharedPreferences(context)

        }

        holder.confirmButton.setOnClickListener {
            val itemName = holder.itemName.text.toString()
            val itemId = holder.itemId.text.toString()
            val itemPassword = holder.itemPassword.text.toString()

            if (itemName.isNotEmpty() && itemId.isNotEmpty() && itemPassword.isNotEmpty()) {
                val item = ItemDataModel(itemName, itemId, itemPassword)
                setItem(item, holder)
                viewModel.saveSharedPreferences(context, item, position)
                Toast.makeText(context, "${itemName}の保存が完了しました", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "すべての領域に値を入力してください", Toast.LENGTH_SHORT).show()
            }
            viewModel.hideKeyboard(it, context)
        }


    }

    fun setItem(item: ItemDataModel, holder: ViewHolder) {
        holder.itemName.setText(item.itemName)
        holder.itemId.setText(item.itemId)
        holder.itemPassword.setText(item.itemPassword)
    }

    override fun getItemCount(): Int {
        return count
    }
}

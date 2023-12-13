package com.example.passwordmanagementapp.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanagementapp.R
import com.example.passwordmanagementapp.adapter.ViewModel.MainViewModel
import com.example.passwordmanagementapp.model.ItemDataModel
import com.example.passwordmanagementapp.ui.MainFragment

class ItemAdapter(
    private val count: Int,
    private val items: ArrayList<ItemDataModel>,
    private val context: Context,
    private val onItemClick: (position: Int) -> Unit
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
        val changeIdText: TextView = itemView.findViewById(R.id.change_id_text)
        val changePasswordText: TextView = itemView.findViewById(R.id.change_password_text)
        val itemDeleteText: TextView = itemView.findViewById(R.id.item_delete_text)

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
        }
        holder.itemName.setOnClickListener {
            if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
        }
        holder.itemId.setOnClickListener {
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
            viewModel.hideKeyboard(it, context)
            if (holder.optionArea.visibility == View.GONE) {
                holder.optionArea.visibility = View.VISIBLE
            } else if (holder.optionArea.visibility == View.VISIBLE) {
                holder.optionArea.visibility = View.GONE
            }
        }


        //アイテム名変更
        holder.changeNameText.setOnClickListener {
            holder.optionArea.visibility = View.GONE
            holder.itemName.isEnabled = true
            holder.itemName.requestFocus()
            viewModel.showKeyboard(holder.itemName, context)
        }

        //ID変更
        holder.changeIdText.setOnClickListener {
            holder.optionArea.visibility = View.GONE
            holder.itemId.isEnabled = true
            holder.itemId.requestFocus()
            viewModel.showKeyboard(holder.itemId, context)
        }

        //パスワード変更
        holder.changePasswordText.setOnClickListener {
            holder.optionArea.visibility = View.GONE
            holder.itemPassword.isEnabled = true
            holder.itemPassword.requestFocus()
            viewModel.showKeyboard(holder.itemPassword, context)
        }

        //アイテム削除
        holder.itemDeleteText.setOnClickListener {
//            viewModel.getSharedPreferences(context)
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
                MainFragment.availableItemFlag = true
                onItemClick.invoke(position)
            } else {
                Toast.makeText(context, "すべての領域に値を入力してください", Toast.LENGTH_SHORT).show()
            }
            viewModel.hideKeyboard(it, context)
        }
        //アイテム名テキスト入力完了時
        holder.itemName.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 完了(DONE)だったらやりたい処理
                holder.itemName.isEnabled = false
                viewModel.hideKeyboard(view, context)
            }
            return@setOnEditorActionListener true
        }

        //IDテキスト入力完了時
        holder.itemId.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 完了(DONE)だったらやりたい処理
                holder.itemId.isEnabled = false
                viewModel.hideKeyboard(view, context)
            }
            return@setOnEditorActionListener true
        }

        //パスワードテキスト入力完了時
        holder.itemPassword.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 完了(DONE)だったらやりたい処理
                holder.itemPassword.isEnabled = false
                viewModel.hideKeyboard(view, context)
            }
            return@setOnEditorActionListener true
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

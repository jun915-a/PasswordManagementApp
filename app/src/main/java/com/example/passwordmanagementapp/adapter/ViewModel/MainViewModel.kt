package com.example.passwordmanagementapp.adapter.ViewModel

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.passwordmanagementapp.model.ItemDataModel
import com.example.passwordmanagementapp.ui.MainFragment
import com.google.gson.Gson
import java.text.FieldPosition


class MainViewModel : ViewModel() {
    var itemList: MutableList<ItemDataModel> = mutableListOf()//TODO:Livedata化

    //    private val mItemList = MutableLiveData<MutableList<String>>()
//    val itemList: LiveData<MutableList<String>> = mItemList
//
//    fun postItemList(list:MutableList<String>) {
//        mItemList.postValue(list)
//    }

    // MutableLiveDataを使用して変更可能なLiveDataを作成
    private val mImageView = MutableLiveData<Int>()

    // 外部に公開される不変のLiveData
    val imageView: LiveData<Int> = mImageView

    fun postImageView(imageView: Int) {
        mImageView.postValue(imageView)
    }
    fun showKeyboard(editText: EditText, context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(view: View, context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun saveSharedPreferences(context: Context, item: ItemDataModel, position: Int) {
        itemList.add(item)
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(item)
        editor.putString("${position}", json)
        editor.apply()

    }

    fun getSharedPreferences(context: Context): MutableList<ItemDataModel> {
        itemList = mutableListOf()
        for (i in 0..MainFragment.MAX_ITEM_VALUE) {
            val json =
                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    .getString("${i}", "")
            if (!json.isNullOrEmpty()) {
                val itemDataModel = Gson().fromJson(json, ItemDataModel::class.java)
                itemList.add(itemDataModel)
            } else {
                return itemList
            }
        }
        return itemList
    }
}

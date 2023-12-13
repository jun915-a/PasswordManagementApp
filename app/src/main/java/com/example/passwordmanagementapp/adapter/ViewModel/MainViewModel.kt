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


class MainViewModel : ViewModel() {
    var itemList: MutableList<ItemDataModel> = mutableListOf()//TODO:Livedata化

    // MutableLiveDataを使用して変更可能なLiveDataを作成
    private val mAdapterList = MutableLiveData<MutableList<ItemDataModel>>()

    // 外部に公開される不変のLiveData
    val adapterList: LiveData<MutableList<ItemDataModel>> = mAdapterList

    fun postImageView(list: MutableList<ItemDataModel>) {
        mAdapterList.postValue(list)
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
        Log.d("test_log", "${itemList.size}")
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(item)
        editor.putString("${position}", json)
        editor.apply()

    }

    fun getSharedPreferences(context: Context): MutableList<ItemDataModel> {
        for (i in 0..MainFragment.MAX_ITEM_VALUE) {
            val json =
                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    .getString("${i}", "")
            if (!json.isNullOrEmpty()) {
                val itemDataModel = Gson().fromJson(json, ItemDataModel::class.java)
            } else {
                return itemList
            }
        }
        return itemList
    }

    fun isNotDataCheckSharedPreferences(context: Context): Boolean {
        val json =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .getString("1", "")
        return json.isNullOrEmpty()

    }
}

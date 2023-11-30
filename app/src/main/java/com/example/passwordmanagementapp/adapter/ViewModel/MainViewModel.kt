package com.example.passwordmanagementapp.adapter.ViewModel

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.passwordmanagementapp.model.ItemDataModel
import com.google.gson.Gson


class MainViewModel : ViewModel() {
    //testhensuu
    val list = mutableListOf<String>()
//    private val mItemList = MutableLiveData<MutableList<String>>()
//    val itemList: LiveData<MutableList<String>> = mItemList
//
//    fun postItemList(list:MutableList<String>) {
//        mItemList.postValue(list)
//    }

    val itemList: List<ItemDataModel>? = null
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

    fun saveSharedPreferences(context: Context, item: ItemDataModel) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(item)
        editor.putString("${item.itemNo}", json)
        editor.apply()
    }

    fun getSharedPreferences(context: Context, position: Int) {
        val json =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("$position", "")
        val test = Gson().fromJson(json, ItemDataModel::class.java)
        Log.d("test_log${position}", "${test}")
    }
}

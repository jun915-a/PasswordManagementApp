package com.example.passwordmanagementapp.adapter.ViewModel

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {
    val list = mutableListOf<String>()
//    private val mItemList = MutableLiveData<MutableList<String>>()
//    val itemList: LiveData<MutableList<String>> = mItemList
//
//    fun postItemList(list:MutableList<String>) {
//        mItemList.postValue(list)
//    }
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
}

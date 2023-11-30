package com.example.passwordmanagementapp.model

import java.io.Serializable

data class ItemDataModel(
    val itemNo: Int,
    val itemName: String,
    val itemId: String,
    val itemPassword: String
) : Serializable {
}

package com.aliosmanarslan.emarket.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    var image: String? = null,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    var price: String? = null,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = null,
    @ColumnInfo(name = "model")
    @SerializedName("model")
    var model: String? = null,
    @ColumnInfo(name = "brand")
    @SerializedName("brand")
    var brand: String? = null,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: String? = null
){

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}

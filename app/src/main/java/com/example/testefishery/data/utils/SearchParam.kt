package com.example.testefishery.data.utils

import com.example.testefishery.data.models.Area
import com.example.testefishery.data.models.Size
import org.json.JSONObject

class SearchParam(var size: Size? = null, var area: Area? = null) {

    fun toJson(): JSONObject {
        val map = HashMap<String, String>()
        if (size != null)
            map.put("size", size!!.size)
        if (area != null) {
            map.put("area_kota", area!!.city)
            map.put("area_provinsi", area!!.province)
        }
        return JSONObject(map as Map<String, String>)
    }

    fun toStringJson(): String = toJson().toString()
}
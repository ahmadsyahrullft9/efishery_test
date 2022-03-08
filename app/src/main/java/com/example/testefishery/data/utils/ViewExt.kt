package com.example.testefishery.data.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner

inline fun EditText.OnAfterTextChange(crossinline listener: (Editable?) -> Unit) {
    val editText = this
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            listener(s)
        }
    })
}

inline fun Spinner.onSelectedListener(
    crossinline listener: (adapterView: AdapterView<*>?, view: View?, position: Int, itemId: Long) -> Unit
) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            listener(p0, p1, p2, p3)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}
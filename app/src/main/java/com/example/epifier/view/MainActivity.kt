package com.example.epifier.view

import android.os.Bundle
import android.text.InputFilter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.epifier.R
import com.example.epifier.extention.afterTextChanged
import com.example.epifier.extention.hideKeyboard
import com.example.epifier.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
    }

    private fun initListeners() {
        panEditText.apply {
            afterTextChanged {
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    dayEditText.requestFocus()
                }
            }
        }
        dayEditText.apply {
            afterTextChanged {
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    monthEditText.requestFocus()
                }
            }
        }
        monthEditText.apply {
            afterTextChanged {
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    yearEditText.requestFocus()
                }
            }
        }
        yearEditText.apply {
            afterTextChanged {
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    currentFocus?.hideKeyboard()
                    button.requestFocus()
                }
            }
        }
    }
}

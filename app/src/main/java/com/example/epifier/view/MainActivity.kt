package com.example.epifier.view

import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.epifier.R
import com.example.epifier.extention.afterTextChanged
import com.example.epifier.extention.hideKeyboard
import com.example.epifier.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
        initObserver()
    }

    private fun initListeners() {
        panEditText.apply {
            afterTextChanged {
                mainViewModel.emitPan(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    dayEditText.requestFocus()
                }
            }
        }
        dayEditText.apply {
            afterTextChanged {
                mainViewModel.emitDay(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    monthEditText.requestFocus()
                }
            }
        }
        monthEditText.apply {
            afterTextChanged {
                mainViewModel.emitMonth(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    yearEditText.requestFocus()
                }
            }
        }
        yearEditText.apply {
            afterTextChanged {
                mainViewModel.emitYear(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    currentFocus?.hideKeyboard()
                }
            }
        }
        button.setOnClickListener {
            Toast.makeText(baseContext, "Details submitted successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
        noPan.setOnClickListener {
            finish()
        }
    }

    @InternalCoroutinesApi
    private fun initObserver() {
        lifecycleScope.launch {
            mainViewModel.isButtonEnabled.collect { value ->
                button.isEnabled = value
            }
        }
    }
}

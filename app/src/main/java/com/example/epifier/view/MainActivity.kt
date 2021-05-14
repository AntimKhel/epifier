package com.example.epifier.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.epifier.R
import com.example.epifier.databinding.ActivityMainBinding
import com.example.epifier.extention.*
import com.example.epifier.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initListeners()
        initObserver()
        initSpannable()
    }

    private fun initListeners() {
        binding.panEditText.apply {
            afterTextChanged {
                mainViewModel.emitPan(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    binding.dayEditText.requestFocus()
                }
            }
        }
        binding.dayEditText.apply {
            afterTextChanged {
                mainViewModel.emitDay(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    binding.monthEditText.requestFocus()
                }
            }
        }
        binding.monthEditText.apply {
            afterTextChanged {
                mainViewModel.emitMonth(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    binding.yearEditText.requestFocus()
                }
            }
        }
        binding.yearEditText.apply {
            afterTextChanged {
                mainViewModel.emitYear(it)
                if (it.length == (filters.first() as? InputFilter.LengthFilter)?.max) {
                    currentFocus?.hideKeyboard()
                }
            }
        }
        binding.button.setOnClickListener {
            mainViewModel.mockApiCall().observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.frameLayout.visibility = View.GONE
                            Toast.makeText(
                                baseContext,
                                "Details submitted successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                baseContext,
                                "Error occurred. Please try again",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        Status.LOADING -> {
                            binding.frameLayout.visibility = View.VISIBLE
                        }
                    }
                }
            })

        }
        binding.noPan.setOnClickListener {
            finish()
        }
    }

    @InternalCoroutinesApi
    private fun initObserver() {
        lifecycleScope.launch {
            mainViewModel.isButtonEnabled.collect { value ->
                binding.button.isEnabled = value
            }
        }
    }

    private fun initSpannable() {
        baseContext.getString(R.string.register_disclaimer).toSpannable().addUrlHandler( {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(it)
                baseContext.startActivity(this)
            }
        }, resources.getColor(R.color.button_purple, null)).let {
            binding.disclaimer.text = it
        }
    }
}

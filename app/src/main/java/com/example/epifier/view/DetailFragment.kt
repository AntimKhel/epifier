package com.example.epifier.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.epifier.R
import com.example.epifier.databinding.FragmentDetailBinding
import com.example.epifier.extention.*
import com.example.epifier.repository.model.Detail
import com.example.epifier.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var detail: Detail

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        initListeners()
        initObserver()
        initSpannable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailBinding.inflate(inflater, container, false).root
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
                    activity?.currentFocus?.hideKeyboard()
                }
            }
        }
        binding.button.setOnClickListener {
            context?.let { baseContext ->
                mainViewModel.mockApiCall(detail).observe(viewLifecycleOwner, {
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
                                binding.frameLayout.visibility = View.GONE
                                Toast.makeText(
                                    baseContext,
                                    "PAN Data already exists",
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

        }
        binding.noPan.setOnClickListener {
            activity?.finish()
        }
    }

    @InternalCoroutinesApi
    private fun initObserver() {
        lifecycleScope.launch {
            mainViewModel.isButtonEnabled.collect { value ->
                binding.button.isEnabled = value
                createDetail(value)
            }
        }
    }

    private fun initSpannable() {
        context?.getString(R.string.register_disclaimer)?.toSpannable()?.addUrlHandler( {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(it)
                context?.startActivity(this)
            }
        }, resources.getColor(R.color.button_purple, null)).let {
            binding.disclaimer.text = it
        }
    }

    private fun createDetail(isValid: Boolean) {
        with(binding) {
            if (isValid)
                detail = Detail(
                    pan = panEditText.text.toString(),
                    yob = yearEditText.text.toString().toInt()
                )
        }
    }
}
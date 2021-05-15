package com.example.epifier.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.epifier.databinding.FragmentThanksDialogBinding
import com.example.epifier.worker.DetailWorker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

class ThanksDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentThanksDialogBinding
    private lateinit var workRequestId: UUID

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThanksDialogBinding.bind(view)
        initListener()
    }

    private fun initObserver() {
        context?.let {
            WorkManager.getInstance(it).getWorkInfoByIdLiveData(workRequestId).observe(
                viewLifecycleOwner,
                { workInfo ->
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> { Toast.makeText(context, "We will contact you.", Toast.LENGTH_LONG).show()}
                        else -> {}
                    }
                })
        }
    }

    private fun initListener() {
        binding.onboardButton.setOnClickListener {
            initWork()
        }
    }

    private fun initWork() {
        context?.let { WorkManager.getInstance(it).enqueue(DetailWorker.buildRequest("base_64_encoded_pan").apply { workRequestId = this.id }) }
        initObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentThanksDialogBinding.inflate(inflater, container, false).root
    }
}
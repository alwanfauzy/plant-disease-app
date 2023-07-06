package com.alwan.plantdisease.ui.main.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.alwan.plantdisease.R
import com.alwan.plantdisease.databinding.DialogSettingsBinding
import com.alwan.plantdisease.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsDialogFragment : DialogFragment() {
    private var _binding: DialogSettingsBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DialogSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWindow()
    }

    private fun initWindow() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.attributes?.windowAnimations = R.style.DialogAnimation
        }

        initBaseUrl()
        initPlantCategory()

        binding.btnSave.setOnClickListener {
            val baseUrl = binding.etBaseUrl.text.toString()
            val plantCategory = binding.spCategory.selectedItem.toString()

            CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.saveBaseUrl(baseUrl)
                mainViewModel.savePlantCategory(plantCategory)
            }

            dialog?.dismiss()
        }
    }

    private fun initBaseUrl() {
        mainViewModel.getBaseUrl().observe(viewLifecycleOwner) {
            binding.etBaseUrl.setText(it)
        }
    }

    private fun initPlantCategory() {
        val items = listOf("Potato", "Corn")

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                items
            )
        binding.spCategory.adapter = adapter

        mainViewModel.getPlantCategory().observe(viewLifecycleOwner) {
            if (it.equals("Potato"))
                binding.spCategory.setSelection(0)
            else
                binding.spCategory.setSelection(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
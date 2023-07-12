package com.example.popupsdksample.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.popupsdksample.R
import com.example.popupsdksample.databinding.FragmentMainBinding

open class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        val view = binding.root

        setViewContent()
        return view
    }

    protected open fun setViewContent() {
        viewModel.popUpContextLive.observe(this.viewLifecycleOwner) {
            binding.getPopUpBody.text = it
        }

        binding.getPopUpButton.apply {
            this.setOnClickListener {
                viewModel.getPopUp(	"10452636", "app_open","")
            }
        }

        viewModel.popUpConfirmLive.observe(this.viewLifecycleOwner) {
            binding.popUpConfirm.text = it
        }

        binding.confirmButton.apply {
            this.setOnClickListener {
                viewModel.popUpConfirm()
            }
        }


        viewModel.sendEventLive.observe(this.viewLifecycleOwner) {
            binding.popUpEventResult.text = it
        }

        binding.sendEventButton.apply {
            this.setOnClickListener {
                viewModel.sendEvent()
            }
        }
    }
}
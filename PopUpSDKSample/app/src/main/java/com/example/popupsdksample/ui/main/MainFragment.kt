package com.example.popupsdksample.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.popupsdksample.R
import loymax.popup.sdk.services.PopUpService

open class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setViewContent(view)
        return view;
    }

    protected open fun setViewContent(view: View) {
        val popup = view.findViewById<TextView>(R.id.pop_up_content)
        viewModel.popUpContext.observe(this.viewLifecycleOwner, Observer {
            popup.text = it
        })

        view.findViewById<Button>(R.id.pop_up_button).apply {
            this.setOnClickListener {
                viewModel.getPopUp("123", "12")
            }
        }

        val popupConfirm = view.findViewById<TextView>(R.id.pop_up_confirm)
        viewModel.popUpConfirm.observe(this.viewLifecycleOwner, Observer {
            popupConfirm.text = it
        })
        view.findViewById<Button>(R.id.confirm_button).apply {
            this.setOnClickListener {
                viewModel.viewPopUp()
            }
        }
    }
}
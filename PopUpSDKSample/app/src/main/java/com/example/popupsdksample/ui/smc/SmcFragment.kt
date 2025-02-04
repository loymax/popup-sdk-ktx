package com.example.popupsdksample.ui.smc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.popupsdksample.R
import com.example.popupsdksample.TokenManager
import com.example.popupsdksample.databinding.FragmentSmcBinding
import com.example.popupsdksample.ui.smc.adapters.ViewPagerAdapter
import kotlinx.coroutines.launch
import loymax.smartcom.sdk.models.AddContactsRequest
import loymax.smartcom.sdk.models.UpdateSubscriptionsRequest

open class SmcFragment : Fragment() {
    companion object {
        fun newInstance() = SmcFragment()
    }

    private lateinit var viewModel: SmcViewModel
    private lateinit var binding: FragmentSmcBinding
    private lateinit var adapter: ViewPagerAdapter

    private val updateChannelsData = mutableListOf(
        "email" to "N",
        "push" to "Y",
        "sms" to "Y"
    )

    private val updateSubscriptionsData = mutableMapOf(
        "mailingCode" to mutableMapOf(
            "email" to "N",
            "sms" to "Y",
            "push" to "ND",
            "messenger:telegram" to "N",
            "messenger:whatsapp" to "Y"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SmcViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_smc, container, false)
        binding.lifecycleOwner = this
        binding.smcViewModel = viewModel

        setViewContent()
        observeViewModel()
        setupViewPager()
        return binding.root
    }

    protected open fun setViewContent() {
        binding.baseUrl.setText(viewModel._baseUrl.value)

        binding.baseUrl.addTextChangedListener {
            viewModel.changeBaseUrl(it.toString())
        }

        binding.username.addTextChangedListener {
            viewModel._userName = it.toString()
        }

        binding.password.addTextChangedListener {
            viewModel._password = it.toString()
        }

        binding.userID.addTextChangedListener {
            viewModel._userId = it.toString()
        }

        binding.authButton.setOnClickListener {
            viewModel.authenticateUser(
                userName = viewModel._userName,
                password = viewModel._password,
            )
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.customerChannels.collect { channels ->
                        channels?.let {
                            val dataList = it.data.map { channelData ->
                                channelData.attributes.type to channelData.attributes.value.toString()
                            }
                            adapter.updateData(0, dataList)
                        }
                    }
                }

                launch {
                    viewModel.customerSubscriptions.collect { subscriptions ->
                        subscriptions?.let {
                            val dataList = it.data.map { subscriptionData ->
                                listOf(
                                    "Type" to (subscriptionData.type ?: ""),
                                    "Name" to (subscriptionData.attributes.name ?: ""),
                                    "Description" to (subscriptionData.attributes.description
                                        ?: ""),
                                    "Status SMS" to (subscriptionData.attributes.status_sms ?: ""),
                                    "Status Push" to (subscriptionData.attributes.status_push
                                        ?: ""),
                                    "Status Email" to (subscriptionData.attributes.status_email
                                        ?: "")
                                )
                            }.flatten()
                            adapter.updateData(2, dataList)
                        }
                    }
                }
            }
        }
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter(
            onButtonClick = { position ->
                when (position) {
                    0 -> TokenManager.getToken()
                        ?.let { viewModel.getCustomerChannels(viewModel._userId) }

                    1 -> toggleUpdateChannelsData()
                    2 -> TokenManager.getToken()
                        ?.let { viewModel.getCustomerSubscriptions(viewModel._userId) }

                    3 -> TokenManager.getToken()?.let {
                    }

                    4 -> TokenManager.getToken()?.let {
                        val listAttributes: List<AddContactsRequest.Data.Attributes.Contact> = listOf(
                            AddContactsRequest.Data.Attributes.Contact(
                                type = "push",
                                subtype = "whatsapp",
                                value = "${TokenManager.getFirebaseToken()}",
                                device = "android"
                            )
                        )
                        viewModel.addCustomerContacts(
                            customerId = viewModel._userId,
                            addContactsRequest = AddContactsRequest(
                                AddContactsRequest.Data(
                                    AddContactsRequest.Data.Attributes(
                                        contacts = listAttributes
                                    )
                                )
                            )
                        )
                    }
                }
            },
            onItemClick = { buttonIndex, itemIndex ->
                if (buttonIndex == 1) toggleUpdateChannelsData(itemIndex)
                if (buttonIndex == 3) toggleUpdateSubscriptionsData(itemIndex)
            },
            onSendButtonClick = {
                when (it) {
                    1 -> {
                        viewModel.updateCustomerChannels(
                            customerId = viewModel._userId,
                            updateChannelsData = updateChannelsData,
                        )
                        println("Send button clicked $updateChannelsData")
                    }

                    3 -> {
                        sendUpdateSubscriptionsRequest()
                        println("Send button clicked $updateSubscriptionsData")
                    }
                }
            },
        )

        binding.viewPagerButtons.adapter = adapter
        binding.viewPagerButtons.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        adapter.updateData(1, updateChannelsData)
        adapter.updateData(3, updateSubscriptionsData.flatMap { (category, values) ->
            values.map { it.key to it.value }
        })
    }

    private fun toggleUpdateChannelsData(index: Int? = null) {
        if (index == null) {
            adapter.updateData(1, updateChannelsData)
            return
        }
        updateChannelsData[index] = updateChannelsData[index].let { (key, value) ->
            key to if (value == "N") "Y" else "N"
        }
        adapter.updateData(1, updateChannelsData)
    }

    private fun toggleUpdateSubscriptionsData(index: Int? = null) {
        val keys = updateSubscriptionsData["mailingCode"]?.keys?.toList() ?: return
        if (index == null || index >= keys.size) return

        val key = keys[index]
        val currentValue = updateSubscriptionsData["mailingCode"]?.get(key)

        val newValue = when (currentValue) {
            "Y" -> "N"
            "N" -> "ND"
            "ND" -> "Y"
            else -> "N"
        }

        updateSubscriptionsData["mailingCode"]?.set(key, newValue)
        adapter.updateData(3, updateSubscriptionsData.flatMap { (category, values) ->
            values.map { it.key to it.value }
        })
    }

    private fun sendUpdateSubscriptionsRequest() {
        val updateRequest = UpdateSubscriptionsRequest(
            data = UpdateSubscriptionsRequest.Data(
                attributes = UpdateSubscriptionsRequest.Data.Attributes(
                    categories = updateSubscriptionsData
                )
            )
        )
        TokenManager.getToken()?.let {
            viewModel.updateCustomerSubscriptions(viewModel._userId, updateRequest)
        }
    }
}
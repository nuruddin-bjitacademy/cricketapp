package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.LiveScoreAdapter
import com.graphicless.cricketapp.adapter.NewsAdapter
import com.graphicless.cricketapp.databinding.FragmentHomeBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private val viewModel: CricketViewModel by viewModels()
    private val netWorkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.HOME

        netWorkConnectionViewModel.isNetworkAvailable.observe(viewLifecycleOwner) { networkAvailable ->

            if (networkAvailable) {
                binding.internetUnavailable.visibility = View.GONE
                binding.information.visibility = View.VISIBLE
                try{
                    viewModel.live().observe(requireActivity()) {
                        val adapter = LiveScoreAdapter(it, requireActivity())
                        binding.recyclerView.adapter = adapter
                    }
                }catch (exception: Exception){
                    Log.e(TAG, "live observe: $exception", )
                }

                // Trying to fetch and insert news 5 times if there is socket timeout exception
                /*viewModel.viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        var retryCount = 0
                        while (retryCount < 5) {
                            try {
                                viewModel.launchNews()
                                break
                            } catch (exception: Exception) {
                                if (exception is SocketTimeoutException) {
                                    retryCount++
                                    delay(2000)
                                } else {
                                    Log.e(TAG, "launchNews: $exception")
                                    break
                                }
                            }
                        }
                    }
                }*/

                try {
                    viewModel.news.observe(viewLifecycleOwner) {
                        val adapter = NewsAdapter(it)
                        binding.rvNews.adapter = adapter
                    }
                } catch (exception: java.lang.Exception) {
                    Log.e(TAG, "News error: $exception")
                }

            } else {
                val snackBarNetworkUnavailable = Snackbar.make(
                    requireActivity().findViewById(R.id.bottom_nav_menu),
                    "No internet! Please check you connection.",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackBarNetworkUnavailable.show()
                binding.internetUnavailable.visibility = View.VISIBLE
                binding.information.visibility = View.GONE
            }
        }
    }// End of onCreateView

}
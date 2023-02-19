package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.graphicless.cricketapp.adapter.LiveScoreAdapter
import com.graphicless.cricketapp.adapter.NewsAdapter
import com.graphicless.cricketapp.databinding.FragmentHomeBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.HOME

        viewModel.isNetworkAvailable.observe(viewLifecycleOwner) { networkAvailable ->

            if (networkAvailable) {
                binding.internetUnavailable.visibility = View.GONE
                binding.information.visibility = View.VISIBLE
                viewModel.live().observe(requireActivity()) {
                    val adapter = LiveScoreAdapter(it, requireActivity())
                    binding.recyclerView.adapter = adapter
                }

                try {
                    viewModel.launchNews()
                    viewModel.news.observe(requireActivity()) {
                        val adapter = NewsAdapter(it)
                        binding.rvNews.adapter = adapter
                    }
                } catch (exception: java.lang.Exception) {
                    Log.e(TAG, "News error: $exception")
                }

            } else {
                binding.internetUnavailable.visibility = View.VISIBLE
                binding.information.visibility = View.GONE
            }
        }


    }

}
package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.FragmentFixtureBinding
import com.graphicless.cricketapp.databinding.FragmentNewsDetailsBinding
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class NewsDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentNewsDetailsBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.author
    }

}
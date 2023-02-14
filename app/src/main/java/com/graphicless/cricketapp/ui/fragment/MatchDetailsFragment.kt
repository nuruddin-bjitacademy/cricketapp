package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "MatchDetailsFragment"

class MatchDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.MATCH_DETAILS

        arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
            when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {
                0 -> {
                    Log.d(TAG, "tab no: 0 and id: ${getInt("fixtureId")}")
                }
                1 -> {
                    Log.d(TAG, "tab no: 1 and id: ${getInt("fixtureId")}")
                }
                1 -> {
                    Log.d(TAG, "tab no: 1 and id: ${getInt("fixtureId")}")
                }
            }
        }
    }
}
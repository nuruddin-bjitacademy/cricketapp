package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsInfoBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MatchDetailsInfoFragment"

class MatchDetailsInfoFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsInfoBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {
            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)
            Log.d(TAG, "onViewCreated: $fixtureId")
            viewModel.getFixtureDetails(fixtureId).observe(requireActivity()) {

                try {
                    binding.series.text = it.stageName
                    binding.match.text = it.teamOneCode.plus(" vs ").plus(it.teamTwoCode)
                    binding.matchNo.text = it.round
                    binding.localTeam.text = it.localTeamName
                    binding.visitorTeam.text = it.visitorTeamName
                    binding.status.text = it.status
                    binding.result.text = it.note
                    binding.toss.text =
                        it.tossWinTeamName.plus(" chose ").plus(it.elected).plus(" first.")
                    binding.stadium.text = it.stadiumName
                    binding.location.text = it.venue
                    binding.capacity.text =
                        NumberFormat.getNumberInstance(Locale.US).format(it.capacity)
                    binding.floodLight.text = if (it.floodLight.toInt() == 1) "Yes" else "No"
                    binding.firstUmpire.text = it.firstUmpire
                    binding.secondUmpire.text = it.secondUmpire
                    binding.tvUmpire.text = it.tvUmpire
                    binding.referee.text = it.referee

                    viewModel.getPlayerById(it.manOfTheMatchId)
                        .observe(viewLifecycleOwner) { PlayerAll ->
                            binding.mom.text = PlayerAll.fullname
                        }

                    val dtStart = it.startingAT
                    val format =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
                    try {
                        val date: Date = format.parse(dtStart) as Date
                        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(
                            date.toString().substring(11, 16)
                        )
                            ?.let { it1 ->
                                SimpleDateFormat("h:mm a", Locale.getDefault()).format(
                                    it1
                                )
                            }
                        binding.date.text = date.toString().substring(0, 10).plus(", ")
                            .plus(date.toString().substring(30, 34))
                        binding.time.text = time.plus(", Your Time")
                    } catch (e: ParseException) {
                        e.printStackTrace()
                        Log.e(TAG, "ParseException: $e")
                    }
                } catch (exception: Exception) {
                    Log.e(TAG, "exception: $exception")
                }
            }
        }
    }
}
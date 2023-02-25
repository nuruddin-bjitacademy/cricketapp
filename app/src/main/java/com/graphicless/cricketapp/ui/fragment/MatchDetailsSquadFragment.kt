package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsSquadBinding
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "MatchDetailsSquadFragment"

class MatchDetailsSquadFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsSquadBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private var comingFrom = "local"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsSquadBinding.inflate(inflater, container, false)

//        val loadingView =
//            LayoutInflater.from(context).inflate(R.layout.layout_loading, binding.container, false)
//        binding.container.addView(loadingView)
        binding.body.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.COMING_FROM) }?.apply {
            if(getString(MyConstants.COMING_FROM)== "live"){
                comingFrom = "live"
            }
        }

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {

            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

            viewModel.launchFixtureWithLineUp(fixtureId)

            viewModel.fixtureWithLineUp.observe(viewLifecycleOwner) {
                val teamOneId = it.data.localteamId
                val teamTwoId = it.data.visitorteamId

                try {
                    if (teamOneId != null) {
                        viewModel.getLocalTeamById(teamOneId).observe(requireActivity()) { team ->
                            try {
                                binding.tvNameTeam1.text = team.name
                                Glide.with(MyApplication.instance).load(team.imagePath)
                                    .into(binding.ivFlagTeam1)
                            } catch (exception: Exception) {
                                Log.e(TAG, "binding team one name and flag: $exception")
                            }
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("error", "ex mdsf teamOneId: $exception")
                }

                try {
                    if (teamTwoId != null) {
                        viewModel.getVisitorTeamById(teamTwoId).observe(requireActivity()) { team ->
                            try {
                                binding.tvNameTeam2.text = team.name
                                Glide.with(MyApplication.instance).load(team.imagePath)
                                    .into(binding.ivFlagTeam2)
                            } catch (exception: Exception) {
                                Log.e(TAG, "binding team two name and flag: $exception")
                            }
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("error", "ex mdsf teamTwoId: $exception")
                }

                Log.d(TAG, "data lineup size: ${it.data.lineup?.size}")

                if (it.data.lineup?.size!! >= 22) {
                    try {
                        binding.tvPlayerOne.text =
                            it.data.lineup?.get(0)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(0)?.position?.name).plus(")")
                        binding.tvPlayerTwo.text =
                            it.data.lineup?.get(1)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(1)?.position?.name).plus(")")
                        binding.tvPlayerThree.text =
                            it.data.lineup?.get(2)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(2)?.position?.name).plus(")")
                        binding.tvPlayerFour.text =
                            it.data.lineup?.get(3)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(3)?.position?.name).plus(")")
                        binding.tvPlayerFive.text =
                            it.data.lineup?.get(4)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(4)?.position?.name).plus(")")
                        binding.tvPlayerSix.text =
                            it.data.lineup?.get(5)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(5)?.position?.name).plus(")")
                        binding.tvPlayerSeven.text =
                            it.data.lineup?.get(6)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(6)?.position?.name).plus(")")
                        binding.tvPlayerEight.text =
                            it.data.lineup?.get(7)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(7)?.position?.name).plus(")")
                        binding.tvPlayerNine.text =
                            it.data.lineup?.get(8)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(8)?.position?.name).plus(")")
                        binding.tvPlayerTen.text =
                            it.data.lineup?.get(9)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(9)?.position?.name).plus(")")
                        binding.tvPlayerEleven.text =
                            it.data.lineup?.get(10)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(10)?.position?.name).plus(")")
                    } catch (exception: Exception) {
                        Log.e("error", "ex mdsf team one names: $exception")
                    }

                    try {
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(0)?.imagePath)
                            .into(binding.ivPlayerOne)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(1)?.imagePath)
                            .into(binding.ivPlayerTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(2)?.imagePath)
                            .into(binding.ivPlayerThree)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(3)?.imagePath)
                            .into(binding.ivPlayerFour)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(4)?.imagePath)
                            .into(binding.ivPlayerFive)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(5)?.imagePath)
                            .into(binding.ivPlayerSix)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(6)?.imagePath)
                            .into(binding.ivPlayerSeven)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(7)?.imagePath)
                            .into(binding.ivPlayerEight)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(8)?.imagePath)
                            .into(binding.ivPlayerNine)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(9)?.imagePath)
                            .into(binding.ivPlayerTen)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(10)?.imagePath)
                            .into(binding.ivPlayerEleven)
                    } catch (exception: Exception) {
                        Log.e("error", "ex mdsf team one pics: $exception")
                    }

                    try {
                        binding.tvPlayerOneTeamTwo.text =
                            it.data.lineup?.get(11)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(11)?.position?.name).plus(")")
                        binding.tvPlayerTwoTeamTwo.text =
                            it.data.lineup?.get(12)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(12)?.position?.name).plus(")")
                        binding.tvPlayerThreeTeamTwo.text =
                            it.data.lineup?.get(13)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(13)?.position?.name).plus(")")
                        binding.tvPlayerFourTeamTwo.text =
                            it.data.lineup?.get(14)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(14)?.position?.name).plus(")")
                        binding.tvPlayerFiveTeamTwo.text =
                            it.data.lineup?.get(15)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(15)?.position?.name).plus(")")
                        binding.tvPlayerSixTeamTwo.text =
                            it.data.lineup?.get(16)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(16)?.position?.name).plus(")")
                        binding.tvPlayerSevenTeamTwo.text =
                            it.data.lineup?.get(17)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(17)?.position?.name).plus(")")
                        binding.tvPlayerEightTeamTwo.text =
                            it.data.lineup?.get(18)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(18)?.position?.name).plus(")")
                        binding.tvPlayerNineTeamTwo.text =
                            it.data.lineup?.get(19)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(19)?.position?.name).plus(")")
                        binding.tvPlayerTenTeamTwo.text =
                            it.data.lineup?.get(20)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(20)?.position?.name).plus(")")
                        binding.tvPlayerElevenTeamTwo.text =
                            it.data.lineup?.get(21)?.fullname.plus("\n").plus("(")
                                .plus(it.data.lineup?.get(21)?.position?.name).plus(")")
                    } catch (exception: Exception) {
                        Log.e("error", "ex mdsf team two names: $exception")
                    }

                    try {
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(11)?.imagePath)
                            .into(binding.ivPlayerOneTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(12)?.imagePath)
                            .into(binding.ivPlayerTwoTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(13)?.imagePath)
                            .into(binding.ivPlayerThreeTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(14)?.imagePath)
                            .into(binding.ivPlayerFourTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(15)?.imagePath)
                            .into(binding.ivPlayerFiveTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(16)?.imagePath)
                            .into(binding.ivPlayerSixTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(17)?.imagePath)
                            .into(binding.ivPlayerSevenTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(18)?.imagePath)
                            .into(binding.ivPlayerEightTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(19)?.imagePath)
                            .into(binding.ivPlayerNineTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(20)?.imagePath)
                            .into(binding.ivPlayerTenTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(21)?.imagePath)
                            .into(binding.ivPlayerElevenTeamTwo)
                    } catch (exception: Exception) {
                        Log.e("error", "ex mdsf team two pics: $exception")
                    }

                    try{
                        binding.tvPlayerOne.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(0)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(1)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerThree.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(2)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerFour.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(3)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerFive.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(4)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerSix.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(5)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerSeven.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(6)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerEight.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(7)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerNine.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(8)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerTen.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(9)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerEleven.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(10)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerOneTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(11)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerTwoTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(12)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerThreeTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(13)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerFourTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(14)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerFive.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(15)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerSixTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(16)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerSevenTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(17)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerEightTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(18)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerNineTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(19)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerTenTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(20)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }
                        binding.tvPlayerElevenTeamTwo.setOnClickListener { _->
                            val playerId = it.data.lineup?.get(21)?.id
                            if(comingFrom == "live"){
                                val direction = playerId?.let { it1 ->
                                    LiveMatchDetailsContainerFragmentDirections.actionLiveMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }else{
                                val direction = playerId?.let { it1 ->
                                    MatchDetailsContainerFragmentDirections.actionMatchDetailsContainerFragmentToPlayerDetailsFragment(
                                        it1
                                    )
                                }
                                if (direction != null) {
                                    findNavController().navigate(direction)
                                }
                            }

                        }



                    }catch (exception: Exception){
                        Log.e(TAG, "Goto player details: $exception", )
                    }

                    try {
//                        binding.container.removeViewAt(0)
                    } catch (exception: Exception) {
                        Log.e(TAG, "remove view from container: $exception")
                    }

                    binding.progressbar.visibility = View.GONE
                    binding.body.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.GONE

                } else {
                    binding.progressbar.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    try {
//                        binding.container.removeViewAt(0)
                    } catch (exception: Exception) {
                        Log.e(TAG, "remove view from container: $exception")
                    }
//                    val noDataView = LayoutInflater.from(context)
//                        .inflate(R.layout.layout_no_data, binding.container, false)
//                    binding.container.addView(noDataView)
                }
            }

        }
    }
}

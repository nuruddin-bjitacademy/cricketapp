package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsSquadBinding.inflate(inflater, container, false)

        val loadingView =
            LayoutInflater.from(context).inflate(R.layout.layout_loading, binding.container, false)
        binding.container.addView(loadingView)
        binding.body.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {

            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

            viewModel.launchFixtureWithLineUp(fixtureId)

            viewModel.fixtureWithLineUp.observe(viewLifecycleOwner) {
                val teamOneId = it.data.localteamId
                val teamTwoId = it.data.visitorteamId

                try{
                    if (teamOneId != null) {
                        viewModel.getLocalTeamById(teamOneId).observe(requireActivity()){ team ->
                            binding.tvNameTeam1.text = team.name
                            Glide.with(MyApplication.instance).load(team.imagePath).into(binding.ivFlagTeam1)
                        }
                    }
                }catch(exception: Exception){
                    Log.e("error", "ex mdsf teamOneId: $exception", )
                }

                try{
                    if (teamTwoId != null) {
                        viewModel.getVisitorTeamById(teamTwoId).observe(requireActivity()){ team ->
                            binding.tvNameTeam2.text = team.name
                            Glide.with(MyApplication.instance).load(team.imagePath).into(binding.ivFlagTeam2)
                        }
                    }
                }catch(exception: Exception){
                    Log.e("error", "ex mdsf teamTwoId: $exception", )
                }

                if(it.data.lineup?.size!! == 2){
                    try{
                        binding.tvPlayerOne.text = it.data.lineup?.get(0)?.fullname ?: "N/A"
                        binding.tvPlayerTwo.text = it.data.lineup?.get(1)?.fullname ?: "N/A"
                        binding.tvPlayerThree.text = it.data.lineup?.get(2)?.fullname ?: "N/A"
                        binding.tvPlayerFour.text = it.data.lineup?.get(3)?.fullname ?: "N/A"
                        binding.tvPlayerFive.text = it.data.lineup?.get(4)?.fullname ?: "N/A"
                        binding.tvPlayerSix.text = it.data.lineup?.get(5)?.fullname ?: "N/A"
                        binding.tvPlayerSeven.text = it.data.lineup?.get(6)?.fullname ?: "N/A"
                        binding.tvPlayerEight.text = it.data.lineup?.get(7)?.fullname ?: "N/A"
                        binding.tvPlayerNine.text = it.data.lineup?.get(8)?.fullname ?: "N/A"
                        binding.tvPlayerTen.text = it.data.lineup?.get(9)?.fullname ?: "N/A"
                        binding.tvPlayerEleven.text = it.data.lineup?.get(10)?.fullname ?: "N/A"
                    }catch(exception: Exception){
                        Log.e("error", "ex mdsf team one names: $exception", )
                    }

                    try{
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(0)?.imagePath).into(binding.ivPlayerOne)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(1)?.imagePath).into(binding.ivPlayerTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(2)?.imagePath).into(binding.ivPlayerThree)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(3)?.imagePath).into(binding.ivPlayerFour)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(4)?.imagePath).into(binding.ivPlayerFive)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(5)?.imagePath).into(binding.ivPlayerSix)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(6)?.imagePath).into(binding.ivPlayerSeven)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(7)?.imagePath).into(binding.ivPlayerEight)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(8)?.imagePath).into(binding.ivPlayerNine)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(9)?.imagePath).into(binding.ivPlayerTen)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(10)?.imagePath).into(binding.ivPlayerEleven)
                    }catch(exception: Exception){
                        Log.e("error", "ex mdsf team one pics: $exception", )
                    }

                    try{
                        binding.tvPlayerOneTeamTwo.text = it.data.lineup?.get(11)?.fullname ?: "N/A"
                        binding.tvPlayerTwoTeamTwo.text = it.data.lineup?.get(12)?.fullname ?: "N/A"
                        binding.tvPlayerThreeTeamTwo.text = it.data.lineup?.get(13)?.fullname ?: "N/A"
                        binding.tvPlayerFourTeamTwo.text = it.data.lineup?.get(14)?.fullname ?: "N/A"
                        binding.tvPlayerFiveTeamTwo.text = it.data.lineup?.get(15)?.fullname ?: "N/A"
                        binding.tvPlayerSixTeamTwo.text = it.data.lineup?.get(16)?.fullname ?: "N/A"
                        binding.tvPlayerSevenTeamTwo.text = it.data.lineup?.get(17)?.fullname ?: "N/A"
                        binding.tvPlayerEightTeamTwo.text = it.data.lineup?.get(18)?.fullname ?: "N/A"
                        binding.tvPlayerNineTeamTwo.text = it.data.lineup?.get(19)?.fullname ?: "N/A"
                        binding.tvPlayerTenTeamTwo.text = it.data.lineup?.get(20)?.fullname ?: "N/A"
                        binding.tvPlayerElevenTeamTwo.text = it.data.lineup?.get(21)?.fullname ?: "N/A"
                    }catch(exception: Exception){
                        Log.e("error", "ex mdsf team two names: $exception", )
                    }

                    try{
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(11)?.imagePath).into(binding.ivPlayerOneTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(12)?.imagePath).into(binding.ivPlayerTwoTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(13)?.imagePath).into(binding.ivPlayerThreeTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(14)?.imagePath).into(binding.ivPlayerFourTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(15)?.imagePath).into(binding.ivPlayerFiveTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(16)?.imagePath).into(binding.ivPlayerSixTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(17)?.imagePath).into(binding.ivPlayerSevenTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(18)?.imagePath).into(binding.ivPlayerEightTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(19)?.imagePath).into(binding.ivPlayerNineTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(20)?.imagePath).into(binding.ivPlayerTenTeamTwo)
                        Glide.with(MyApplication.instance).load(it.data.lineup?.get(21)?.imagePath).into(binding.ivPlayerElevenTeamTwo)
                    }catch(exception: Exception){
                        Log.e("error", "ex mdsf team two pics: $exception", )
                    }

                    try {
                        binding.container.removeViewAt(0)
                    } catch (exception: Exception) {
                        Log.e(TAG, "remove view from container: $exception")
                    }

                    binding.body.visibility = View.VISIBLE

                }else{
                    try {
                        binding.container.removeViewAt(0)
                    } catch (exception: Exception) {
                        Log.e(TAG, "remove view from container: $exception")
                    }
                    val noDataView = LayoutInflater.from(context)
                        .inflate(R.layout.layout_no_data, binding.container, false)
                    binding.container.addView(noDataView)
                }
            }

        }
    }
}

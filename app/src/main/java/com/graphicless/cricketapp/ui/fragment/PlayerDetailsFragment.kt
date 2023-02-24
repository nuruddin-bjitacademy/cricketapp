package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.PlayerDetailsBowlingCareerAdapter
import com.graphicless.cricketapp.adapter.PlayerDetailsBattingCareerAdapter
import com.graphicless.cricketapp.adapter.PlayerDetailsTeamsAdapter
import com.graphicless.cricketapp.databinding.FragmentPlayerDetailsBinding
import com.graphicless.cricketapp.Model.PlayerDetailsBatting
import com.graphicless.cricketapp.Model.PlayerDetailsBowling
import com.graphicless.cricketapp.Model.PlayerDetailsNew
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PlayerDetailsFragment"

class PlayerDetailsFragment : Fragment() {
    private lateinit var _binding: FragmentPlayerDetailsBinding
    private val binding get() = _binding

    private val args: PlayerDetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    var selectCareer = "batting"
    var selectedTab = "career"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val playerId = args.playerId
        Log.d(TAG, "Player id: $playerId")

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val screenWidth = displayMetrics.widthPixels

        binding.playerImageHolder.layoutParams =
            ViewGroup.LayoutParams(screenWidth, screenHeight / 3)

        viewModel.launchPlayerDetails(playerId)

        try {
            viewModel.playerDetails.observe(requireActivity()) { it ->
                binding.playerFirstName.text =
                    if (it != null) it.firstname else MyConstants.NOT_AVAILABLE
                binding.playerLastName.text =
                    if (it != null) it.lastname else MyConstants.NOT_AVAILABLE
                if (it != null) {
                    Glide.with(MyApplication.instance).load(it.imagePath).into(binding.playerImage)
                }
                val age = it?.dateofbirth?.let { it1 -> calculateAge(it1) }
                if (it != null) {
                    binding.playerCountryAge.text =
                        it.country?.name.plus(activity?.getString(R.string.special_bullet))
                            .plus(age).plus(" years")
                }

                careerTab(it)
                changeTabColor(selectedTab)

                binding.tvCareer.setOnClickListener { _ ->
                    selectedTab = "career"
                    careerTab(it)
                    changeTabColor(selectedTab)
                }

                binding.tvTeams.setOnClickListener { _ ->
                    selectedTab = "teams"
                    teamsTab(it)
                    changeTabColor(selectedTab)
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, "player details observe: $exception")
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun teamsTab(data: PlayerDetailsNew.Data?) {
        if (data != null) {
            val items = listOf("Current Teams", "All Teams")
            val spinnerAdapter =
                ArrayAdapter(
                    MyApplication.instance,
                    android.R.layout.simple_spinner_item,
                    items
                )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCareer.adapter = spinnerAdapter

            binding.spinnerCareer.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        when (position) {
                            0 -> {
                                Log.d(TAG, "Tab selected: ${items[0]}")
                                if (data.currentteams == null) {
                                    binding.tvNoData.visibility = View.VISIBLE
                                } else {
                                    Log.d(TAG, "current teams size: ${data.currentteams!!.size}")
                                    try {
                                        val currentTeamList: MutableList<PlayerDetailsNew.Data.Currentteam?> =
                                            mutableListOf()
                                        for (i in data.currentteams!!.indices) {
                                            currentTeamList.add(i, data.currentteams!![i])
                                        }

                                        Log.d(TAG, "currentTeamList size : ${currentTeamList.size}")
                                        Log.d(TAG, "currentTeamList : $currentTeamList")
                                        val adapter = PlayerDetailsTeamsAdapter(
                                            currentTeamList,
                                            null,
                                            requireActivity()
                                        )
                                        val layoutManager =
                                            LinearLayoutManager(
                                                requireContext(),
                                                LinearLayoutManager.VERTICAL,
                                                false
                                            )
                                        binding.rvTeams.layoutManager = layoutManager
                                        binding.rvCareer.adapter = null
                                        binding.rvTeams.adapter = adapter
                                        if(binding.rvTeams.adapter?.itemCount == 0){
                                            binding.tvNoData.visibility = View.VISIBLE
                                        }else{
                                            binding.tvNoData.visibility = View.GONE
                                        }
                                    } catch (exception: Exception) {
                                        Log.e(TAG, "binding current team: $exception")
                                    }
                                }
                            }
                            1 -> {
                                Log.d(TAG, "Tab selected: ${items[1]}")
                                try {
                                    if (data.teams == null) {
                                        binding.tvNoData.visibility = View.VISIBLE
                                    } else {
                                        Log.d(TAG, "all teams size: ${data.teams!!.size}")
                                        binding.tvNoData.visibility = View.GONE
                                        val teamList: MutableList<PlayerDetailsNew.Data.Team?> =
                                            mutableListOf()
                                        for (i in data.teams!!.indices) {
                                            teamList.add(i, data.teams!![i])
                                        }
                                        Log.d(TAG, "Team list: $teamList")
                                        Log.d(TAG, "Team list size: ${teamList.size}")
                                        val adapter = PlayerDetailsTeamsAdapter(
                                            null,
                                            teamList,
                                            requireActivity()
                                        )
                                        val layoutManager =
                                            LinearLayoutManager(
                                                requireContext(),
                                                LinearLayoutManager.VERTICAL,
                                                false
                                            )
                                        binding.rvTeams.layoutManager = layoutManager
                                        binding.rvCareer.adapter = null
                                        binding.rvTeams.adapter = adapter
                                        if(binding.rvTeams.adapter?.itemCount == 0){
                                            binding.tvNoData.visibility = View.VISIBLE
                                        }else{
                                            binding.tvNoData.visibility = View.GONE
                                        }
                                    }
                                } catch (exception: Exception) {
                                    Log.e(TAG, "binding all team: $exception")
                                }
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
        }
    }

    private fun careerTab(data: PlayerDetailsNew.Data?) {

        val items = listOf("Batting Career", "Bowling Career")
        val spinnerAdapter =
            ArrayAdapter(
                MyApplication.instance,
                android.R.layout.simple_spinner_item,
                items
            )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCareer.adapter = spinnerAdapter

        val careerBattingTypeList: MutableList<String> = mutableListOf()
        val careerBattingList: MutableList<PlayerDetailsBatting> = mutableListOf()
        val careerBattingListWithType: MutableList<PlayerDetailsBatting> =
            mutableListOf()
        val careerBowlingTypeList: MutableList<String> = mutableListOf()
        val careerBowlingList: MutableList<PlayerDetailsBowling> = mutableListOf()
        val careerBowlingListWithType: MutableList<PlayerDetailsBowling> =
            mutableListOf()

        try {
            if (data != null) {

                if (data.position?.id == 2) {
                    binding.playerPosition.setImageResource(R.drawable.icon_ball)
//                    selectCareer = "bowling"
//                    binding.spinnerCareer.setSelection(1)
                } else {
                    binding.playerPosition.setImageResource(R.drawable.icon_bat)
//                    binding.spinnerCareer.setSelection(0)
                }

                for (i in data.career?.indices!!) {

                    val type = data.career!![i]?.type

                    if (data.career!![i]?.batting != null) {

                        val info = data.career!![i]?.batting

                        var season = data.career!![i]?.season?.code
                        if (season.isNullOrEmpty()) season = MyConstants.NOT_AVAILABLE

                        val heading = type.plus(" (").plus(season.toString()).plus(")")

                        if (type != null) {
                            try {
                                careerBattingTypeList.add(i, heading)
                                val batting = PlayerDetailsBatting(
                                    info!!.matches!!,
                                    info.runsScored!!,
                                    info.innings!!,
                                    info.ballsFaced!!,
                                    info.highestInningScore!!,
                                    info.hundreds!!,
                                    info.fifties!!,
                                    info.average!!,
                                    info.notOuts!!,
                                    info.fourX!!,
                                    info.sixX!!,
                                    info.strikeRate!!
                                )
                                careerBattingList.add(i, batting)
                            } catch (exception: Exception) {
                                Log.e(TAG, "careerBattingList add: $exception")
                            }
                        }
                    }

                    if (data.career!![i]?.bowling != null) {

                        val info = data.career!![i]?.bowling

                        var season = data.career!![i]?.season?.code
                        if (season.isNullOrEmpty()) season = MyConstants.NOT_AVAILABLE
                        val heading = type.plus(" (").plus(season.toString()).plus(")")

                        if (type != null) {
                            try {
                                careerBowlingTypeList.add(i, heading)
                                val bowling = PlayerDetailsBowling(
                                    info!!.matches!!,
                                    info.overs!!,
                                    info.innings!!,
                                    info.average!!,
                                    info.econRate!!,
                                    info.medians!!,
                                    info.runs!!,
                                    info.wickets!!,
                                    info.wide!!,
                                    info.noball!!,
                                    info.strikeRate!!,
                                    info.fourWickets!!,
                                    info.fiveWickets!!,
                                    info.tenWickets!!,
                                    info.rate!!
                                )
                                careerBowlingList.add(i, bowling)
                            } catch (exception: Exception) {
                                Log.e(TAG, "careerBowlingList add: $exception")
                            }
                        }
                    }
                }
                // End career for loop

                for (i in careerBattingTypeList.indices) {
                    careerBattingListWithType.add(i, careerBattingList[i])
                }

                for (i in careerBowlingTypeList.indices) {
                    careerBowlingListWithType.add(i, careerBowlingList[i])
                }
            }

            Log.d(TAG, "selectCareer $selectCareer")
            Log.d(TAG, "careerBattingTypeList $careerBattingTypeList")
            Log.d(TAG, "careerBattingListWithType $careerBattingListWithType")
            Log.d(TAG, "careerBowlingTypeList $careerBowlingTypeList")
            Log.d(TAG, "careerBowlingListWithType $careerBowlingListWithType")

            showCareer(
                selectCareer,
                careerBattingTypeList,
                careerBattingListWithType,
                careerBowlingTypeList,
                careerBowlingListWithType
            )

            binding.spinnerCareer.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (data != null) {
//                            if (data.position?.id == 2) {
//                                selectCareer = if (position == 0) "bowling" else "batting"
//                            }else{
                                selectCareer = if (position == 0) "batting" else "bowling"
//                            }
                        }
                        showCareer(
                            selectCareer,
                            careerBattingTypeList,
                            careerBattingListWithType,
                            careerBowlingTypeList,
                            careerBowlingListWithType
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.d(TAG, "onNothingSelected: called")
                    }
                }
        } catch (exception: Exception) {
            Log.e(TAG, "player details observe: $exception")
        }

    }

    private fun showCareer(
        selectCareer: String,
        careerBattingTypeList: MutableList<String>,
        careerBattingListWithType: MutableList<PlayerDetailsBatting>,
        careerBowlingTypeList: MutableList<String>,
        careerBowlingListWithType: MutableList<PlayerDetailsBowling>
    ) {
        when(selectCareer){
            "batting" -> {
                val adapter = PlayerDetailsBattingCareerAdapter(
                    careerBattingTypeList,
                    careerBattingListWithType
                )
                val layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                binding.rvCareer.layoutManager = layoutManager
                binding.rvTeams.adapter = null
                binding.rvCareer.adapter = adapter
                Log.d(TAG, "showCareer: batting : $careerBattingListWithType")
                if(binding.rvCareer.adapter?.itemCount == 0){
                    binding.tvNoData.visibility = View.VISIBLE
                }else{
                    binding.tvNoData.visibility = View.GONE
                }
                binding.progressbar.visibility = View.GONE
            }
            "bowling" -> {
                val adapter = PlayerDetailsBowlingCareerAdapter(
                    careerBowlingTypeList,
                    careerBowlingListWithType
                )
                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rvCareer.layoutManager = layoutManager
                binding.rvTeams.adapter = null
                binding.rvCareer.adapter = adapter
                if(binding.rvCareer.adapter?.itemCount == 0){
                    binding.tvNoData.visibility = View.VISIBLE
                }else{
                    binding.tvNoData.visibility = View.GONE
                }
                binding.progressbar.visibility = View.GONE
            }
        }
    }

    private fun calculateAge(birthDate: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(birthDate)

        // create calendar object for birth date
        val dob = Calendar.getInstance()
        if (date != null) {
            dob.time = date
        }

        // create calendar object for current date
        val today = Calendar.getInstance()

        // calculate age in years
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }

    private fun changeTabColor(item: String) {
        val unselectedBackground =
            ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle_round, null)
        val unselectedTextColor = ResourcesCompat.getColor(resources, R.color.button_text_unselected, null)
        val selectedBackground = ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle_round_solid, null)
        val selectedTextColor = ResourcesCompat.getColor(resources, R.color.button_text_selected, null)
        when (item) {
            "career" -> {
                binding.tvCareer.background = selectedBackground
                binding.tvCareer.setTextColor(selectedTextColor)
                binding.tvTeams.background = unselectedBackground
                binding.tvTeams.setTextColor(unselectedTextColor)
            }
            "teams" -> {
                binding.tvTeams.background = selectedBackground
                binding.tvTeams.setTextColor(selectedTextColor)
                binding.tvCareer.background = unselectedBackground
                binding.tvCareer.setTextColor(unselectedTextColor)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
        val window: Window = requireActivity().window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar?.show()
        val window: Window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}
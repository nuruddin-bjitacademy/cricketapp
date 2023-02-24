package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.MatchAdapter
import com.graphicless.cricketapp.adapter.StageAdapterForPrevious
import com.graphicless.cricketapp.adapter.StageAdapterForUpcoming
import com.graphicless.cricketapp.databinding.FragmentMatchesBinding
import com.graphicless.cricketapp.utils.DateValidator
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "MatchesFragment"

class MatchesFragment : Fragment() {

    private var shouldCalenderVisible = true

    private lateinit var _binding: FragmentMatchesBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    lateinit var calendar: MenuItem

    var tabSelected: String = "recent"
    var selectedLeagueId: Int = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
//        exitTransition = Hold()
//        exitTransition = MaterialElevationScale(/* growing = */ false)
//        reenterTransition = MaterialElevationScale(/* growing = */ true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.COMING_FROM) }?.apply {

//            tabSelected = getString(MyConstants.COMING_FROM).toString()
//            Log.d(TAG, "onViewCreated: tabSelected $tabSelected")
            Log.d(TAG, "onViewCreated: ${getString(MyConstants.COMING_FROM)}")

            when (getString(MyConstants.COMING_FROM)) {
                "upcoming" -> {
                    setOptionMenuVisibility(false)
                    tabSelected = "previous"
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {
                            0 -> {// BPL
                                Log.d(TAG, "upcoming BPL")
                                upcomingMatchSummaryByLeagueId(9)
                                selectedLeagueId = 9
                            }
                            1 -> {// IPL
                                Log.d(TAG, "upcoming IPL")
                                upcomingMatchSummaryByLeagueId(1)
                                selectedLeagueId = 1
                            }
                            2 -> {// BBL
                                Log.d(TAG, "upcoming BBL")
                                upcomingMatchSummaryByLeagueId(5)
                                selectedLeagueId = 5
                            }
                            3 -> {// ODI
                                Log.d(TAG, "upcoming ODI")
                                upcomingMatchSummaryByLeagueId(2)
                                selectedLeagueId = 2
                            }
                            4 -> {// T20I
                                Log.d(TAG, "upcoming T20I")
                                upcomingMatchSummaryByLeagueId(3)
                                selectedLeagueId = 3
                            }
                        }
                    }
                }
                "recent" -> {
                    setOptionMenuVisibility(false)
                    tabSelected = "recent"
                    when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {
                        0 -> {// BPL
                            recentMatchSummaryByLeagueId(9)
                            selectedLeagueId = 9
                        }
                        1 -> {// IPL
                            recentMatchSummaryByLeagueId(1)
                            selectedLeagueId = 1
                        }
                        2 -> {// BBL
                            recentMatchSummaryByLeagueId(5)
                            selectedLeagueId = 5
                        }
                        3 -> {// ODI
                            recentMatchSummaryByLeagueId(2)
                            selectedLeagueId = 2
                        }
                        4 -> {// T20I
                            recentMatchSummaryByLeagueId(3)
                            selectedLeagueId = 3
                        }
                    }
                }
                "previous" -> {
                    setOptionMenuVisibility(true)
                    tabSelected = "previous"
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        /*val tabPosition = getInt(MyConstants.CATEGORY_TAB_NUMBER)
                        val leagueNameList = listOf("BPL", "IPL", "BBL", "ODI", "T20I")
                        val leagueIdList = listOf<Int>(9, 1, 5, 2, 3)
                        val selectedLeagueId = leagueIdList[tabPosition]
                        previousMatchSummaryByLeagueId(selectedLeagueId)
                        typeSelected = selectedLeagueId*/
                        when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {
                            0 -> {// BPL
                                Log.d(TAG, "previous T20I")
                                previousMatchSummaryByLeagueId(9)
                                selectedLeagueId = 9
                            }
                            1 -> {// IPL
                                Log.d(TAG, "previous IPL")
                                previousMatchSummaryByLeagueId(1)
                                selectedLeagueId = 1
                            }
                            2 -> {// BBL
                                Log.d(TAG, "previous BBL")
                                previousMatchSummaryByLeagueId(5)
                                selectedLeagueId = 5
                            }
                            3 -> {// ODI
                                Log.d(TAG, "previous ODI")
                                previousMatchSummaryByLeagueId(2)
                                selectedLeagueId = 2
                            }
                            4 -> {// T20I
                                Log.d(TAG, "previous T20I")
                                previousMatchSummaryByLeagueId(3)
                                selectedLeagueId = 3
                            }
                        }
                    }
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        calendar = menu.add("Calendar")
        calendar.setShowAsAction(1)
        calendar.setIcon(R.drawable.baseline_calendar_month_24)
        calendar.isVisible = shouldCalenderVisible
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.calendar ->
//                // Not implemented here
//                return false
            calendar.itemId -> {
                showCalender(tabSelected, selectedLeagueId)
                Log.d(TAG, "onOptionsItemSelected: $tabSelected")
                /*if (tabSelected == "recent") {
                    Toast.makeText(
                        MyApplication.instance,
                        "Filter Date is not available in this section",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
//                    showCalender(tabSelected, selectedLeagueId)
                    Toast.makeText(
                        MyApplication.instance,
                        "Filter Date is available in this section",
                        Toast.LENGTH_SHORT
                    ).show()
                }*/
//                getPreviousMatchesByDate(3, "2021-01-07")
                return super.onOptionsItemSelected(item)

            }
            else -> {}
        }
        return false
    }


    private fun showCalender(tabSelected: String, selectedLeagueId: Int) {

        when (tabSelected) {
            "upcoming" -> {
                getAllUpcomingMatchDateByType(selectedLeagueId)
            }
            "previous" -> {
                getAllPreviousMatchDateByType(selectedLeagueId)
            }
        }
    }

    private fun getAllUpcomingMatchDateByType(leagueId: Int) {
        viewModel.getAllUpcomingMatchDateByType(leagueId).observe(requireActivity()) { dates ->
            myCalendar(dates, tabSelected)
            Log.d(TAG, "getAllPreviousMatchDateByType: $dates")
        }
    }

    private fun getAllPreviousMatchDateByType(leagueId: Int) {
        viewModel.getAllPreviousMatchDateByType(leagueId).observe(requireActivity()) { dates ->
            myCalendar(dates, tabSelected)
            Log.d(TAG, "getAllPreviousMatchDateByType: $dates")
        }
    }

    private fun showAllFixture() {
        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.MATCHES
        binding.recyclerView.visibility = View.VISIBLE
        binding.recyclerViewByDate.visibility = View.GONE
    }

    private fun myCalendar(dateStringRaw: List<String>, selectedTab: String) {

        val dateStringFormatted: MutableList<String> = mutableListOf()
        val dates: MutableList<Long> = mutableListOf()

        for (i in dateStringRaw.indices) {
            dateStringFormatted.add(i, dateStringRaw[i].substring(0, 10))
        }

        for (i in dateStringFormatted.indices) {
            val timeStamp = getTimeStamp(
                dateStringFormatted[i].substring(0, 4).toInt(),
                dateStringFormatted[i].substring(5, 7).toInt(),
                dateStringFormatted[i].substring(8, 10).toInt()
            )
            dates.add(i, timeStamp)
        }

        Log.d(TAG, "myCalendar: dates: $dates")

        val builderRange = MaterialDatePicker.Builder.datePicker()
        builderRange.setCalendarConstraints(matchCalendarConstraints(dates)!!.build())
        builderRange.setTitleText("Select Date Range")
        val pickerRange = builderRange.build()
        pickerRange.show(requireActivity().supportFragmentManager, pickerRange.toString())

        pickerRange.addOnPositiveButtonClickListener { date ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val formattedDate = dateFormat.format(Date(date))
            Log.d(TAG, "myCalendar: $formattedDate")
            when (selectedTab) {
                "upcoming" -> {
                    getUpcomingMatchesByDate(selectedLeagueId, formattedDate.substring(0, 10))
                }
                "previous" -> {
                    getPreviousMatchesByDate(selectedLeagueId, formattedDate.substring(0, 10))
                }
            }
        }
    }

    private fun matchCalendarConstraints(dates: List<Long>): CalendarConstraints.Builder? {
        val constraintsBuilderRange = CalendarConstraints.Builder()
        constraintsBuilderRange.setValidator(
            DateValidator(
                dates
            )
        )
        return constraintsBuilderRange
    }

    private fun getTimeStamp(year: Int, month: Int, date: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, date, 6, 0, 0)
        return calendar.timeInMillis / 1000
    }

    private fun getPreviousMatchesByDate(leagueId: Int, startingAt: String) {
        Log.d(TAG, "getPreviousMatchesByDate: league id : $leagueId starting at : $startingAt")
        viewModel.getPreviousMatchesByDate(leagueId, startingAt).observe(this) {
            Log.d(TAG, "fixture by date size: ${it.size}")
            Log.d(TAG, "fixture by date: $it")
            if (it != null) {
                binding.recyclerView.adapter = null
                (activity as AppCompatActivity).supportActionBar?.title =
                    java.lang.StringBuilder("Matches on ").append(startingAt)
            }
            binding.recyclerViewByDate.visibility = View.VISIBLE
            val adapter = MatchAdapter(it, null, 0, requireActivity())
            binding.recyclerViewByDate.adapter = adapter
        }
    }

    private fun getUpcomingMatchesByDate(leagueId: Int, startingAt: String) {
        Log.d(TAG, "getUpcomingMatchesByDate: league id : $leagueId starting at : $startingAt")
        viewModel.getUpcomingMatchesByDate(leagueId, startingAt).observe(this) {
            Log.d(TAG, "fixture by date size: ${it.size}")
            Log.d(TAG, "fixture by date: $it")
            if (it != null) {
                binding.recyclerView.adapter = null
                (activity as AppCompatActivity).supportActionBar?.title =
                    java.lang.StringBuilder("Matches on ").append(startingAt)
            }
            binding.recyclerViewByDate.visibility = View.VISIBLE
            val adapter = MatchAdapter(it, null, 0, requireActivity())
            binding.recyclerViewByDate.adapter = adapter
        }
    }


/*
    private fun itemSelected(item: String) {
        val unselectedBackground =
            ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle, null)
        val unselectedTextColor = ResourcesCompat.getColor(resources, R.color.tab_text, null)
        val selectedBackground = ResourcesCompat.getColor(resources, R.color.action_bar, null)
        val selectedTextColor = ResourcesCompat.getColor(resources, R.color.tab_text_selected, null)
        when (item) {
            "t20i" -> {
                binding.tvT20i.setBackgroundColor(selectedBackground)
                binding.tvT20i.setTextColor(selectedTextColor)
                binding.tvBbc.background = unselectedBackground
                binding.tvBbc.setTextColor(unselectedTextColor)
                binding.tvT20c.background = unselectedBackground
                binding.tvT20c.setTextColor(unselectedTextColor)
            }
            "bbc" -> {
                binding.tvBbc.setBackgroundColor(selectedBackground)
                binding.tvBbc.setTextColor(selectedTextColor)
                binding.tvT20i.background = unselectedBackground
                binding.tvT20i.setTextColor(unselectedTextColor)
                binding.tvT20c.background = unselectedBackground
                binding.tvT20c.setTextColor(unselectedTextColor)
            }
            "t20c" -> {
                binding.tvT20c.setBackgroundColor(selectedBackground)
                binding.tvT20c.setTextColor(selectedTextColor)
                binding.tvBbc.background = unselectedBackground
                binding.tvBbc.setTextColor(unselectedTextColor)
                binding.tvT20i.background = unselectedBackground
                binding.tvT20i.setTextColor(unselectedTextColor)
            }
        }
    }
*/

    private fun upcomingMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getUpcomingMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueId total: ${it.size}")
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueIds: $it")
            try{
                val adapter = StageAdapterForUpcoming(it, requireActivity(), MyApplication.instance)
                binding.recyclerView.adapter = adapter
                binding.progressbar.visibility = View.GONE
                binding.recyclerViewByDate.adapter = null
                if (binding.recyclerView.adapter?.itemCount == 0) {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }catch (exception: Exception){
                Log.e(TAG, "upcomingMatchSummaryByLeagueId: $exception")
            }
        }
    }
    private fun recentMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getRecentMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            try{
                val adapter = MatchAdapter(it, null, null, requireActivity())
                binding.recyclerView.adapter = null
                binding.recyclerViewByDate.adapter = adapter
                binding.progressbar.visibility = View.GONE
                if (binding.recyclerViewByDate.adapter?.itemCount == 0) {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }catch (exception: Exception){
                Log.e(TAG, "getRecentMatchSummaryByLeagueId: $exception")
            }
        }
    }

    private fun previousMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getPreviousMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "previousMatchSummaryByLeagueId total: ${it.size}")
            try{
                val adapter = StageAdapterForPrevious(it, requireActivity(), MyApplication.instance)
                binding.recyclerView.adapter = adapter
                binding.progressbar.visibility = View.GONE
                binding.recyclerViewByDate.adapter = null
                if (binding.recyclerView.adapter?.itemCount == 0) {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }catch (exception: Exception){
                Log.e(TAG, "previousMatchSummaryByLeagueId: $exception")
            }
        }
    }

    fun setOptionMenuVisibility(visible: Boolean) {
        shouldCalenderVisible = visible
        requireActivity().invalidateOptionsMenu()
    }


}
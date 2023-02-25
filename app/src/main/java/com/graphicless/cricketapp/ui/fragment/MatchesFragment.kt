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

    private lateinit var calendar: MenuItem

    var tabSelected: String = "upcoming"
    var selectedLeagueId: Int = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setOptionMenuVisibility(true)
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

            when (getString(MyConstants.COMING_FROM)) {
                "upcoming" -> {
                    setOptionMenuVisibility(false)
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {
                            0 -> {// BPL
                                selectedLeagueId = 9
                                upcomingMatchSummaryByLeagueId(9)
                            }
                            1 -> {// IPL
                                upcomingMatchSummaryByLeagueId(1)
                                selectedLeagueId = 1
                            }
                            2 -> {// BBL
                                upcomingMatchSummaryByLeagueId(5)
                                selectedLeagueId = 5
                            }
                            3 -> {// ODI
                                upcomingMatchSummaryByLeagueId(2)
                                selectedLeagueId = 2
                            }
                            4 -> {// T20I
                                upcomingMatchSummaryByLeagueId(3)
                                selectedLeagueId = 3
                            }
                        }
                    }
                }
                "recent" -> {
                    setOptionMenuVisibility(false)
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
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        /*val tabPosition = getInt(MyConstants.CATEGORY_TAB_NUMBER)
                        val leagueNameList = listOf("BPL", "IPL", "BBL", "ODI", "T20I")
                        val leagueIdList = listOf<Int>(9, 1, 5, 2, 3)
                        val selectedLeagueId = leagueIdList[tabPosition]
                        previousMatchSummaryByLeagueId(selectedLeagueId)
                        typeSelected = selectedLeagueId*/
                        when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {
                            0 -> {// BPL
                                previousMatchSummaryByLeagueId(9)
                                selectedLeagueId = 9
                            }
                            1 -> {// IPL
                                previousMatchSummaryByLeagueId(1)
                                selectedLeagueId = 1
                            }
                            2 -> {// BBL
                                previousMatchSummaryByLeagueId(5)
                                selectedLeagueId = 5
                            }
                            3 -> {// ODI
                                previousMatchSummaryByLeagueId(2)
                                selectedLeagueId = 2
                            }
                            4 -> {// T20I
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
            calendar.itemId -> {
                showCalender(selectedLeagueId)
                Log.d(TAG, "tabSelected: $tabSelected, leagueId $selectedLeagueId")
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }


    private fun showCalender(selectedLeagueId: Int) {
        getAllPreviousMatchDateByType(selectedLeagueId)
    }

    private fun getAllUpcomingMatchDateByType(leagueId: Int) {
        viewModel.getAllUpcomingMatchDateByType(leagueId).observe(requireActivity()) { dates ->
            myCalendar(dates)
            Log.d(TAG, "getAllPreviousMatchDateByType: $dates")
        }
    }

    private fun getAllPreviousMatchDateByType(leagueId: Int) {
        viewModel.getAllPreviousMatchDateByType(leagueId).observe(requireActivity()) { dates ->
            myCalendar(dates)
        }
    }

    private fun showAllFixture() {
        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.MATCHES
        binding.recyclerView.visibility = View.VISIBLE
        binding.recyclerViewByDate.visibility = View.GONE
    }

    private fun myCalendar(dateStringRaw: List<String>) {

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

        val builderRange = MaterialDatePicker.Builder.datePicker()
        builderRange.setCalendarConstraints(matchCalendarConstraints(dates).build()).setTheme(R.style.ThemeMaterialCalendar)
        builderRange.setTitleText("Select Date Range")
        val pickerRange = builderRange.build()
        pickerRange.show(requireActivity().supportFragmentManager, pickerRange.toString())
        pickerRange.addOnPositiveButtonClickListener { date ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val formattedDate = dateFormat.format(Date(date))
            Log.d(TAG, "myCalendar: $formattedDate")
            getPreviousMatchesByDate(selectedLeagueId, formattedDate.substring(0, 10))
            Log.d(TAG, "myCalendar: position button clicked")
        }
    }

    private fun matchCalendarConstraints(dates: List<Long>): CalendarConstraints.Builder {
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
                binding.recyclerView.visibility = View.GONE
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

    private fun setOptionMenuVisibility(visible: Boolean) {
        shouldCalenderVisible = visible
        requireActivity().invalidateOptionsMenu()
    }


}
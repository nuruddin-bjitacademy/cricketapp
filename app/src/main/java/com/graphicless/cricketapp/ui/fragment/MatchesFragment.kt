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

    private val viewModel: CricketViewModel by viewModels()

    private lateinit var calendar: MenuItem

    var tabSelected: String = "upcoming"
    var selectedLeagueId: Int = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setOptionMenuVisibility(true)
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
                        val tabPosition = getInt(MyConstants.CATEGORY_TAB_NUMBER)
                        val leagueNameList = listOf(
                            "IPL",
                            "ODI",
                            "T20I",
                            "BBL",
                            "SS",
                            "APL",
                            "PSL",
                            "BPL",
                            "ASIA CUP",
                            "WWCT20",
                            "WCT20",
                            "WC"
                        )
                        val leagueIdList = listOf(1, 2, 3, 5, 6, 7, 8, 9, 11, 16, 17, 18)
                        val selectedLeagueId = leagueIdList[tabPosition]
                        upcomingMatchSummaryByLeagueId(selectedLeagueId)
                    }
                }
                "recent" -> {
                    setOptionMenuVisibility(false)
                    val tabPosition = getInt(MyConstants.CATEGORY_TAB_NUMBER)
                    val leagueNameList = listOf(
                        "IPL",
                        "ODI",
                        "T20I",
                        "BBL",
                        "SS",
                        "APL",
                        "PSL",
                        "BPL",
                        "ASIA CUP",
                        "WWCT20",
                        "WCT20",
                        "WC"
                    )
                    val leagueIdList = listOf(1, 2, 3, 5, 6, 7, 8, 9, 11, 16, 17, 18)
                    val selectedLeagueId = leagueIdList[tabPosition]
                    recentMatchSummaryByLeagueId(selectedLeagueId)

                }
                "previous" -> {
                    setOptionMenuVisibility(true)
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        val tabPosition = getInt(MyConstants.CATEGORY_TAB_NUMBER)
                        val leagueNameList = listOf(
                            "IPL",
                            "ODI",
                            "T20I",
                            "BBL",
                            "SS",
                            "APL",
                            "PSL",
                            "BPL",
                            "ASIA CUP",
                            "WWCT20",
                            "WCT20",
                            "WC"
                        )
                        val leagueIdList = listOf(1, 2, 3, 5, 6, 7, 8, 9, 11, 16, 17, 18)
                        val selectedLeagueId = leagueIdList[tabPosition]
                        previousMatchSummaryByLeagueId(selectedLeagueId)
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
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }


    private fun showCalender(selectedLeagueId: Int) {
        getAllPreviousMatchDateByType(selectedLeagueId)
    }

    private fun getAllPreviousMatchDateByType(leagueId: Int) {
        viewModel.getAllPreviousMatchDateByType(leagueId).observe(requireActivity()) { dates ->
            myCalendar(dates)
        }
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
        builderRange.setCalendarConstraints(matchCalendarConstraints(dates).build())
            .setTheme(R.style.ThemeMaterialCalendar)
        builderRange.setTitleText("Select Date")
        val pickerRange = builderRange.build()
        pickerRange.show(requireActivity().supportFragmentManager, pickerRange.toString())
        pickerRange.addOnPositiveButtonClickListener { date ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val formattedDate = dateFormat.format(Date(date))
            getPreviousMatchesByDate(selectedLeagueId, formattedDate.substring(0, 10))
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

    private fun upcomingMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getUpcomingMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueId total: ${it.size}")
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueIds: $it")
            try {
                val adapter = StageAdapterForUpcoming(it, requireActivity(), MyApplication.instance)
                binding.recyclerView.adapter = adapter
                binding.progressbar.visibility = View.GONE
                binding.recyclerViewByDate.adapter = null
                if (binding.recyclerView.adapter?.itemCount == 0) {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            } catch (exception: Exception) {
                Log.e(TAG, "upcomingMatchSummaryByLeagueId: $exception")
            }
        }
    }

    private fun recentMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getRecentMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            try {
                val adapter = MatchAdapter(it, null, null, requireActivity())
                binding.recyclerView.adapter = null
                binding.recyclerViewByDate.adapter = adapter
                binding.progressbar.visibility = View.GONE
                if (binding.recyclerViewByDate.adapter?.itemCount == 0) {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            } catch (exception: Exception) {
                Log.e(TAG, "getRecentMatchSummaryByLeagueId: $exception")
            }
        }
    }

    private fun previousMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getPreviousMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "previousMatchSummaryByLeagueId total: ${it.size}")
            try {
                val adapter = StageAdapterForPrevious(it, requireActivity(), MyApplication.instance)
                binding.recyclerView.adapter = adapter
                binding.progressbar.visibility = View.GONE
                binding.recyclerViewByDate.adapter = null
                if (binding.recyclerView.adapter?.itemCount == 0) {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            } catch (exception: Exception) {
                Log.e(TAG, "previousMatchSummaryByLeagueId: $exception")
            }
        }
    }

    private fun setOptionMenuVisibility(visible: Boolean) {
        shouldCalenderVisible = visible
        requireActivity().invalidateOptionsMenu()
    }

}
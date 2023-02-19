package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayoutMediator
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.MatchAdapter
import com.graphicless.cricketapp.adapter.StageAdapterForPrevious
import com.graphicless.cricketapp.adapter.StageAdapterForUpcoming
import com.graphicless.cricketapp.adapter.ViewPagerAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchesBinding
import com.graphicless.cricketapp.utils.DateValidator
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "MatchesFragment"

class MatchesFragment : Fragment() {

    private lateinit var _binding: FragmentMatchesBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    lateinit var calendar: MenuItem

    var tabSelected: String = "upcoming"
    var typeSelected: Int = 3

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

        arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
            when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                /*0 -> {// BPL

                }
                1 -> {// IPL

                }
                2 -> {// BBL

                }
                3 -> {// ODI

                }
                4 -> {// T20I

                }*/




                0 -> {

                    tabSelected = "upcoming"
                    itemSelected("t20i")
                    upcomingMatchSummaryByLeagueId(3)
                    showAllFixture()

                    binding.tvBbc.setOnClickListener {
                        typeSelected = 5
                        itemSelected("bbc")
                        upcomingMatchSummaryByLeagueId(5)
                        showAllFixture()
                    }
                    binding.tvT20i.setOnClickListener {
                        typeSelected = 3
                        itemSelected("t20i")
                        upcomingMatchSummaryByLeagueId(3)
                        showAllFixture()
                    }
                    binding.tvT20c.setOnClickListener {
                        typeSelected = 10
                        itemSelected("t20c")
                        upcomingMatchSummaryByLeagueId(10)
                        showAllFixture()
                    }
                }
                1 -> {
                    tabSelected = "previous"
                    typeSelected = 3
                    itemSelected("t20i")
                    previousMatchSummaryByLeagueId(3)
                    showAllFixture()

                    binding.tvBbc.setOnClickListener {
                        typeSelected = 5
                        itemSelected("bbc")
                        previousMatchSummaryByLeagueId(5)
                        showAllFixture()
                    }
                    binding.tvT20i.setOnClickListener {
                        typeSelected = 3
                        itemSelected("t20i")
                        previousMatchSummaryByLeagueId(3)
                        showAllFixture()
                    }
                    binding.tvT20c.setOnClickListener {
                        typeSelected = 10
                        itemSelected("t20c")
                        previousMatchSummaryByLeagueId(10)
                        showAllFixture()
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
    }

    private fun showCalender(tabSelected: String, typeSelected: Int) {

        when (tabSelected) {
            "upcoming" -> {
                getAllUpcomingMatchDateByType(typeSelected)
            }
            "previous" -> {
                getAllPreviousMatchDateByType(typeSelected)
            }
        }


    }

    private fun showAllFixture(){
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
            getPreviousMatchesByDate(typeSelected, formattedDate.substring(0, 10))
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
            if(it != null){
                binding.recyclerView.visibility = View.GONE
                (activity as AppCompatActivity).supportActionBar?.title = startingAt
            }
            binding.recyclerViewByDate.visibility = View.VISIBLE
            val adapter = MatchAdapter(it, null, 0, requireActivity())
            binding.recyclerViewByDate.adapter = adapter
        }
    }

    private fun getAllPreviousMatchDateByType(leagueId: Int) {
        viewModel.getAllPreviousMatchDateByType(leagueId).observe(requireActivity()) {
            myCalendar(it)
            Log.d(TAG, "getAllPreviousMatchDateByType: $it")
        }
    }

    private fun getAllUpcomingMatchDateByType(leagueId: Int) {
        viewModel.getAllUpcomingMatchDateByType(leagueId).observe(requireActivity()) {
            myCalendar(it)
            Log.d(TAG, "getAllPreviousMatchDateByType: $it")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.calendar ->
//                // Not implemented here
//                return false
            calendar.itemId -> {
//                showCalender(tabSelected, typeSelected)
                showCalender(tabSelected, typeSelected)
//                getPreviousMatchesByDate(3, "2021-01-07")
                return true

            }
            else -> {}
        }
        return false
    }

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

    private fun upcomingMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getUpcomingMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueId total: ${it.size}")
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueIds: $it")
            val adapter = StageAdapterForUpcoming(it, requireActivity(), MyApplication.instance)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun previousMatchSummaryByLeagueId(leagueId: Int) {
        viewModel.getPreviousMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "previousMatchSummaryByLeagueId total: ${it.size}")
            val adapter = StageAdapterForPrevious(it, requireActivity(), MyApplication.instance)
            binding.recyclerView.adapter = adapter
        }
    }


}
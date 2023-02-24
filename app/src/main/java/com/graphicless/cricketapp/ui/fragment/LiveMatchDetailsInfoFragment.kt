package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.FragmentLiveMatchDetailsInfoBinding
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsInfoBinding
import com.graphicless.cricketapp.network.CricketApi
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.utils.Utils
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "LiveMatchDetailsInfoFragment"
class LiveMatchDetailsInfoFragment : Fragment() {


    private lateinit var _binding: FragmentLiveMatchDetailsInfoBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private val networkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLiveMatchDetailsInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {

            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

            networkConnectionViewModel.isNetworkAvailable.observe(requireActivity()){isNetworkAvailable ->
                if(isNetworkAvailable){
                    binding.tvNoData.visibility = View.GONE
                    viewModel.launchLiveMatchInfo(fixtureId)
                    viewModel.liveMatchInfo.observe(requireActivity()){
                        binding.series.text = it.data?.stage?.name ?: MyConstants.NOT_AVAILABLE
                        binding.match.text = it.data?.localteam?.code.plus(" vs ").plus(it.data?.visitorteam?.code)
                        binding.matchNo.text = it.data?.round
                        binding.localTeam.text = it.data?.localteam?.name ?: MyConstants.NOT_AVAILABLE
                        binding.visitorTeam.text = it.data?.visitorteam?.name ?: MyConstants.NOT_AVAILABLE
                        binding.status.text = it.data?.status ?: MyConstants.NOT_AVAILABLE
                        binding.toss.text = it.data?.tosswon?.name.plus(" chose ").plus(it.data?.elected).plus(" first.")
                        binding.stadium.text = it.data?.venue?.name ?: MyConstants.NOT_AVAILABLE
                        binding.location.text = it.data?.venue?.city ?: MyConstants.NOT_AVAILABLE
                        binding.capacity.text = it.data?.venue?.capacity.toString()
                        binding.floodLight.text = it.data?.venue?.floodlight.toString()
                        binding.firstUmpire.text = it.data?.firstumpire?.fullname ?: MyConstants.NOT_AVAILABLE
                        binding.secondUmpire.text = it.data?.secondumpire?.fullname?: MyConstants.NOT_AVAILABLE
                        binding.tvUmpire.text = it.data?.tvumpire?.fullname?: MyConstants.NOT_AVAILABLE
                        binding.referee.text = it.data?.referee?.fullname?: MyConstants.NOT_AVAILABLE
                    }
                }else{
                    Utils().networkUnavailable()
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }



        }

        /*var isNavigationVisible = true
        val HIDE_THRESHOLD = 20

        val scrollView = binding.root

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val view1 = scrollView.getChildAt(scrollView.childCount - 1) as View

            val diff = view1.bottom - (scrollView.height + scrollView.scrollY)

            if (diff <= 0 && isNavigationVisible) {
                // Scrolling up, hide the bottom navigation
                hideBottomNavigationWithAnimation()
                isNavigationVisible = false
            } else if (diff > HIDE_THRESHOLD && !isNavigationVisible) {
                // Scrolling down, show the bottom navigation
                activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_menu)?.animate()
                    ?.translationY(0f)
                    ?.setInterpolator(DecelerateInterpolator(2f))?.start()
                isNavigationVisible = true
            }
        }*/
    }
    private fun hideBottomNavigationWithAnimation() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_menu)?.animate()?.translationY(activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_menu)?.height!!.toFloat())
            ?.setInterpolator(AccelerateInterpolator(2f))?.start()
    }
}
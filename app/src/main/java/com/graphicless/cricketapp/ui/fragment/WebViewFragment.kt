package com.graphicless.cricketapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private lateinit var _binding: FragmentWebViewBinding
    private val binding get() = _binding

    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            loadUrl(args.url)
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu).visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu).visibility = View.VISIBLE
    }
}
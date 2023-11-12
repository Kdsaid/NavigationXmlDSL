package com.example.testnavigation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testnavigation.databinding.FragmentNotificationsBinding
import com.example.testnavigation.router.Routes.NavigationDetails.ARG_Details_NAME

class DetailsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        val name: String? = arguments?.getString(ARG_Details_NAME)
        textView.text = name
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
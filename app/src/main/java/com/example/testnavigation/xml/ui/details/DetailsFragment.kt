package com.example.testnavigation.xml.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testnavigation.databinding.FragmentNotificationsBinding
import com.example.testnavigation.routerxml.Destination.UserDetailsScreen.Companion.FIST_NAME_KEY
import com.example.testnavigation.routerxml.Destination.UserDetailsScreen.Companion.LAST_NAME_KEY

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
        val name: String? = arguments?.getString(FIST_NAME_KEY)
        val last: String? = arguments?.getString(LAST_NAME_KEY)
        textView.text =
            StringBuilder().append(name).append("  ").append("family").append("  ").append(last)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
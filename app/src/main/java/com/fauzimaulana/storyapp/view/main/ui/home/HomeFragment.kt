package com.fauzimaulana.storyapp.view.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fauzimaulana.storyapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupViewModel()
//    }

//    private fun setupViewModel() {
//        val factory = ViewModelFactory.getInstance(requireContext())
//        homeViewModel = ViewModelProvider(
//            requireActivity(),
//            factory
//        )[HomeViewModel::class.java]
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
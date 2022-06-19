package com.fauzimaulana.storyapp.view.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.ui.StoryAdapter
import com.fauzimaulana.storyapp.core.vo.Resource
import com.fauzimaulana.storyapp.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var storyAdapter: StoryAdapter
//    private lateinit var user: UserModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storyAdapter = StoryAdapter()
        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
//            this.user = user
            homeViewModel.getAllStories(user.token).observe(viewLifecycleOwner) { stories ->
                when(stories) {
                    is Resource.Loading -> binding.shimmerProgressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.shimmerProgressBar.visibility = View.GONE
                        storyAdapter.submitList(stories.data)
                    }
                    is Resource.Error -> {
                        binding.shimmerProgressBar.visibility = View.GONE
                        Toast.makeText(context, resources.getString(R.string.fetch_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        with(binding.rvStory) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = storyAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
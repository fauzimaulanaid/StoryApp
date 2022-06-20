package com.fauzimaulana.storyapp.view.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.ui.StoryAdapter
import com.fauzimaulana.storyapp.core.vo.Resource
import com.fauzimaulana.storyapp.databinding.FragmentHomeBinding
import com.fauzimaulana.storyapp.view.upload.UploadStoryActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var storyAdapter: StoryAdapter

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
        setHasOptionsMenu(true)
        storyAdapter = StoryAdapter()
        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            homeViewModel.getAllStories(user.token).observe(viewLifecycleOwner) { stories ->
                when(stories) {
                    is Resource.Loading -> binding.shimmerProgressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.shimmerProgressBar.visibility = View.GONE
                        storyAdapter.submitList(stories.data)
                    }
                    is Resource.Error -> {
                        binding.shimmerProgressBar.visibility = View.GONE
                        Toast.makeText(context, stories.message, Toast.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addStory -> {
                val intent = Intent(requireContext(), UploadStoryActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
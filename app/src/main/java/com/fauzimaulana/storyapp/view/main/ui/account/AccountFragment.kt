package com.fauzimaulana.storyapp.view.main.ui.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fauzimaulana.storyapp.databinding.FragmentAccountBinding
import com.fauzimaulana.storyapp.domain.model.UserPreference
import com.fauzimaulana.storyapp.view.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AccountFragment() : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        accountViewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[AccountViewModel::class.java]

        accountViewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.nameTextView.text = user.name
            binding.emailTextView.text = user.email
        }
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            accountViewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
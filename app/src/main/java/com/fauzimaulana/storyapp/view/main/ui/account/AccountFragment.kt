package com.fauzimaulana.storyapp.view.main.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fauzimaulana.storyapp.databinding.FragmentAccountBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AccountFragment() : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val accountViewModel: AccountViewModel by viewModel()

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
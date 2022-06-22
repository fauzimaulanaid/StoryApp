package com.fauzimaulana.storyapp.view.main.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.databinding.FragmentAccountBinding
import com.fauzimaulana.storyapp.view.preference.PreferenceFragment
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
        requireActivity().supportFragmentManager.beginTransaction().add(R.id.preferenceLanguage, PreferenceFragment()).commit()
    }

    private fun setupViewModel() {
        accountViewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.nameTextView.text = user.name
            binding.emailTextView.text = user.email
        }
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            showAlertLogout()
        }
    }

    private fun showAlertLogout() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        with(alertDialogBuilder) {
            setTitle(resources.getString(R.string.alert))
            setMessage(resources.getString(R.string.logout_confirmation))
            setCancelable(false)
            setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                accountViewModel.logout()
            }
            setNegativeButton(resources.getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
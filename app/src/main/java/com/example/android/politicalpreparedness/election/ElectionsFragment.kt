package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment: Fragment() {

    private val viewModel: ElectionsViewModel by lazy {
        val viewModelFactory = ElectionsViewModelFactory(requireContext())
        ViewModelProvider(this, viewModelFactory)
            .get(ElectionsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.electionsViewModel = viewModel

        val upcomingAdapter = ElectionListAdapter(ElectionListener { election ->
            viewModel.navigateToVoterInformation(election)
        })
        binding.upcomingElectionsRv.adapter = upcomingAdapter
        viewModel.upcomingElections.observe(viewLifecycleOwner) { upcomingElections ->
            upcomingElections.apply {
                upcomingAdapter.elections = upcomingElections
            }
        }

        val savedAdapter = ElectionListAdapter(ElectionListener {
            viewModel.navigateToVoterInformation(it)
        })
        binding.savedElectionsRv.adapter = savedAdapter
        viewModel.savedElections.observe(viewLifecycleOwner) { savedElections ->
            savedElections.apply {
                savedAdapter.elections = savedElections
            }
        }

        viewModel.navigateToVoterInformation.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it.id, it.isSaved, it.division))
                viewModel.navigateToVoterInformationCompleted()
            }
        })
        return binding.root
    }

}
package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private val viewModel: VoterInfoViewModel by lazy {
        val viewModelFactory = VoterInfoViewModelFactory(requireContext())
        ViewModelProvider(this, viewModelFactory)
            .get(VoterInfoViewModel::class.java)
    }

    private val args: VoterInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel.getVoterInfo(args.argDivision, args.argElectionId, args.argIsSaved)

        viewModel.election.observe(viewLifecycleOwner, {
            binding.saveElectionBtn.text =
                if (it.isSaved) getText(R.string.unfollow_election) else getText(R.string.follow_election)
        })

        viewModel.url.observe(viewLifecycleOwner, {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        })

        binding.voterViewModel = viewModel

        return binding.root
    }
}
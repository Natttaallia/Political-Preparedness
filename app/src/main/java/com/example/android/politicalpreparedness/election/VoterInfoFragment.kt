package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private val viewModel: VoterInfoViewModel by lazy {
        val viewModelFactory = VoterInfoViewModelFactory(requireNotNull(this.activity).application)
        ViewModelProvider(this, viewModelFactory)
            .get(VoterInfoViewModel::class.java)
    }

    private val args: VoterInfoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.voterViewModel = viewModel

        viewModel.getVoterInfo(args.argDivision.country, args.argElectionId)

        viewModel.url.observe(viewLifecycleOwner, Observer {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        })

        return binding.root

        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

    }
}
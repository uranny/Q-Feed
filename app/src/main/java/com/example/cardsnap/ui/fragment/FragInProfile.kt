package com.example.cardsnap.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.databinding.FrameInProfileBinding
import com.example.cardsnap.ui.vm.HomeViewModel
import com.example.cardsnap.ui.vm.InProfileViewModel

class FragInProfile : Fragment() {

    private lateinit var binding: FrameInProfileBinding
    private val homeViewModel : HomeViewModel by activityViewModels()
    private lateinit var profileViewModel : InProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frame_in_profile, container, false)
        binding.viewModel = profileViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun/*fun : 재밌다*/ onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

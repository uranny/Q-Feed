package com.example.cardsnap.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.databinding.FrameInProfileBinding
import com.example.cardsnap.presentation.vm.InProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragInProfile : Fragment() {

    private lateinit var binding: FrameInProfileBinding
    private val profileViewModel : InProfileViewModel by viewModels()

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

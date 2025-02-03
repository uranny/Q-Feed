package com.example.cardsnap.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.databinding.FrameInProfileBinding
import com.example.cardsnap.databinding.FrameSystemBinding

class FragOther : Fragment() {

    private lateinit var binding: FrameInProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrameInProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun/*fun : 재밌다*/ onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        setFrag()
    }

    private fun setFrag() {
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.otherProfile, FragSystem()).commit()
    }
}

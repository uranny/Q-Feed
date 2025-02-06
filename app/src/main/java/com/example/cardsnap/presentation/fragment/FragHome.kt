package com.example.cardsnap.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.R
import com.example.cardsnap.presentation.adapter.PostAdapter
import com.example.cardsnap.domain.entity.item.Post
import com.example.cardsnap.data.source.user.UserInfo
import com.example.cardsnap.databinding.FrameHomeBinding
import com.example.cardsnap.presentation.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragHome : androidx.fragment.app.Fragment() {

    private lateinit var postAdapter: PostAdapter
    private lateinit var binding: FrameHomeBinding
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frame_home, container, false)

        val postView = binding.recycleView
        val navController = findNavController()
        Log.d("GetArticle", "getArticle : ${UserInfo.postLst}")

        postAdapter = PostAdapter(
            UserInfo.postLst,
            onViewClicked = {
                navController.navigate(R.id.action_fragHome_to_fragInProfile)
            },
            onChatClicked = { postItem ->
                showBottomSheet(postItem)
            }
        )

        postView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        postView.adapter = postAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(postView)

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getArticlesSuccess.collectLatest { _ ->
                    postAdapter.updateData(UserInfo.postLst)
                    Log.d("GetArticle", "FragHome : ${UserInfo.postLst.size}")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (lastVisibleItemPosition == UserInfo.postLst.size - 3) {
                    viewModel.getArticles(UserInfo.accessToken!!)
                }
            }
        })
    }

    private fun showBottomSheet(postItem : Post) {
        val bottomSheet = CmtBottomSheet(postItem)
        bottomSheet.show(childFragmentManager, "Tag")
    }
}

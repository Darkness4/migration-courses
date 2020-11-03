package com.ismin.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismin.android.databinding.FragmentBookListBinding
import com.ismin.android.ui.adapters.BookAdapter
import com.ismin.android.ui.viewmodels.MainViewModel

class BookListFragment : Fragment() {
    private val activityViewModel by viewModels<MainViewModel> (
        ownerProducer = { requireActivity() }
    )
    private lateinit var binding: FragmentBookListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        binding.viewModel = activityViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        activityViewModel.goToCreation.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    findNavController().navigate(
                        BookListFragmentDirections.actionBookListFragmentToCreateBookFragment()
                    )
                    activityViewModel.goToCreationDone()
                }
            }
        )

        binding.aMainRcvBooks.adapter = BookAdapter()
        val linearLayoutManager = LinearLayoutManager(context)
        binding.aMainRcvBooks.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        binding.aMainRcvBooks.addItemDecoration(dividerItemDecoration)
        return binding.root
    }
}

package com.ismin.android.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ismin.android.databinding.FragmentCreateBookBinding
import com.ismin.android.ui.viewmodels.CreateBookViewModel
import com.ismin.android.ui.viewmodels.MainViewModel
import org.joda.time.DateTime

class CreateBookFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateBookBinding
    private val viewModel by viewModels<CreateBookViewModel>()
    private val activityViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBookBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.saveBook.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    viewModel.validate()
                    if (viewModel.isValid) {
                        viewModel.toBook()?.let { book ->
                            activityViewModel.addBook(book)
                            closeKeyBoard()
                            findNavController().popBackStack()
                        }
                        viewModel.saveBookDone()
                    } else {
                        viewModel.showValidationError()
                    }
                }
            }
        )

        viewModel.showDatePicker.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    val date = viewModel.date.value!!
                    val dialog = DatePickerDialog(
                        requireContext(),
                        { _, year, monthOfYear, dayOfMonth ->
                            viewModel.date.value =
                                DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0)
                        },
                        date.year,
                        date.monthOfYear - 1,
                        date.dayOfMonth
                    )
                    dialog.show()
                    viewModel.showDatePickerDone()
                }
            }
        )

        viewModel.title.observe(viewLifecycleOwner) { viewModel.validateTitle() }
        viewModel.titleError.observe(viewLifecycleOwner) {
            binding.aCreateBookEdtTitle.error = if (it) "Shouldn't be empty" else null
        }

        viewModel.author.observe(viewLifecycleOwner) { viewModel.validateAuthor() }
        viewModel.authorError.observe(viewLifecycleOwner) {
            binding.aCreateBookEdtAuthor.error = if (it) "Shouldn't be empty" else null
        }

        viewModel.showValidationError.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(
                    context,
                    "There are some errors.",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.showValidationErrorDone()
            }
        }

        // Fire validate at least once
        viewModel.validate()
        return binding.root
    }

    private fun closeKeyBoard() {
        activity?.currentFocus?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}

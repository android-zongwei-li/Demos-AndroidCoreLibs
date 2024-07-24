package com.lizw.core_apis.jetpack.livedata_viewmodel.sample_same

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lizw.core_apis.R

class LeftFragment : Fragment() {
    private val viewModel: BlankViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_left, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val leftButton: Button = view.findViewById(R.id.left_button)
        val leftText: Button = view.findViewById(R.id.left_text)
        leftButton.setOnClickListener {
            viewModel.addOne()
        }
        activity?.let { it ->
            viewModel.getLiveData().observe(it) {
                leftText.text = it.toString()
            }
        }
    }
}
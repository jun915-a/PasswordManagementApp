package com.example.passwordmanagementapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passwordmanagementapp.adapter.ItemAdapter
import com.example.passwordmanagementapp.adapter.ViewModel.MainViewModel
import com.example.passwordmanagementapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            recyclerView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {
                    binding.motionLayout.transitionToEnd()
                } else if (scrollY < oldScrollY) {
                    binding.motionLayout.transitionToStart()
                }
            }

            recyclerView.layoutManager = layoutManager
            addItemButton.setOnClickListener {
                viewModel.list.add("a")
                val adapter = ItemAdapter(viewModel.list, requireContext()) {
                    //クリック処理　エディットテキスト入力
                    Log.d("test_log", "test_log")

                }
                recyclerView.adapter = adapter
            }

            //他のViewタップでhideKeyBord
            motionLayout.setOnClickListener {
                addItemButton.requestFocus()
                context?.let { context -> viewModel.hideKeyboard(addItemButton, context) }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

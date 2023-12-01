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
import com.example.passwordmanagementapp.model.ItemDataModel
import timber.log.Timber

class MainFragment : Fragment() {
    companion object {
        const val MAX_ITEM_VALUE = 30
    }

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
        binding.recyclerView.layoutManager = layoutManager

        binding.apply {
            //sheredの値を参照して、アプリ起動時にアダプター生成する
            viewModel.getSharedPreferences(requireContext())
            if (viewModel.itemList.isNotEmpty()) {
                val adapter = ItemAdapter(
                    viewModel.itemList.size + 1,
                    viewModel.itemList,
                    requireContext()
                ) {}
                binding.recyclerView.adapter = adapter
            }
            recyclerView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {
                    binding.motionLayout.transitionToEnd()
                } else if (scrollY < oldScrollY) {
                    binding.motionLayout.transitionToStart()
                }
            }

            addItemButton.setOnClickListener {
                val adapter = if (viewModel.itemList.isEmpty()) {
                    Log.d("!!!","${viewModel.itemList.size}")
                    ItemAdapter(1, mutableListOf(), requireContext()) {}
                } else {
                    Log.d("!!!!!!","${viewModel.itemList.size}")
//                    ItemAdapter(viewModel.itemList.size + 1, requireContext())
                    ItemAdapter(viewModel.itemList.size + 1, viewModel.itemList, requireContext()) {
                        //クリック処理　エディットテキスト入力
                    }
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

package com.example.passwordmanagementapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passwordmanagementapp.adapter.ItemAdapter
import com.example.passwordmanagementapp.adapter.ViewModel.MainViewModel
import com.example.passwordmanagementapp.databinding.FragmentMainBinding
import com.example.passwordmanagementapp.model.ItemDataModel

class MainFragment : Fragment() {
    companion object {
        const val MAX_ITEM_VALUE = 30
        var availableItemFlag = true
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
//
//        viewModel.adapterList.observe(viewLifecycleOwner) {
//            Log.d("test_log ", "${it}")
//        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        binding.apply {
            //sheredの値を参照して、アプリ起動時にアダプター生成する
            val savedListItem = viewModel.getSharedPreferences(requireContext())
            if (savedListItem.isNotEmpty()) {
                val adapter = ItemAdapter(
                    savedListItem.size + 1,
                    savedListItem as ArrayList,
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
                Toast.makeText(
                    context,
                    "${viewModel.getSharedPreferences(requireContext()).size}",
                    Toast.LENGTH_SHORT
                ).show()
                if (!availableItemFlag) {
                    Toast.makeText(context, "現在のアイテムを確定していません。", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "itemList ${viewModel.firstAdapterItemList.size}。",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (viewModel.getSharedPreferences(requireContext()).size == 0) {
                        availableItemFlag = false

                        val adapter = ItemAdapter(1, arrayListOf(), requireContext()) {}
                        recyclerView.adapter = adapter

                    } else {
                        availableItemFlag = false
                        val itemAdapterList = viewModel.getSharedPreferences(requireContext())
                        val adapter = ItemAdapter(
                            itemAdapterList.size + 1,
                            itemAdapterList as ArrayList<ItemDataModel>,
                            requireContext()
                        ) {
                            //クリック処理　エディットテキスト入力
                        }
                        recyclerView.adapter = adapter
                    }
                }
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

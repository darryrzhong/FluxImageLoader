package com.flux.loader.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flux.loader.ImgData
import com.flux.loader.databinding.FragmentHomeBinding
import com.flux.loader.dp


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = ImgLoadAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initLoad()
        return root
    }

    private fun initLoad() {
        binding.rvImg.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvImg.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = 12f.dp
                outRect.left = 12f.dp
                outRect.right = 12f.dp
                outRect.bottom = 12f.dp
            }
        });
        binding.rvImg.adapter = adapter
        adapter.submitList(
            arrayListOf(
                ImgData(0, "常规"),
                ImgData(1, "圆角"),
                ImgData(2, "圆形"),
                ImgData(3, "高斯模糊"),
                ImgData(4, "高斯模糊"),
                ImgData(5, "高斯模糊"),
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
    }
}
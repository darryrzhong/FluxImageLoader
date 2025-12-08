package com.flux.loader.ui.other;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flux.loader.ExtKt;
import com.flux.loader.ImgData;
import com.flux.loader.databinding.FragmentOtherBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     类描述  :
 *
 *     @author : never
 *     @since  : 2025/12/8
 * </pre>
 */
public class OtherFragment extends Fragment {

    private FragmentOtherBinding binding;

    private ImgLoadJavaAdapter adapter = new ImgLoadJavaAdapter();

    public OtherFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentOtherBinding.inflate(inflater, container, false);
        initLoad();
        return binding.getRoot();
    }

    private void initLoad() {
        binding.rvImg.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        binding.rvImg.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ExtKt.getDp(12);
                outRect.left =ExtKt.getDp(12);
                outRect.right = ExtKt.getDp(12);
                outRect.bottom = ExtKt.getDp(12);
            }
        });
        binding.rvImg.setAdapter(adapter);
        List<ImgData> imgs = new ArrayList<>();
        imgs.add(new ImgData(0, "常规"));
        imgs.add(new ImgData(1, "圆角"));
        imgs.add(new ImgData(2, "圆形"));
        imgs.add(new ImgData(3, "高斯模糊"));
        imgs.add(new ImgData(4, "高斯模糊"));
        imgs.add(new ImgData(5, "高斯模糊"));
        imgs.add(new ImgData(6, "webp"));
        imgs.add(new ImgData(7, "gif"));
        imgs.add(new ImgData(8, "video_frame"));
        adapter.submitList(new ArrayList<ImgData>(imgs));


    }

    @Override
    public void onResume() {
        super.onResume();
        // Your logic here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // prevent memory leak
    }
}

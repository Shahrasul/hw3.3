package com.example.hw33.ui.fragments.post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw33.R;
import com.example.hw33.data.models.Post;
import com.example.hw33.data.network.RetrofitService;
import com.example.hw33.databinding.FragmentPostBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment implements PostAdapter.OnItemClicks {


    private static final String ARG_PARAM1 = "userNum";
    private static final String ARG_PARAM2 = "param2";

    private Integer mParam1;
    private String mParam2;

    private PostAdapter adapter;
    private NavController navController;
    private FragmentPostBinding binding;

    public PostFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
        LinearLayoutManager lnm = new LinearLayoutManager(getContext());
        binding.postsRecycler.setLayoutManager(lnm);
        List<Post> list = setPostList();
        adapter = new PostAdapter(list,this);
        binding.postsRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddFragment();
            }
        });
    }

    private void openAddFragment() {
        navController.navigate(R.id.action_postFragment2_to_addFragment2);
    }

    private List<Post> setPostList() {
        List<Post> postList = new ArrayList<>();
        RetrofitService.getInstance().getUserPosts(mParam1).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null)
                    postList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
        return postList;
    }

    @Override
    public void onItemClick(int pos) {
        Bundle b = new Bundle();
        b.putInt("postId", adapter.getItem(pos).getId());
        navController.navigate(R.id.action_postFragment2_to_editFragment, b);
    }

    @Override
    public void onItemLongClick(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Удаление");
        builder.setMessage("Удалить пост?");
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        RetrofitService.getInstance().deletePost(adapter.getItem(pos).getId()).enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> call, Response<Post> response) {
                            }

                            @Override
                            public void onFailure(Call<Post> call, Throwable t) {
                            }

                        });
                        adapter.removeItem(pos);
                    }
                });
        builder.show();
    }
}
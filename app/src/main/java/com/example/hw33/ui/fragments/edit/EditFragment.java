package com.example.hw33.ui.fragments.edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw33.R;
import com.example.hw33.data.models.Post;
import com.example.hw33.data.network.RetrofitService;
import com.example.hw33.databinding.FragmentEditBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Integer mParam1;
    private FragmentEditBinding binding;


    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt("postId");
        }else
            Log.e("tag", "lol");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmEdits();
            }
        });
    }

    private void confirmEdits() {
        Post post = new Post();
        post.setUser(Integer.valueOf(binding.edtUser.getText().toString()));
        post.setGroup(Integer.valueOf(binding.edtGroup.getText().toString()));
        post.setTitle(binding.edtTitle.getText().toString());
        post.setContent(binding.edtContent.getText().toString());
        RetrofitService.getInstance().updatePost(mParam1, post.getTitle(), post.getContent(), post.getUser(), post.getGroup()).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    navController.navigateUp();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void initViews(View view) {
        RetrofitService.getInstance().getPost(mParam1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null){
                    Post post = response.body();
                    binding.edtTitle.setText(post.getTitle());
                    binding.edtContent.setText(post.getContent());
                    binding.edtUser.setText(String.valueOf(post.getUser()));
                    binding.edtGroup.setText(String.valueOf(post.getGroup()));
                    Log.e("tag", "getDetails");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

}
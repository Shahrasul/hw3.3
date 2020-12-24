package com.example.hw33.ui.fragments.add;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw33.R;
import com.example.hw33.data.models.Post;
import com.example.hw33.data.network.RetrofitService;
import com.example.hw33.databinding.FragmentAddBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Post post;
    private NavController navController;
    private FragmentAddBinding binding;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater,container,false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPost();
                navController = Navigation.findNavController(requireActivity(),R.id.nav_host);
                navController.navigateUp();
            }
        });
    }

    private void addNewPost() {
        post = new Post();
        post.setTitle(binding.addTitle.getText().toString());
        post.setContent(binding.addContent.getText().toString());
        post.setGroup(Integer.valueOf(binding.addGroup.getText().toString()));
        post.setUser(Integer.valueOf(binding.addUser.getText().toString()));
        RetrofitService.getInstance().newPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }
}
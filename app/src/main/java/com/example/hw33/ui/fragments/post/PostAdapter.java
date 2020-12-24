package com.example.hw33.ui.fragments.post;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw33.R;
import com.example.hw33.data.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private OnItemClicks onClicks;


    public PostAdapter(List<Post> postList, OnItemClicks onClicks) {
        this.postList = postList;
        this.onClicks = onClicks;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public Post getItem(int pos){
        return postList.get(pos);
    }

    public void removeItem(int pos) {
        postList.remove(pos);
        notifyDataSetChanged();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView postTitle;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicks.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClicks.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });

            postTitle = itemView.findViewById(R.id.post_title);

        }
        public void bind(Post post) {
            postTitle.setText("post: "+post.getTitle());
        }
    }

    public interface OnItemClicks{
        void onItemClick(int pos);
        void onItemLongClick(int pos);
    }
}

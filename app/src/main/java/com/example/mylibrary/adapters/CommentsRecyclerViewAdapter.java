package com.example.mylibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylibrary.R;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.Comment;
import com.example.mylibrary.model.User;

import java.util.ArrayList;
import java.util.List;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.ViewHolder> {
    private List<Comment> commentList = new ArrayList<>();
    private List<User> allUsersList = new ArrayList<>();
    private Book currentBook;
    private final LayoutInflater inflater;


    public CommentsRecyclerViewAdapter(Context context, List<Comment> commentList, List<User> allUsersList) {
        this.inflater = LayoutInflater.from(context);
        this.commentList = commentList;
        this.allUsersList = allUsersList;
    }


    @NonNull
    @Override
    public CommentsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comments_recycler_view_item, parent, false);
        return new CommentsRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        for(int i=0; i<allUsersList.size(); i++){
            if(commentList.get(position).getIDNameOfUser() == allUsersList.get(i).getId()){
                holder.nameTextView.setText(allUsersList.get(i).getUserName());
                break;
            }
        }
        
        holder.commentTextView.setText(commentList.get(position).getCreatedComments());

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView commentTextView;


        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.userNameTextView);
            commentTextView = view.findViewById(R.id.commentTextView);

        }

    }

    ;
}


package com.example.ejtabacco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MedalData> medalDatas;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle;
        TextView mTextContent;
        ImageView crownImage;

        ViewHolder(View v) {
            super(v);
            mTextTitle = (TextView) v.findViewById(R.id.text_title);
            mTextContent = (TextView) v.findViewById(R.id.text_content);
            crownImage = (ImageView) v.findViewById(R.id.text_image);

        }
    }

    MyAdapter(List<MedalData> medalDatas) {
        this.medalDatas = medalDatas;
    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (medalDatas.get(position).done == 1) {
            // 条件をクリアしていた時
            holder.mTextTitle.setText(medalDatas.get(position).title);
            holder.mTextContent.setText(medalDatas.get(position).content);
            holder.crownImage.setImageResource(R.drawable.crown);
        } else {
            // 条件をクリアしていない時
            holder.mTextTitle.setText("未取得");
            holder.mTextContent.setText(medalDatas.get(position).content);
            holder.crownImage.setImageResource(R.drawable.lock_crown);
        }

    }

    @Override
    public int getItemCount() {
        //return dataTitle.length;
        return medalDatas.size();
    }
}
package com.example.niubilityapp.live_TV;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.niubilityapp.R;

import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.VideoHolder> {

    private final Activity context;
    private List<VideoBean> videos;


    public LiveAdapter(List<VideoBean> videos, Activity activity) {
        this.videos = videos;
        this.context = activity;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_layout, parent, false);
        return new VideoHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, int position) {
        VideoBean videoBean = videos.get(position);
        holder.textView.setText(videoBean.getTitle());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LivePlayerActivity.class);
            intent.putExtra(IntentKeys.URL, videoBean.getUrl());
            if (videoBean.getTitle().startsWith("点播")){
                intent.putExtra(IntentKeys.IS_LIVE, false);
                intent.putExtra(IntentKeys.TITLE, "点播");
            }else {
                intent.putExtra(IntentKeys.IS_LIVE, true);
                intent.putExtra(IntentKeys.TITLE, "直播");
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        VideoHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_live);
        }
    }
}
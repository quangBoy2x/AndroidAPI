package com.example.apibtl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class YoutubeAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<VideoYoutube> videoYoutubes;

    public YoutubeAdapter(Context context, int layout, List<VideoYoutube> videoYoutubes) {
        this.context = context;
        this.layout = layout;
        this.videoYoutubes = videoYoutubes;
    }

    @Override
    public int getCount() {
        return videoYoutubes.size();
    }

    @Override
    public Object getItem(int position) {
        return videoYoutubes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitleVid);
            viewHolder.imgVid = (ImageView) convertView.findViewById(R.id.imgVid);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder =(ViewHolder) convertView.getTag();
        }


        VideoYoutube videoYoutube = videoYoutubes.get(position);
        viewHolder.tvTitle.setText(videoYoutube.getTitle());
        Picasso.with(context).load(videoYoutube.getThumbnail()).into(viewHolder.imgVid);

        return convertView;
    }

    class ViewHolder{
        ImageView imgVid;
        TextView tvTitle;
    }


}

package com.example.adriaalvarez.android_boilerplate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adriaalvarez.android_boilerplate.R;
import com.example.adriaalvarez.android_boilerplate.activities.ImageActivity;
import com.example.adriaalvarez.android_boilerplate.models.Image;
import com.example.adriaalvarez.android_boilerplate.services.ImageService;

import java.util.ArrayList;

/**
 * Created by adria.alvarez on 08/06/2017.
 */

public class GridViewAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Image> data = new ArrayList();

    private ImageService imageService;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

        this.imageService = new ImageService();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.tags = (TextView) row.findViewById(R.id.tags);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Image item = data.get(position);

        String tags = item.getTags();
        if (!tags.isEmpty()) {
            holder.tags.setText(item.getTags());
            holder.tags.setVisibility(View.VISIBLE);
        } else {
            holder.tags.setVisibility(View.INVISIBLE);
        }

        imageService.downloadImage(item.getUrl(), holder.image);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("imageId", item.getId());
                context.startActivity(intent);
            }
        });
        return row;
    }

    static class ViewHolder {
        TextView tags;
        ImageView image;
    }
}
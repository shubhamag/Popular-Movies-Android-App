package com.example.shubhama.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubhama on 12/21/2015.
 */
public class PosterAdapter extends ArrayAdapter<MovieData>{
    private int mResource;

    private int mFieldId = 0;

    private Context mContext;


    public PosterAdapter(Context context,  ArrayList<MovieData> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieData mTemp = getItem(position);
        LayoutInflater inflater= (LayoutInflater)getContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.movieposter_layout
            ,parent,false);
        }
        ImageView posterView  = (ImageView)convertView.findViewById(R.id.moviesItem);
//        posterView.setImageResource(posterint);
        Picasso.with(getContext()).load(mTemp.getPosterpathSmall()).into(posterView);

        return convertView;

    }
}

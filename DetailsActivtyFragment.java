package com.example.shubhama.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivtyFragment extends Fragment {

    public DetailsActivtyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent parentIntent = getActivity().getIntent();
        View rootFragmentView = inflater.inflate(R.layout.fragment_details_activty, container, false);
        if(!(parentIntent==null) && parentIntent.hasExtra("TITLE")) {
            String Title = parentIntent.getStringExtra("TITLE");
            String posterPath = parentIntent.getStringExtra("POSTER_PATH");
            String releaseDate = parentIntent.getStringExtra("RELEASE_DATE");
            String description = parentIntent.getStringExtra("PLOT_SYNOPSIS");
            Double voteavg = parentIntent.getDoubleExtra("VOTE_AVERAGE",5.0);


            TextView titletextv = (TextView) rootFragmentView.findViewById(R.id.detailtitle);
            ImageView iv  = (ImageView) rootFragmentView.findViewById(R.id.detailimageView);
            TextView releasetextv = (TextView) rootFragmentView.findViewById(R.id.detailRelease);
            TextView voteTextv = (TextView) rootFragmentView.findViewById(R.id.detailVoteAvg);
            TextView descriptionV = (TextView) rootFragmentView.findViewById(R.id.detailSynopsis);


            titletextv.setText(Title);
            Picasso.with(getActivity()).load(posterPath).into(iv);
            releasetextv.setText("Release :" + releaseDate);
            voteTextv.setText("Votes Avg : " + Double.toString(voteavg));
            descriptionV.setText(description);
        }
        return rootFragmentView;
    }
}


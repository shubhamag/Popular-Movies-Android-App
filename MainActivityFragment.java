package com.example.shubhama.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    protected PosterAdapter mPosterAdapter;



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragment_main, container, false);
//        MovieDataFetcher mFetcher = new MovieDataFetcher();
//        mFetcher.execute("popularity.desc");
        GridView posterGrid = (GridView) rootview.findViewById(R.id.maingridview);
        ArrayList<MovieData> firstList = new ArrayList<MovieData>();
        final MovieData m1 = new MovieData("m1","first movie","2012-3",123l,2.1,2.3);
        m1.setPosterpath("/kqjL17yufvn9OVLyXYpvtyrFfak.jpg");
        MovieData m2 = new MovieData("m2","first movie","2012-3",123l,2.1,2.3);
        m2.setPosterpath("/5aGhaIHYuQbqlHWvWYqMCnj40y2.jpg");
        MovieData m3 = new MovieData("m3","first movie good movie dummy synopys test ","2012-3",123l,2.1,2.3);
        m3.setPosterpath("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        MovieData m4 = new MovieData("m4","first movie","2012-3",123l,2.1,2.3);
        m4.setPosterpath("/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg");
        firstList.add(m1);
        firstList.add(m2);
        firstList.add(m3);
        firstList.add(m4);

//        ArrayList<Integer> drawables =  new ArrayList<> (Arrays.asList(drawableIds));
        mPosterAdapter = new PosterAdapter(getActivity(),firstList);
        posterGrid.setAdapter(mPosterAdapter);
        posterGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               MovieData mClicked = (MovieData) mPosterAdapter.getItem(position);
//                String dayWeatherDataString = (String)(listView.getItemAtPosition(position));


                Intent downloadIntent = new Intent(getActivity(), DetailsActivty.class);
                downloadIntent.putExtra("TITLE", mClicked.getTitle());
                downloadIntent.putExtra("POSTER_PATH", mClicked.getPosterPathLarge());
                downloadIntent.putExtra("RELEASE_DATE", mClicked.getReleaseDate());
                downloadIntent.putExtra("VOTE_AVERAGE", mClicked.getVoteAverage());
                downloadIntent.putExtra("PLOT_SYNOPSIS", mClicked.getSynopsis());

                startActivity(downloadIntent);
            }

        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
        String s = prefs.getString(getString(R.string.sort_pref_key), getString(R.string.pref_sort_default));

//            String s = "vote_average.desc";
        Log.i("FETCHER", "calling FETCHER");
        new MovieDataFetcher().execute(s);
        return rootview;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movierefreshmenu, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
        String s = prefs.getString(getString(R.string.sort_pref_key),getString(R.string.pref_sort_default));

//            String s = "vote_average.desc";
        Log.i("FETCHER","calling FETCHER");
        new MovieDataFetcher().execute(s);
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            Context context = getActivity();


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
            String s = prefs.getString(getString(R.string.sort_pref_key),getString(R.string.pref_sort_default));

//            String s = "vote_average.desc";
            Log.i("FETCHER","calling FETCHER");
            new MovieDataFetcher().execute(s);
            return true;
        }
        if(id == R.id.action_about){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getActivity(), "Popular movies By Shubham Agrawal", duration);
            toast.show();
        }

        return super.onOptionsItemSelected(item);


    }

    public class MovieDataFetcher extends AsyncTask< String, Void, ArrayList<MovieData> > {

        private final String FETCHER_LOGTAG = MovieDataFetcher.class.getSimpleName();

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> fetchedMovies) {

            if(fetchedMovies!=null){
                Log.v(FETCHER_LOGTAG,"not null results");
//                List<String> forecastData = new ArrayList <String> ( Arrays.asList(resultStrs));
//                forecastListAdapter  = new ArrayAdapter <String>(getActivity(), R.layout.list_item_forecast,R.id.list_item_forecast_textview,forecastData );
                    mPosterAdapter.clear();
                    mPosterAdapter.addAll(fetchedMovies);

                for (MovieData m : fetchedMovies) {
                    Log.v(FETCHER_LOGTAG, "Movie entry: " + m.getTitle() + "\nOverview" + m.getSynopsis() + "\nPoster" + m.getPosterpathSmall());
                }
            }
        }


        @Override
        protected ArrayList<MovieData> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Log.e(FETCHER_LOGTAG,"entered runinbackground");
            String appid = getString(R.string.api_key);
            final String FORECAST_BASE_URL  = "https://api.themoviedb.org/3/discover/movie?";

            final String SORT_PARAM = "sort_by";
            final String APPID_PARAM = "api_key";
            String MoviesJsonStr = null;


            try {


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM,params[0])
                        .appendQueryParameter(APPID_PARAM,appid)

                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(FETCHER_LOGTAG,"URI : " + builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
//
//            // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null) {
//                // Nothing to do.
                    return null;
                }
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    Log.e(FETCHER_LOGTAG, "Empty buffer");
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                MoviesJsonStr = buffer.toString();
                Log.v(FETCHER_LOGTAG,MoviesJsonStr);
            } catch (IOException e) {
                Log.e(FETCHER_LOGTAG, "IO exception Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;

            }
            finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(FETCHER_LOGTAG, "Error closing stream", e);
                    }
                }
            }
//            Log.v(FETCHER_LOGTAG, forecastJsonStr);


            try {
                return getMovieDataFromJson(MoviesJsonStr);
            } catch (JSONException e) {
                Log.e(FETCHER_LOGTAG,"couldnt parse json " + e.getMessage(),e);
                e.printStackTrace();
            }
            return null;
        }
    }

    private ArrayList<MovieData> getMovieDataFromJson(String movieJson)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String J_POSTER = "poster_path";
        final String J_TITLE = "original_title";
        final String J_SYNOPSIS = "overview";
        final String J_RELEASE = "release_date";
        final String J_POP  = "popularity";
        final String J_RATING = "vote_average";
        final String J_ID = "id";

        JSONObject movieJsonObject = new JSONObject(movieJson);
        JSONArray moviesArray = movieJsonObject.getJSONArray("results");


        ArrayList<MovieData> movieDataArrayList = new ArrayList<MovieData>();

        for(int i = 0; i < moviesArray.length() && i<8; i++) {
            // For now, using the format "Day, description, hi/low"
            String title;
            String description;
            String releaseDate;
            Long id;
            Double voteAverage;
            Double popularity;
            String posterPath;



            JSONObject movieData = moviesArray.getJSONObject(i);
            title = movieData.getString(J_TITLE);
            description = movieData.getString(J_SYNOPSIS);
            releaseDate = movieData.getString(J_RELEASE);
            popularity = movieData.getDouble(J_POP);
            voteAverage = movieData.getDouble(J_RATING);
            id = movieData.getLong(J_ID);

            MovieData myMovieObject =  new MovieData(title,description,releaseDate,id,voteAverage,popularity);


            if(movieData.get(J_POSTER)!=null) {
                myMovieObject.setPosterpath(movieData.getString(J_POSTER));
            }
            else {
                myMovieObject.setPosterpath( "/weUSwMdQIa3NaXVzwUoIIcAi85d.jpg");
            }

            movieDataArrayList.add(myMovieObject);

        }


        return movieDataArrayList;

    }
}

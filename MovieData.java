package com.example.shubhama.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhama on 12/23/2015.
 */
public class MovieData implements Parcelable {
    public MovieData(String title, String synopsis, String releaseDate, Long id, Double voteAverage, Double popularity) {
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.id = id;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }

    String title;
    String synopsis;
    String releaseDate;
    Long id;
    Double voteAverage;
    Double popularity;
    String posterPath;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterpathSmall() {


        return "http://image.tmdb.org/t/p/w185" + posterPath;

    }
    public String getPosterPathLarge(){

        return "http://image.tmdb.org/t/p/w342" + posterPath;
    }

    public void setPosterpath(String posterpath) {


        if(posterpath==null || posterpath.isEmpty()){
            this.posterPath = "/weUSwMdQIa3NaXVzwUoIIcAi85d.jpg";
        }
        else{
            this.posterPath = posterpath;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeString(releaseDate);
        dest.writeLong(id);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);

    }
    public  MovieData(Parcel in) {
        title = in.readString();
        synopsis = in.readString();
        releaseDate = in.readString();
        id  =in.readLong();
        voteAverage = in.readDouble();
        popularity =  in.readDouble();

        posterPath = in.readString();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}

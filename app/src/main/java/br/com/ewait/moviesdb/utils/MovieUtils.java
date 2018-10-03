package br.com.ewait.moviesdb.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.ewait.moviesdb.BuildConfig;

@SuppressWarnings("ALL")
public class MovieUtils {

    public static final String EXTRA_SELECTED_MOVIE = "extra_selected_movie";

    public static String formatDate (String date) {

        Date formattedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            formattedDate = sdf.parse(date);
        } catch (ParseException e) {
            return "Unknown";
        }

        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(formattedDate);

    }

    public static String getTmdbApiKey() {
        return BuildConfig.TMDB_API_KEY;
    }
}

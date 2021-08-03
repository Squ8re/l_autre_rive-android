package com.franciscain.lautrerive.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franciscain.lautrerive.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEnseignement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEnseignement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView evangile;
    private TextView textViewLienVideo;

    public FragmentEnseignement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEnseignement.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEnseignement newInstance(String param1, String param2) {
        FragmentEnseignement fragment = new FragmentEnseignement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enseignement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        evangile    = getView().findViewById(R.id.evangile);
        textViewLienVideo   = getView().findViewById(R.id.lienVideo);
        textViewLienVideo.setMovementMethod(LinkMovementMethod.getInstance());

        new RecupereEvangile().execute();
        new RecupereVideo().execute();
    }

    public class RecupereEvangile extends AsyncTask<Void, Void, Void>{

        String textEvangile;

        @Override
        protected Void doInBackground(Void... voids) {
            Date c = Calendar.getInstance().getTime();                                              // Récupération de la date du jour
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());  // Création du format de date
            String url = "https://www.aelf.org/" + df.format(c) + "/romain/messe";
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
            String textBrut = doc.text();
            String[] premierParsing= textBrut.split("Évangile");
            textEvangile = "Évangile" + premierParsing[premierParsing.length-1];

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            evangile.setText(textEvangile);
        }
    }
    public class RecupereVideo extends AsyncTask<Void, Void, Void>{

        String lienVideo;

        @Override
        protected Void doInBackground(Void... voids) {
            // TODO : Récupérer vidéo depuis la database
            lienVideo = "https://youtu.be/kP6cgoMBYbc";
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textViewLienVideo.setText(Html.fromHtml("<a href=\""+lienVideo+"\">Vidéo d'enseignement</a> "));
            textViewLienVideo.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
package com.franciscain.lautrerive.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franciscain.lautrerive.R;

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

    TextView evangile;

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
        evangile = getView().findViewById(R.id.evangile);

        new RecupereEvangile().execute();
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
}
package com.franciscain.lautrerive.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franciscain.lautrerive.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFAQ#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFAQ extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout ListeVideoFAQ;

    public FragmentFAQ() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFAQ.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFAQ newInstance(String param1, String param2) {
        FragmentFAQ fragment = new FragmentFAQ();
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
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        ListeVideoFAQ = getView().findViewById(R.id.listeVideoFAQ);
        new VideosFAQ().execute();
    }

    public class VideosFAQ extends AsyncTask<Void, Void, Void> {

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
            for (int i = 0; i<45; i++) {
                TextView video = new TextView(getActivity());
                video.setText(Html.fromHtml("<a href=\"" + lienVideo + "\">Vidéo FAQ</a> "));
                video.setMovementMethod(LinkMovementMethod.getInstance());
                ListeVideoFAQ.addView(video);
            }
        }
    }
}
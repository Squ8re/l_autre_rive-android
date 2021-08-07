package com.franciscain.lautrerive.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import com.franciscain.lautrerive.R;
import com.franciscain.lautrerive.objets.Objectif;
import com.franciscain.lautrerive.outils.Database;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTableauDeBord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTableauDeBord extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GridLayout gridLayout;



    public FragmentTableauDeBord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTableauDeBord.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTableauDeBord newInstance(String param1, String param2) {
        FragmentTableauDeBord fragment = new FragmentTableauDeBord();
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
        return inflater.inflate(R.layout.fragment_tableau_de_bord, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        gridLayout = getView().findViewById(R.id.gridLayoutBadges);
        new MesObjectifs().execute();

    }

    public class MesObjectifs extends AsyncTask<Void, Void, Void> {

        Objectif[] objectifs;

        @Override
        protected Void doInBackground(Void... voids) {
            objectifs = Database.getMesObjectifs().toArray(new Objectif[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            gridLayout.setColumnCount(3);

            int nombreBoutons       = objectifs.length;
            int boutonsParColonne   = 3;
            int boutonsAjoutés      = 0;
            int indexColonne        = 0;
            int indexLigne          = 0;

            for(int i=0; i <nombreBoutons; i++){
                if(!(boutonsAjoutés <boutonsParColonne)){
                    indexLigne++;
                    boutonsAjoutés  = 0;
                    indexColonne    = 0;
                }
                GridLayout.Spec ligne   = GridLayout.spec(indexLigne,1);
                GridLayout.Spec colonne = GridLayout.spec(indexColonne,1);
                GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(ligne,colonne);

                Button boutonObjectif = creerBoutonObjectif(objectifs[i]);
                gridLayout.addView( boutonObjectif,gridLayoutParam);

                boutonsAjoutés++;
                indexColonne++;
            }
        }
    }

        private Button creerBoutonObjectif(Objectif objectif) {
            Button bouton = new Button(getContext());
            bouton.setText(objectif.getNom());
            return bouton;
        }
    }
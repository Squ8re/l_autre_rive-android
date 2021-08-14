package com.franciscain.lautrerive.ui.main;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.franciscain.lautrerive.R;
import com.franciscain.lautrerive.objets.Objectif;
import com.franciscain.lautrerive.objets.Utilisateur;
import com.franciscain.lautrerive.outils.AdapterObjectifs;
import com.franciscain.lautrerive.outils.Database;
import com.franciscain.lautrerive.outils.DialogCloseListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTableauDeBord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTableauDeBord extends Fragment implements DialogCloseListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView; // TODO : nouvel fct
    private AdapterObjectifs adapterObjectifs;

    private List<Objectif> objectifListe;
    private ProgressBar progressBar;
    private FloatingActionButton fab;


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
        objectifListe   = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_tableau_de_bord, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        progressBar     = view.findViewById(R.id.progressBar_tableau_de_Bord);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView    = getView().findViewById(R.id.recyclerView_objectifs);
        fab   = getView().findViewById(R.id.bouton_nouvel_objectif);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterObjectifs = new AdapterObjectifs(this);
        recyclerView.setAdapter(adapterObjectifs);

        Objectif objectif = new Objectif();
        objectif.setNom("Patientez pendant le chargement de vos objectifs.");

        objectifListe.add(objectif);
        adapterObjectifs.setObjectifs(objectifListe);
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilisateur utilisateur = snapshot.getValue(Utilisateur.class);
                progressBar.setProgress(90);
                adapterObjectifs.setObjectifs(utilisateur.getObjectifs());
                adapterObjectifs.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDialog_AjoutObjectif.newInstance().show(getParentFragmentManager(), FragmentDialog_AjoutObjectif.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        /*objectifListe = Database.getMesObjectifs().getObjectifs();
        adapterObjectifs.setObjectifs(objectifListe);
        adapterObjectifs.notifyDataSetChanged();*/
    }
}
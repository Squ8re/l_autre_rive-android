package com.franciscain.lautrerive.outils;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.franciscain.lautrerive.objets.Objectif;
import com.franciscain.lautrerive.objets.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class Database {

    /**
     * Cette fonction récupère les objectifs attribués à tous les utilisateurs en début de parcours
     * sous forme de Strings
     * @return un tableau de string contenant les objectifs
     */
    public static String[] objectifsPredefinis(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Default").child("objectifs");
        ArrayList<String> listeObjectifs = new ArrayList<>();
        // Read from the database
        final DataSnapshot[] snapshot = new DataSnapshot[1];
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    snapshot[0] = task.getResult();
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        for(DataSnapshot dss : snapshot[0].getChildren()){
            listeObjectifs.add(dss.getValue(String.class));
        }

        return listeObjectifs.toArray(new String[0]);
    }

    public static ArrayList<Objectif> getMesObjectifs() {
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final Utilisateur[] utilisateur = {new Utilisateur()};
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                utilisateur[0] = snapshot.getValue(Utilisateur.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        while(utilisateur[0].getNom() == null){
            //waiting
        }
        return utilisateur[0].getObjectifs();
    }

    public static Utilisateur getUtilisateur(){
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final Utilisateur[] utilisateur = {new Utilisateur()};
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                utilisateur[0] = snapshot.getValue(Utilisateur.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error
            }
        });
        while(utilisateur[0].getNom() == null){
            //waiting
        }
        return utilisateur[0];
    }
}

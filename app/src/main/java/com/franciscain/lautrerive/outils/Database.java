package com.franciscain.lautrerive.outils;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.franciscain.lautrerive.objets.Objectif;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    listeObjectifs.add(dss.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return listeObjectifs.toArray(new String[listeObjectifs.size()]);
    }
}

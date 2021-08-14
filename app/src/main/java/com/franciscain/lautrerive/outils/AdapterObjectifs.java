package com.franciscain.lautrerive.outils;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.franciscain.lautrerive.R;
import com.franciscain.lautrerive.objets.Objectif;
import com.franciscain.lautrerive.objets.Utilisateur;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Utilities;

import java.time.LocalDate;
import java.util.List;

public class AdapterObjectifs extends RecyclerView.Adapter<AdapterObjectifs.ViewHolder>{
    private List<Objectif> liste_objectifs;
    private Fragment fragment;
    Utilisateur utilisateur;

    public AdapterObjectifs(Fragment fragment){
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.objectif_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilisateur utilisateur = snapshot.getValue(Utilisateur.class);
                Objectif item;
                int myPos = holder.getAdapterPosition();
                if(myPos > -1)
                    item = liste_objectifs.get(myPos);
                else
                    item = liste_objectifs.get(position);
                holder.objectif.setText(item.getNom());
                holder.objectif.setChecked(toBoolean(item.getStatut()));
                holder.objectif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked){
                            utilisateur.objectifReussi(item.getNom(), LocalDate.now());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return liste_objectifs.size();
    }

    public void setObjectifs(List<Objectif> liste_objectifs){
        this.liste_objectifs = liste_objectifs;
        notifyDataSetChanged();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    /**
     * Retourne l'indice de l'objectif portant le nom nomSecondaire
     * @param nomSecondaire nom d'un objectif
     * @return index de l'objectif avec nomSecondaire, -1 sinon.
     */
    private int indexOfName(String nomSecondaire){
        for(int i = 0; i< liste_objectifs.size(); i++){
            if(liste_objectifs.get(i).getNom().equals(nomSecondaire))
                return i;
        }
        return -1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox objectif;

        ViewHolder(View view){
            super(view);
            objectif = view.findViewById(R.id.todo_checkbox);
        }
    }
}

package com.franciscain.lautrerive.ui.main;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.franciscain.lautrerive.R;
import com.franciscain.lautrerive.objets.Objectif;
import com.franciscain.lautrerive.objets.Utilisateur;
import com.franciscain.lautrerive.outils.Database;
import com.franciscain.lautrerive.outils.DialogCloseListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentDialog_AjoutObjectif extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText nouvelObjectif;
    private Button bouton_enregistrer;
    private Utilisateur utilisateur;

    public static FragmentDialog_AjoutObjectif newInstance(){
        return new FragmentDialog_AjoutObjectif();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.nouvel_objectif_layout, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        nouvelObjectif = view.findViewById(R.id.editText_nv_objectif);
        bouton_enregistrer = view.findViewById(R.id.bouton_nouvel_objectif);
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilisateur utilisateur = snapshot.getValue(Utilisateur.class);
                nouvelObjectif.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        if(s.toString().equals("")){
                            bouton_enregistrer.setEnabled(false);
                            bouton_enregistrer.setBackgroundColor(Color.GRAY);
                        }else{
                            bouton_enregistrer.setEnabled(true);
                            bouton_enregistrer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        }
                        bouton_enregistrer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text = nouvelObjectif.getText().toString();
                                utilisateur.addObjectif(text);
                                dismiss();
                            }
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Fragment fragment = getParentFragment();
        if(fragment instanceof DialogCloseListener){
            ((DialogCloseListener)fragment).handleDialogClose(dialog);
        }
    }
}

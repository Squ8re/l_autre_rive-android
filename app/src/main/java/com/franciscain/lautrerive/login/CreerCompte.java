package com.franciscain.lautrerive.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.franciscain.lautrerive.MainActivity;
import com.franciscain.lautrerive.R;
import com.franciscain.lautrerive.objets.Objectif;
import com.franciscain.lautrerive.objets.Utilisateur;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Cette classe permet à un utilisateur de se créer un compte dans l'application
 *
 * Le tutoriel de "CodeWithMazn" a été très utile dans la réalisation de cette classe.
 *          https://www.youtube.com/watch?v=w-Uv-ydX_LY
 */
public class CreerCompte extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    private EditText editTextNom, editTextMail, editTextMotDePasse, editTextMotDePasseConfirmation;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        // Récupération des instances et champs
        mAuth = FirebaseAuth.getInstance();

        TextView enregistrement = (Button) findViewById(R.id.boutonInscription);
        enregistrement.setOnClickListener(this);

        editTextNom                     = findViewById(R.id.editTextNom);
        editTextMail                    = findViewById(R.id.editTextEmailCréer);
        editTextMotDePasse              = findViewById(R.id.editTextNewPassword);
        editTextMotDePasseConfirmation  = findViewById(R.id.editTextNewPassword2);

        progressBar = findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.boutonInscription)
            EnregistrerUtilisateur();
    }

    /**
     * Cette méthode crée un utilisateur dans la base de donnée distante et vérifie
     * les éventuelles erreurs de contenu.
     */
    private void EnregistrerUtilisateur() {
        String nom = editTextNom.getText().toString().trim();
        String mail      = editTextMail.getText().toString().trim();
        String motDePasse = editTextMotDePasse.getText().toString().trim();
        String motDePasseC= editTextMotDePasseConfirmation.getText().toString().trim();

        // On vérifie que les strings correspondent à ce qu'on demande
        if(nom.isEmpty()){
            editTextNom.setError("Nous avons besoin de votre joli nom !");
            editTextNom.requestFocus();
            return;
        }

        if(mail.isEmpty()){
            editTextMail.setError("Nous avons besoin de votre adresse mail !");
            editTextMail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            editTextMail.setError("Votre adresse mail semble incorrecte !");
            editTextMail.requestFocus();
            return;
        }

        if(motDePasse.isEmpty()){
            editTextMotDePasse.setError("Oups, vous avez oublié le mot de passe ?!");
            editTextMotDePasse.requestFocus();
            return;
        }
        if(motDePasse.length() < 6){
            editTextMotDePasse.setError("Votre mot de passe doit faire minimum 6 caractères.");
            editTextMotDePasse.requestFocus();
            return;
        }

        if(!motDePasse.equals(motDePasseC)){
            editTextMotDePasseConfirmation.setError("Vous n'avez pas écrit deux fois la même chose !");
            editTextMotDePasseConfirmation.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE); // On indique que le chargement commence

        // Ajout du membre dans la base de données
        mAuth.createUserWithEmailAndPassword(mail, motDePasse)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Utilisateur utilisateur = new Utilisateur(nom, mail);
                        ArrayList<Objectif> mesObjectifs = utilisateur.getObjectifs();
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(utilisateur).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                // user.sendEmailVerification();    // TODO : activer la vérification mail
                                Toast.makeText(this, "Vous êtes enregistré !", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(this, MainActivity.class));
                            } else {
                                Toast.makeText(this, "Il y a eu une erreur ! Réessayez !", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }else{
                        Toast.makeText(this, "Il y a eu une erreur ! Réessayez !", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
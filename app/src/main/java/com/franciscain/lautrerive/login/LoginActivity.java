package com.franciscain.lautrerive.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.google.firebase.auth.FirebaseAuth;

/**
 * Cette activité permet à un utilisateur de se connecter à l'application
 *
 * Le tutoriel de "CodeWithMazn" a été très utile dans la réalisation de cette classe.
 *          https://www.youtube.com/watch?v=w-Uv-ydX_LY
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView editTextEmail, editTextMotDePasse;
    private ProgressBar progressBar;

    private static final int TIME_DELAY = 2000;     // Temps pour appuyer une seconde fois sur retour
    private static long back_pressed;               // et quitter l'app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupération des boutons et champs
        TextView creerUnCompte = findViewById(R.id.boutonCreerCompte);
        creerUnCompte.setOnClickListener(this);

        Button login        = findViewById(R.id.boutonLogIn);
        login.setOnClickListener(this);

        TextView oubliMDP   = findViewById(R.id.BoutonMDPOublie);
        oubliMDP.setOnClickListener(this);

        editTextEmail       = (EditText) findViewById(R.id.editTextEmail);
        editTextMotDePasse  = (EditText) findViewById(R.id.editTextTextPassword);

        ConstraintLayout loginForm = (ConstraintLayout) findViewById(R.id.LayoutConnexion);

        progressBar = findViewById(R.id.progressBar);

        // TODO : activer la détection de connexion
        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            loginForm.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.boutonCreerCompte:
                startActivity(new Intent(this, CreerCompte.class));
                break;

            case R.id.boutonLogIn:
                userLogin();
                break;

            case R.id.BoutonMDPOublie:
                startActivity(new Intent(this, MDPOublie.class));
                break;
        }
    }

    private void userLogin(){
        // Récupération des données
        String email = editTextEmail.getText().toString().trim();
        String motDePasse = editTextMotDePasse.getText().toString().trim();

        // Vérification des données
        if(email.isEmpty()){
            editTextEmail.setError("Oups, vous avez oublié de rentrer une adresse mail !");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Votre adresse mail n'est pas valide ! Réessayez.");
            editTextEmail.requestFocus();
            return;
        }

        if(motDePasse.length() < 6) {
            editTextMotDePasse.setError("Votre mot de passe n'est pas valide. Réessayez !");
            editTextMotDePasse.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE); // Le chargement commence
        FirebaseAuth mAuth = FirebaseAuth.getInstance();     // On récupère l'utilisateur
        // Connexion de l'utilisateur à la base de donnée
        mAuth.signInWithEmailAndPassword(email, motDePasse).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                startActivity(new Intent(this, MainActivity.class));
            }else{
                Toast.makeText(this, "Echec de la connexion, vérifiez vos informations et réessayez.", Toast.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    /**
     * Cette fonction permet à l'utilisateur de quitter l'application en cliquant deux fois sur
     * le bouton retour.
     * La durée pour appuyer sur le bouton peut être changée avec la variable TIME_DELAY, exprimée en ms.
     */
    @Override
    public void onBackPressed(){
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Appuyez encore une fois pour quitter !",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
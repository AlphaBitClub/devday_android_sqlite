package android.alphabit;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nom,prenom;
    Button add,view,modify,delete;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des variable en leurs donne l'ID des composents dans le fichier XML
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        add = (Button) findViewById(R.id.add);
        view = (Button) findViewById(R.id.view);
        modify = (Button) findViewById(R.id.modify);
        delete = (Button) findViewById(R.id.delete);

        // (1) Faire appler à onClick(View view) pour chaque Button
        add.setOnClickListener(this);
        view.setOnClickListener(this);
        modify.setOnClickListener(this);
        delete.setOnClickListener(this);


    }

    // Cette fonction s'execute lorsqu'on appuie sur un composent (qu'on l'appel View dans AndroidStudio)
    @Override
    public void onClick(View view) {
        // On fait un Switch & Case pour chaque setOnClickListener qu'on a déclaré à (1)
        switch (view.getId()) {
            case R.id.add:
                database = new Database(MainActivity.this); // Initialisation de la Database on leur donne le context de cette classe
                database.open(); // Ouverture du Database en mode Ecriture

                String stringNom = nom.getText().toString(); // Récupérer le nom
                String stringPrenom = prenom.getText().toString(); // Récupérer le prénom

                database.add(stringNom,stringPrenom);  // Faire appelle à la fonction d'ajout une donnée

                database.close(); // Fermeture du database
                break;
            case R.id.view:
                database = new Database(MainActivity.this);
                database.open();
                String result = database.getData(); // Faire appelle à la fonction de récupération des données
                Toast.makeText(MainActivity.this,"Date : \n" + result,Toast.LENGTH_LONG).show(); // Affichage les données récupéré
                database.close();

                break;
            case R.id.modify:

                // Rien de nouveau dans le reste ...


                database = new Database(MainActivity.this);
                database.open();

                String s1 = nom.getText().toString();
                String s2 = prenom.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("nom",s1);
                contentValues.put("prenom",s2);

                database.modify(contentValues,s1);
                database.close();

                break;
            case R.id.delete:
                database = new Database(MainActivity.this);
                database.open();
                String stringN = nom.getText().toString();
                database.delete(stringN);
                database.close();
                break;

        }
    }
}

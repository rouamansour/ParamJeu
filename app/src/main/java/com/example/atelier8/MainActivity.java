package com.example.atelier8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spType;
    private SeekBar seekNiveau;
    private RadioGroup son_activé;
    private RadioButton rdOui, rdNon;
    private Button btnEnregistrer, btnAfficher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiser();
    }

    private void initialiser() {
        spType = findViewById(R.id.spType);
        seekNiveau = findViewById(R.id.seekNiveau);
        son_activé = findViewById(R.id.son_activé);
        rdOui = findViewById(R.id.rdOui);
        rdNon = findViewById(R.id.rdNon);
        btnEnregistrer = findViewById(R.id.btnEnregistrer);
        btnAfficher = findViewById(R.id.btnAfficher);
        ajouterEcouteurs();
        recuperer();
    }

    private void ajouterEcouteurs() {
        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrer();
            }
        });
        btnAfficher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficher();
            }
        });
    }

    private void enregistrer() {
        boolean a = true;
        int t = spType.getSelectedItemPosition();
        int n = seekNiveau.getProgress();
        switch (son_activé.getCheckedRadioButtonId()) {
            case R.id.rdOui:
                a = true;
                break;
            case R.id.rdNon:
                a = false;
                break;
        }
        SharedPreferences p = getSharedPreferences("conn", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = p.edit();
        ed.putInt("Type", t);
        ed.putInt("Niveau", n);
        ed.putBoolean("Activé", a);
        ed.commit();
        Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_LONG).show();
    }

    private void afficher() {
        String a = "";
        switch (son_activé.getCheckedRadioButtonId()) {
            case R.id.rdOui:
                a = "Oui";
                break;
            case R.id.rdNon:
                a = "Non";
                break;
        }
        String m = "";
        m = "Type: " + spType.getSelectedItem() + "\n" +
                "Niveau: " + seekNiveau.getProgress() + "\n" +
                "Son activé: " + a;
        Toast.makeText(this, m, Toast.LENGTH_LONG).show();
    }

    private void recuperer() {
        SharedPreferences p = getSharedPreferences("conn", Context.MODE_PRIVATE);
        spType.setSelection(p.getInt("Type", 0));
        seekNiveau.setProgress(p.getInt("Niveau", 0));
        boolean c = p.getBoolean("Activé", true);
        if (c == true)
            rdOui.setChecked(true);
        else
            rdNon.setChecked(true);
    }
}
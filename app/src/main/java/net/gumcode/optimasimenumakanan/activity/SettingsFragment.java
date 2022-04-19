package net.gumcode.optimasimenumakanan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import net.gumcode.optimasimenumakanan.R;

/**
 * Created by A. Fauzi Harismawan on 3/21/2016.
 */
public class
SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        Preference pso = findPreference("pref_pso");
        pso.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent change = new Intent(getActivity(), PsoActivity.class);
                startActivity(change);
                return false;
            }
        });

//        Preference sa = findPreference("pref_sa");
//        sa.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                Intent change = new Intent(getActivity(), SaActivity.class);
//                startActivity(change);
//                return false;
//            }
//        });

        Preference hewani = findPreference("pref_protein_hewani");
        hewani.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "HEWANI");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference nabati = findPreference("pref_protein_nabati");
        nabati.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "NABATI");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference lemak = findPreference("pref_lemak");
        lemak.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "LEMAK");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference karbohidrat = findPreference("pref_karbohidrat");
        karbohidrat.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "KARBOHIDRAT");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference gula = findPreference("pref_gula");
        gula.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "GULA");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference buah = findPreference("pref_buah");
        buah.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "BUAH");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference sayur = findPreference("pref_sayur");
        sayur.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "SAYUR");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference susu = findPreference("pref_susu");
        susu.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Bundle send = new Bundle();
                Intent change = new Intent(getActivity(), TableActivity.class);
                send.putString("ASD", "SUSU");
                change.putExtras(send);
                startActivity(change);
                return false;
            }
        });

        Preference keluarga = findPreference("pref_keluarga");
        keluarga.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent change = new Intent(getActivity(), KeluargaActivity.class);
                startActivity(change);
                return false;
            }
        });
    }
}

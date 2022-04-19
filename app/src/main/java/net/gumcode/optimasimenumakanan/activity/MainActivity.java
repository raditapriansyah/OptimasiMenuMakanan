package net.gumcode.optimasimenumakanan.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.algoritma.MyGaSa3;
import net.gumcode.optimasimenumakanan.database.DatabaseHelper;
import net.gumcode.optimasimenumakanan.model.Akg;
import net.gumcode.optimasimenumakanan.model.Ga;
import net.gumcode.optimasimenumakanan.model.Hasil;
import net.gumcode.optimasimenumakanan.model.Keluarga;
import net.gumcode.optimasimenumakanan.model.Sa;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog dialog;
    private DatabaseHelper db;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        try {
            db.getGa();
        } catch (SQLiteException e) {
            e.printStackTrace();
            db.createDataBase();
        }

        sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout a1 = (RelativeLayout) findViewById(R.id.a1);
        assert a1 != null;
        a1.setOnClickListener(this);

        RelativeLayout a2 = (RelativeLayout) findViewById(R.id.a2);
        assert a2 != null;
        a2.setOnClickListener(this);

        RelativeLayout a3 = (RelativeLayout) findViewById(R.id.a3);
        assert a3 != null;
        a3.setOnClickListener(this);

        RelativeLayout a4 = (RelativeLayout) findViewById(R.id.a4);
        assert a4 != null;
        a4.setOnClickListener(this);

        RelativeLayout a5 = (RelativeLayout) findViewById(R.id.a5);
        assert a5 != null;
        a5.setOnClickListener(this);

        RelativeLayout a6 = (RelativeLayout) findViewById(R.id.a6);
        assert a6 != null;
        a6.setOnClickListener(this);

    }

    private void proses() {
        new AsyncTask<Void, Void, Void>() {

            ProgressDialog pg;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pg = ProgressDialog.show(MainActivity.this, "", "Loading", true, false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                MyGaSa3 GaSa = new MyGaSa3(MainActivity.this);
                int[] Ranking = new int[10000];
                double[] FitnessTemp;

                int[][] PopulasiGabunganSorting;

                //inisialisasi parameter gizi
                ArrayList<Keluarga> sd = db.getKeluarga();

                int JmlOrang = sd.size();
                String[] Nama = new String[JmlOrang];
                String[] Jk = new String[JmlOrang];
                int[] Usia = new int[JmlOrang];
                int[] Bb = new int[JmlOrang];
                int[] Tb = new int[JmlOrang];
                int[] FaktorAktivitas = new int[JmlOrang];

                for (int i = 0; i < sd.size(); i++) {
                    Nama[i] = sd.get(i).nama;
                    Jk[i] = sd.get(i).jk;
                    Usia[i] = sd.get(i).usia;
                    Bb[i] = sd.get(i).berat;
                    Tb[i] = sd.get(i).tinggi;
                    FaktorAktivitas[i] = sd.get(i).aktivitas;
                }

                //inisialisasi parameter algoritma genetika
                Ga aa = db.getGa();
                int MaxIterasi = (int) aa.generasi;
                int hari = db.getHari();
                int OneChromosom = 24;
                int StringLenChromosome = OneChromosom * hari;
                int PopSize = (int) aa.popsize;
                double Cr = aa.cr;
                double Mr = aa.mr;

                //inisialisasi parameter simulated annealing
//                Sa b = db.getSa();
//                double T0 = b.to;
//                double Alpha = b.alpha;
//                double Tn = b.tn;

                double[] Energi;
                double[] Protein;
                double[] Lemak;
                double[] Karbohidrat;
                int[] Type;
//                int[][] IndividuTerbaikGenerasi = new int[MaxIterasi][StringLenChromosome];
//                double[] FitnessTerbaikGenerasi = new double[MaxIterasi];

                //menghitung kebutuhan gizi
                Energi = GaSa.HitungKebutuhanEnergi(JmlOrang, Usia, Jk, Bb, Tb, FaktorAktivitas);
                Protein = GaSa.HitungKebutuhanProtein(Energi, Usia);
                Lemak = GaSa.HitungKebutuhanLemak(Energi);
                Karbohidrat = GaSa.HitungKebutuhanKarbohidrat(Energi, Protein, Lemak);
                Type = GaSa.CariTypeAnjuranPorsi(Energi, Usia);

                //mencetak kebutuhan gizi
                //GaSa.CetakKebutuhanGizi(Energi, Karbohidrat, Protein, Lemak, Type);
                db.deleteAkg();
                for (int i = 0; i < JmlOrang; i++) {
                    Akg a = new Akg();
                    a.no = i + 1;
                    a.nama = Nama[i];
                    a.energi = (float) Energi[i];
                    a.karbohidrat = (float) Karbohidrat[i];
                    a.protein = (float) Protein[i];
                    a.lemak = (float) Lemak[i];
                    a.tipe = Type[i];
                    db.setAkg(a);
                    Log.d("AKG", a.no + " " + a.nama + " " + String.format(Locale.US, "%.2f", a.energi) + " " + String.format(Locale.US, "%.2f", a.karbohidrat) + " " + String.format(Locale.US, "%.2f", a.protein) + " " + String.format(Locale.US, "%.2f", a.lemak) + " " + a.tipe);
                }

                //Proses Algortima Genetika
                int[][] PopulasiAwal = GaSa.Inisialisasi(PopSize, StringLenChromosome, 55); //inisialisasi populasi awal

                //System.out.println("Inisialisasi Populasi Awal");
                //mulai iterasi proses algoritma genetika dan simulated annealing
                db.deleteHasil();
                for (int i = 0; i < MaxIterasi; i++) {
                    System.out.println("Generasi ke-" + (i + 1));
                    //Proses One Cut Point Crossover
                    int OffspringCrossover = (int) (Math.round(Cr * PopSize));

                    // Proses crossover dilakukan sebanyak (n_crossover)
                    int n_crossover = (int) Math.round((double) OffspringCrossover / 2); // dibagi 2, krn 1 kali proses crossover akan menghasilkan 2 anak
                    int[][] HasilCrossover = GaSa.OneCutPointCrossover(PopulasiAwal, Cr);
                    int OffspringMutasi = (int) (Math.round(Mr * PopSize));

                    // Proses mutasi dilakukan sebanyak (n_mutasi)
                    int n_mutasi = OffspringMutasi;
                    int[][] HasilMutasi = GaSa.InsertionMutation(PopulasiAwal, Mr);

                    //Menyimpan Gabungan Individu Awal dan Hasil Reproduksi
                    int PopSizeGabungan = PopSize + OffspringCrossover + OffspringMutasi;
                    int[][] PopulasiGabungan = new int[PopSizeGabungan][StringLenChromosome];

                    for (int j = 0; j < PopSize; j++) {
                        for (int k = 0; k < StringLenChromosome; k++) {
                            PopulasiGabungan[j][k] = PopulasiAwal[j][k];
                        }
                    }

                    for (int j = PopSize; j < (PopSize + OffspringCrossover); j++) {
                        for (int k = 0; k < StringLenChromosome; k++) {
                            PopulasiGabungan[j][k] = HasilCrossover[j - PopSize][k];
                        }
                    }

                    for (int j = (PopSize + OffspringCrossover); j < PopSizeGabungan; j++) {
                        for (int k = 0; k < StringLenChromosome; k++) {
                            PopulasiGabungan[j][k] = HasilMutasi[j - (PopSize + OffspringCrossover)][k];
                        }
                    }

                    //menampilkan populasi gabungan
                    double[] FitnessGabungan = new double[PopSizeGabungan];
                    for (int j = 0; j < PopSizeGabungan; j++) {
                        FitnessGabungan[j] = GaSa.HitungFitness(PopulasiGabungan[j], Type, Jk, Energi,
                                Karbohidrat, Protein, Lemak);
                    }
                    FitnessTemp = new double[PopSizeGabungan];
                    for (int j = 0; j < PopSizeGabungan; j++) {
                        FitnessTemp[j] = FitnessGabungan[j];
                    }

                    //seleksi elitsm
                    GaSa.SeleksiElitsm(FitnessTemp);
                    PopulasiGabunganSorting = new int[PopSizeGabungan][StringLenChromosome];

                    for (int j = 0; j < PopSizeGabungan; j++) {
                        for (int k = 0; k < PopSizeGabungan; k++) {
                            if (FitnessTemp[j] == FitnessGabungan[k]) {
                                Ranking[j] = (k + 1);
                                //System.out.println(k + "," + j);
                                PopulasiGabunganSorting[j] = PopulasiGabungan[k];
                            }
                        }
                    }

//                    int[] IndividuSA = GaSa.SimulatedAnnealing(T0, Alpha, Tn, PopulasiGabunganSorting[0], Type, Jk, Energi, Karbohidrat, Protein, Lemak);

//                    for (int j = 0; j < PopSize; j++) {
//                        PopulasiAwal[j] = PopulasiGabunganSorting[j];
//                    }
//                    PopulasiAwal[0] = IndividuSA;
//                    FitnessTemp[0] = GaSa.HitungFitness(IndividuSA, Type, Jk, Energi, Karbohidrat, Protein, Lemak);
                /*
                System.out.println("Hasil Seleksi untuk Generasi Selanjutnya");
                for (int j = 0; j < PopSize; j++) {
                    System.out.print("P" + (j + 1 + " = "));
                    for (int k = 0; k < StringLenChromosome; k++) {
                        System.out.print(PopulasiAwal[j][k] + " ");
                    }
                    System.out.println("");
                    System.out.println("");
                }
                System.out.println("");
                */
                    String Kromosom1 = "";
                    for (int j = 0; j < StringLenChromosome; j++) {
                        Kromosom1 = Kromosom1 + " " + String.valueOf(PopulasiAwal[0][j]);
                    }
                    Hasil h = new Hasil();
                    h.gen = i + 1;
                    h.kromosom = Kromosom1;
                    h.fitness =  (float) FitnessTemp[0];
                    db.setHasil(h);
                    Log.d("MODEL3", h.gen + " " + h.kromosom + " " + String.format(Locale.US, "%.2f", h.fitness));
                }
                //end perulangan GASA

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("keterangan", GaSa.CetakHasilIndividu2(PopulasiAwal[0], Type, Jk, Energi, Karbohidrat, Protein, Lemak));
                editor.commit();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                pg.dismiss();
                Intent change = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(change);
            }
        }.execute();
    }

    private void showAlert() {
        View header = getLayoutInflater().inflate(R.layout.dialog_header, null);
        TextView title = (TextView) header.findViewById(R.id.title);
        title.setText("KELUAR");

        View content = getLayoutInflater().inflate(R.layout.dialog_content, null);
        Button cancel = (Button) content.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        Button ok = (Button) content.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCustomTitle(header);
        builder.setView(content);

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent change;
        switch (v.getId()) {
            case R.id.a1:
                proses();
                break;
            case R.id.a2:
                change = new Intent(this, ResultActivity.class);
                startActivity(change);
                break;
            case R.id.a3:
                change = new Intent(this, SettingsActivity.class);
                startActivity(change);
                break;
            case R.id.a4:
                break;
            case R.id.a5:
                break;
            case R.id.a6:
                showAlert();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showAlert();
    }
}


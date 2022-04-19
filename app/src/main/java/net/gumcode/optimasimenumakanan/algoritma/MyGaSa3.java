/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.gumcode.optimasimenumakanan.algoritma;

import android.content.Context;

import net.gumcode.optimasimenumakanan.database.DatabaseHelper;

import java.sql.SQLException;
import java.util.Random;

/**
 *
 * @author TOSHIBA
 */
public class MyGaSa3 {

    private DatabaseHelper db;

    public MyGaSa3(Context context) {
        this.db = new DatabaseHelper(context);
    }

    public int[][] Inisialisasi(int Popsize, int Jumlahgen, int BatasAtas) {
        int[][] Populasi = new int[Popsize][Jumlahgen];
        Random random = new Random();
        for (int i = 0; i < Popsize; i++) {
            for (int j = 0; j < Jumlahgen; j++) {
                Populasi[i][j] = random.nextInt(BatasAtas) + 1;
            }
        }
        return Populasi;
    }

    public int[][] Inisialisasi2(int Popsize, int Jumlahgen, int BatasAtas) {
        int[][] Populasi = new int[Popsize][Jumlahgen];
        Random random = new Random();
        for (int i = 0; i < Popsize; i++) {
            for (int j = 0; j < 9; j++) {
                Populasi[i][j] = random.nextInt(BatasAtas) + 1;
            }
        }

        return Populasi;
    }

    public int[][] OneCutPointCrossover(int[][] Populasi, double Cr) {
        Random random = new Random();
        int StringLenChromosome = Populasi[0].length;
        int Popsize = Populasi.length;
        int OffspringCrossover = (int) (Math.round(Cr * Popsize));
        int[][] HasilTemp = new int[OffspringCrossover + 1][StringLenChromosome];
        int[][] HasilAkhir = new int[OffspringCrossover][StringLenChromosome];
        int hari = StringLenChromosome / 24;
        int[] PosisiOneCutPoint = new int[hari];
        int[][] P1 = new int[1][StringLenChromosome];
        int[][] P2 = new int[1][StringLenChromosome];

        for (int i = 0; i < OffspringCrossover; i = i + 2) {
            // Menentukan PosisiOneCutPoint secara random  
            int BatasBawahRand = 0, BatasAtasRand = 23;
            for (int j = 0; j < hari; j++) {
                PosisiOneCutPoint[j] = random.nextInt((BatasAtasRand - BatasBawahRand) + 1) + BatasBawahRand;
                //System.out.println("Posisi One Cut Poin Hari ke-" + (j + 1) + " : " + (PosisiOneCutPoint[j] + 1));
                BatasBawahRand = BatasBawahRand + 23;
                BatasAtasRand = BatasAtasRand + 23;
            }

            // memilih 2 parent secara random                   
            BatasBawahRand = 0;
            BatasAtasRand = Popsize - 1;

            boolean flag = true;
            int IndexP1 = 0, IndexP2 = 0;
            while (flag) {
                IndexP1 = random.nextInt(BatasAtasRand - BatasBawahRand + 1);
                IndexP2 = random.nextInt(BatasAtasRand - BatasBawahRand + 1);
                if (IndexP1 != IndexP2) {
                    flag = false;
                }
            }//end
            /*
            // menampilkan hasil induk terpilih
            System.out.println("Index P1 = " + (IndexP1 + 1) + ", Index P2 = " + (IndexP2 + 1));
            System.out.print("P1 = ");
            for (int j = 0; j < StringLenChromosome; j++) {
                System.out.print(Populasi[IndexP1][j] + " ");
            }
            System.out.println();

            System.out.print("P2 = ");
            for (int j = 0; j < StringLenChromosome; j++) {
                System.out.print(Populasi[IndexP2][j] + " ");
            }
            System.out.println();
            //end
            */
            //System.arraycopy(Individu[IndexP1], 0, P1[0], 0, StringLenChromosome);
            for (int j = 0; j < StringLenChromosome; j++) {
                P1[0][j] = Populasi[IndexP1][j];
                HasilTemp[i][j] = Populasi[IndexP1][j];
            }

            //System.arraycopy(Individu[IndexP2], 0, P2[0], 0, StringLenChromosome);
            for (int j = 0; j < StringLenChromosome; j++) {
                P2[0][j] = Populasi[IndexP2][j];
                HasilTemp[i + 1][j] = Populasi[IndexP2][j];
            }

            // melakukan proses pindah silang P1 dan P2
            int Batashari = 24;
            for (int j = 0; j < PosisiOneCutPoint.length; j++) {
                for (int k = (PosisiOneCutPoint[j] + 1); k < Batashari; k++) {
                    HasilTemp[i][k] = P2[0][k];
                    HasilTemp[i + 1][k] = P1[0][k];
                }
                Batashari = Batashari + 23;
            }//end
            /*
            // menampilkan hasil crossover
            for (int j = i; j < (i + 2); j++) {
                System.out.print("C" + (j + 1) + " = ");
                for (int k = 0; k < StringLenChromosome; k++) {
                    System.out.print(HasilTemp[j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
            */
        }//end crossover

        for (int i = 0; i < OffspringCrossover; i++) {
            for (int j = 0; j < StringLenChromosome; j++) {
                HasilAkhir[i][j] = HasilTemp[i][j];
            }
        }
        return HasilAkhir;
    }

    public int[][] InsertionMutation(int[][] Populasi, double Mr) {
        Random random = new Random();
        int StringLenChromosome = Populasi[0].length;
        int Popsize = Populasi.length;
        int OffspringMutasi = (int) (Math.round(Mr * Popsize));
        int[][] Hasil = new int[OffspringMutasi][StringLenChromosome];
        int hari = StringLenChromosome / 24;
        int[] SelectedGen = new int[hari];
        int[] InsertPosition = new int[hari];

        // Menentukan PosisiPointRandom secara random
        for (int k = 0; k < OffspringMutasi; k++) {
            int BatasBawahRand = 0, BatasAtasRand = 23;
            for (int i = 0; i < hari; i++) {
                boolean flag = true;
                while (flag) {
                    SelectedGen[i] = random.nextInt(BatasAtasRand - BatasBawahRand + 1) + BatasBawahRand;
                    InsertPosition[i] = random.nextInt(BatasAtasRand - BatasBawahRand + 1) + BatasBawahRand;
                    if (SelectedGen[i] != InsertPosition[i]) {
                        flag = false;
                    }
                }
                //System.out.println("Insert Poin Hari ke-" + (i + 1) + "     : " + (SelectedGen[i] + 1));
                //System.out.println("Insert Position Hari ke-" + (i + 1) + " : " + (InsertPosition[i] + 1));
                BatasBawahRand = BatasBawahRand + 23;
                BatasAtasRand = BatasAtasRand + 23;
            }

            // memilih 1 parent secara random        
            int[][] P1 = new int[1][StringLenChromosome];
            BatasBawahRand = 0;
            BatasAtasRand = Popsize - 1;
            int IndexP1 = random.nextInt(BatasAtasRand - BatasBawahRand + 1) + BatasBawahRand;
            /*
            // menampilkan hasil induk terpilih
            System.out.println("Index P1 = " + (IndexP1 + 1));
            System.out.print("P1 = ");
            for (int j = 0; j < StringLenChromosome; j++) {
                System.out.print(Populasi[IndexP1][j] + " ");
            }
            System.out.println();
            */
            //System.arraycopy(Individu[IndexP1], 0, P1[0], 0, StringLenChromosome);
            for (int i = 0; i < StringLenChromosome; i++) {
                P1[0][i] = Populasi[IndexP1][i];
                Hasil[k][i] = Populasi[IndexP1][i];
            }

            // melakukan proses  mutasi
            for (int i = 0; i < SelectedGen.length; i++) {
                Hasil[0][InsertPosition[i]] = P1[0][SelectedGen[i]];
                if (SelectedGen[i] > InsertPosition[i]) {
                    for (int j = InsertPosition[i]; j < SelectedGen[i]; j++) {
                        Hasil[k][j + 1] = P1[0][j];
                    }
                } else {
                    for (int j = SelectedGen[i] + 1; j <= InsertPosition[i]; j++) {
                        Hasil[k][j - 1] = P1[0][j];
                    }
                }
            }
            /*
            // menampilkan hasil mutasi
            System.out.print("C1 = ");
            for (int j = 0; j < StringLenChromosome; j++) {
                System.out.print(Hasil[k][j] + " ");
            }
            System.out.println();
            System.out.println();
            */
        }

        return Hasil;
    }

    public int[] KonversiIndexBM(int[] Individu) {
        int StringLenChromosome = Individu.length;
        int[] IndexBM = new int[StringLenChromosome];
        for (int j = 0; j < StringLenChromosome; j = j + 8) {
            IndexBM[j] = (int) Math.round(((db.CountRow(DatabaseHelper.TABLE_KARBOHIDRT) - 1) * ((double) (Individu[j] - 1) / (55 - 1))) + 1);
            IndexBM[j + 1] = (int) Math.round((db.CountRow(DatabaseHelper.TABLE_PROTEIN_HEWANI) - 1) * ((double) (Individu[j + 1] - 1) / (55 - 1)) + 1);
            IndexBM[j + 2] = (int) Math.round((db.CountRow(DatabaseHelper.TABLE_PROTEIN_NABATI) - 1) * ((double) (Individu[j + 2] - 1) / (55 - 1)) + 1);
            IndexBM[j + 3] = (int) Math.round((db.CountRow(DatabaseHelper.TABLE_SAYURANB) - 1) * ((double) (Individu[j + 3] - 1) / (55 - 1)) + 1);
            IndexBM[j + 4] = (int) Math.round((db.CountRow(DatabaseHelper.TABLE_BUAH) - 1) * ((double) (Individu[j + 4] - 1) / (55 - 1)) + 1);
            IndexBM[j + 5] = (int) Math.round((db.CountRow(DatabaseHelper.TABLE_LEMAK) - 1) * ((double) (Individu[j + 5] - 1) / (55 - 1)) + 1);
            IndexBM[j + 6] = (int) Math.round((db.CountRow(DatabaseHelper.TABLE_GULA) - 1) * ((double) (Individu[j + 6] - 1) / (55 - 1)) + 1);
            IndexBM[j + 7] = (int) Math.round((db.CountRow(DatabaseHelper.TABLE_SUSU) - 1) * ((double) (Individu[j + 7] - 1) / (55 - 1)) + 1);
        }
        return IndexBM;
    }

    public String[] KonversiNamaBM(int[] IndexBM) {
        int StringLenChromosome = IndexBM.length;
        //menyimpan nama bahan makanan
        String[] NamaBM = new String[StringLenChromosome];
        for (int j = 0; j < StringLenChromosome; j = j + 8) {
            String NamaBM1 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);
            NamaBM[j] = NamaBM1;

            String NamaBM2 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);
            NamaBM[j + 1] = NamaBM2;

            String NamaBM3 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);
            NamaBM[j + 2] = NamaBM3;

            String NamaBM4 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);
            NamaBM[j + 3] = NamaBM4;

            String NamaBM5 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);
            NamaBM[j + 4] = NamaBM5;

            String NamaBM6 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);
            NamaBM[j + 5] = NamaBM6;

            String NamaBM7 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);
            NamaBM[j + 6] = NamaBM7;

            String NamaBM8 = db.HasilQuery1(DatabaseHelper.COLUMN_NAMA_BM, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);
            NamaBM[j + 7] = NamaBM8;
        }
        return NamaBM;
    }

    public double[] HitungKebutuhanEnergi(int JmlOrang, int[] Usia, String[] JK, int[] Bb, int[] Tb, int[] Aktivitas) {
        //menghitung kebutuhan gizi setiap anggota keluarga
        double[] Energi = new double[JmlOrang];
        double[] Bbi = new double[JmlOrang];

        //kebutuhan energi
        for (int i = 0; i < JmlOrang; i++) {
            //perhitungan berat badan ideal
            Bbi[i] = Tb[i] - 100 - (0.1 * (Tb[i] - 100));
            if ("L".equals(JK[i])) {
                double faktor_aktivitas = Double.valueOf(db.HasilQuery1(DatabaseHelper.COLUMN_PRIA, DatabaseHelper.TABLE_FAKTOR_AKTIVITAS, DatabaseHelper.COLUMN_AKTIFITAS, Aktivitas[i]));
                Energi[i] = (65 + (13.7 * Bbi[i]) + (5 * Tb[i]) - (6.8 * Usia[i])) * faktor_aktivitas;
            } else {
                double faktor_aktivitas = Double.valueOf(db.HasilQuery1(DatabaseHelper.COLUMN_WANITA, DatabaseHelper.TABLE_FAKTOR_AKTIVITAS, DatabaseHelper.COLUMN_AKTIFITAS, Aktivitas[i]));
                Energi[i] = (655 + (9.6 * Bbi[i]) + (1.8 * Tb[i]) - (4.7 * Usia[i])) * faktor_aktivitas;
            }
        }
        return Energi;
    }

    public double[] HitungKebutuhanKarbohidrat(double[] Energi, double[] Protein, double[] Lemak) {
        int JmlOrang = Energi.length;
        double[] Karbohidrat = new double[JmlOrang];
        for (int i = 0; i < JmlOrang; i++) {
            Karbohidrat[i] = (Energi[i] - (Protein[i] * 4 + Lemak[i] * 9)) / 4;
        }
        return Karbohidrat;
    }

    public double[] HitungKebutuhanProtein(double[] Energi, int[] Usia) {
        int JmlOrang = Energi.length;
        double[] Protein = new double[JmlOrang];
        for (int i = 0; i < JmlOrang; i++) {
            if (Usia[i] > 18) {
                Protein[i] = Energi[i] * 0.15 / 4;
            } else {
                Protein[i] = Energi[i] * 0.12 / 4;
            }
        }
        return Protein;
    }

    public double[] HitungKebutuhanLemak(double[] Energi) {
        int JmlOrang = Energi.length;
        double[] Lemak = new double[JmlOrang];
        for (int i = 0; i < JmlOrang; i++) {
            Lemak[i] = Energi[i] * 0.3 / 9;
        }
        return Lemak;
    }

    public int[] CariTypeAnjuranPorsi(double[] Energi, int[] Usia) {
        int JmlOrang = Energi.length;

        //mencari type anjuran porsi sehari setiap anggota keluarga
        int[] Type = new int[JmlOrang];
        for (int i = 0; i < JmlOrang; i++) {
            for (int j = 1; j <= (db.CountRow(DatabaseHelper.TABLE_ANJURAN_PORSI)); j++) {
                int BatasBawahUmur = Integer.valueOf(db.HasilQuery1(DatabaseHelper.COLUMN_UMUR_BAWAH, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, (j)));

                int BatasAtasUmur = Integer.valueOf(db.HasilQuery1(DatabaseHelper.COLUMN_UMUR_ATAS, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, (j)));
                System.out.println(j+","+BatasBawahUmur+","+BatasAtasUmur);
                if (BatasBawahUmur >= Usia[i] && Usia[i] >= BatasAtasUmur) {
                    System.out.println(BatasBawahUmur+ ","+BatasAtasUmur);
                    Type[i] = j;
                    //break;
                }
            }
        }
        return Type;
    }

    public void CetakKebutuhanGizi(double[] Energi, double[] Karbohidrat, double[] Protein, double[] Lemak, int[] Type) {
        int JmlOrang = Energi.length;
        System.out.println("Kebutuhan Gizi");

        //mencetak kebutuhan gizi setiap anggota keluarga
        for (int i = 0; i < JmlOrang; i++) {
            System.out.println("Orang ke-" + (i + 1) + " :");
            System.out.printf("Energi          : %.3f kkal \n", Energi[i]);
            System.out.printf("Karbohidrat     : %.3f gram \n", Karbohidrat[i]);
            System.out.printf("Protein         : %.3f gram \n", Protein[i]);
            System.out.printf("Lemak           : %.3f gram \n", Lemak[i]);
            System.out.println("Anjuran Porsi   : Type " + Type[i]);
            System.out.println("");
        }
    }

    public double[][] HitungBeratBMPerOrang(int[] IndexBM, int[] Type, String[] JK) {
        int StringLenChromosome = IndexBM.length;
        int JmlOrang = Type.length;
        int PorsiKarbohidrat;
        int PorsiProteinHewani;
        int PorsiProteinNabati;
        int PorsiSayuranB;
        int PorsiBuah;
        int PorsiLemak;
        int PorsiGula;
        int PorsiSusu;

        //menghitung berat bahan makanan dan kalori setiap anggota keluarga
        double[][] Berat = new double[StringLenChromosome][JmlOrang];
        for (int j = 0; j < StringLenChromosome; j = j + 24) {
            for (int k = 0; k < JmlOrang; k++) {

                int BeratKarbohidrat1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int BeratProteinHewani1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int BeratProteinNabati1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int BeratSayuranB1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int BeratBuah1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int BeratLemak1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int BeratGula1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int BeratSusu1 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                int BeratKarbohidrat2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 8]);

                int BeratProteinHewani2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 9]);

                int BeratProteinNabati2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 10]);

                int BeratSayuranB2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 11]);

                int BeratBuah2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 12]);

                int BeratLemak2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 13]);

                int BeratGula2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 14]);

                int BeratSusu2 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 15]);

                int BeratKarbohidrat3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 16]);

                int BeratProteinHewani3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 17]);

                int BeratProteinNabati3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 18]);

                int BeratSayuranB3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 19]);

                int BeratBuah3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 20]);

                int BeratLemak3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 21]);

                int BeratGula3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 22]);

                int BeratSusu3 = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 23]);

                //query porsi
                if (JK[k].equalsIgnoreCase("L")) {
                    PorsiKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_KH_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_PH_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_PN_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_SAYURB_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiBuah = db.HasilQuery2(DatabaseHelper.COLUMN_BUAH_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiLemak = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiGula = db.HasilQuery2(DatabaseHelper.COLUMN_GULA_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiSusu = db.HasilQuery2(DatabaseHelper.COLUMN_SUSU_L, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);
                } else {
                    PorsiKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_KH_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_PH_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_PN_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_SAYURB_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiBuah = db.HasilQuery2(DatabaseHelper.COLUMN_BUAH_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiLemak = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiGula = db.HasilQuery2(DatabaseHelper.COLUMN_GULA_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);

                    PorsiSusu = db.HasilQuery2(DatabaseHelper.COLUMN_SUSU_P, DatabaseHelper.TABLE_ANJURAN_PORSI, DatabaseHelper.COLUMN_TYPE, Type[k]);
                }

                //makan pagi
                Berat[j][k] = BeratKarbohidrat1 * PorsiKarbohidrat * 0.25;
                Berat[j + 1][k] = BeratProteinHewani1 * PorsiProteinHewani * 0.25;
                Berat[j + 2][k] = BeratProteinNabati1 * PorsiProteinNabati * 0.25;
                Berat[j + 3][k] = BeratSayuranB1 * PorsiSayuranB * 0.25;
                Berat[j + 4][k] = BeratBuah1 * PorsiBuah * 0.25;
                Berat[j + 5][k] = BeratLemak1 * PorsiLemak * 0.25;
                Berat[j + 6][k] = BeratGula1 * PorsiGula * 0.25;
                Berat[j + 7][k] = BeratSusu1 * PorsiSusu * 0.25;

                //makan siang
                Berat[j + 8][k] = BeratKarbohidrat2 * PorsiKarbohidrat * 0.4;
                Berat[j + 9][k] = BeratProteinHewani2 * PorsiProteinHewani * 0.4;
                Berat[j + 10][k] = BeratProteinNabati2 * PorsiProteinNabati * 0.4;
                Berat[j + 11][k] = BeratSayuranB2 * PorsiSayuranB * 0.4;
                Berat[j + 12][k] = BeratBuah2 * PorsiBuah * 0.4;
                Berat[j + 13][k] = BeratLemak2 * PorsiLemak * 0.4;
                Berat[j + 14][k] = BeratGula2 * PorsiGula * 0.4;
                Berat[j + 15][k] = BeratSusu2 * PorsiSusu * 0.4;

                //makan malam
                Berat[j + 16][k] = BeratKarbohidrat3 * PorsiKarbohidrat * 0.35;
                Berat[j + 17][k] = BeratProteinHewani3 * PorsiProteinHewani * 0.35;
                Berat[j + 18][k] = BeratProteinNabati3 * PorsiProteinNabati * 0.35;
                Berat[j + 19][k] = BeratSayuranB3 * PorsiSayuranB * 0.35;
                Berat[j + 20][k] = BeratBuah3 * PorsiBuah * 0.35;
                Berat[j + 21][k] = BeratLemak3 * PorsiLemak * 0.35;
                Berat[j + 22][k] = BeratGula3 * PorsiGula * 0.35;
                Berat[j + 23][k] = BeratSusu3 * PorsiSusu * 0.35;
            }
        }
        return Berat;
    }

    public double[] HitungBeratTotal(double[][] Berat) {
        //menghitung total berat setiap bahan makanan yang dibutuhkan satu keluarga
        int StringLenChromosome = Berat.length;
        int JmlOrang = Berat[0].length;
        double[] BeratTotal = new double[StringLenChromosome];

        for (int j = 0; j < StringLenChromosome; j++) {
            for (int k = 0; k < JmlOrang; k++) {
                BeratTotal[j] = (BeratTotal[j] + Berat[j][k]);
            }
        }
        return BeratTotal;
    }

    public double[][] HitungKaloriPerBMPerOrang(int[] IndexBM, double[][] Berat) {
        int StringLenChromosome = Berat.length;
        int JmlOrang = Berat[0].length;
        double[][] Kalori = new double[StringLenChromosome][JmlOrang];
        //menghitung kandungan gizi bahan makanan untuk setiap anggota keluarga
        for (int j = 0; j < StringLenChromosome; j = j + 8) {
            for (int k = 0; k < JmlOrang; k++) {
                //query berat bahan makanan
                int BeratKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int BeratProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int BeratProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int BeratSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int BeratBuah = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int BeratLemak = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int BeratGula = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int BeratSusu = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                //query kalori 
                int KaloriKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int KaloriProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int KaloriProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int KaloriSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int KaloriBuah = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int KaloriLemak = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int KaloriGula = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int KaloriSusu = db.HasilQuery2(DatabaseHelper.COLUMN_KALORI, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                Kalori[j][k] = Berat[j][k] / BeratKarbohidrat * KaloriKarbohidrat;
                Kalori[j + 1][k] = Berat[j + 1][k] / BeratProteinHewani * KaloriProteinHewani;
                Kalori[j + 2][k] = Berat[j + 2][k] / BeratProteinNabati * KaloriProteinNabati;
                Kalori[j + 3][k] = Berat[j + 3][k] / BeratSayuranB * KaloriSayuranB;
                Kalori[j + 4][k] = Berat[j + 4][k] / BeratBuah * KaloriBuah;
                Kalori[j + 5][k] = Berat[j + 5][k] / BeratLemak * KaloriLemak;
                Kalori[j + 6][k] = Berat[j + 6][k] / BeratGula * KaloriGula;
                Kalori[j + 7][k] = Berat[j + 7][k] / BeratSusu * KaloriSusu;
            }
        }
        return Kalori;
    }

    public double[] HitungRataRataKaloriPerHari(double[] Energi, double[][] Kalori) {
        int StringLenChromosome = Kalori.length;
        int JmlOrang = Kalori[0].length;
        //menghitung selisih kalori
        double[] KaloriTotal = new double[JmlOrang];
        int hari = StringLenChromosome / 24;

        for (int k = 0; k < JmlOrang; k++) {
            for (int j = 0; j < StringLenChromosome; j++) {
                KaloriTotal[k] = KaloriTotal[k] + Kalori[j][k];
            }
            KaloriTotal[k] = KaloriTotal[k] / hari;
        }
        return KaloriTotal;
    }

    public double[][] HitungKarbohidratPerBMPerOrang(int[] IndexBM, double[][] Berat) {
        int StringLenChromosome = Berat.length;
        int JmlOrang = Berat[0].length;
        double[][] Karbohidrat = new double[StringLenChromosome][JmlOrang];
        //menghitung kandungan gizi bahan makanan untuk setiap anggota keluarga

        for (int j = 0; j < StringLenChromosome; j = j + 8) {
            for (int k = 0; k < JmlOrang; k++) {
                //query berat bahan makanan
                int BeratKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int BeratProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int BeratProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int BeratSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int BeratBuah = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int BeratLemak = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int BeratGula = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int BeratSusu = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                //query kalori
                int KarbohidratKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int KarbohidratProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int KarbohidratProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int KarbohidratSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int KarbohidratBuah = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int KarbohidratLemak = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int KarbohidratGula = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int KarbohidratSusu = db.HasilQuery2(DatabaseHelper.COLUMN_KARBOHIDRAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                Karbohidrat[j][k] = Berat[j][k] / BeratKarbohidrat * KarbohidratKarbohidrat;
                Karbohidrat[j + 1][k] = Berat[j + 1][k] / BeratProteinHewani * KarbohidratProteinHewani;
                Karbohidrat[j + 2][k] = Berat[j + 2][k] / BeratProteinNabati * KarbohidratProteinNabati;
                Karbohidrat[j + 3][k] = Berat[j + 3][k] / BeratSayuranB * KarbohidratSayuranB;
                Karbohidrat[j + 4][k] = Berat[j + 4][k] / BeratBuah * KarbohidratBuah;
                Karbohidrat[j + 5][k] = Berat[j + 5][k] / BeratLemak * KarbohidratLemak;
                Karbohidrat[j + 6][k] = Berat[j + 6][k] / BeratGula * KarbohidratGula;
                Karbohidrat[j + 7][k] = Berat[j + 7][k] / BeratSusu * KarbohidratSusu;
            }
        }
        return Karbohidrat;
    }

    public double[] HitungRataRataKarbohidratPerHari(double[] KebutuhanKarbohidrat, double[][] Karbohidrat) {
        int StringLenChromosome = Karbohidrat.length;
        int JmlOrang = Karbohidrat[0].length;
        //menghitung selisih kalori
        double[] KarbohidratTotal = new double[JmlOrang];
        int hari = StringLenChromosome / 24;

        for (int k = 0; k < JmlOrang; k++) {
            for (int j = 0; j < StringLenChromosome; j++) {
                KarbohidratTotal[k] = KarbohidratTotal[k] + Karbohidrat[j][k];
            }
            KarbohidratTotal[k] = KarbohidratTotal[k] / hari;
        }
        return KarbohidratTotal;
    }

    public double[][] HitungProteinPerBMPerOrang(int[] IndexBM, double[][] Berat) {
        int StringLenChromosome = Berat.length;
        int JmlOrang = Berat[0].length;
        double[][] Protein = new double[StringLenChromosome][JmlOrang];
        //menghitung kandungan gizi bahan makanan untuk setiap anggota keluarga

        for (int j = 0; j < StringLenChromosome; j = j + 8) {
            for (int k = 0; k < JmlOrang; k++) {
                //query berat bahan makanan
                int BeratKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int BeratProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int BeratProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int BeratSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int BeratBuah = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int BeratLemak = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int BeratGula = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int BeratSusu = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                //query kalori
                int ProteinKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int ProteinProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int ProteinProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int ProteinSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int ProteinBuah = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int ProteinLemak = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int ProteinGula = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int ProteinSusu = db.HasilQuery2(DatabaseHelper.COLUMN_PROTEIN, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                Protein[j][k] = Berat[j][k] / BeratKarbohidrat * ProteinKarbohidrat;
                Protein[j + 1][k] = Berat[j + 1][k] / BeratProteinHewani * ProteinProteinHewani;
                Protein[j + 2][k] = Berat[j + 2][k] / BeratProteinNabati * ProteinProteinNabati;
                Protein[j + 3][k] = Berat[j + 3][k] / BeratSayuranB * ProteinSayuranB;
                Protein[j + 4][k] = Berat[j + 4][k] / BeratBuah * ProteinBuah;
                Protein[j + 5][k] = Berat[j + 5][k] / BeratLemak * ProteinLemak;
                Protein[j + 6][k] = Berat[j + 6][k] / BeratGula * ProteinGula;
                Protein[j + 7][k] = Berat[j + 7][k] / BeratSusu * ProteinSusu;
            }
        }
        return Protein;
    }

    public double[] HitungRataRataProteinPerHari(double[] KebutuhanProtein, double[][] Protein) {
        int StringLenChromosome = Protein.length;
        int JmlOrang = Protein[0].length;
        //menghitung selisih kalori
        double[] ProteinTotal = new double[JmlOrang];
        int hari = StringLenChromosome / 24;

        for (int k = 0; k < JmlOrang; k++) {
            for (int j = 0; j < StringLenChromosome; j++) {
                ProteinTotal[k] = ProteinTotal[k] + Protein[j][k];
            }
            ProteinTotal[k] = ProteinTotal[k] / hari;
        }
        return ProteinTotal;
    }

    public double[][] HitungLemakPerBMPerOrang(int[] IndexBM, double[][] Berat) {
        int StringLenChromosome = Berat.length;
        int JmlOrang = Berat[0].length;
        double[][] Lemak = new double[StringLenChromosome][JmlOrang];
        //menghitung kandungan gizi bahan makanan untuk setiap anggota keluarga

        for (int j = 0; j < StringLenChromosome; j = j + 8) {
            for (int k = 0; k < JmlOrang; k++) {
                //query berat bahan makanan
                int BeratKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int BeratProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int BeratProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int BeratSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int BeratBuah = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int BeratLemak = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int BeratGula = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int BeratSusu = db.HasilQuery2(DatabaseHelper.COLUMN_BERAT, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                //query kalori 
                int LemakKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

                int LemakProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

                int LemakProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

                int LemakSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

                int LemakBuah  = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

                int LemakLemak = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

                int LemakGula = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

                int LemakSusu = db.HasilQuery2(DatabaseHelper.COLUMN_LEMAK, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

                Lemak[j][k] = Berat[j][k] / BeratKarbohidrat * LemakKarbohidrat;
                Lemak[j + 1][k] = Berat[j + 1][k] / BeratProteinHewani * LemakProteinHewani;
                Lemak[j + 2][k] = Berat[j + 2][k] / BeratProteinNabati * LemakProteinNabati;
                Lemak[j + 3][k] = Berat[j + 3][k] / BeratSayuranB * LemakSayuranB;
                Lemak[j + 4][k] = Berat[j + 4][k] / BeratBuah * LemakBuah;
                Lemak[j + 5][k] = Berat[j + 5][k] / BeratLemak * LemakLemak;
                Lemak[j + 6][k] = Berat[j + 6][k] / BeratGula * LemakGula;
                Lemak[j + 7][k] = Berat[j + 7][k] / BeratSusu * LemakSusu;
            }
        }
        return Lemak;
    }

    public double[] HitungRataRataLemakPerHari(double[] KebutuhanLemak, double[][] Lemak) {
        int StringLenChromosome = Lemak.length;
        int JmlOrang = Lemak[0].length;
        //menghitung selisih kalori
        double[] LemakTotal = new double[JmlOrang];
        int hari = StringLenChromosome / 24;

        for (int k = 0; k < JmlOrang; k++) {
            for (int j = 0; j < StringLenChromosome; j++) {
                LemakTotal[k] = LemakTotal[k] + Lemak[j][k];
            }
            LemakTotal[k] = LemakTotal[k] / hari;
        }
        return LemakTotal;
    }

    public double HitungFitnessGizi(double[] Energi, double[] Karbohidrat,
            double[] Protein, double[] Lemak, double[] KaloriTotalPerOrg, double[] KarbohidratTotalPerOrg,
            double[] ProteinTotalPerOrg, double[] LemakTotalPerOrg, int hari) {
        int JmlOrang = Energi.length;
        double KebEnergiTotal = 0, KebKarbohidratTotal = 0, KebProteinTotal = 0, KebLemakTotal = 0;
        double KaloriTotal = 0, KarbohidratTotal = 0, ProteinTotal = 0, LemakTotal = 0;
        for (int i = 0; i < JmlOrang; i++) {
            KebEnergiTotal = KebEnergiTotal + Energi[i];
            KebKarbohidratTotal = KebKarbohidratTotal + Karbohidrat[i];
            KebProteinTotal = KebProteinTotal + Protein[i];
            KebLemakTotal = KebLemakTotal + Lemak[i];
            KaloriTotal = KaloriTotal + KaloriTotalPerOrg[i];
            KarbohidratTotal = KarbohidratTotal + KarbohidratTotalPerOrg[i];
            ProteinTotal = ProteinTotal + ProteinTotalPerOrg[i];
            LemakTotal = LemakTotal + LemakTotalPerOrg[i];
        }

        //menghitung nilai fitness     
        double PenaltiGizi = (100000 / ((Math.abs(KebEnergiTotal - KaloriTotal)) + (Math.abs(KebKarbohidratTotal - KarbohidratTotal))
                + (Math.abs(KebProteinTotal - ProteinTotal)) + (Math.abs(KebLemakTotal - LemakTotal))))/4;
        return PenaltiGizi;
    }

    public double[] HitungHargaBM(int[] IndexBM, double[] BeratTotal) {
        int StringLenChromosome = IndexBM.length;
        double[] Harga = new double[StringLenChromosome];

        for (int j = 0; j < StringLenChromosome; j = j + 8) {
            int HargaKarbohidrat = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_KARBOHIDRT, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j]);

            int HargaProteinHewani = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_PROTEIN_HEWANI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 1]);

            int HargaProteinNabati = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_PROTEIN_NABATI, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 2]);

            int HargaSayuranB = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_SAYURANB, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 3]);

            int HargaBuah = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_BUAH, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 4]);

            int HargaLemak = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_LEMAK, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 5]);

            int HargaGula = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_GULA, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 6]);

            int HargaSusu = db.HasilQuery2(DatabaseHelper.COLUMN_HARGA, DatabaseHelper.TABLE_SUSU, DatabaseHelper.COLUMN_INDEX_BM, IndexBM[j + 7]);

            //menghitung harga setiap index bahan makanan sejumlah berat total
            Harga[j] = BeratTotal[j] / 1000 * HargaKarbohidrat;
            Harga[j + 1] = BeratTotal[j + 1] / 1000 * HargaProteinHewani;
            Harga[j + 2] = BeratTotal[j + 2] / 1000 * HargaProteinNabati;
            Harga[j + 3] = BeratTotal[j + 3] / 1000 * HargaSayuranB;
            Harga[j + 4] = BeratTotal[j + 4] / 1000 * HargaBuah;
            Harga[j + 5] = BeratTotal[j + 5] / 1000 * HargaLemak;
            Harga[j + 6] = BeratTotal[j + 6] / 1000 * HargaGula;
            Harga[j + 7] = BeratTotal[j + 7] / 1000 * HargaSusu;
        }
        return Harga;
    }

    public double HitungHargaTotalBM(double[] Harga) {
        //menghitung harga total
        int StringLenChromosome = Harga.length;
        double HargaTotal = 0;

        for (int j = 0; j < StringLenChromosome; j++) {
            HargaTotal = HargaTotal + Harga[j];
        }
        return HargaTotal;
    }

    public double HitungFitnessHarga(double HargaTotalBM) {
        //double FitnessHarga = (AlokasiDana*hari-HargaTotalBM)/1000;
        double FitnessHarga = 10000000 / HargaTotalBM;
        return FitnessHarga;
    }

    public int HitungVariasi(String[] NamaBM) {
        //menghitung variasi  
        int StringLenChromosome = NamaBM.length;
        int Variasi = 0;

        for (int j = 0; j < StringLenChromosome; j++) {
            int Beda = 0;
            for (int k = 0; k < StringLenChromosome; k++) {
                if (!(NamaBM[j].equalsIgnoreCase(NamaBM[k]))) {
                    Beda = Beda + 1;
                }
            }
            if (Beda == (StringLenChromosome - 1)) {
                Variasi = Variasi + 1;
            }
        }
        //ariasi = Variasi*10;
        return Variasi;
    }

    public double HitungFitness(int[] Individu, int[] Type, String[] JK, double[] Energi, double[] Karbohidrat,
            double[] Protein, double[] Lemak) {
        int hari = Individu.length / 24;
        ////mengkonversi angka permutasi menjadi index bahan makanan
        int[] IndexBM = this.KonversiIndexBM(Individu);
        //mengkonversi index bahan makanan menjadi nama bahan makanan
        String[] NamaBM = this.KonversiNamaBM(IndexBM);
        //menghitung berat masing-masing bahan makanan untuk setiap orang
        double[][] Berat = this.HitungBeratBMPerOrang(IndexBM, Type, JK);
        //menghitung berat total setiap bahan makanan untuk satu keluarga
        double[] BeratTotal = this.HitungBeratTotal(Berat);
        //menghitung kandungan kalori setiap bahan makanan untuk setiap orang
        double[][] KandunganKalori = this.HitungKaloriPerBMPerOrang(IndexBM, Berat);
        //menghitung selisih kalori yang dikonsumsi dengan kalori yang dibutuhkan
        double[] KaloriTotalPerOrg = this.HitungRataRataKaloriPerHari(Energi, KandunganKalori);
        //menghitung kandungan karbohidrat setiap bahan makanan untuk setiap orang
        double[][] KandunganKarbohidrat = this.HitungKarbohidratPerBMPerOrang(IndexBM, Berat);
        //menghitung selisih karbohidrat yang dikonsumsi dengan karbohidrat yang dibutuhkan 
        double[] KarbohidratTotalPerOrg = this.HitungRataRataKarbohidratPerHari(Karbohidrat, KandunganKarbohidrat);
        //menghitung kandungan protein setiap bahan makanan untuk setiap orang
        double[][] KandunganProtein = this.HitungProteinPerBMPerOrang(IndexBM, Berat);
        //menghitung selisih protein yang dikonsumsi dengan protein yang dibutuhkan
        double[] ProteinTotalPerOrg = this.HitungRataRataProteinPerHari(Protein, KandunganProtein);
        //menghitung kandungan lemak setiap bahan makanan untuk setiap orang
        double[][] KandunganLemak = this.HitungLemakPerBMPerOrang(IndexBM, Berat);
        //menghitung selisih lemak yang dikonsumsi dengan lemak yang dibutuhkan
        double[] LemakTotalPerOrg = this.HitungRataRataLemakPerHari(Lemak, KandunganLemak);
        //menghitung harga setiap bahan makanan sesuai dengan berat total untuk satu keluarga
        double FitnessGizi = this.HitungFitnessGizi(Energi, Karbohidrat, Protein, Lemak, KaloriTotalPerOrg, KarbohidratTotalPerOrg, ProteinTotalPerOrg, LemakTotalPerOrg, hari);
        double[] Harga = this.HitungHargaBM(IndexBM, BeratTotal);
        //menghitung harga total bahan makanan
        double HargaTotal = this.HitungHargaTotalBM(Harga);
        double FitnessHarga = this.HitungFitnessHarga(HargaTotal);
        int Variasi = this.HitungVariasi(NamaBM);//menghitung variasi bahan makanan

        //menghitung nilai fitness     
        double Fitness = FitnessGizi + FitnessHarga + Variasi;
        return Fitness;
    }

    public void CetakHasilIndividu(int[] Individu, int[] Type, String[] JK, double[] Energi, double[] Karbohidrat,
            double[] Protein, double[] Lemak) throws SQLException {

        int StringLenChromosome = Individu.length;
        int JmlOrang = Type.length;
        int hari = StringLenChromosome / 24;
        int[] IndexBM = this.KonversiIndexBM(Individu); ////mengkonversi angka permutasi menjadi index bahan makanan
        String[] NamaBM = this.KonversiNamaBM(IndexBM);//mengkonversi index bahan makanan menjadi nama bahan makanan
        double[][] Berat = this.HitungBeratBMPerOrang(IndexBM, Type, JK);//menghitung berat masing-masing bahan makanan untuk setiap orang
        double[] BeratTotal = this.HitungBeratTotal(Berat);//menghitung berat total setiap bahan makanan untuk satu keluarga
        double[][] KandunganKalori = this.HitungKaloriPerBMPerOrang(IndexBM, Berat);//menghitung kandungan kalori setiap bahan makanan untuk setiap orang
        double[] KaloriTotalPerOrg = this.HitungRataRataKaloriPerHari(Energi, KandunganKalori);//menghitung selisih kalori yang dikonsumsi dengan kalori yang dibutuhkan
        double[][] KandunganKarbohidrat = this.HitungKarbohidratPerBMPerOrang(IndexBM, Berat);//menghitung kandungan karbohidrat setiap bahan makanan untuk setiap orang
        double[] KarbohidratTotalPerOrg = this.HitungRataRataKarbohidratPerHari(Karbohidrat, KandunganKarbohidrat);//menghitung selisih karbohidrat yang dikonsumsi dengan karbohidrat yang dibutuhkan 
        double[][] KandunganProtein = this.HitungProteinPerBMPerOrang(IndexBM, Berat);//menghitung kandungan protein setiap bahan makanan untuk setiap orang
        double[] ProteinTotalPerOrg = this.HitungRataRataProteinPerHari(Protein, KandunganProtein);//menghitung selisih protein yang dikonsumsi dengan protein yang dibutuhkan
        double[][] KandunganLemak = this.HitungLemakPerBMPerOrang(IndexBM, Berat);//menghitung kandungan lemak setiap bahan makanan untuk setiap orang
        double[] LemakTotalPerOrg = this.HitungRataRataLemakPerHari(Lemak, KandunganLemak);//menghitung selisih lemak yang dikonsumsi dengan lemak yang dibutuhkan
        double FitnessGizi = this.HitungFitnessGizi(Energi, Karbohidrat, Protein, Lemak, KaloriTotalPerOrg, KarbohidratTotalPerOrg, ProteinTotalPerOrg, LemakTotalPerOrg, hari);
        double[] Harga = this.HitungHargaBM(IndexBM, BeratTotal);//menghitung harga setiap bahan makanan sesuai dengan berat total untuk satu keluarga
        double HargaTotal = this.HitungHargaTotalBM(Harga);//menghitung harga total bahan makanan
        int Variasi = this.HitungVariasi(NamaBM);//menghitung variasi bahan makanan

        double Fitness = this.HitungFitness(Individu, Type, JK, Energi, Karbohidrat, Protein, Lemak);

        int Hari = 1;
        //int Hari = StringLenChromosome/24;
        System.out.println("=============================================================================="
                + "=========================================================");
        for (int j = 0; j < StringLenChromosome; j++) {
            if (j == 0 || ((j % 24) == 0)) {
                System.out.println("Hari ke-" + Hari);
                System.out.println("Makan Pagi");
                Hari++;
            } else if (j != 0) {
                if (j == 8 || ((j % 32) == 0)) {
                    System.out.println("Makan Siang");
                }
                if (j == 16 || ((j % 40) == 0)) {
                    System.out.println("Makan Malam");
                }
            }
            System.out.println("Gen   : " + Individu[j] + "|    "
                    + "Index Bahan Makanan  : " + IndexBM[j] + "|     "
                    + "Nama Bahan Makanan   : " + NamaBM[j] + "|       ");
            for (int k = 0; k < JmlOrang; k++) {
                System.out.printf("Takaran Orang ke-" + (k + 1) + ": %.3f gram|       ", Berat[j][k]);
                System.out.printf("Kandungan Kalori : %.3f kkal|      ", KandunganKalori[j][k]);
                System.out.printf("Kandungan Karbohidrat : %.3f gram|      ", KandunganKarbohidrat[j][k]);
                System.out.printf("Kandungan Protein : %.3f gram|      ", KandunganProtein[j][k]);
                System.out.printf("Kandungan Lemak : %.3f gram|      ", KandunganLemak[j][k]);
                System.out.println("");
            }
            System.out.printf("Berat Total : %.3f gram    \n", BeratTotal[j]);
            System.out.printf("Harga Total : Rp %.2f \n", Harga[j]);
            System.out.println();
        }
        System.out.println("Kandungan Gizi :");
        for (int i = 0; i < JmlOrang; i++) {
            System.out.printf("Selisih Kalori Total Orang ke- " + i + "         : %.3f% kkal \n", (Energi[i] - KaloriTotalPerOrg[i]) / Energi[i] * 100);
            System.out.printf("Selisih Karbohidrat Total Orang ke- " + i + "    : %.3f% gram \n", (Karbohidrat[i] - KarbohidratTotalPerOrg[i]) / Karbohidrat[i] * 100);
            System.out.printf("Selisih Protein Total Orang ke- " + i + "        : %.3f% gram \n", (Protein[i] - ProteinTotalPerOrg[i]) / Protein[i] * 100);
            System.out.printf("Selisih Lemak Total Orang ke- " + i + "          : %.3f% gram \n", (Lemak[i] - LemakTotalPerOrg[i]) / Lemak[i] * 100);
            System.out.println("");
        }
        System.out.printf("Fitness Gizi                 : %.3f\n", FitnessGizi);
        System.out.printf("Harga Total                  : Rp %.2f \n", HargaTotal);
        System.out.printf("Fitness Harga                : %.2f \n", (1000000 / HargaTotal));
        System.out.printf("Variasi                      : " + Variasi + "\n");
        System.out.printf("Nilai Fitness                : %.3f \n", Fitness);
        System.out.println("=============================================================================="
                + "=========================================================");
        System.out.println();
    }

    public String CetakHasilIndividu2(int[] Individu, int[] Type, String[] JK, double[] Energi, double[] Karbohidrat,
            double[] Protein, double[] Lemak) {

        int StringLenChromosome = Individu.length;
        int JmlOrang = Type.length;
        int hari = StringLenChromosome / 24;
        int[] IndexBM = this.KonversiIndexBM(Individu); ////mengkonversi angka permutasi menjadi index bahan makanan
        String[] NamaBM = this.KonversiNamaBM(IndexBM);//mengkonversi index bahan makanan menjadi nama bahan makanan
        double[][] Berat = this.HitungBeratBMPerOrang(IndexBM, Type, JK);//menghitung berat masing-masing bahan makanan untuk setiap orang
        double[] BeratTotal = this.HitungBeratTotal(Berat);//menghitung berat total setiap bahan makanan untuk satu keluarga
        double[][] KandunganKalori = this.HitungKaloriPerBMPerOrang(IndexBM, Berat);//menghitung kandungan kalori setiap bahan makanan untuk setiap orang
        double[] KaloriTotalPerOrg = this.HitungRataRataKaloriPerHari(Energi, KandunganKalori);//menghitung selisih kalori yang dikonsumsi dengan kalori yang dibutuhkan
        double[][] KandunganKarbohidrat = this.HitungKarbohidratPerBMPerOrang(IndexBM, Berat);//menghitung kandungan karbohidrat setiap bahan makanan untuk setiap orang
        double[] KarbohidratTotalPerOrg = this.HitungRataRataKarbohidratPerHari(Karbohidrat, KandunganKarbohidrat);//menghitung selisih karbohidrat yang dikonsumsi dengan karbohidrat yang dibutuhkan 
        double[][] KandunganProtein = this.HitungProteinPerBMPerOrang(IndexBM, Berat);//menghitung kandungan protein setiap bahan makanan untuk setiap orang
        double[] ProteinTotalPerOrg = this.HitungRataRataProteinPerHari(Protein, KandunganProtein);//menghitung selisih protein yang dikonsumsi dengan protein yang dibutuhkan
        double[][] KandunganLemak = this.HitungLemakPerBMPerOrang(IndexBM, Berat);//menghitung kandungan lemak setiap bahan makanan untuk setiap orang
        double[] LemakTotalPerOrg = this.HitungRataRataLemakPerHari(Lemak, KandunganLemak);//menghitung selisih lemak yang dikonsumsi dengan lemak yang dibutuhkan
        double FitnessGizi = this.HitungFitnessGizi(Energi, Karbohidrat, Protein, Lemak, KaloriTotalPerOrg, KarbohidratTotalPerOrg, ProteinTotalPerOrg, LemakTotalPerOrg, hari);
        double[] Harga = this.HitungHargaBM(IndexBM, BeratTotal);//menghitung harga setiap bahan makanan sesuai dengan berat total untuk satu keluarga
        double HargaTotal = this.HitungHargaTotalBM(Harga);//menghitung harga total bahan makanan
        double FitnessHarga = this.HitungFitnessHarga(HargaTotal);
        int Variasi = this.HitungVariasi(NamaBM);//menghitung variasi bahan makanan
        double Fitness = this.HitungFitness(Individu, Type, JK, Energi, Karbohidrat, Protein, Lemak);
        double KebEnergiTotal = 0;
        double KebKarbohidratTotal = 0;
        double KebProteinTotal = 0;
        double KebLemakTotal = 0;
        double KaloriTotal = 0;
        double KarbohidratTotal = 0;
        double ProteinTotal = 0;
        double LemakTotal = 0;

        int Hari = 1;
        String Hasil = "";
        String Day = "";
        String Waktu = "";
        String WaktuMakan = "";
        for (int j = 0; j < StringLenChromosome; j++) {
            if (j == 0 || ((j % 24) == 0)) {
                Day = "Hari ke-" + Hari + "\n";
                WaktuMakan = "Makan Pagi\n";
                Waktu = Day + WaktuMakan;
                Hari++;
            } else if (j != 0) {
                if (j == 8 || ((j % 32) == 0)) {
                    WaktuMakan = "Makan Siang\n";
                    Waktu = WaktuMakan;
                } else if (j == 16 || ((j % 40) == 0)) {
                    WaktuMakan = "Makan Malam\n";
                    Waktu = WaktuMakan;
                } else {
                    Waktu = "";
                }
            }
            String Bm = ("Gen   : " + Individu[j] + "|    "
                    + "Index Bahan Makanan  : " + IndexBM[j] + "|     "
                    + "Nama Bahan Makanan   : " + NamaBM[j] + "|       \n");
            String Kandungan = "";
            for (int k = 0; k < JmlOrang; k++) {
                String Takaran = String.format("Takaran Orang ke-" + (k + 1) + ": %.3f gram|       ", Berat[j][k]);
                String Kalori = String.format("Kandungan Kalori : %.3f kkal|      ", KandunganKalori[j][k]);
                String Kh = String.format("Kandungan Karbohidrat : %.3f gram|      ", KandunganKarbohidrat[j][k]);
                String Prot = String.format("Kandungan Protein : %.3f gram|      ", KandunganProtein[j][k]);
                String Lem = String.format("Kandungan Lemak : %.3f gram|      ", KandunganLemak[j][k]);
                String Spasi = ("\n");
                Kandungan = Kandungan + Takaran + Kalori + Kh + Prot + Lem + Spasi;
            }
            String BeratTot = String.format("Berat Total : %.3f gram    \n", BeratTotal[j]);
            String HargaTot = String.format("Harga Total : Rp %.2f \n", Harga[j]);
            System.out.println();

            Hasil = Hasil + Waktu + Bm + Kandungan + BeratTot + HargaTot + "\n";
        }
        String Selisih = "";
        for (int i = 0; i < JmlOrang; i++) {
            String SelisihKaloriPerOrg = String.format("Selisih Kalori Total Orang ke- " + (i + 1) + "         : " + (KaloriTotalPerOrg[i] - Energi[i]) + " kkal (%.1f persen) \n", (KaloriTotalPerOrg[i] - Energi[i]) / Energi[i] * 100);
            String SelisihKhPerOrg = String.format("Selisih Karbohidrat Total Orang ke- " + (i + 1) + "    : " + ((KarbohidratTotalPerOrg[i]) - Karbohidrat[i]) + " gram (%.1f persen) \n", ((KarbohidratTotalPerOrg[i]) - Karbohidrat[i]) / Karbohidrat[i] * 100);
            String SelisihProteinPerOrg = String.format("Selisih Protein Total Orang ke- " + (i + 1) + "        : " + ((ProteinTotalPerOrg[i]) - Protein[i]) + " gram (%.1f persen) \n", ((ProteinTotalPerOrg[i]) - Protein[i]) / Protein[i] * 100);
            String SelisihLemakPerOrg = String.format("Selisih Lemak Total Orang ke- " + (i + 1) + "          : " + ((LemakTotalPerOrg[i]) - Lemak[i]) + " gram (%.1f persen) \n", ((LemakTotalPerOrg[i]) - Lemak[i]) / Lemak[i] * 100);
            String Spasi = ("\n");
            Selisih = Selisih + SelisihKaloriPerOrg + SelisihKhPerOrg + SelisihProteinPerOrg + SelisihLemakPerOrg + Spasi;
        }
        for (int i = 0; i < JmlOrang; i++) {
            KebEnergiTotal = KebEnergiTotal + Energi[i];
            KebKarbohidratTotal = KebKarbohidratTotal + Karbohidrat[i];
            KebProteinTotal = KebProteinTotal + Protein[i];
            KebLemakTotal = KebLemakTotal + Lemak[i];
            KaloriTotal = KaloriTotal + KaloriTotalPerOrg[i];
            KarbohidratTotal = KarbohidratTotal + KarbohidratTotalPerOrg[i];
            ProteinTotal = ProteinTotal + ProteinTotalPerOrg[i];
            LemakTotal = LemakTotal + LemakTotalPerOrg[i];
        }
        String SelisihKaloriTotal = String.format("Selisih Kalori Total : " + (KaloriTotal - KebEnergiTotal) + " kkal (%.1f persen) \n", ((KaloriTotal - KebEnergiTotal) / KebEnergiTotal * 100));
        String SelisihKarbohidratTotal = String.format("Selisih Karbohidrat Total : " + (KarbohidratTotal - KebKarbohidratTotal) + " gram (%.1f persen) \n", ((KarbohidratTotal - KebKarbohidratTotal) / KebKarbohidratTotal * 100));
        String SelisihProteinTotal = String.format("Selisih Protein Total : " + (ProteinTotal - KebProteinTotal) + " gram (%.1f persen) \n", ((ProteinTotal - KebProteinTotal) / KebProteinTotal * 100));
        String SelisihLemakTotal = String.format("Selisih Lemak Total : " + (LemakTotal - KebLemakTotal) + " gram (%.1f persen) \n", ((LemakTotal - KebLemakTotal) / KebLemakTotal * 100));
        //String AlokasiDana2 = String.format("Alokasi Dana : %.3f\n", (AlokasiDana * hari));
        String HargaTotal2 = String.format("Harga Total : Rp %.2f \n", HargaTotal);
        String PenaltiGizi2 = String.format("Fitness Gizi : %.3f\n", FitnessGizi);
        String PenaltiHarga = String.format("Fitness Harga : %.2f \n", FitnessHarga);
        String Var = String.format("Variasi : " + Variasi + "\n");
        String Fit = String.format("Nilai Fitness : %.3f \n", Fitness);
        String.format("=============================================================================="
                + "=========================================================");
        System.out.println();
        Hasil = Hasil + Selisih + SelisihKaloriTotal + SelisihKarbohidratTotal + SelisihProteinTotal + SelisihLemakTotal +  HargaTotal2
                + PenaltiGizi2 + PenaltiHarga + Var + Fit + "\n";
        return Hasil;
    }

    public double[] SeleksiElitsm(double[] Fitness) {
        for (int i = 0; i < Fitness.length; i++) {
            double min = Fitness[i];
            int j = i;
            while ((j > 0) && (min > Fitness[j - 1])) {
                Fitness[j] = Fitness[j - 1];
                j--;
            }
            Fitness[j] = min;
        }
        return Fitness;
    }

    public int[] NeighborhoodSA(int[] Individu) {
        int StringLenChromosome = Individu.length;
        Random random = new Random();

        //memilih posisi pertukaran gen (neighborhood)
        int hari = StringLenChromosome / 24;
        int[] Exchange1 = new int[hari];
        int[] Exchange2 = new int[hari];

        int BatasBawahRand = 0, BatasAtasRand = 23;
        for (int j = 0; j < hari; j++) {
            boolean flag = true;
            while (flag) {
                Exchange1[j] = random.nextInt((BatasAtasRand - BatasBawahRand) + 1) + BatasBawahRand;
                Exchange2[j] = random.nextInt((BatasAtasRand - BatasBawahRand) + 1) + BatasBawahRand;
                if (Exchange1[j] != Exchange2[j]) {
                    flag = false;
                }
            }
            /*
            System.out.println("Exchange 1 Hari ke-" + (j + 1) + " : " + ((Exchange1[j]) + 1));
            System.out.println("Exchange 2 Hari ke-" + (j + 1) + " : " + ((Exchange2[j]) + 1));
            System.out.println();
            */
            BatasBawahRand = BatasBawahRand + 23;
            BatasAtasRand = BatasBawahRand + 23;
        }

        //copy array
        int[] HasilModif = new int[StringLenChromosome];

        for (int j = 0; j < StringLenChromosome; j++) {
            HasilModif[j] = Individu[j];
        }

        //proses menukar gen
        for (int j = 0; j < hari; j++) {
            HasilModif[Exchange1[j]] = Individu[Exchange2[j]];
            HasilModif[Exchange2[j]] = Individu[Exchange1[j]];
        }

        return HasilModif;
    }

    public int[] SimulatedAnnealing(double T0, double Alpha, double Tn, int[] Individu,
            int[] Type, String[] JK, double[] Energi, double[] Karbohidrat, double[] Protein,
            double[] Lemak) {

        int StringLenChromosome = Individu.length;
        double FitnessChild = 0;
        double FitnessNeighborhood;
        int suhu = 0;
        double TempAwal = T0;
        double TempAkh = Tn;
        while (TempAwal > TempAkh) {
            //System.out.println("Suhu T" + suhu);
            suhu = suhu + 1;
            //System.out.println("Proses Neighborhood");
            int[] IndividuNeighborhood = this.NeighborhoodSA(Individu); //proses neighborhood               

            //Proses menghitung perbandingan fitness antara populasi child dengan populasi hasil neihgborhood               
            FitnessChild = this.HitungFitness(Individu, Type, JK, Energi, Karbohidrat, Protein, Lemak);//untuk child 
            FitnessNeighborhood = this.HitungFitness(IndividuNeighborhood, Type, JK, Energi, Karbohidrat, Protein, Lemak);//untuk hasil neighborhood
            /*
            System.out.println("");
            System.out.println("Hasil Proses Neighborhood");
            System.out.println("Individu                    Kromosom                                            "
                    + "Fitness");

            for (int k = 0; k < StringLenChromosome; k++) {
                System.out.print(Individu[k] + " ");
            }
            System.out.printf(" | %.3f \n", FitnessChild);

            for (int k = 0; k < StringLenChromosome; k++) {
                System.out.print(IndividuNeighborhood[k] + " ");
            }
            System.out.printf(" | %.3f \n", FitnessNeighborhood);
            System.out.println("");
            */
            double SelisihFitness = FitnessNeighborhood - FitnessChild;
            //System.out.println("Selisih = " + SelisihFitness);
            //membandingan fitness child dengan fitness neighborhood
            if (SelisihFitness >= 0) {
                //menukar individu child dengan individu hasil neighborhood yang memiliki nilai fitness lebih besar

                Individu = IndividuNeighborhood;
                FitnessChild = FitnessNeighborhood;

            } else {
                //menghitung probabilitas P
                double P = Math.exp(SelisihFitness / TempAwal);
                //membangkitkan nilai random antara 0-1
                double R = Math.random();
                //membandingkan nilai P dan R   
                System.out.println("P = " + P + ", " + "R = " + R);
                if (P >= R) {
                    //menukar individu child dengan individu hasil neighborhood jika nilai P lebih besar dari R 
                    Individu = IndividuNeighborhood;
                    FitnessChild = FitnessNeighborhood;
                } else {
                    TempAwal = Alpha * TempAwal;
                }
            }
        }
        /*
        System.out.println("");
        System.out.println("Hasil Simulated Annealing");
        System.out.println("Individu                    Kromosom                                            "
                + "Fitness");
        for (int k = 0; k < StringLenChromosome; k++) {
            System.out.print(Individu[k] + " ");
        }
        System.out.printf(" | %.3f \n", FitnessChild);
        */
        return Individu;
    }




}

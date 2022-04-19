package net.gumcode.optimasimenumakanan.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.adapter.ColumnHeaderAdapter;
import net.gumcode.optimasimenumakanan.adapter.KeluargaColumnDataAdapter;
import net.gumcode.optimasimenumakanan.database.DatabaseHelper;
import net.gumcode.optimasimenumakanan.model.Keluarga;

import java.util.ArrayList;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;

/**
 * Created by A. Fauzi Harismawan on 8/9/2016.
 */
public class KeluargaActivity extends AppCompatActivity {

    private SortableTableView tableView;
    private AlertDialog dialog;
    private DatabaseHelper db;
    private ArrayList<Keluarga> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angkel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        tableView = (SortableTableView) findViewById(R.id.table);
        tableView.setHeaderAdapter(new ColumnHeaderAdapter(this, getResources().getStringArray(R.array.angkel_title)));
        tableView.addDataLongClickListener(new TableDataLongClickListener() {
            @Override
            public boolean onDataLongClicked(int rowIndex, Object clickedData) {
                showDelete(arr.get(rowIndex).id);
                return false;
            }
        });
        initTable();
    }

    private void initTable() {
        arr = db.getKeluarga();
        tableView.setDataAdapter(new KeluargaColumnDataAdapter(this, arr));
    }

    private void showDelete(final int id) {
        View header = getLayoutInflater().inflate(R.layout.dialog_header, null);
        TextView title = (TextView) header.findViewById(R.id.title);
        title.setText("HAPUS");

        View content = getLayoutInflater().inflate(R.layout.dialog_content, null);
        TextView text = (TextView) content.findViewById(R.id.text);
        text.setText("Apakah anda yakin ingin menghapus ?");

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
                db.deleteKeluarga(id);
                initTable();
                dialog.cancel();

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(KeluargaActivity.this);
        builder.setCustomTitle(header);
        builder.setView(content);

        dialog = builder.create();
        dialog.show();
    }

    private void showAlert() {
        View header = getLayoutInflater().inflate(R.layout.dialog_header, null);
        TextView title = (TextView) header.findViewById(R.id.title);
        title.setText("TAMBAH");

        View content = getLayoutInflater().inflate(R.layout.dialog_add, null);
        final EditText nama = (EditText) content.findViewById(R.id.nama);
        final EditText usia = (EditText) content.findViewById(R.id.usia);
        final Spinner jk = (Spinner) content.findViewById(R.id.jk);
        final EditText berat = (EditText) content.findViewById(R.id.berat);
        final EditText tinggi = (EditText) content.findViewById(R.id.tinggi);
        final Spinner aktivitas = (Spinner) content.findViewById(R.id.aktivitas);

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
                Keluarga a = new Keluarga();
                a.nama = nama.getText().toString();
                a.usia = Integer.valueOf(usia.getText().toString());
                if (jk.getSelectedItemPosition() == 0) {
                    a.jk = "L";
                } else {
                    a.jk = "P";
                }
                a.berat = Integer.valueOf(berat.getText().toString());
                a.tinggi = Integer.valueOf(tinggi.getText().toString());
                a.aktivitas = aktivitas.getSelectedItemPosition() + 1;

                db.insertKeluarga(a);
                initTable();
                dialog.cancel();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(KeluargaActivity.this);
        builder.setCustomTitle(header);
        builder.setView(content);

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showAlert();
                return true;
            case R.id.action_def:
                db.deleteAllKeluarga();

                Keluarga a1 = new Keluarga();
                a1.nama = "Ayah";
                a1.jk = "L";
                a1.usia = 54;
                a1.berat = 65;
                a1.tinggi = 160;
                a1.aktivitas = 3;
                db.insertKeluarga(a1);

                Keluarga a2 = new Keluarga();
                a2.nama = "Ibu";
                a2.jk = "P";
                a2.usia = 48;
                a2.berat = 50;
                a2.tinggi = 155;
                a2.aktivitas = 2;
                db.insertKeluarga(a2);

                Keluarga a3 = new Keluarga();
                a3.nama = "Anak 1";
                a3.jk = "L";
                a3.usia = 20;
                a3.berat = 60;
                a3.tinggi = 160;
                a3.aktivitas = 3;
                db.insertKeluarga(a3);

                Keluarga a4 = new Keluarga();
                a4.nama = "Anak 2";
                a4.jk = "P";
                a4.usia = 12;
                a4.berat = 30;
                a4.tinggi = 134;
                a4.aktivitas = 3;
                db.insertKeluarga(a4);

                initTable();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
package net.gumcode.optimasimenumakanan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.adapter.ColumnDataAdapter;
import net.gumcode.optimasimenumakanan.adapter.ColumnHeaderAdapter;
import net.gumcode.optimasimenumakanan.database.DatabaseHelper;

import de.codecrafters.tableview.SortableTableView;

/**
 * Created by A. Fauzi Harismawan on 8/9/2016.
 */
public class TableActivity extends AppCompatActivity {

    private SortableTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle recv = getIntent().getExtras();

        DatabaseHelper db = new DatabaseHelper(this);

        tableView = (SortableTableView) findViewById(R.id.table);
        tableView.setHeaderAdapter(new ColumnHeaderAdapter(this, getResources().getStringArray(R.array.header_title)));

        if (recv.getString("ASD").equals("HEWANI")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_PROTEIN_HEWANI)));
        } else if (recv.getString("ASD").equals("NABATI")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_PROTEIN_NABATI)));
        } else if (recv.getString("ASD").equals("LEMAK")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_LEMAK)));
        } else if (recv.getString("ASD").equals("KARBOHIDRAT")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_KARBOHIDRT)));
        } else if (recv.getString("ASD").equals("GULA")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_GULA)));
        } else if (recv.getString("ASD").equals("BUAH")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_BUAH)));
        } else if (recv.getString("ASD").equals("SAYUR")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_SAYURANB)));
        } else if (recv.getString("ASD").equals("SUSU")) {
            tableView.setDataAdapter(new ColumnDataAdapter(this, db.getBahan(DatabaseHelper.TABLE_SUSU)));
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_table, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_add:
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
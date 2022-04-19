package net.gumcode.optimasimenumakanan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.adapter.ColumnHeaderAdapter;
import net.gumcode.optimasimenumakanan.database.DatabaseHelper;

import de.codecrafters.tableview.SortableTableView;

/**
 * Created by A. Fauzi Harismawan on 8/9/2016.
 */
public class AKGActivity extends AppCompatActivity {

    private SortableTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseHelper db = new DatabaseHelper(this);
//        Queries q = new Queries(db);

        tableView = (SortableTableView) findViewById(R.id.table);
        tableView.setHeaderAdapter(new ColumnHeaderAdapter(this, getResources().getStringArray(R.array.akg_title)));
//        tableView.setDataAdapter(new AkgColumnDataAdapter(this, q.getAKG()))
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
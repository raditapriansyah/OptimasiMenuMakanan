package net.gumcode.optimasimenumakanan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.database.DatabaseHelper;
import net.gumcode.optimasimenumakanan.model.Ga;

/**
 * Created by A. Fauzi Harismawan on 8/9/2016.
 */
public class PsoActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText popSize, cr, mr, gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pso);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        initView();

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ga ga = new Ga();
                ga.popsize = Float.valueOf(popSize.getText().toString());
                ga.cr = Float.valueOf(cr.getText().toString());
                ga.mr = Float.valueOf(mr.getText().toString());
                ga.generasi = Float.valueOf(gen.getText().toString());
                db.setGa(ga);
                finish();
            }
        });
    }

    private void initView() {
        popSize = (EditText) findViewById(R.id.pop_size);
        cr = (EditText) findViewById(R.id.cr);
        mr = (EditText) findViewById(R.id.mr);
        gen = (EditText) findViewById(R.id.gen);

        Ga ga = db.getGa();
        popSize.setText(Float.toString(ga.popsize));
        cr.setText(Float.toString(ga.cr));
        mr.setText(Float.toString(ga.mr));
        gen.setText(Float.toString(ga.generasi));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_def:
                popSize.setText("5");
                cr.setText("0.6");
                mr.setText("0.4");
                gen.setText("3");
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
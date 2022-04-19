//package net.gumcode.optimasimenumakanan.activity;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import net.gumcode.optimasimenumakanan.R;
//import net.gumcode.optimasimenumakanan.database.DatabaseHelper;
//import net.gumcode.optimasimenumakanan.model.Sa;
//
///**
// * Created by A. Fauzi Harismawan on 8/9/2016.
// */
//public class SaActivity extends AppCompatActivity {
//
//    private DatabaseHelper db;
//    private EditText to, tn, alpha;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sa);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        db = new DatabaseHelper(this);
//
//        initView();
//
//        Button submit = (Button) findViewById(R.id.submit);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Sa sa = new Sa();
//                sa.to = Float.valueOf(to.getText().toString());
//                sa.tn = Float.valueOf(tn.getText().toString());
//                sa.alpha = Float.valueOf(alpha.getText().toString());
//                db.setSa(sa);
//                finish();
//            }
//        });
//    }
//
//    private void initView() {
//        to = (EditText) findViewById(R.id.to);
//        tn = (EditText) findViewById(R.id.tn);
//        alpha = (EditText) findViewById(R.id.alpha);
//
//        Sa sa = db.getSa();
//        to.setText(Float.toString(sa.to));
//        tn.setText(Float.toString(sa.tn));
//        alpha.setText(Float.toString(sa.alpha));
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_edit, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_def:
//                to.setText("1");
//                tn.setText("0.2");
//                alpha.setText("0.5");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return false;
//    }
//}
package net.gumcode.optimasimenumakanan.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.adapter.ViewPagerAdapter;
import net.gumcode.optimasimenumakanan.view.SlidingTabLayout;

/**
 * Created by A. Fauzi Harismawan on 11/17/2016.
 */
public class ResultActivity extends AppCompatActivity {

    private String[] titles = {"AKG", "HASIL", "KETERANGAN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initTabs();
    }

    private void initTabs() {
        Object localObject = new ViewPagerAdapter(getSupportFragmentManager(), titles, titles.length);
        ViewPager localViewPager = (ViewPager) findViewById(R.id.pager);
        localViewPager.setAdapter((PagerAdapter) localObject);
        localObject = findViewById(R.id.tabs);
        ((SlidingTabLayout) localObject).setDistributeEvenly(true);
        ((SlidingTabLayout) localObject).setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            public int getIndicatorColor(int paramInt) {
                return ResultActivity.this.getResources().getColor(R.color.colorPrimaryDark);
            }
        });
        ((SlidingTabLayout) localObject).setViewPager(localViewPager);
    }
}

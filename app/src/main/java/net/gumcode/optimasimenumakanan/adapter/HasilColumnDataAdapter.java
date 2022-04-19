package net.gumcode.optimasimenumakanan.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gumcode.optimasimenumakanan.model.Hasil;

import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by X2 on 01/09/2016.
 */
public class HasilColumnDataAdapter extends TableDataAdapter<Hasil> {


    public HasilColumnDataAdapter(Context context, List<Hasil> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
       Hasil m = getRowData(rowIndex);

        View renderedView = null;

        switch (columnIndex) {
            case 0:
                TextView textView = new TextView(getContext());
                textView.setText(Integer.toString(m.gen));
                textView.setPadding(20, 10, 20, 10);
                textView.setGravity(Gravity.CENTER);
                renderedView = textView;
                break;
            case 1:
                TextView textView1 = new TextView(getContext());
                textView1.setText(m.kromosom);
                textView1.setPadding(20, 10, 20, 10);
                textView1.setGravity(Gravity.CENTER);
                renderedView = textView1;
                break;
            case 2:
                TextView textView2 = new TextView(getContext());
                textView2.setText(String.format(Locale.US, "%.2f", m.fitness));
                textView2.setPadding(20, 10, 20, 10);
                textView2.setGravity(Gravity.CENTER);
                renderedView = textView2;
                break;
        }

        return renderedView;
    }
}

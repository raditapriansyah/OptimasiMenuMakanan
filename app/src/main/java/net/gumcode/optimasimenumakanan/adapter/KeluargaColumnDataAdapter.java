package net.gumcode.optimasimenumakanan.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gumcode.optimasimenumakanan.model.Keluarga;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by X2 on 01/09/2016.
 */
public class KeluargaColumnDataAdapter extends TableDataAdapter<Keluarga> {


    public KeluargaColumnDataAdapter(Context context, List<Keluarga> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Keluarga m = getRowData(rowIndex);

        View renderedView = null;

        switch (columnIndex) {
            case 0:
                TextView textView = new TextView(getContext());
                textView.setText(Integer.toString(rowIndex + 1));
                textView.setPadding(20, 10, 20, 10);
                textView.setGravity(Gravity.CENTER);
                renderedView = textView;
                break;
            case 1:
                TextView textView1 = new TextView(getContext());
                textView1.setText(m.nama);
                textView1.setPadding(20, 10, 20, 10);
                textView1.setGravity(Gravity.CENTER);
                renderedView = textView1;
                break;
            case 2:
                TextView textView2 = new TextView(getContext());
                textView2.setText(Integer.toString(m.usia));
                textView2.setPadding(20, 10, 20, 10);
                textView2.setGravity(Gravity.CENTER);
                renderedView = textView2;
                break;
            case 3:
                TextView textView3 = new TextView(getContext());
                textView3.setText(m.jk);
                textView3.setPadding(20, 10, 20, 10);
                textView3.setGravity(Gravity.CENTER);
                renderedView = textView3;
                break;
            case 4:
                TextView textView4 = new TextView(getContext());
                textView4.setText(Integer.toString(m.berat));
                textView4.setPadding(20, 10, 20, 10);
                textView4.setGravity(Gravity.CENTER);
                renderedView = textView4;
                break;
            case 5:
                TextView textView5 = new TextView(getContext());
                textView5.setText(Integer.toString(m.tinggi));
                textView5.setPadding(20, 10, 20, 10);
                textView5.setGravity(Gravity.CENTER);
                renderedView = textView5;
                break;
            case 6:
                TextView textView6 = new TextView(getContext());
                textView6.setText(Integer.toString(m.aktivitas));
                textView6.setPadding(20, 10, 20, 10);
                textView6.setGravity(Gravity.CENTER);
                renderedView = textView6;
                break;
        }

        return renderedView;
    }
}

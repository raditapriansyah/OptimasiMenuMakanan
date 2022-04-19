package net.gumcode.optimasimenumakanan.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gumcode.optimasimenumakanan.R;

/**
 * Created by A. Fauzi Harismawan on 11/17/2016.
 */
public class FragmentKeterangan extends Fragment {

    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_keterangan, null, false);
        text = (TextView) v.findViewById(R.id.ket);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        text.setText(preferences.getString("keterangan", ""));
    }
}

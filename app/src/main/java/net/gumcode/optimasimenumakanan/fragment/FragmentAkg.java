package net.gumcode.optimasimenumakanan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.adapter.AkgColumnDataAdapter;
import net.gumcode.optimasimenumakanan.adapter.ColumnHeaderAdapter;
import net.gumcode.optimasimenumakanan.database.DatabaseHelper;

import de.codecrafters.tableview.SortableTableView;

/**
 * Created by A. Fauzi Harismawan on 11/17/2016.
 */
public class FragmentAkg extends Fragment {

    private SortableTableView tableView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_akg, null, false);
        tableView = (SortableTableView) v.findViewById(R.id.table);
        tableView.setHeaderAdapter(new ColumnHeaderAdapter(getActivity(), getResources().getStringArray(R.array.akg_title)));
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(getActivity());

        tableView.setDataAdapter(new AkgColumnDataAdapter(getActivity(), db.getAkg()));
    }
}

package net.gumcode.optimasimenumakanan.view;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import net.gumcode.optimasimenumakanan.R;
import net.gumcode.optimasimenumakanan.database.DatabaseHelper;

/**
 * Created by A. Fauzi Harismawan on 5/10/2016.
 */
public class EditTextDialogPreference extends DialogPreference {

    private DatabaseHelper db;
    private EditText amount;

    public EditTextDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialog_edit_text);

        db = new DatabaseHelper(context);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        amount = (EditText) view.findViewById(R.id.amount);
        amount.setText(Integer.toString(db.getHari()));
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            if (!amount.getText().toString().equals("")) {
                db.setHari(Integer.valueOf(amount.getText().toString()));
            } else {
                amount.setError(getContext().getString(R.string.parameter));
                amount.requestFocus();
            }
        }
    }
}

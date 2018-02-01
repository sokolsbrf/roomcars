package ru.dimasokol.demo.roomcars;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * Add vendor
 */
public class AddVendorDialogFragment extends DialogFragment {

    private static final String TEXT_KEY = "text";

    private EditText mInput;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_add_vendor);

        mInput = new EditText(getContext());
        mInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(mInput);

        if (savedInstanceState != null) {
            mInput.setText(savedInstanceState.getString(TEXT_KEY, ""));
        }

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String vendorName = mInput.getText().toString().trim();

                if (!TextUtils.isEmpty(vendorName)) {
                    ((MainActivity) getActivity()).createVendor(vendorName);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mInput != null) {
            outState.putString(TEXT_KEY, mInput.getText().toString());
            mInput = null;
        }
    }
}

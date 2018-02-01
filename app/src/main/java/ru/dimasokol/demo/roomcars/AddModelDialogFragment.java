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
 * Диалог добавления модели
 */
public class AddModelDialogFragment extends DialogFragment {

    private static final String TEXT_KEY = "text";
    private static final String ARG_VENDOR = "vendor";

    public static AddModelDialogFragment newInstance(String vendorName) {
        AddModelDialogFragment fragment = new AddModelDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VENDOR, vendorName);

        fragment.setArguments(args);
        return fragment;
    }

    private EditText mInput;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_add_model, getArguments().getString(ARG_VENDOR)));

        mInput = new EditText(getContext());
        mInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(mInput);

        if (savedInstanceState != null) {
            mInput.setText(savedInstanceState.getString(TEXT_KEY, ""));
        }

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String modelName = mInput.getText().toString().trim();

                if (!TextUtils.isEmpty(modelName)) {
                    ((ModelsActivity) getActivity()).createModel(modelName);
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

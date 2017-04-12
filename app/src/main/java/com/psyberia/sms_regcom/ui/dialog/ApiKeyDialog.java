package com.psyberia.sms_regcom.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.helper.PreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by combo on 29.03.2017.
 */

public class ApiKeyDialog extends AlertDialog.Builder {

    private final ApiKeyDialog builder;
    @BindView(R.id.input_layout_key)
    TextInputLayout inputLayoutKey;

    @BindView(R.id.input_key)
    EditText inputKey;
    private PreferencesHelper mpm;


    public ApiKeyDialog(Context context) {
        super(context);
        builder = this;
        mpm = PreferencesHelper.getInstance();
        setTitle("Введите ключ доступа");
        setIcon(R.mipmap.ic_launcher);

        View content = LayoutInflater.from(context).inflate(R.layout.dialog_api_key, null);
        ButterKnife.bind(this, content);
        setView(content);
        inputKey.setText(mpm.getApiKey());

        setCancelable(false).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitForm();
                //dialog.cancel();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        create();
    }


    private void submitForm() {
        if (!validateKey()) {
            return;
        }
        mpm.setApiKey(inputKey.getText().toString().trim());
        Toast.makeText(getContext(), "Ключ сохранен", Toast.LENGTH_SHORT).show();
    }

    private boolean validateKey() {
        if (inputKey.getText().toString().trim().isEmpty()) {
            inputLayoutKey.setError(getContext().getString(R.string.err_msg_key));
            requestFocus(inputKey);
            return false;
        } else {
            inputLayoutKey.setEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            // ApiKeyDialog.this.dialog.getWindow().setSoftInputMode(
            // WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}

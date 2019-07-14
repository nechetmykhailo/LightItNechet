package com.example.lightitnechet.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.lightitnechet.R;
import com.example.lightitnechet.singleton.User;

public class EditProfileDialog extends DialogFragment {

    private EditText etNameEditProf;
    private EditText etLastNameEditProf;
    private Button btnOK;

    private String name;
    private String lastName;

    private DismissListener dismissListener;

    public interface DismissListener{
        void onDismiss();
    }

    public void setDismissListener(DismissListener dismissListener){
        this.dismissListener = dismissListener;
    }

    public EditProfileDialog(){
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_editprofile, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setView(view);

        etNameEditProf = view.findViewById(R.id.etNameEditProf);
        etLastNameEditProf = view.findViewById(R.id.etLastNameEditProf);
        btnOK = view.findViewById(R.id.btnOK);

        etLastNameEditProf.setText(User.getInstance().getLastName());
        etNameEditProf.setText(User.getInstance().getName());

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastName = etLastNameEditProf.getText().toString();
                name = etNameEditProf.getText().toString();

                if(User.getInstance().getToken() == null || lastName.equals("") || name.equals("") ){
                    Toast.makeText(getContext(),"Все поля должны быть заполнены!",Toast.LENGTH_SHORT);
                }else {
                    User.getInstance().setName(name);
                    User.getInstance().setLastName(lastName);
                    dismiss();
                }
            }
        });

        Dialog dialog = adb.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        dismissListener.onDismiss();
    }
}


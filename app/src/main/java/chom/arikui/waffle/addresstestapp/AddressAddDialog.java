package chom.arikui.waffle.addresstestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddressAddDialog extends DialogFragment {

    private EditText editName;
    private EditText editPhoneNum;
    private EditText editFaxNum;
    private EditText editEmail;
    private EditText editNote;
    private Button buttonAdd;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.layout_address_add_dialog, null);
        editName = layout.findViewById(R.id.edit_name);
        editPhoneNum = layout.findViewById(R.id.edit_phone_num);
        editFaxNum = layout.findViewById(R.id.edit_fax_num);
        editEmail = layout.findViewById(R.id.edit_email);
        editNote = layout.findViewById(R.id.edit_note);
        buttonAdd = layout.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();
                try {
                    object.put("id", UserState.Id);
                    object.put("password", UserState.PASSWORD);
                    object.put("name", editName.getText().toString());
                    object.put("phone_num", editPhoneNum.getText().toString());
                    object.put("fax_num", editFaxNum.getText().toString());
                    object.put("mail_address", editEmail.getText().toString());
                    object.put("note", editNote.getText().toString());

                    AsyncNetworkTaskPut taskPut = new AsyncNetworkTaskPut (getActivity(), AsyncNetworkTaskPut.ADD);
                    taskPut.execute(MainActivity.SERVER_URL, object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        return builder.setView(layout).setTitle("連絡先の追加").create();
    }
}

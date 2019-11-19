package chom.arikui.waffle.addresstestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginDialog extends DialogFragment {

    private MainActivity mainActivity;
    private TextView textCheckId;
    private TextView textCheckPassword;

    private String id;
    private String password;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.layout_login_dialog, null);

        final TextView textId = layout.findViewById(R.id.text_id);
        final TextView textPassword = layout.findViewById(R.id.text_password);
        textCheckId = layout.findViewById(R.id.text_check_id);
        textCheckPassword = layout.findViewById(R.id.text_check_password);

        final EditText editId = layout.findViewById(R.id.edit_id);
        final EditText editPassword = layout.findViewById(R.id.edit_password);
        Button buttonLogin = layout.findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCheckId.setVisibility(View.GONE);
                textCheckPassword.setVisibility(View.GONE);

                textId.setTextColor(Color.BLUE);
                textPassword.setTextColor(Color.BLUE);

                id = editId.getText().toString();
                password = editPassword.getText().toString();

                if (id.length() == 0 || password.length() == 0) {

                    if (id.length() == 0) {

                        textCheckId.setText("入力してください");
                        textCheckId.setTextSize(15);
                        textCheckId.setTextColor(Color.RED);
                        textCheckId.setVisibility(View.VISIBLE);
                    }

                    if (password.length() == 0) {

                        textCheckPassword.setText("入力してください");
                        textCheckPassword.setTextSize(15);
                        textCheckPassword.setTextColor(Color.RED);
                        textCheckPassword.setVisibility(View.VISIBLE);
                    }

                    return;
                }

                if (id.matches(".*[^a-zA-Z0-9._].*") || password.matches(".*[^a-zA-Z0-9._].*")) {

                    if (id.matches(".*[^a-zA-Z0-9._].*")) {

                        textCheckId.setText("使用不可な文字が含まれています");
                        textCheckId.setTextSize(15);
                        textCheckId.setTextColor(Color.RED);
                        textCheckId.setVisibility(View.VISIBLE);

                        textId.setTextColor(Color.RED);
                    }

                    if (password.matches(".*[^a-zA-Z0-9._].*")) {

                        textCheckPassword.setText("使用不可な文字が含まれています");
                        textCheckPassword.setTextSize(15);
                        textCheckPassword.setTextColor(Color.RED);
                        textCheckPassword.setVisibility(View.VISIBLE);

                        textPassword.setTextColor(Color.RED);
                    }

                    return;
                }

                JSONObject object = new JSONObject();
                try {
                    object.put("id", id);
                    object.put("password", password);
                    AsyncNetworkTaskPost taskPost = new AsyncNetworkTaskPost(mainActivity, AsyncNetworkTaskPost.GET_ADDRESS_DATA, LoginDialog.this);
                    taskPost.execute(MainActivity.SERVER_URL, object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity, R.style.MyAlertDialogStyle);

        this.setCancelable(false);

        return builder.setView(layout).setTitle("ログインフォーム").create();
    }

    public void setIdAndPassword () {
        UserState.Id = id;
        UserState.PASSWORD = password;
    }

}

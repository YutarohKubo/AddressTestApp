package chom.arikui.waffle.addresstestapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yutaro on 2017/09/21.
 */

public class AsyncNetworkTaskPost extends AsyncTask<String, Integer, String> {

    private static final String TAG = "Task_Post";

    public static final String LOGIN = "login";
    public static final String GET_ADDRESS_DATA = "get_address_data";

    private static final String SUCCESS = "SUCCESS";
    private static final String DUPLICATE = "DUPLICATE";
    private static final String FAILED = "FAILED";

    private DataLoadDialog dialogLoading;

    private MainActivity mainActivity;
    private String type;
    private String userId;
    private DialogFragment dialog;
    private ListView listAddress;

    public AsyncNetworkTaskPost(Context context, String type, DialogFragment dialog) {
        super();
        if (context instanceof MainActivity) {

            mainActivity = (MainActivity) context;
        }
        this.type = type;
        this.dialog = dialog;
    }


    @Override
    protected String doInBackground(String... params) {

        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(params[0] + "?type=" + type);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            PrintStream ps = new PrintStream(os);
            //ps.print(params[1] + "$%$&" + params[2] + "$%$&" + params[3]);
            ps.print(params[1]);
            ps.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            //content = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        publishProgress(100);

        Log.i("AsyncNetworkTaskPost", builder.toString());

        return builder.toString();
    }

    @Override
    protected void onPreExecute() {

        if (type.equals(GET_ADDRESS_DATA)) {

            dialogLoading = new DataLoadDialog();
            dialogLoading.setCancelable(true);
            dialogLoading.show(mainActivity.getFragmentManager(), "data_loading_fragment");
        }

    }

    @Override
    protected void onPostExecute(String result) {

        switch (type) {

            case GET_ADDRESS_DATA:

            if (result.equals(FAILED)) {
                Toast.makeText(mainActivity, "失敗", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(mainActivity, "ログインしました", Toast.LENGTH_SHORT).show();
                Log.i(TAG, result);
                if (dialog != null) {
                    dialog.dismiss();
                    if (dialog instanceof LoginDialog) {
                        ((LoginDialog) dialog).setIdAndPassword();
                    }
                }

                try {
                    JSONArray addressJson = new JSONArray(result);
                    for (int i = 0; i < addressJson.length(); i++) {
                        Log.i(TAG, "i = " + i);
                        JSONObject object = addressJson.getJSONObject(i);
                        mainActivity.addAddressData(new AddressData(object.getString("name"), object.getString("phone_num"), object.getString("fax_num"), object.getString("mail_address"), object.getString("note")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            dialogLoading.dismiss();

            break;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }


    @Override
    protected void onCancelled() {

    }
}

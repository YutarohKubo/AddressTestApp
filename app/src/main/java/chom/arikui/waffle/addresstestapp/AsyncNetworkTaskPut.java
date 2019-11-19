package chom.arikui.waffle.addresstestapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class AsyncNetworkTaskPut extends AsyncTask<String, Integer, String> {

    public static final String ADD = "add";

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";

    private MainActivity mainActivity;
    private DataLoadDialog dialogLoading;
    private String type;


    public AsyncNetworkTaskPut(Context context, String type) {
        super();

        if (context instanceof MainActivity) {

            mainActivity = (MainActivity) context;
        }
        this.type = type;
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(params[0] + "?type=" + type);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
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

        Log.i("AsyncNetworkTaskPut", builder.toString());

        return builder.toString();
    }

    @Override
    protected void onPreExecute() {
        if (type.equals(ADD)) {
            dialogLoading = new DataLoadDialog();
            dialogLoading.setCancelable(true);
            dialogLoading.show(mainActivity.getFragmentManager(), "data_loading_fragment");
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if (result.equals(SUCCESS)) {
            Toast.makeText(mainActivity, SUCCESS, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mainActivity, FAILED, Toast.LENGTH_SHORT).show();
        }
        dialogLoading.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }


    @Override
    protected void onCancelled() {

    }
}

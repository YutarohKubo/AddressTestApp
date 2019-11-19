package chom.arikui.waffle.addresstestapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by Yutaro on 2018/02/06.
 */

public class DataLoadDialog extends DialogFragment {
    private ProgressDialog prog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        prog = new ProgressDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog);
        prog.setMessage("now loading...");
        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prog.setCancelable(false);

        return prog;
    }

}

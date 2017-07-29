package com.rajat.registrationcop290.Tools;
import com.rajat.registrationcop290.R;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tools {
    public static AlertDialog blackalert;

    public static ProgressDialog showProgressBar(Context c) {
        ProgressDialog progressDialog = new ProgressDialog(c,R.style.DialogTheme);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public static void showAlertDialog(String message,Context con)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(con,R.style.DialogTheme);
        alert.setTitle(message);
        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        blackalert.dismiss();
                    }
                });
        blackalert = alert.create();
        blackalert.show();
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

/*DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;*/
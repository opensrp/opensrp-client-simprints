package org.smartregister.simprint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import static com.simprints.libsimprints.Constants.SIMPRINTS_PACKAGE_NAME;

public class Utils {

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {

            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }
    public static void downloadSimprintIdApk(final Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Need to install simprint id apk");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Download",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + SIMPRINTS_PACKAGE_NAME)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + SIMPRINTS_PACKAGE_NAME)));
                        }

                        dialog.dismiss();
                        if(context instanceof Activity){
                            ((Activity)context).finish();
                        }
                    }
                });
        alertDialog.show();

    }
}

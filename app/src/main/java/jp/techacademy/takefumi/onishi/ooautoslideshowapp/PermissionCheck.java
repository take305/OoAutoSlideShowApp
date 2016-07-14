package jp.techacademy.takefumi.onishi.ooautoslideshowapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;

/**
 * Created by Takefumi on 2016/07/14.
 */


public class PermissionCheck {

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    static Boolean isPermissionCheck(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (PermissionChecker.checkSelfPermission
                    (context,Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);

                if (checkSelfPermission
                        (Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult
            (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            default:
                break;
        }
    }



}

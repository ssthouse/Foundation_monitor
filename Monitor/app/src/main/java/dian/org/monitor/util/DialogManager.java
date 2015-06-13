package dian.org.monitor.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import dian.org.monitor.R;

/**
 * Created by ssthouse on 2015/6/12.
 */
public class DialogManager {

    /**
     * 显示确认退出的Dialog
     */
    public static void showInVisiableDialog(final Activity activity) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        dialogBuilder.withDuration(10).show();
        dialogBuilder.dismiss();
    }

    /**
     * 显示---选择拍照开始图库的Dialog
     *
     * @param activity
     */
    public static void showAlbumOrCameraDialog(final Activity activity) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        LinearLayout llMain = (LinearLayout) LayoutInflater.from(activity).
                inflate(R.layout.album_or_camera_dialog, null);
        llMain.findViewById(R.id.id_ll_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                PictureManager.getPictureFromCamera(activity);
            }
        });
        llMain.findViewById(R.id.id_ll_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                PictureManager.getPictureFromAlbum(activity);
            }
        });
        dialogBuilder.withTitle(null)             //.withTitle(null)  no title
                .withMessage(null)//.withMessage(null)  no Msg
                .withDialogColor(activity.getResources().getColor(R.color.dialog_color))
                .withEffect(Effectstype.Slidetop)       //def Effectstype.Slidetop
                .isCancelableOnTouchOutside(false)       //设为可以点击空白退出
                .setCustomView(llMain, activity)
                .withDuration(200)
                .show();
    }
}

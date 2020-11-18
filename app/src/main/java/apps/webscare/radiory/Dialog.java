package apps.webscare.radiory;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class Dialog {

    public KProgressHUD progressDialog;
    public String title;
    public void startDialog(Context context){
        progressDialog = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(title)
                .setCancellable(true)
                .setBackgroundColor(context.getResources().getColor(R.color.colorPrimary))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
    public Dialog(String title) {
        this.title = title;
    }
}

package at.xtools.pwawrapper;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class JavaScriptInterface {
    Activity mActivity;

    public JavaScriptInterface(Activity activity) {
        this.mActivity = activity;
    }

    @JavascriptInterface
    public void subscribeToTopic(String userId) {
        FirebaseMessaging.getInstance().subscribeToTopic("incoming-call")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed to incoming-call topic " + userId;
                        if (!task.isSuccessful()) {
                            msg = "Failed to subscribe to incoming-call topic";
                        }
                        Log.d("JavaScriptInterface", msg);
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

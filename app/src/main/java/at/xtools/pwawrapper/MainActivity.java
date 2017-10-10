package at.xtools.pwawrapper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import at.xtools.pwawrapper.ui.UIManager;
import at.xtools.pwawrapper.webview.WebViewHelper;

public class MainActivity extends AppCompatActivity {
    // Globals
    private WebView webView;
    private UIManager uiManager;
    private WebViewHelper webViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup App
        webView = (WebView) this.findViewById(R.id.webView);
        uiManager = new UIManager(this);
        webViewHelper = new WebViewHelper(this, uiManager);
        webViewHelper.setupWebView();
        uiManager.changeRecentAppsIcon();

        // load up the Web App
        webView.loadUrl(Constants.WEBAPP_URL);
    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webView.onResume();
        // retrieve content from cache primarily if not connected,
        // fetch from web otherwise to get updates.
        webViewHelper.useCache(
                !webViewHelper.isNetworkAvailable()
        );
        super.onResume();
    }

    // Handle back-press in browser
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

package in.ds.maaad.group;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.view.*;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class MaaadWebActivity extends Activity {
    /** Called when the activity is first created. */
	
	WebView mMaaadWebView;
	private LinearLayout mSplashLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_web_layout);
        
        mMaaadWebView = (WebView) findViewById(R.id.activity_main_webview);
        mSplashLayout = (LinearLayout)findViewById(R.id.splashLayout);
        
        mMaaadWebView.setWebViewClient(new MaaadWebClient());
        mMaaadWebView.getSettings().setJavaScriptEnabled(true);
        mMaaadWebView.loadUrl("http://www.maaadgroup.com");
	    
		
    }
    
    public class MaaadWebClient extends WebViewClient
    {
    	@Override
    	public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		// TODO Auto-generated method stub
    		super.onPageStarted(view, url, favicon);
    	}
    	
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		// TODO Auto-generated method stub
    		
    		view.loadUrl(url);
    		return false;
    		
    	}
    	
    	@Override
    	public void onPageFinished(WebView view, String url) {
    		// TODO Auto-generated method stub
    		super.onPageFinished(view, url);
            mSplashLayout.setVisibility(View.GONE);
    	}
    }
    
    // To handle "Back" key press event for WebView to go back to previous screen.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mMaaadWebView.canGoBack()) {
			mMaaadWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
@Override
public boolean onCreateOptionsMenu(Menu menu)
{
	// Inflate the menu items for use in the action bar
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.exit, menu);
	return super.onCreateOptionsMenu(menu);
}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.exit_button:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);}}

}
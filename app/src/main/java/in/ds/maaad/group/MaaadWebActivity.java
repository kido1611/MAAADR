package in.ds.maaad.group;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import android.content.res.*;
import android.support.v4.widget.*;
import android.util.*;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class MaaadWebActivity extends Activity{
    /** Called when the activity is first created. */
	
	private CustomWebView mMaaadWebView;
	private LinearLayout mSplashLayout;
	
    private SwipeRefreshLayout swipeRefreshLayout;
    // get application context from MainActivity
    private static Context context = MyApplication.getContextOfApplication();

	private SharedPreferences preferences;

	
    @Override
	@SuppressLint("setJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_web_layout);
        
        mMaaadWebView = (CustomWebView) findViewById(R.id.activity_main_webview);
        mSplashLayout = (LinearLayout)findViewById(R.id.splashLayout);
        mMaaadWebView.setWebViewClient(new MaaadWebClient());
        mMaaadWebView.getSettings().setJavaScriptEnabled(true);
        mMaaadWebView.loadUrl("http://www.maaadgroup.com");
        mMaaadWebView.setOnScrollChangedCallback(new CustomWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                if(t>10){
                    if(getActionBar().isShowing())
                        getActionBar().hide();
                }else{
                    if(!getActionBar().isShowing())
                        getActionBar().show();
                }
            }
        });
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
		
	}

	private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        // refreshing pages
        @Override
        public void onRefresh() {
            // notify when there is no internet connection
            if (!Connectivity.isConnected(getApplicationContext()))
                Toast.makeText(getApplicationContext(), getString(R.string.no_conectivity), Toast.LENGTH_SHORT).show();

            // reloading page
            mMaaadWebView.reload();

            new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
						// done!
					}

				}, 2000);
        }};
	
    
    public class MaaadWebClient extends WebViewClient
    {

		private boolean refreshed;
    	@Override
    	public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		// TODO Auto-generated method stub
    		super.onPageStarted(view, url, favicon);
            mSplashLayout.setVisibility(View.VISIBLE);
    	}
    	
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		// TODO Auto-generated method stub
			// handling external links as intents
			if (Uri.parse(url).getHost().endsWith("maaadgroup.com")) {
				return false;
			}
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			view.getContext().startActivity(intent);
			return true;
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			// refresh on connection error (sometimes there is an error even when there is a network connection)
			if (!refreshed) {
				view.loadUrl(failingUrl);
				// when network error is real do not reload url again
				refreshed = true;
			}}
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.i("MainActivity", "onDestroy: Destroying...");
        super.onDestroy();
        mMaaadWebView.removeAllViews();
        mMaaadWebView.destroy();
    }

}
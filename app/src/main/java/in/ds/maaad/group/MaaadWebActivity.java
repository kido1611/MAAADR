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
	private ProgressBar mProgressBar;
 // variables for drawer layout
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] itemList;

	
    @Override
	@SuppressLint("setJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_web_layout);
        
		// piece of code for drawer layout
        itemList = getResources().getStringArray(R.array.item_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, itemList));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mMaaadWebView = (CustomWebView) findViewById(R.id.activity_main_webview);
        mSplashLayout = (LinearLayout)findViewById(R.id.splashLayout);
		mProgressBar = (ProgressBar) findViewById(R.id.progres_loading);
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
	// the click listener for ListView in the navigation drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
	
	// when a drawer item is clicked do instructions from below
    private void selectItem(int position) {
		// standard application menu (it's default)
		switch (position) {
			case 0:
				mMaaadWebView.loadUrl("javascript:scroll(0,0)");
				break;
case 1:
				mMaaadWebView.loadUrl("http://maaadgroup.com/usercp.php");
				break;
case 2:
				mMaaadWebView.loadUrl("http://maaadgroup.com/index.php");
				break;
			case 3:
				mMaaadWebView.loadUrl("http://maaadgroup.com/portal.php");
				break;
			case 4:
				mMaaadWebView.loadUrl("http://maaadgroup.com/memberlist.php");
				break;
			case 5:
				mMaaadWebView.loadUrl("http://maaadgroup.com/calendar.php");
				break;
			case 6:
				mMaaadWebView.loadUrl("http://maaadgroup.com/misc.php?action=help");
				break;
			case 7:
				mMaaadWebView.loadUrl("http://maaadgroup.com/search.php");
				break;
			
	}
	// update selected item, then close the drawer
	mDrawerList.setItemChecked(position, true);
	mDrawerLayout.closeDrawer(mDrawerList);
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
			mProgressBar.setVisibility(View.VISIBLE);
     
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
			return false;
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
			mProgressBar.setVisibility(View.GONE);
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
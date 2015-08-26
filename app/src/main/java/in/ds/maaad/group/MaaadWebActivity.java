package in.ds.maaad.group;

import android.annotation.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import in.ds.sa3a.material.widget.*;
import in.ds.maaad.group.model.*;
import in.ds.maaad.group.preference.*;
import java.util.*;
import android.support.v7.widget.Toolbar;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class MaaadWebActivity extends ActionBarActivity{
    /** Called when the activity is first created. */
	
	private CustomWebView mMaaadWebView;
	private LinearLayout mSplashLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
	private ProgressBarDeterminate mProgressBar;
	
	private String mUrl = "http://www.maaadgroup.com";
	
	// variables for drawer layout
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] itemList;
	
	
	// error handling, shared prefs and TrayPreferences
	private static final String TAG = MaaadWebActivity.class.getSimpleName();
	private SharedPreferences mPreferences;

	


	int backgroundColor = Color.parseColor("#1E88E5");

    private ArrayList<NavDrawerItem> mItems;
    private NavDrawerAdapter adapter;
	
    @Override
	@SuppressLint("setJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		if(mPreferences==null){
			Toast.makeText(this, "Preference kosong", Toast.LENGTH_SHORT).show();
			return;
		}
		
        // set the main content view (for drawer position)

		if(mPreferences.getString("drawer_pos", "0").equals("0"))
			setContentView(R.layout.main_web_layout);
		else
			setContentView(R.layout.main_web_layout_drawer_right);
			// recreate activity when something important was just changed
		if (getIntent().getBooleanExtra("core_settings_changed", false)) {
			finish(); // finish and create a new Instance
			Intent restart = new Intent(MaaadWebActivity.this, MaaadWebActivity.class);
			startActivity(restart);
		}
		// set a toolbar to replace the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		BarTinting tintManager = new BarTinting(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.semi_transparent);
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setNavigationBarTintResource(R.color.semi_transparent);
        

		// piece of code for drawer layout
        itemList = getResources().getStringArray(R.array.item_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mItems = new ArrayList<NavDrawerItem>();
        mItems.add(new NavDrawerItem(itemList[0], R.drawable.ic_launcher));
        mItems.add(new NavDrawerItem(itemList[1], R.drawable.ic_menu_usercp));
        mItems.add(new NavDrawerItem(itemList[2], R.drawable.ic_menu_index));
        mItems.add(new NavDrawerItem(itemList[3], R.drawable.ic_menu_portal));
        mItems.add(new NavDrawerItem(itemList[4], R.drawable.ic_menu_memberlist));
        mItems.add(new NavDrawerItem(itemList[5], R.drawable.ic_menu_calendar));
        mItems.add(new NavDrawerItem(itemList[6], R.drawable.ic_menu_help));
        mItems.add(new NavDrawerItem(itemList[7], R.drawable.ic_menu_search));
		mItems.add(new NavDrawerItem(itemList[8], R.drawable.ic_menu_help));
		

        adapter = new NavDrawerAdapter(this, mItems);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
        mMaaadWebView = (CustomWebView) findViewById(R.id.activity_main_webview);
		
        mSplashLayout = (LinearLayout)findViewById(R.id.splashLayout);
		
		mProgressBar = (ProgressBarDeterminate) findViewById(R.id.progres_loading);
		
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
        mMaaadWebView.setWebViewClient(new MaaadWebClient());
		mMaaadWebView.setWebChromeClient(new WebChromeClient () {
			
				public void onProgressChanged(WebView view, int progress) {
					if (mPreferences.getBoolean("progress_bar", true)) {
						if (progress < 100 && mProgressBar.getVisibility() == View.GONE) {
							mProgressBar.setVisibility(View.VISIBLE);
						}
						mProgressBar.setProgress(progress);
						if (progress == 100) {
							mProgressBar.setVisibility(View.GONE);
						}
					} else {
						// if progress bar is disabled hide it immediately
						mProgressBar.setVisibility(View.GONE);
					}
						
					}
				
			
			
		});
		
        mMaaadWebView.getSettings().setJavaScriptEnabled(true);
		mMaaadWebView.getSettings().setUseWideViewPort(true);
		mMaaadWebView.getSettings().setLoadWithOverviewMode(true);
		mMaaadWebView.getSettings().setAllowFileAccess(true);
	    mMaaadWebView.getSettings().setDisplayZoomControls(true);
		
		mMaaadWebView.setBackgroundColor(getResources().getColor(R.color.transparent));
        mMaaadWebView.loadUrl(mUrl);
     
		
		
		
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
		
		
		
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
			case 8:
				Intent settings = new Intent(this, SettingsActivity.class);
				startActivity(settings);
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
     
    	}
    	
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		// TODO Auto-generated method stub
			// handling external links as intents
			
			if (Uri.parse(url).getHost().endsWith("maaadgroup.com")) {
				return false;
			}
			
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
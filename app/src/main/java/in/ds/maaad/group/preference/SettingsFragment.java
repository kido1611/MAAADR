package in.ds.maaad.group.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import in.ds.maaad.group.*;

public class SettingsFragment extends PreferenceFragment {

    private static Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener myPrefListner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the preferences from an XML resource
        addPreferencesFromResource(R.xml.maaad_preference);

        // set context
        context = getActivity();

        // get shared preferences
		// get shared preferences
        final SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
        // listener for changing preferences (works after the value change)
        myPrefListner = new SharedPreferences.OnSharedPreferenceChangeListener() {

			@Override
			public void onSharedPreferenceChanged(SharedPreferences p1, String p2)
			{
				// TODO: Implement this method
			}

			
		};
		
		// listener for changing drawer_pos preference
        findPreference("drawer_pos").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					Log.v("SettingsFragment", "drawer_pos changed");
					//relaunch();
					return true;
				}
			});
			

        // register the listener above
     
        // listener for clearing cache preference
        findPreference("clear_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.v("SettingsFragment", "clearing cache...");
                // clear cache dirs
                deleteCache(getActivity());
                // restart the app (really ugly way of doing it but...)
                android.os.Process.killProcess(android.os.Process.myPid());
                return true;
            }
        });
    }
	
	// relaunch the app
    private void relaunch() {
        // notify user about relaunching the app
        Toast.makeText(getActivity(), getString(R.string.applying_changes), Toast.LENGTH_SHORT).show();
        // sending intent to onNewIntent() of MainActivity
        Intent intent = new Intent(getActivity(), MaaadWebActivity.class);
        intent.putExtra("core_settings_changed", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // methods for clearing cache
    private static void deleteCache(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib") && !s.equals("shared_prefs")) {
                    deleteDir(new File(appDir, s));
                    Log.i("MAAADR", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
package in.ds.maaad.group;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.*;
import ds.sa3a.material.widget.*;

import ds.sa3a.material.widget.Dialog;
import ds.sa3a.material.widget.Button;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import in.ds.maaad.group.*;


public class MaaadActivity extends Activity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Button btnContentWeb = (Button)findViewById(R.id.maaad_web);
		btnContentWeb.setOnClickListener((new View.OnClickListener(){
			@Override
			public void onClick(View arg0)
			{
				// TODO: Implement this method
				Intent intent = new Intent(MaaadActivity.this,MaaadWebActivity.class);
				startActivity(intent);
				}}));
		Button btnAuth = (Button)findViewById(R.id.maaad_group);
		btnAuth.setOnClickListener((new View.OnClickListener(){
			@Override
			public void onClick(View arg0)
			{
				// TODO: Implement this method
				Uri uri = Uri.parse("https://m.facebook.com/groups/1418950598368267?_rdr");
				Intent intent= new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				}}));
		//		SHOW DiALOG
		Button btnAbout = (Button)findViewById(R.id.author_profile);
		btnAbout.setOnClickListener((new View.OnClickListener(){
			@Override
			public void onClick(View arg0)
			{
				// TODO: Implement this method
				Uri uri = Uri.parse("https://m.facebook.com/dikisatria.toejoehx?v=info&refid=17");
				Intent intent= new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				}}));
				}
@Override
public boolean onCreateOptionsMenu(Menu menu)
{
	// Inflate the menu items for use in the action bar
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.menu, menu);
	return super.onCreateOptionsMenu(menu);
}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// Handle item selection
	switch (item.getItemId()) {
		case R.id.action_about:
			showDialog();
			return true;
			default:
			return super.onOptionsItemSelected(item);}}
			
			private void showDialog(){
				Dialog dialog = new Dialog(MaaadActivity.this, "About", "Mediatek Android Art and Dev \nApp Version : V"+BuildConfig.VERSION_NAME+"\n \n-  Easy acess to official website \n-  Easy acess to facebook group \n \nAuthor : dikisatria10@gmail.com \nCopyright Diki Satria 2015");
				
				dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(MaaadActivity.this, "Thanks", 1).show();
					}
				});
				dialog.show();
			
		
}}
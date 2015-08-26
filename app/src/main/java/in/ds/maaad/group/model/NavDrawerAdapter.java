package in.ds.maaad.group.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.ds.maaad.group.R;

/**
 * Created by Server on 09/08/2015.
 */
public class NavDrawerAdapter  extends BaseAdapter{
    private Context context;
    private ArrayList<NavDrawerItem> mItems;
    private SharedPreferences pref;
    private LayoutInflater mInflater;
    public NavDrawerAdapter(Context context, ArrayList<NavDrawerItem> items){
        this.context = context;
        this.mItems = items;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isDrawerinLeft(){
        if(pref.getString("drawer_pos", "0").equals("0")){
            return true;
        }
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(isDrawerinLeft())
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        else
            convertView = mInflater.inflate(R.layout.drawer_list_item_right, null);

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(mItems.get(position).getIcon());
        txtTitle.setText(mItems.get(position).getTitle());

        return convertView;
    }
}

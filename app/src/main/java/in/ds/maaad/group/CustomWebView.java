package in.ds.maaad.group;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.AbsListView;

/**
 * Created by Server on 06/08/2015.
 */
public class CustomWebView extends WebView{

    private Context mContext;

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public CustomWebView(Context context){
        super(context);
        mContext = context;
    }

    public CustomWebView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t);
    }

    public OnScrollChangedCallback getOnScrollChangedCallback()
    {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback)
    {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public static interface OnScrollChangedCallback
    {
        public void onScroll(int l, int t);
    }
}

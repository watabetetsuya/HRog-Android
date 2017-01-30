package jp.co.goalist.hrog.hrog.adapter.menu;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import jp.co.goalist.hrog.hrog.MainActivity;
import jp.co.goalist.hrog.hrog.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public String url;
    public ImageView icon;
    public Activity parent;
    public MenuViewHolder(View itemView, Activity _parent) {
        super(itemView);
        parent = _parent;
        final DrawerLayout layout = (DrawerLayout)_parent.findViewById(R.id.drawerLayout);
        final WebView wv = (WebView)_parent.findViewById(R.id.webView);
        textView = (TextView)itemView.findViewById(R.id.menu_title);
        icon = (ImageView)itemView.findViewById(R.id.menu_icon);
        itemView.findViewById(R.id.menu_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url == null || url.equals("")) {
                    ((MainActivity) parent).showMenu(null);

                } else {
                    ((AppCompatActivity) parent).getSupportActionBar().setTitle(textView.getText());
                    wv.loadUrl(url);
                    layout.closeDrawers();
                }
            }
        });
    }
}

package jp.co.goalist.hrog.hrog.adapter.menu;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.co.goalist.hrog.hrog.R;

public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private Activity parentActivity;

    private static final MenuData[] ITEMS = {
        new MenuData("ALL", "http://app.hrog.net", R.drawable.home_image),
        new MenuData("ニュース", "http://app.hrog.net/category/home_news", R.drawable.news_image),
        new MenuData("プレス", "http://app.hrog.net/category/press", R.drawable.press_image),
        new MenuData("レポート", "http://app.hrog.net/category/report", R.drawable.report_image),
        new MenuData("まとめ", "http://app.hrog.net/category/matome", R.drawable.matome_image),
        new MenuData("設定", "", R.drawable.setting_image)
    };

    public MenuDrawerAdapter(Activity _parentActivity) {
        super();
        this.parentActivity = _parentActivity;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.menu_item, parent, false);
        itemView.setClickable(true);

        TypedValue outValue = new TypedValue();
        parent.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        itemView.setBackgroundResource(outValue.resourceId);

        // ViewHolderインスタンス生成

        MenuViewHolder vh = new MenuViewHolder(itemView, parentActivity);

        return vh;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        MenuData data = ITEMS[position];
        holder.textView.setText(data.title);
        holder.url = data.url;
        holder.icon.setImageResource(data.image);
    }

    @Override
    public int getItemCount() {
        return ITEMS.length;
    }
}

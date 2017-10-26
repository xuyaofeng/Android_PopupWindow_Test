package com.example.yaofengxu.listpopuptest;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private float density;
    private int screenHeight;
    private int popupWidth;
    private int popupHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        density = getResources().getDisplayMetrics().density;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        setPopupWindow();
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyAdapter());
    }

    private void setPopupWindow() {
        View popupWindowView = LayoutInflater.from(this).inflate(R.layout.popup_window_view,null);
        popupWidth = (int) (100*density);
        popupHeight = (int) (120*density);
        popupWindow = new PopupWindow(popupWindowView, popupWidth, popupHeight,true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_view_item,null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.textView = (TextView) convertView.findViewById(R.id.text_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView.setOnClickListener(onClickListener);
            holder.imageView.setTag(position);
            holder.textView.setOnClickListener(onClickListener2);
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = (Integer) v.getTag();
            PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_LONG).show();
                    return true;
                }
            });
            popupMenu.show();
        }
    };

    private View.OnClickListener onClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] location = new int[2];
            v.getLocationInWindow(location);
            if (needShowUp(v,location[1])) {
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
            } else {
                popupWindow.showAsDropDown(v,(v.getWidth() - popupWidth) / 2,0);
            }

        }
    };

    private boolean needShowUp(View v, int height) {
        return (screenHeight - height - v.getHeight() < popupHeight);
    }


}

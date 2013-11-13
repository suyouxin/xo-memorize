package com.morphoss.xo.memorize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

    public class MenuAdapter extends ArrayAdapter<MenuAdapter.MenuInfo> {
        static public class MenuInfo {
            public String text;
            public int imgResID;
            
            public MenuInfo() {
                
            }
            
            public MenuInfo(String txt, int img) {
                text = txt;
                imgResID = img;
            }
        };

        public MenuAdapter(Context context, int textViewResourceId, MenuInfo[] objects) {
            super(context, textViewResourceId, objects);
            
        }
 
        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            final LayoutInflater inflater = 
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View menu = inflater.inflate(R.layout.menu_text_img, parent, false);

            TextView label = (TextView)menu.findViewById(R.id.company);
            label.setText(this.getItem(position).text);

            if (this.getItem(position).imgResID != 0) {
                ImageView icon = (ImageView)menu.findViewById(R.id.image);
                icon.setImageResource(this.getItem(position).imgResID);
            }
            return menu;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LayoutInflater inflater = 
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView menu = (ImageView)inflater.inflate(R.layout.menu_img_only, parent, false);
            menu.setImageResource(this.getItem(position).imgResID);
            return menu;
        }
    }
    
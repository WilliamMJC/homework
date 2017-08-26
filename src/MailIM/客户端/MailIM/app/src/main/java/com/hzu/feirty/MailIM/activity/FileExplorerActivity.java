
package com.hzu.feirty.MailIM.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.feirty.MailIM.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileExplorerActivity extends Activity {

    private ListView listview;
    private List<String> pathlist;
    private String rootpath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer);

        listview = (ListView) findViewById(R.id.files_listview);
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            rootpath = Environment.getExternalStorageDirectory().toString();
        }

        getFileDir(rootpath);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                File file = new File(pathlist.get(position));
                if (file.isDirectory()) {
                    try {
                        getFileDir(file.getPath());
                    } catch (Exception e) {
                        getFileDir(file.getParent());
                    }
                } else {
                    backToPreviousActivity(file.getPath().toLowerCase(Locale.CHINA));
                }
            }
        });

    }

    /**

     */
    private void backToPreviousActivity(String result) {
        setResult(1, new Intent().putExtra("FILEPATH", result));
        finish();
    }

    /**

     */
    private void getFileDir(String filepath) {
        ArrayList<String> items = new ArrayList<String>();
        pathlist = new ArrayList<String>();
        File sfile = new File(filepath);

        File[] files = sfile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                items.add(0, file.getName());
                pathlist.add(0, file.getPath());
            } else {
                items.add(file.getName());
                pathlist.add(file.getPath());
            }
        }
        if (!rootpath.equals(filepath)) {
            items.add(0, "..");
            pathlist.add(0, sfile.getParent());
        }
        listview.setAdapter(new FileAdapter(this, items, pathlist));
    }

    private class FileAdapter extends BaseAdapter {

        private List<String> filenames;
        private List<String> filepaths;
        private Context context;

        /**

         */
        public FileAdapter(Context context, List<String> items, List<String> pathlist) {
            this.context = context;
            this.filenames = items;
            this.filepaths = pathlist;
        }

        @Override
        public int getCount() {
            return filenames.size();
        }

        @Override
        public Object getItem(int position) {
            return filenames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                holder.nameView = new TextView(context);
                holder.nameView.setGravity(Gravity.CENTER_VERTICAL);
                holder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                convertView = holder.nameView;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            File file = new File(filepaths.get(position).toString());
            Drawable drawable;
            if (filenames.get(position).toString().equals("..")) {
                holder.nameView.setText("xxxx");
                drawable = getResources().getDrawable(R.drawable.explorer_back);
            } else {
                if (file.isDirectory()) {
                    drawable = getResources().getDrawable(R.drawable.explorer_folder);
                } else {
                    drawable = getResources().getDrawable(R.drawable.explorer_file);
                }
                holder.nameView.setText(file.getName());
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.nameView.setCompoundDrawables(drawable, null, null, null);
            return convertView;
        }

        private class ViewHolder {
            TextView nameView;
        }
    }

}

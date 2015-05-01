package com.jiajun.edusocial.SelectClaSch;

import java.util.ArrayList;
import java.util.HashMap;

import com.jiajun.edusocial.R;
import com.jiajun.edusocial.R.id;
import com.jiajun.edusocial.R.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class MyAdapter4ClassLIST extends BaseAdapter {
	// ������ݵ�list
	
	private ArrayList<String> list;
	// ��������CheckBox��ѡ��״��
	private static HashMap<Integer, Boolean> isSelected;
	// ������
	private Context context;
	// �������벼��
	private LayoutInflater inflater = null;

	// ������
	public MyAdapter4ClassLIST(ArrayList<String> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// ��ʼ������
		initDate();
	}

	// ��ʼ��isSelected������
	private void initDate() {
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			isSelected.put(i, false);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {

			holder = new ViewHolder();

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.listitem_class, null);
			}
//			convertView = inflater.inflate(R.layout.listitem_class, null);
			holder.tvclass = (TextView) convertView.findViewById(R.id.item_tv);
			holder.tvclass.setTextColor(Color.rgb(0, 0, 0));
			holder.cbclass = (CheckBox) convertView.findViewById(R.id.item_cb);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}


		holder.tvclass.setText(list.get(position));
		holder.cbclass.setChecked(getIsSelected().get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyAdapter4ClassLIST.isSelected = isSelected;
	}

}
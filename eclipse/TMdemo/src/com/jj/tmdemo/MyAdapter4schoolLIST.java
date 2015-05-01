package com.jj.tmdemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyAdapter4schoolLIST extends BaseAdapter {
	// ������ݵ�list
	private ArrayList<String> list;
	// ��������CheckBox��ѡ��״��
	private static HashMap<Integer, Boolean> isSelected;
	// ������
	private Context context;
	// �������벼��
	private LayoutInflater inflater = null;

	// ������
	public MyAdapter4schoolLIST(ArrayList<String> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// ��ʼ������
		initDate();
	}

	// ��ʼ��isSelected������
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
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
			// ���ViewHolder����
			holder = new ViewHolder();
			// ���벼�ֲ���ֵ��convertview
			convertView = inflater.inflate(R.layout.listitem_class, null);
			holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			// Ϊview���ñ�ǩ
			convertView.setTag(holder);
		} else {
			// ȡ��holder
			holder = (ViewHolder) convertView.getTag();
		}

		// ����list��TextView����ʾ
		holder.tv.setText(list.get(position));
		holder.cb.setChecked(getIsSelected().get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyAdapter4schoolLIST.isSelected = isSelected;
	}

}
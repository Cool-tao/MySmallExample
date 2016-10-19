package com.mysmallexample.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.mysmallexample.R;

public class ListViewItemActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {
	private ListView noteBookslist;
	private NoteBookAdapter noteBookAdapter;
	private List<String> fileTypeList;
	private int currentPosition = -1;
	private int mSingleChoiceID = -1;
	private String[] mItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list);
		initView();
		setData();

	}

	private void initView() {
		noteBookslist = (ListView) findViewById(R.id.notebook_list);
		noteBookslist.setOnItemClickListener(this);
		noteBookslist.setOnItemLongClickListener(this);
	}

	private void setData() {
		fileTypeList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			fileTypeList.add("Test" + i);
		}
		if (noteBookAdapter == null) {
			noteBookAdapter = new NoteBookAdapter(this, fileTypeList);
			noteBookslist.setAdapter(noteBookAdapter);
		} else {
			noteBookAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onResume() {
		updateNoteBookList();
		super.onResume();
	}

	private void updateNoteBookList() {
		if (noteBookAdapter != null) {
			setData();
			noteBookAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> listview, View arg1, int position, long arg3) {
		currentPosition = -1;
		noteBookAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> listview, View arg1, int position, long arg3) {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(40);
		currentPosition = position;
		noteBookAdapter.notifyDataSetChanged();
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				noteBookAdapter.notifyDataSetChanged();
			}
		}
	};

	class NoteBookAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<String> fileTypeList;

		public NoteBookAdapter(Activity activity, List<String> fileTypeList) {
			this.context = activity;
			this.fileTypeList = fileTypeList;
			inflater = LayoutInflater.from(context);
		}

		public int getCount() {

			return fileTypeList.size();
		}

		public int getItemViewType(int position) {
			return position;
		}

		public Object getItem(int arg0) {
			return fileTypeList.get(arg0);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_comm_note, parent, false);
				holder = new ViewHolder();
				holder.fileNameText = (TextView) convertView.findViewById(R.id.item_name);
				holder.fileTimeText = (TextView) convertView.findViewById(R.id.item_time);
				holder.fileSizeText = (TextView) convertView.findViewById(R.id.item_pm);
				holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.layout_other);
				holder.openLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_open);
				holder.editLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_edit);
				holder.moveLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_move);
				holder.deleteLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String strs = fileTypeList.get(position);
			holder.fileNameText.setText(strs);
			holder.fileTimeText.setText("");
			holder.fileSizeText.setText("");
			if (position == currentPosition) {
				holder.linearLayout.setVisibility(View.VISIBLE);
				holder.openLinearLayout.setClickable(true);
				holder.editLinearLayout.setClickable(true);
				holder.moveLinearLayout.setClickable(true);
				holder.deleteLinearLayout.setClickable(true);
				holder.openLinearLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						currentPosition = -1;

					}
				});
				holder.editLinearLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						currentPosition = -1;

					}
				});
				holder.moveLinearLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(ListViewItemActivity.this);
						mSingleChoiceID = -1;
						builder.setIcon(android.R.drawable.ic_dialog_info);
						builder.setTitle("移动至");
						builder.setSingleChoiceItems(mItems, 0, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								mSingleChoiceID = whichButton;
							}
						});
						builder.setPositiveButton("确\t定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								if (mSingleChoiceID > 0) {
									currentPosition = -1;
									handler.sendEmptyMessage(1);
								}
							}
						});
						builder.setNegativeButton("取\t消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {

							}
						});
						builder.create().show();
					}
				});
				holder.deleteLinearLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						currentPosition = -1;
						fileTypeList.remove(position);
						handler.sendEmptyMessage(1);
					}
				});
			} else {
				holder.linearLayout.setVisibility(View.GONE);
				holder.openLinearLayout.setClickable(false);
				holder.editLinearLayout.setClickable(false);
				holder.moveLinearLayout.setClickable(false);
				holder.deleteLinearLayout.setClickable(false);
			}

			return convertView;
		}

		class ViewHolder {
			public TextView fileNameText;
			public TextView fileTimeText;
			public TextView fileSizeText;
			public LinearLayout linearLayout;
			public LinearLayout openLinearLayout;
			public LinearLayout editLinearLayout;
			public LinearLayout moveLinearLayout;
			public LinearLayout deleteLinearLayout;
		}
	}
}

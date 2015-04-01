package com.mithi.androidnotifier;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.orm.query.Select;


import com.mithi.androidnotifier.R;

public class SecondFragment extends ListFragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	public static SecondFragment newInstance(int sectionNumber) {
		SecondFragment fragment = new SecondFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public SecondFragment() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		updateList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//TODO : Show message when tapped on an item in the list
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_second, container,
				false);

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}

	public void updateList()
	{
		//	List<DatabaseHandler> messages = DatabaseHandler.listAll(DatabaseHandler.class);
		List<DatabaseHandler> messages = Select.from(DatabaseHandler.class).orderBy("message").list();
		ArrayAdapter<DatabaseHandler> adapter = new ArrayAdapter<DatabaseHandler>(getActivity(),R.layout.fragment_second_delete_button,R.id.taskTextView, messages);
		ListView listView = getListView();
		listView.setAdapter(adapter);

	}

	public void onDoneButtonClick(View view)
	{

	}

}


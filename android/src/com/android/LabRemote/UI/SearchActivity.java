/**
 * SearchActivity.java
 *     
 * Copyright (C) 2010 LabRemote Team
 * Version 1.0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.android.LabRemote.UI;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.android.LabRemote.R;
import com.android.LabRemote.Utils.StudentProvider;

/**
 * Activity that handles search query and lists the results
 * @see StudentProvider
 */
public class SearchActivity extends LabRemoteActivity {
	/** List filled with resulted students */
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
		mListView = (ListView) findViewById(android.R.id.list);	 
		mListView.setEmptyView(findViewById(android.R.id.empty));

		Intent intent = getIntent();
		if (Intent.ACTION_VIEW.equals(intent.getAction())) { /** click on a suggestion */
			Intent individualIntent = new Intent(getApplicationContext(), StudentView.class);
			individualIntent .putExtra("ID", intent.getDataString());
			startActivityForResult(individualIntent, LabRemoteActivity.REQUEST_FROM_STUDENT);
			finish();
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) { /** click on search */
			String query = intent.getStringExtra(SearchManager.QUERY);
			showResults(query);
		}
	}

	/**
	 * Displays search results 
	 */
	private void showResults(String query) {
		Cursor cursor = managedQuery(StudentProvider.CONTENT_URI, 
				null, null, new String[] {query}, null);

		if (cursor != null) {
			String[] from = new String[] {SearchManager.SUGGEST_COLUMN_TEXT_1};
			int[] to = new int[] {R.id.resultName};

			SimpleCursorAdapter words = new SimpleCursorAdapter(this,
					R.layout.search_result_item, cursor, from, to);
			mListView.setAdapter(words);

			mListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent individualIntent = new Intent(getApplicationContext(), StudentView.class);
					individualIntent.putExtra("ID", String.valueOf(id));
					startActivityForResult(individualIntent, LabRemoteActivity.REQUEST_FROM_STUDENT);
				}
			});
		} 
	}
}

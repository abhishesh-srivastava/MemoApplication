package com.example.aashankpratap.memoapplication.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.aashankpratap.memoapplication.R;
import com.example.aashankpratap.memoapplication.adapter.MemoListAdapter;
import com.example.aashankpratap.memoapplication.db.MemoDBHelper;
import com.example.aashankpratap.memoapplication.model.Memo;

public class MemoMainActivity extends AppCompatActivity {

    private ListView mListView;
    private EditText memoETCreateTag, memoETCreateContent, memoETSearchTag;
    private List<Memo> mMemoList;
    private MemoListAdapter mAdapter;
    private MemoDBHelper mHelper;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton mFloatingActionButton;
    private TextView mTxtNoData;
    private boolean isSearchView = false;
    private LinearLayout mLLTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_ui);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHelper = MemoDBHelper.getInstance(getApplicationContext());
        mMemoList = new ArrayList<Memo>();
        mMemoList = mHelper.getAllMemo();
        mAdapter = new MemoListAdapter(this, mMemoList);
        mListView = (ListView) findViewById(R.id.memo_tags);
        mTxtNoData = (TextView) findViewById(R.id.txt_no_data);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mLLTop = (LinearLayout) findViewById(R.id.ll_top);
        mListView.setAdapter(mAdapter);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder memoList = new AlertDialog.Builder(MemoMainActivity.this);
                memoList.setTitle(R.string.create_memo_title);
                memoList.setMessage(R.string.create_memo_description);
                LayoutInflater inflater = getLayoutInflater();
                View dialogCreateView = inflater.inflate(R.layout.custom_list_view01, null);
                memoList.setView(dialogCreateView);
                memoETCreateTag = (EditText) dialogCreateView.findViewById(R.id.memoTag);
                memoETCreateContent = (EditText) dialogCreateView.findViewById(R.id.memoContent);

                memoList.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tag = memoETCreateTag.getText().toString();
                                String content = memoETCreateContent.getText().toString();
                                Memo memo = new Memo();
                                memo.setTag(tag);
                                memo.setContent(content);
                                mMemoList.add(memo);
                                mHelper.insertMemo(tag, content);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                memoList.setNegativeButton(R.string.cancel, null);
                memoList.create().show();
            }
        });
    }

    private void refreshMemoListView() {
        mMemoList = mHelper.getAllMemo();
        mAdapter.setList(mMemoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memo_ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_action) {

            AlertDialog.Builder memoSearch = new AlertDialog.Builder(this);
            memoSearch.setTitle(R.string.search_memo_title);
            memoSearch.setMessage(R.string.search_memo_description);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogSearchView = inflater.inflate(R.layout.custom_list_view02, null);
            memoSearch.setView(dialogSearchView);
            memoETSearchTag = (EditText) dialogSearchView.findViewById(R.id.memoSearchTag);

            memoSearch.setPositiveButton(R.string.ok,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tag = memoETSearchTag.getText().toString();
                            mMemoList = mHelper.getData(tag);
                            if(mMemoList == null || mMemoList.size() == 0) { // when no memo found
                                mTxtNoData.setVisibility(View.VISIBLE);
                                mListView.setVisibility(View.GONE);
                                mLLTop.setVisibility(View.GONE);
                            } else {
                                mAdapter.setList(mMemoList);
                                mAdapter.notifyDataSetChanged();
                            }
                            isSearchView = true;
                            mFloatingActionButton.setVisibility(View.GONE);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setTitle("Search Results: " + tag);
                            getSupportActionBar().setDisplayShowHomeEnabled(true);
                        }
                    });
            memoSearch.setNegativeButton(R.string.cancel, null);
            memoSearch.create().show();

            return true;
        } else if(id == android.R.id.home)
            handleBackPress();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isSearchView)
            handleBackPress();
        else
            super.onBackPressed();
    }

    private void handleBackPress() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(R.string.app_name);
        mTxtNoData.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mLLTop.setVisibility(View.VISIBLE);
        mFloatingActionButton.setVisibility(View.VISIBLE);
        isSearchView = false;
        refreshMemoListView();
    }
}

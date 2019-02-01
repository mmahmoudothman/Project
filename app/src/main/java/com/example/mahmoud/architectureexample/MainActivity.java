package com.example.mahmoud.architectureexample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.mahmoud.architectureexample.adapter.SourceAdapter;
import com.example.mahmoud.architectureexample.model.Source;

import java.util.List;

import static com.example.mahmoud.architectureexample.common.Const.URL_EXTRA;
import static com.example.mahmoud.architectureexample.common.Const.URL_TITLE;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_SOURCE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private SourceViewModel sourceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddSource = findViewById(R.id.button_add_source);
        buttonAddSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSourceActivity.class);
                startActivityForResult(intent, ADD_SOURCE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SourceAdapter adapter = new SourceAdapter();
        recyclerView.setAdapter(adapter);

        sourceViewModel = ViewModelProviders.of(this).get(SourceViewModel.class);
        sourceViewModel.getAllSources().observe(this, new Observer<List<Source>>() {
            @Override
            public void onChanged(@Nullable List<Source> sources) {
                adapter.submitList(sources);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                sourceViewModel.delete(adapter.getSourceAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Source deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new SourceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Source source) {

                Intent intent = new Intent(MainActivity.this, ListNews.class);
                intent.putExtra(URL_EXTRA, source.getUrl());
                intent.putExtra(URL_TITLE, source.getTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SOURCE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddSourceActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddSourceActivity.EXTRA_DESCRIPTION);

            Source source = new Source(title, description);
            sourceViewModel.insert(source);

            Toast.makeText(this, "Source saved", Toast.LENGTH_SHORT).show();
        }
         else {
            Toast.makeText(this, "Source not saved", Toast.LENGTH_SHORT).show();
        }
    }


}
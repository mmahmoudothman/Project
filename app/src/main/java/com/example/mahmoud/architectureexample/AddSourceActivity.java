package com.example.mahmoud.architectureexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.mahmoud.architectureexample.common.Const.PRE_URL;

public class AddSourceActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "AddSourceActivity.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "AddSourceActivity.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "AddSourceActivity.EXTRA_DESCRIPTION";

    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_source);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_link);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Source");

    }

    private void saveSource() {
        String title = editTextTitle.getText().toString();
        String urlProvider = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || urlProvider.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        if (!urlProvider.toLowerCase().contains(PRE_URL.toLowerCase())) {
            urlProvider = PRE_URL + urlProvider;
        }
        data.putExtra(EXTRA_DESCRIPTION, urlProvider);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_source_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_source:
                saveSource();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
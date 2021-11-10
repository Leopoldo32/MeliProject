package com.example.meliproject.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import com.example.meliproject.R;
import com.example.meliproject.constants.Constants;
import com.example.meliproject.retrofit.Results;
import com.example.meliproject.viewmodel.MeLiViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MeLiViewModel meLiViewModel;
    private EditText searchText;
    private String item;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchText);


        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item = searchText.getText().toString();

                if(item.length() > 0){
                    searchButton.setEnabled(true);
                }else{
                    searchButton.setEnabled(false);
                }

            }

            public void afterTextChanged(Editable s) {
            }
        });

        searchButton.setOnClickListener(this);
    }

    private void searchItem(){
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Buscando Articulos");
        pd.setMessage("Buscando...");
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        pd.setIndeterminate(false);
        pd.show();



        meLiViewModel = new ViewModelProvider(this).get(MeLiViewModel.class);
        meLiViewModel.deleteAllResults();
        meLiViewModel.getAllSearchData(item).observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(List<Results> results) {
                meLiViewModel.insertAllResults(results);
                pd.dismiss();
                goToSearchResultActivity();

            }
        });
    }


    private void goToSearchResultActivity(){
        Intent intent = new Intent(MainActivity.this, com.example.meliproject.ui.SearchResultActivity.class);
        startActivityForResult(intent, Constants.SEARCH_RESULT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchButton:
                searchItem();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

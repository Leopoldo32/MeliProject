package com.example.meliproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.meliproject.R;
import com.example.meliproject.constants.Constants;
import com.example.meliproject.db.entity.ResultEntity;
import com.example.meliproject.viewmodel.MeLiViewModel;

public class SearchResultActivity extends AppCompatActivity {

    private MeLiViewModel meLiViewModel;
    private RecyclerView srRecyclerView;
    private RecyclerView.LayoutManager srLayoutMgr;
    private ResultRecyclerViewAdapter reAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#313E71'>"+"Resultado de busqueda"+"</font>"));


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Resultado de busqueda");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back3);

        }

        meLiViewModel = new ViewModelProvider(this).get(MeLiViewModel.class);
        srRecyclerView = findViewById(R.id.recyclerResult);
        srLayoutMgr = new LinearLayoutManager(this);
        srRecyclerView.setLayoutManager(srLayoutMgr);
            meLiViewModel.getAll().observe(this, new Observer<List<ResultEntity>>() {
                @Override
                public void onChanged(List<ResultEntity> resultEntities) {
                    reAdapter = new ResultRecyclerViewAdapter(resultEntities, R.layout.result_item_recycler_view, new ResultRecyclerViewAdapter.ResultRecyclerViewListener() {
                        @Override
                        public void itemOnClick(View v, int position) {
                            ResultEntity res = resultEntities.get(position);
                            goToDetailActivity(res.getIdRoom());
                        }
                    });
                    srRecyclerView.setAdapter(reAdapter);
                    reAdapter.setNuevosResultados(resultEntities);
                }
            });
    }

    private void goToDetailActivity(int idRoom){
        Intent intent = new Intent(SearchResultActivity.this, DetailProductActivity.class);
        intent.putExtra("idRoom", idRoom);
        startActivityForResult(intent, Constants.SEARCH_RESULT_ACTIVITY_REQUEST_CODE);
    }

}

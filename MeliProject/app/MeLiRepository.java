package com.example.meliproject;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import com.example.meliproject.constants.Constants;
import com.example.meliproject.db.MeLiRoomDatabase;
import com.example.meliproject.db.dao.ResultDao;
import com.example.meliproject.db.entity.ResultEntity;
import com.example.meliproject.retrofit.MLService;
import com.example.meliproject.retrofit.Results;
import com.example.meliproject.retrofit.SearchResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeLiRepository {
    private ResultDao resultDao;
    private LiveData<List<ResultEntity>> allResults;
    private LiveData<List<ResultEntity>> resultById;

    public MeLiRepository(Application application){
        MeLiRoomDatabase db = MeLiRoomDatabase.getDatabase(application);
        resultDao = db.resultDao();
        allResults = resultDao.getAll();
    }

    /* Repository Room
        Aca se encuentran los metodos del repositorio que se comunican mediante el DAO a los modelos
        de Room.
     */

    public LiveData<List<ResultEntity>> getAll() { return allResults; }

    public LiveData<List<ResultEntity>> getResultById(int idRoom){
        resultById = resultDao.getResultsById(idRoom);
        return resultById;
    }

    public void insert(List<Results> results){
        new insertAsyncTask(resultDao).execute(results);
    }

    public void deleteAll(){
        new deleteAsyncTask(resultDao).execute();
    }
    private static class insertAsyncTask extends AsyncTask<List<Results> , Void, Void> {
        private ResultDao resultAsyncTask;

        insertAsyncTask(ResultDao dao) {
            resultAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(List<Results> ... resultEntities) {
            List<Results> results = resultEntities[0];
            for (Results res:results) {
                ResultEntity re = new ResultEntity(res.getId(), res.getSite_id(),res.getTitle(),res.getPrice(),res.getCurrency_Id()
                        ,res.getThumbnail(),res.getCondition(),res.getPermalink(),res.getAddress().getState_id(),
                        res.getAddress().getState_name(), res.getAddress().getCity_id(),res.getAddress().getCity_name());
                resultAsyncTask.insert(re);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void>{
        private ResultDao resultDao;

        public deleteAsyncTask(ResultDao dao) {
            resultDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            resultDao.deleteAll();
            return null;
        }
    }



    public LiveData<List<Results>> getAllResults(String item){
        MutableLiveData<List<Results>> results = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.MELI_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MLService service = retrofit.create(MLService.class);
        Call<SearchResult> searchCall = service.getMLSearchResult(item);
        searchCall.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                results.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {

            }
        });

        return results;
    }




}

package com.example.meliproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.meliproject.MeLiRepository;
import com.example.meliproject.db.entity.ResultEntity;
import com.example.meliproject.retrofit.Results;

public class MeLiViewModel extends AndroidViewModel {

    private MeLiRepository meLiRepository;
    private LiveData<List<Results>> allSearchedData;
    private LiveData<List<ResultEntity>> allResults;
    private LiveData<List<ResultEntity>> resultByIdRoom;


    public MeLiViewModel(@NonNull Application application) {
        super(application);
        meLiRepository = new MeLiRepository(application);
        allResults = meLiRepository.getAll();
//aa
    }

    public LiveData<List<ResultEntity>> getAll(){
        return allResults;
    }

    public LiveData<List<Results>> getAllSearchData(String item){
        allSearchedData = meLiRepository.getAllResults(item);
        return allSearchedData;
    }


    public LiveData<List<ResultEntity>> getDataById(int idRoom){
        resultByIdRoom = meLiRepository.getResultById(idRoom);
        return  resultByIdRoom;
    }

    public void insertAllResults(List<Results> resultList){
        meLiRepository.insert(resultList);
    }

    public void deleteAllResults(){
        meLiRepository.deleteAll();
    }
}

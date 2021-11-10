package com.example.meliproject.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class SearchResult {

    private List<com.example.meliproject.retrofit.Results> results;

    public SearchResult() {
    }

    public static com.example.meliproject.retrofit.Results parseJsonResults(String response) {
        Gson gson = new GsonBuilder().create();
        com.example.meliproject.retrofit.Results results = gson.fromJson(response, com.example.meliproject.retrofit.Results.class);
        return results;
    }

    public List<com.example.meliproject.retrofit.Results> getResults() {
        return results;
    }

    public void setResults(List<com.example.meliproject.retrofit.Results> results) {
        this.results = results;
    }
}



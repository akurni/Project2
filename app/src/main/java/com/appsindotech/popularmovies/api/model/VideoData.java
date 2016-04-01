package com.appsindotech.popularmovies.api.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoData {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("results")
@Expose
private List<VideoResult> results = new ArrayList<VideoResult>();

/**
* 
* @return
* The id
*/
public Integer getId() {
return id;
}

/**
* 
* @param id
* The id
*/
public void setId(Integer id) {
this.id = id;
}

/**
* 
* @return
* The results
*/
public List<VideoResult> getResults() {
return results;
}

/**
* 
* @param results
* The results
*/
public void setResults(List<VideoResult> results) {
this.results = results;
}

}
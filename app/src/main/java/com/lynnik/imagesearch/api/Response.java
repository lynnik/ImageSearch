package com.lynnik.imagesearch.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

  @SerializedName("result_count")
  @Expose
  private long resultCount;
  @SerializedName("images")
  @Expose
  private List<Image> images = null;

  public long getResultCount() {
    return resultCount;
  }

  public void setResultCount(long resultCount) {
    this.resultCount = resultCount;
  }

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }
}
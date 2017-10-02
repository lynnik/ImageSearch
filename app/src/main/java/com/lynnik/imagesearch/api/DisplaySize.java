package com.lynnik.imagesearch.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplaySize {

  @SerializedName("is_watermarked")
  @Expose
  private boolean isWatermarked;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("uri")
  @Expose
  private String uri;

  public boolean isIsWatermarked() {
    return isWatermarked;
  }

  public void setIsWatermarked(boolean isWatermarked) {
    this.isWatermarked = isWatermarked;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
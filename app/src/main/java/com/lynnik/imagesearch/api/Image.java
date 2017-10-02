package com.lynnik.imagesearch.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {

  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("display_sizes")
  @Expose
  private List<DisplaySize> displaySizes = null;
  @SerializedName("title")
  @Expose
  private String title;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<DisplaySize> getDisplaySizes() {
    return displaySizes;
  }

  public void setDisplaySizes(List<DisplaySize> displaySizes) {
    this.displaySizes = displaySizes;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
package com.lynnik.imagesearch.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

  public static final String HEADER = "Api-Key: 34sbfa5ahs7erxc8cn4evcwz";
  public static final String REQUEST =
      "v3/search/images?fields=id,title,thumb&sort_order=best";
  public static final String REQUEST_PHRASE = "phrase";

  private static final String BASE_URL = "https://api.gettyimages.com/";

  public static Retrofit getRetrofit() {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static GettyImagesService getService(Retrofit retrofit) {
    return retrofit.create(GettyImagesService.class);
  }
}
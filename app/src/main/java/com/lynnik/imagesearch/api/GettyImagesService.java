package com.lynnik.imagesearch.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GettyImagesService {

  @Headers(RetrofitUtil.HEADER)
  @GET(RetrofitUtil.REQUEST)
  Call<Response> getImages(@Query(RetrofitUtil.REQUEST_PHRASE) String phrase);
}
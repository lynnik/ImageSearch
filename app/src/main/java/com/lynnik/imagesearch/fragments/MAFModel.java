package com.lynnik.imagesearch.fragments;

import android.content.Context;

import com.lynnik.imagesearch.api.GettyImagesService;
import com.lynnik.imagesearch.api.RetrofitUtil;
import com.lynnik.imagesearch.database.ImageRealm;
import com.lynnik.imagesearch.api.Response;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MAFModel {

  private static final String SORT_BY_ID = "id";

  private Realm mRealm;
  private Retrofit mRetrofit;
  private GettyImagesService mGettyImagesService;

  public void initRealm(Context context) {
    mRealm = Realm.getInstance(context);
  }

  public void closeRealm() {
    mRealm.close();
  }

  public void initRetrofit() {
    mRetrofit = RetrofitUtil.getRetrofit();
  }

  public void initGettyImagesService() {
    mGettyImagesService = RetrofitUtil.getService(mRetrofit);
  }

  public RealmResults<ImageRealm> getHistoryList() {
    return mRealm.where(ImageRealm.class)
        .findAllSorted(SORT_BY_ID, Sort.DESCENDING);
  }

  public boolean isHistoryEmpty() {
    return getHistoryList().size() == 0;
  }

  public void removeAllObjects() {
    mRealm.beginTransaction();
    mRealm.clear(ImageRealm.class);
    mRealm.commitTransaction();
  }

  public Call<Response> createRequest(String query) {
    return mGettyImagesService.getImages(query);
  }

  public void saveObjectToDatabase(String query, String uri) {
    mRealm.beginTransaction();

    ImageRealm imageRealm = mRealm.createObject(ImageRealm.class);
    imageRealm.setId(System.currentTimeMillis());
    imageRealm.setQuery(query);
    imageRealm.setUri(uri);

    mRealm.commitTransaction();
  }
}
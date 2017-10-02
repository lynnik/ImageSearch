package com.lynnik.imagesearch.fragments;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lynnik.imagesearch.R;
import com.lynnik.imagesearch.api.Image;
import com.lynnik.imagesearch.database.ImageRealm;
import com.lynnik.imagesearch.api.Response;

import java.util.List;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;

public class MAFPresenter {

  private MAFModel mModel;
  private MAFView mView;

  public MAFPresenter(MAFView view) {
    mView = view;
    mModel = new MAFModel();
  }

  public void initRealm(Context context) {
    mModel.initRealm(context);
  }

  public void closeRealm() {
    mModel.closeRealm();
  }

  public void setAdapter() {
    RealmResults<ImageRealm> images = mModel.getHistoryList();

    if (images.size() != 0) {
      mView.onSetAdapter(images);
    }
  }

  public void initRetrofit() {
    mModel.initRetrofit();
  }

  public void initGettyImagesService() {
    mModel.initGettyImagesService();
  }

  public void clearDatabase() {
    mModel.removeAllObjects();
    showHistoryOrEmptyHistoryText();
  }

  public void createRequest(final String query) {
    mView.onShowProgressBar();

    Call<Response> request = mModel.createRequest(query);
    request.enqueue(new Callback<Response>() {
      @Override
      public void onResponse(
          @NonNull Call<Response> call,
          @NonNull retrofit2.Response<Response> response) {
        if (response.isSuccessful()) {
          List<Image> images = response.body().getImages();
          if (images != null && images.size() > 0) {
            String uri = images.get(0).getDisplaySizes().get(0).getUri();
            mModel.saveObjectToDatabase(query, uri);
          } else {
            mView.onShowMessage(R.string.error);
          }
        } else {
          mView.onShowMessage(R.string.error);
        }

        setAdapter();
        showHistoryOrEmptyHistoryText();
      }

      @Override
      public void onFailure(
          @NonNull Call<Response> call,
          @NonNull Throwable t) {
        mView.onShowMessage(R.string.error);
        showHistoryOrEmptyHistoryText();
      }
    });
  }

  public void showHistoryOrEmptyHistoryText() {
    if (mModel.isHistoryEmpty()) {
      mView.onShowEmptyHistoryText();
      mView.onInactiveClearHistoryMenuItem();
    } else {
      mView.onShowHistory();
      mView.onActiveClearHistoryMenuItem();
    }
  }
}
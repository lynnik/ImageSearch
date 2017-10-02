package com.lynnik.imagesearch.fragments;

import com.lynnik.imagesearch.database.ImageRealm;

import java.util.List;

public interface MAFView {

  void onSetAdapter(List<ImageRealm> images);

  void onShowHistory();

  void onShowProgressBar();

  void onShowEmptyHistoryText();

  void onActiveClearHistoryMenuItem();

  void onInactiveClearHistoryMenuItem();

  void onShowMessage(int messageResId);
}
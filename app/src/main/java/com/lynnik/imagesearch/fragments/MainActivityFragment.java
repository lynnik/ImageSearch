package com.lynnik.imagesearch.fragments;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lynnik.imagesearch.R;
import com.lynnik.imagesearch.database.ImageRealm;

import java.util.List;

import static android.content.Context.SEARCH_SERVICE;

public class MainActivityFragment extends Fragment
    implements SearchView.OnQueryTextListener, MAFView {

  private MAFPresenter mPresenter;

  private FrameLayout mFrameLayout;
  private RecyclerView mRecyclerView;
  private TextView mEmptyHistoryTextView;
  private ProgressBar mProgressBar;
  private MenuItem mClearHistoryMenuItem;

  public static MainActivityFragment newInstance() {
    Bundle args = new Bundle();

    MainActivityFragment fragment = new MainActivityFragment();
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);
    setRetainInstance(true);

    mPresenter = new MAFPresenter(this);
    mPresenter.initRealm(getActivity());
    mPresenter.initRetrofit();
    mPresenter.initGettyImagesService();
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_main, container, false);

    viewInit(v);
    mPresenter.setAdapter();

    return v;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.closeRealm();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_fragment_main, menu);

    mClearHistoryMenuItem = menu.findItem(R.id.clear);
    MenuItem searchMenuItem = menu.findItem(R.id.action_search);
    SearchManager searchManager = (SearchManager)
        getActivity().getSystemService(SEARCH_SERVICE);

    SearchView searchView = (SearchView) searchMenuItem.getActionView();
    searchView.setOnQueryTextListener(this);
    searchView.setSearchableInfo(
        searchManager.getSearchableInfo(
            getActivity().getComponentName()));

    mPresenter.showHistoryOrEmptyHistoryText();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.clear:
        mPresenter.clearDatabase();
        break;
      default:
        break;
    }

    return true;
  }

  @Override
  public boolean onQueryTextSubmit(final String query) {
    mPresenter.createRequest(query);
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    return false;
  }

  @Override
  public void onSetAdapter(List<ImageRealm> images) {
    mRecyclerView.setAdapter(new PhotoAdapter(images));
  }

  @Override
  public void onShowHistory() {
    mRecyclerView.setVisibility(View.VISIBLE);
    mProgressBar.setVisibility(View.GONE);
    mEmptyHistoryTextView.setVisibility(View.GONE);
  }

  @Override
  public void onShowProgressBar() {
    mProgressBar.setVisibility(View.VISIBLE);
    mRecyclerView.setVisibility(View.GONE);
    mEmptyHistoryTextView.setVisibility(View.GONE);
  }

  @Override
  public void onShowEmptyHistoryText() {
    mEmptyHistoryTextView.setVisibility(View.VISIBLE);
    mRecyclerView.setVisibility(View.GONE);
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void onActiveClearHistoryMenuItem() {
    mClearHistoryMenuItem.setEnabled(true);
  }

  @Override
  public void onInactiveClearHistoryMenuItem() {
    mClearHistoryMenuItem.setEnabled(false);
  }

  @Override
  public void onShowMessage(int messageResId) {
    Snackbar snackbar = Snackbar
        .make(mFrameLayout, messageResId, Snackbar.LENGTH_LONG)
        .setActionTextColor(Color.WHITE);
    snackbar.getView().setBackgroundColor(
        ContextCompat.getColor(getActivity(), R.color.colorAccent));
    snackbar.show();
  }

  private void viewInit(View v) {
    mFrameLayout = (FrameLayout) v.findViewById(R.id.frameLayout);
    mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
    mEmptyHistoryTextView = (TextView)
        v.findViewById(R.id.emptyHistoryTextView);
    mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
    mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
  }

  private class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private List<ImageRealm> mImages;

    public PhotoAdapter(List<ImageRealm> images) {
      mImages = images;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getActivity());
      View v = inflater.inflate(R.layout.recycler_view_item, parent, false);

      return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
      ImageRealm image = mImages.get(position);
      holder.fillItem(image);
    }

    @Override
    public int getItemCount() {
      return mImages.size();
    }
  }

  private class PhotoViewHolder extends RecyclerView.ViewHolder {

    private ImageView mPhotoImageView;
    private TextView mTitleTextView;

    public PhotoViewHolder(View v) {
      super(v);

      mPhotoImageView = (ImageView) v.findViewById(R.id.imageImageView);
      mTitleTextView = (TextView) v.findViewById(R.id.titleTextView);
    }

    public void fillItem(ImageRealm image) {
      setImage(image.getUri());
      setText(image.getQuery());
    }

    private void setImage(String uri) {
      Glide.with(getActivity())
          .load(uri)
          .into(mPhotoImageView);
    }

    private void setText(String query) {
      mTitleTextView.setText(query);
    }
  }
}
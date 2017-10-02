package com.lynnik.imagesearch.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lynnik.imagesearch.fragments.MainActivityFragment;
import com.lynnik.imagesearch.R;

public class MainActivity extends SingleFragmentActivity {

  @Override
  protected Fragment createFragment() {
    return MainActivityFragment.newInstance();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mToolbar.setLogo(R.mipmap.ic_launcher);
  }
}
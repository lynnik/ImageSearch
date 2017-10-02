package com.lynnik.imagesearch.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lynnik.imagesearch.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

  protected Toolbar mToolbar;

  protected abstract Fragment createFragment();

  @LayoutRes
  protected int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);

    addFragment();
  }

  private void addFragment() {
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

    if (fragment == null) {
      fragment = createFragment();
      fm.beginTransaction()
          .add(R.id.fragmentContainer, fragment)
          .commit();
    }
  }
}
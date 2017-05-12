package com.slava.viewpager.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.slava.viewpager.R;
import com.slava.viewpager.adapters.PagerAdapter;
import com.slava.viewpager.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 234;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        if(getSupportFragmentManager().findFragmentByTag(MainFragment.class.getCanonicalName())==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.parent_view, new MainFragment(), MainFragment.class.getCanonicalName())
                    .commitAllowingStateLoss();
        }
        final ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

/*        mImageView = ImageView.class.cast(findViewById(R.id.img_view));
        findViewById(R.id.btn_take).setOnClickListener(this);*/
    }

    private boolean checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void dispatchTakePictureIntent() {
        if (!checkCameraPermission()) {
            requestCameraPermisson();
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void requestCameraPermisson() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Log.d(TAG, "requestCameraPermisson");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "Permission denied, boo!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btn_take:
                dispatchTakePictureIntent();*/
        }
    }
}

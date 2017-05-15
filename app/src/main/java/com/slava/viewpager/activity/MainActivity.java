package com.slava.viewpager.activity;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
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
import com.slava.viewpager.fragment.CommandFragment;
import com.slava.viewpager.fragment.MainFragment;
import com.slava.viewpager.fragment.PhotoFragment;
import com.slava.viewpager.interfaces.OnHeadlineSelectedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnHeadlineSelectedListener {
    private final static String TAG = "MainActivity";
    final static String KEY_PHOTO = "KeyPhoto";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 234;
    private ImageView mImageView;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab()
                    .setText(pagerAdapter.getPageTitle(i)));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        if(getSupportFragmentManager().findFragmentByTag(MainFragment.class.getCanonicalName())==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.parent_view, new MainFragment(), MainFragment.class.getCanonicalName())
                    .commitAllowingStateLoss();
        }
        pager = (ViewPager) findViewById(R.id.viewPager);
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
        //mImageView = ImageView.class.cast(findViewById(R.id.image_view));
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
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            extras.putString("key","bitmap");
            PhotoFragment photoFragment = new PhotoFragment();
            photoFragment.setArguments(extras);
            Log.d("Arguments", " "+photoFragment.getArguments());
            //pager.setCurrentItem(2, true);
            //mImageView.setImageBitmap(imageBitmap);
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

    @Override
    public void passData(String data) {
        CommandFragment commandFragment = (CommandFragment) getSupportFragmentManager().findFragmentById(R.id.button_fragment);
        if(commandFragment != null){
            Log.d("Listener", "called");
        } else {
            dispatchTakePictureIntent();
            PhotoFragment photoFragment = new PhotoFragment();
            Bundle args = new Bundle();
            args.putString(PhotoFragment.DATA_RECEIVE, data);
            Log.d("data", " "+data);
            photoFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.parent_view, photoFragment )
                    .commit();
            pager.setCurrentItem(2, true);
            /*Log.d("Listener", "called else");
            CommandFragment newCommandFragment = new CommandFragment();
            Bundle args = new Bundle();
            args.putInt(CommandFragment.BUNDLE_KEY, 1);
            newCommandFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.parent_view, newCommandFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();*/
        }
    }
}

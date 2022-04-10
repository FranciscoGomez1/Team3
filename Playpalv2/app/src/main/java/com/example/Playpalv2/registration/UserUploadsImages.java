package com.example.Playpalv2.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import com.example.Playpalv2.GridAdapter;
import com.example.Playpalv2.R;
import com.example.Playpalv2.databinding.ActivityUserUploadsImagesBinding;
import com.example.Playpalv2.progressDialog.CustomProgressDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserUploadsImages extends AppCompatActivity {
    private static final int PHOTO_PICKER_MULTI_SELECT_REQUEST_CODE = 4;
    private static final int REQUEST_PHOTO_PICKER_SINGLE_SELECT = 1;
    private static final int REQUEST_PHOTO_PICKER_MULTI_SELECT = 4 ;

    final int maxNumPhotosAndVideos = 10;

    private Button pickBtn;
    private Button nextBtn;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db;
    String userID;

    //ImagesToFirestore imagesToFirestore;
    UploadUserImagesToFirebase uploadUserImagesToFirebase;
    Uri[] userImages = new Uri[4];
    ActivityUserUploadsImagesBinding binding;
    private String[] urlsImages = new String[4];

    private static final String TAG= "UserUloadsImages";
    private  boolean allImagesloaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityUserUploadsImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_user_uploads_images);
        Intent myIntent = getIntent();
        String userId = myIntent.getExtras().getString("userId");

       uploadUserImagesToFirebase = new UploadUserImagesToFirebase(userImages,"Dog Owners",userId,this);

        pickBtn = findViewById(R.id.user_picks_photos_buttom);
        nextBtn = findViewById(R.id.btn_next3);

        CustomProgressDialog customProgressDialog = new CustomProgressDialog(UserUploadsImages.this);
        pickBtn.setOnClickListener(View -> {
            // Agustin needs to comment these out when pushing
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxNumPhotosAndVideos);
            startActivityForResult(intent, PHOTO_PICKER_MULTI_SELECT_REQUEST_CODE);

        });
        // This sets the image the user pick from their phone gallery
        GridAdapter gridAdapter1 = new GridAdapter(this,userImages);
        binding.gridViewUserImages.setAdapter(gridAdapter1);

        nextBtn.setOnClickListener(View -> {

            // imagesToFirestore.addImagesOfDogToFirebase();
            uploadUserImagesToFirebase.setUserImagesLinksUrls();
            uploadUserImagesToFirebase.setMyActivity(this);

            // Moves to the next activity

            while(true) {

                if(uploadUserImagesToFirebase.isSucess()){
                    Intent intent = new Intent(this, Register3.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        });

    }

    private void goToRegister3() {
        Intent intent = new Intent(this, Register3.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    // onActivityResult() handles callbacks from the photo picker.
    @Override
    protected void onActivityResult( int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CustomProgressDialog customProgressDialog = new CustomProgressDialog(this);
        customProgressDialog.initializeProgressDialog();
        if (resultCode != UserUploadsImages.RESULT_OK) {
            // Handle error
            return;
        }

        switch (requestCode) {
            case REQUEST_PHOTO_PICKER_SINGLE_SELECT:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();
                // Do stuff with the photo/video URI.
                return;
            case REQUEST_PHOTO_PICKER_MULTI_SELECT:
                // Get photo picker response for multi select

                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri currentUri2 = data.getClipData().getItemAt(i).getUri();
                    userImages[i] = currentUri2;

                    GridAdapter gridAdapter = new GridAdapter(this,userImages);
                    binding.gridViewUserImages.setAdapter(gridAdapter);


                    // Do stuff with each photo/video URI.
                }
                customProgressDialog.disMiss();
                uploadUserImagesToFirebase.uploadImages();

                //uploadImages();
                return;
        }
    }
}
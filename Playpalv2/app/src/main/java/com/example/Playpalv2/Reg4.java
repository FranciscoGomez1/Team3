package com.example.Playpalv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

public class Reg4 extends AppCompatActivity {

    private static final int PHOTO_PICKER_MULTI_SELECT_REQUEST_CODE = -1;
    private static final int REQUEST_PHOTO_PICKER_SINGLE_SELECT = 1;
    private static final int REQUEST_PHOTO_PICKER_MULTI_SELECT = 10 ;
    private Button pickBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg4);

        pickBtn = findViewById(R.id.pick_photos_button);
        pickBtn.setOnClickListener(View -> {
            final int mediaSelectionLimit = 10;
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, mediaSelectionLimit);
            startActivityForResult(intent, PHOTO_PICKER_MULTI_SELECT_REQUEST_CODE);
        });



    }
    // onActivityResult() handles callbacks from the photo picker.
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
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

                    // Do stuff with each photo/video URI.
                }
                return;
        }
    }
}
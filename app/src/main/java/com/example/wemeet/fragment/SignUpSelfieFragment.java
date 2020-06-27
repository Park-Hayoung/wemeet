package com.example.wemeet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wemeet.R;
import com.example.wemeet.data.UserItem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpSelfieFragment extends Fragment {
    private FrameLayout container1;
    private FrameLayout container2;
    private FrameLayout container3;
    private FrameLayout container4;

    private ImageView imageViewUserIcon1;

    private ImageView imageViewSelfie1;
    private ImageView imageViewSelfie2;
    private ImageView imageViewSelfie3;
    private ImageView imageViewSelfie4;

    private ImageView selectedImageView;

    private Button buttonComplete;

    private String currentPhotoPath;

    private UserItem user;

    private static final int REQUEST_TAKE_PHOTO = 1;

    private static final String TAG = "SignUpSelfie";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root =
                (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_selfie, container, false);

        container1 = root.findViewById(R.id.container_picture_1);
        container2 = root.findViewById(R.id.container_picture_2);
        container3 = root.findViewById(R.id.container_picture_3);
        container4 = root.findViewById(R.id.container_picture_4);

        imageViewUserIcon1 = root.findViewById(R.id.imageView_user_icon_1);

        imageViewSelfie1 = root.findViewById(R.id.imageView_selfie_1);
        imageViewSelfie2 = root.findViewById(R.id.imageView_selfie_2);
        imageViewSelfie3 = root.findViewById(R.id.imageView_selfie_3);
        imageViewSelfie4 = root.findViewById(R.id.imageView_selfie_4);

        buttonComplete = root.findViewById(R.id.button_complete);
        buttonComplete.setEnabled(false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewUserIcon1.setColorFilter(Color.parseColor("#FA9380"), PorterDuff.Mode.SRC_IN);

        user = (UserItem) getArguments().getSerializable("userItem");

        container1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                selectedImageView = imageViewSelfie1;
            }
        });

        container2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                selectedImageView = imageViewSelfie2;
            }
        });

        container3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                selectedImageView = imageViewSelfie3;
            }
        });

        container4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                selectedImageView = imageViewSelfie4;
            }
        });

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printUserItem();

                Bundle bundle = new Bundle();
                bundle.putSerializable("userItem", user);

                NavHostFragment.findNavController(SignUpSelfieFragment.this)
                        .navigate(R.id.action_signUpSelfieFragment_to_mainFragment, bundle);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getContext(), "사진파일을 생성하는 도중에 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "사진파일을 생성하는 도중에 에러 발생.");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.wemeet",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            // 촬영한 사진을 기기에 저장
            galleryAddPic();

            // 촬영한 사진을 이미지뷰에 적용
            setPic(selectedImageView);
            selectedImageView.setClipToOutline(true); //
            selectedImageView.bringToFront();

            // 유저객체에 사진파일경로 저장
            int imageViewId = selectedImageView.getId();
            switch (imageViewId) {
                case R.id.imageView_selfie_1:
                    user.setUserImage1(currentPhotoPath);
                    break;
                case R.id.imageView_selfie_2:
                    user.setUserImage2(currentPhotoPath);
                    break;
                case R.id.imageView_selfie_3:
                    user.setUserImage3(currentPhotoPath);
                    break;
                case R.id.imageView_selfie_4:
                    user.setUserImage4(currentPhotoPath);
                    break;
                default:
                    Log.d(TAG, "잘못된 imageView id 입니다.");
                    break;
            }

            if (selectedImageView.getId() == imageViewSelfie1.getId()) {
                buttonComplete.setBackgroundResource(R.drawable.bg_rect_pink_border);
                buttonComplete.setEnabled(true);
                buttonComplete.setTextColor(Color.parseColor("#FA9380"));
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    private void setPic(ImageView imageView) {
        /*
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
         */

        // Decode the image file into a Bitmap sized to fill the View
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void printUserItem() {
        Log.d(TAG, user.getUserId());
        Log.d(TAG, user.getUserPassword());
        Log.d(TAG, user.getNickname());
        Log.d(TAG, String.valueOf(user.isTermsUse()));
        Log.d(TAG, String.valueOf(user.isTermsNotification()));
        Log.d(TAG, user.getCountry());
        Log.d(TAG, user.getPhone());
        Log.d(TAG, String.valueOf(user.getTall()));
        Log.d(TAG, user.getBodyShape());
        Log.d(TAG, user.getEducation());
        Log.d(TAG, String.valueOf(user.getAge()));
        Log.d(TAG, user.getJob());
        Log.d(TAG, user.getDrink());
        Log.d(TAG, user.getSmoke());
        Log.d(TAG, user.getUserImage1());
    }
}

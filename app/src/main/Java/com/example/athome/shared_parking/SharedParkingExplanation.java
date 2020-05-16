package com.example.athome.shared_parking;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.naver.maps.geometry.LatLng;
import com.example.athome.retrofit.EnrollResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class SharedParkingExplanation extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private ImageView imageView;
    private Button btn_back_parking_info;
    private Button btn_select_photo;
    private Button btn_assigner_lookup;
    private EditText parking_info_name_value;
    private Boolean isPermission = true;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_parking_info);

        //사용자에게 권한 설정 받기
        tedWritePermission();

        imageView = (ImageView) findViewById(R.id.parking_info_img_value);
        btn_select_photo = (Button) findViewById(R.id.btn_select_photo);
        parking_info_name_value = (EditText) findViewById(R.id.parking_info_name_value);

        // 확인 버튼 누르면 서버로 배정자 정보 전달
        btn_assigner_lookup = (Button) findViewById(R.id.btn_assigner_lookup);
        btn_assigner_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempFile != null) {
                    Intent intent = getIntent();
                    File file = new File(tempFile.getAbsolutePath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part image = MultipartBody.Part.createFormData("img", file.getName(), requestFile);

                    RequestBody userBirth = RequestBody.create(MediaType.parse("text/plain"), intent.getStringExtra("birth"));
                    RequestBody userCarNumber = RequestBody.create(MediaType.parse("text/plain"),intent.getStringExtra("carNum"));
                    RequestBody location = RequestBody.create(MediaType.parse("text/plain"),intent.getStringExtra("locationName"));
                    LatLng SelectLocation = intent.getParcelableExtra("SelectLocation");
                    RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),Double.toString(SelectLocation.latitude));
                    RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),Double.toString(SelectLocation.longitude));
                    RequestBody parkingInfo = RequestBody.create(MediaType.parse("text/plain"),parking_info_name_value.getText().toString());

                    SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                    String sharedToken = sf.getString("token", "");

                    ApiService serviceApi = new RestRequestHelper().getApiService();
                    final Call<EnrollResult> res = serviceApi.postRegister(sharedToken, image, userBirth, userCarNumber, location, latitude, longitude, parkingInfo);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EnrollResult enrollResult = res.execute().body();
                                //User에 담는다 받은 결과를
                                if (enrollResult.getResult().equals("success")) {

                                } else {

                                }
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(SharedParkingExplanation.this, "주차장 사진이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //뒤로가기 버튼
        btn_back_parking_info = (Button) findViewById(R.id.btn_back_parking_info);
        btn_back_parking_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });

        btn_select_photo.setOnClickListener((View.OnClickListener) this);
    }

    //카메라에서 사진 촬영
    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {
            if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                Uri photoUri = FileProvider.getUriForFile(this,
                        "com.example.athome.fileprovider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
            else{
                Uri photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
        }



    }

    //앨범에서 이미지 가져오기
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e("jiwon", tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                Uri photoUri = data.getData();
                Log.i("jiwon",photoUri.toString());

                Cursor cursor = null;
                try {

                    /*
                     *  Uri 스키마를
                     *  content:/// 에서 file:/// 로  변경한다.
                     */
                    String[] proj = { MediaStore.Images.Media.DATA };

                    assert photoUri != null;
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();

                    tempFile = new File(cursor.getString(column_index));
                    Log.i("jiwon", tempFile.getAbsolutePath().toString());
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

                try {
                    setImage();
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            case PICK_FROM_CAMERA: {
                try {
                    setImage();
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }


            case CROP_FROM_IMAGE: { //이미지 변수 Bitmap photo

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(!isPermission) {
            Toast.makeText(this, getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            return;
        }
        if (v.getId() == R.id.btn_select_photo) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }

    }

    private void tedWritePermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2)+"폴더 생성")
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void tedReadPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void setImage() throws FileNotFoundException {

        Uri uri = getUriFromPath(tempFile.getAbsolutePath());
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        imageView.setImageBitmap(originalBm);
    }

    private File createImageFile() throws IOException{

        // 이미지 파일 이름 ( AtHome_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "AtHome_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( AtHome )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/AtHome/");
        storageDir.mkdirs();

        // 빈 파일 생성

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    private Uri getUriFromPath(String filePath) {
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, "_data = '" + filePath + "'", null, null);

        cursor.moveToNext();
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

        return uri;
    }




}
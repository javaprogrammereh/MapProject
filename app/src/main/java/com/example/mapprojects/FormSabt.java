package com.example.mapprojects;
/*support telgram id =@javaprogrammer_eh
 * 11/05/1398
 * creted by elmira hossein zadeh*/
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mapprojects.dataBase.Database;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class FormSabt extends AppCompatActivity {

    EditText edtplaceName, edtphoneNumber, edtdetail;
    Button btnSave;
    ImageView imageGallery;
    Spinner spinGuidType;
    public static String spinValue;
    public static Double latitude, longitude;
    public static String latLng;
    //    private int PICK_IMAGE_REQUEST = 1;
    ///////////////////
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    public static boolean flagPoint , flagMoshakhasatShow;
    public static String nameStaticPlace ,distanceLocationFormsabt ;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //////////addNew
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                    String path = saveImage(bMapScaled);
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageGallery.setImageBitmap(bMapScaled);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Bitmap bMapScaled = Bitmap.createScaledBitmap(thumbnail, 100, 100, true);
            imageGallery.setImageBitmap(bMapScaled);
            saveImage(bMapScaled);
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

        ///////////
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                ImageView imageView = findViewById(R.id.img_gallery);
//                imageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//         Result code is RESULT_OK only if the user selects an Image
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode) {
//                case 1:
//                    //data.getData returns the content URI for the selected Image
//                    Uri selectedImage = data.getData();
//                    imageGallery.setImageURI(selectedImage);
//                    break;
//            }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_sabt);
        requestMultiplePermissions();
        btnSave = findViewById(R.id.btn_save);
        edtplaceName = findViewById(R.id.edt_name_search);
        edtphoneNumber = findViewById(R.id.edt_phoneNumber);
        edtdetail = findViewById(R.id.edt_detail);
        imageGallery = findViewById(R.id.img_gallery);
        spinGuidType = findViewById(R.id.spinner_sabt);
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        String[] arraySpinGuids = Database.getguidsData(getApplicationContext());
        ArrayAdapter<String> arrayAdapterGuid = new ArrayAdapter<String>(getApplicationContext(), R.layout.sppiner, arraySpinGuids);
        spinGuidType.setAdapter(arrayAdapterGuid);
        spinGuidType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getSelectedItem();
                spinValue = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        imageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog();

//                chooseImage();
                //Create an Intent with action as ACTION_PICK
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                // Sets the type as image/*. This ensures only components of type image are selected
//                intent.setType("image/*");
//                //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
//                String[] mimeTypes = {"image/jpeg", "image/png"};
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//                // Launching the Intent
//                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] imageByteEnd = new byte[0];
                String namePlace = edtplaceName.getText().toString().trim();
                String details = edtdetail.getText().toString().trim();
                String guidType = spinValue;

                if (!namePlace.equals("") && !details.equals("")) {

                    Long phoneNumber = Long.parseLong(edtphoneNumber.getText().toString().trim());
                    try {
                        BitmapDrawable drawable = (BitmapDrawable) imageGallery.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        byte[] imageByte = getBytes(bitmap);
                        imageByteEnd =imageByte;
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                   
                    Database.addPlaceData(namePlace, phoneNumber, details, imageByteEnd, latitude, longitude, guidType, getApplicationContext());
                    if (Database.flagPlace == true) {
                        flagPoint = true;
                        flagMoshakhasatShow = true;
                        nameStaticPlace=namePlace;
                        MapsActivity.sabtShodShow = true;
                        Moshakhasat.spaceFragmentMashkhasat = distanceLocationFormsabt;
                        Toast.makeText(getApplicationContext(), "ثبت اطلاعات", Toast.LENGTH_SHORT).show();
                        FormSabt.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "خطا در عملیات!", Toast.LENGTH_SHORT).show();
                        flagPoint = false;
                        flagMoshakhasatShow = false;
                    }
                } else {
                    flagPoint = false;
                    flagMoshakhasatShow = false;
                    Toast.makeText(getApplicationContext(), "لطفا اطلاعات را کامل کنید!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("انتخاب عملیات");
        String[] pictureDialogItems = {
                "انتخاب عکس از گالری",
                "گرفتن عکس با دوربین"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    //////////////method
//    public void chooseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(intent.ACTION_PICK);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }

    //////////
//    private void askForPermission(String permission, Integer requestCode) {
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//            }
//        } else {
//            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    // convert from bitmap to byte array
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


}

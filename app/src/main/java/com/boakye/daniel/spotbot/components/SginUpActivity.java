package com.boakye.daniel.spotbot.components;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boakye.daniel.spotbot.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.boakye.daniel.spotbot.components.UploadPhotoDialog.MEDIA_TYPE_IMAGE;
import static com.boakye.daniel.spotbot.components.UploadPhotoDialog.MEDIA_TYPE_VIDEO;
import static com.boakye.daniel.spotbot.components.UploadPhotoDialog.TAG;


public class SginUpActivity extends Activity {

    protected EditText mUserName;
    protected EditText mPassword;
    protected EditText userKey;
    protected EditText confirmPass;
    protected Button signUpBut;
    ImageView passportphoto;
    protected Button UploadPhotoOption;
    protected EditText mEmailAdress;
    UploadPhotoDialog retrieve = new UploadPhotoDialog();
    protected Uri mMediaUri;
    public String mFileType;
    ParseUser newUser = new ParseUser();
    public static final int TAKE_PHOTO_REQUEST = 0;
    public Bitmap bitmap;
    static TextView imageDetails      = null;
    public  static ImageView showImg  = null;
    SginUpActivity CameraActivity = null;
    Cursor   cursor;
    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    final static int LOAD_IMAGE_FROM_DRIVE = 2;
    private String selectedImagePath;
    String imageId ;
    protected  string mFile_type;
    byte fileBytes[];



    DialogInterface.OnClickListener mDialogListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Take picture
                        {
                           /* Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                            */
                            /////////////////////////////////////////////////////////////


                             /*   takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);

                                startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                                */
                                // Define the file-name to save photo taken by Camera activity

                                String fileName = "Camera_Example.jpg";

                                // Create parameters for Intent with filename

                                ContentValues values = new ContentValues();

                                values.put(MediaStore.Images.Media.TITLE, fileName);

                                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");

                                // imageUri is the current activity attribute, define and save it for later usage

                                mMediaUri = getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                                /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/


                                // Standard Intent action that can be sent to have the camera
                                // application capture an image and return it.

                                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);

                             //   intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                                startActivityForResult( intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                                /*************************** Camera Intent End ************************/

                        }
                        break;
                        case 1: // Choose picture

                            String fileName = "Camera_photo.jpg";

                            // Create parameters for Intent with filename

                            ContentValues values = new ContentValues();

                            values.put(MediaStore.Images.Media.TITLE, fileName);

                            values.put(MediaStore.Images.Media.DESCRIPTION,"Image upload from drive");

                            // imageUri is the current activity attribute, define and save it for later usage

                            mMediaUri = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                            //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                            startActivityForResult(Intent.createChooser(intent,
                                    "Select Picture"), LOAD_IMAGE_FROM_DRIVE);
                            break;
                        case 2: //
                            break;
                        case 3: // Choose video
                            break;
                    }
                }


            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgin_up);
        Parse.initialize(this, "aV4oLiG745IHpmjdGotoYpfkrcidpMegxvhs6PAx",
                "K2VUItIYGQMK73RLhys2t1tymlirLeFaig16nrYG");
        passportphoto = (ImageView) findViewById(R.id.use_photo);
        mUserName = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mEmailAdress = (EditText) findViewById(R.id.email_address);
        confirmPass = (EditText) findViewById(R.id.confirm_password);
        UploadPhotoOption = (Button) findViewById(R.id.upload_button);
        userKey = (EditText) findViewById(R.id.handshake);
        signUpBut = (Button) findViewById(R.id.sign_up);
        mFileType = (ParseConstants.KEY_FILE_TYPE);

        UploadPhotoOption.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SginUpActivity.this);
                builder.setItems(R.array.camera_choices, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        signUpBut.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = mUserName.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmailAdress.getText().toString();
                String confirmpassw = confirmPass.getText().toString();
                String handshake = userKey.getText().toString();

                username = username.trim();
                email.trim();
                password = password.trim();
                confirmpassw = confirmpassw.trim();
                handshake = handshake.trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()
                        || confirmpassw.isEmpty() || handshake.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            SginUpActivity.this);
                    builder.setMessage(R.string.signUpError)
                            .setTitle(R.string.errorTitle)
                            .setPositiveButton(string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {

                    newUser.setUsername(username);
                    newUser.setEmail(email);
                    newUser.setPassword(password);
                    newUser.put("handshake", handshake);
                    newUser.signUpInBackground(new SignUpCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(SginUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        SginUpActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.errorTitle)
                                        .setPositiveButton(string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == LOAD_IMAGE_FROM_DRIVE) {
                InputStream stream = null;
              //  Uri   mMediaUri = data.getData();
                try {
                    stream = getContentResolver().openInputStream(data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap photo = BitmapFactory.decodeStream(stream);
            Bitmap  refined_photo =  Bitmap.createScaledBitmap(photo,
                        75, 75, true);
                passportphoto.setImageBitmap(refined_photo);
                Log.i("Request code",requestCode+"");
                Log.i("Uri", mMediaUri.toString());
               // imageId = convertImageUriToFile(mMediaUri, CameraActivity);


                //  Create and excecute AsyncTask to load capture image

            //    new LoadImagesFromSDCard().execute("" + imageId);
                saveUserImageToParse(refined_photo);
               // Bundle bundle = data.getExtras();

                // selectedImagePath = getPath(selectedImageUri);
              //  System.out.println("Image Path : " + selectedImagePath);
              //  imageId = convertImageUriToFile(mMediaUri, CameraActivity);
/*
                InputStream image_stream = null;
                try {
                    image_stream = getContentResolver().openInputStream(mMediaUri);
                } catch (FileNotFoundException e) {
                Log.i("Stack from imagestream",e.toString()) ;
                }
                try {
                   fileBytes = readBytes(image_stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ParseFile file = new ParseFile(newUser.getUsername(), fileBytes);
                file.saveInBackground();
                newUser.put(Components.ParseConstants.Current_USER_PHOTO, file);
                Bitmap bitmap= BitmapFactory.decodeStream(image_stream );
               Bitmap re_sized_Bitmap = resize(bitmap,72,72);


                //  Create and excecute AsyncTask to load capture image

               // new LoadImagesFromSDCard().execute("" + imageId);
               passportphoto.setImageBitmap(re_sized_Bitmap);
                saveUserImageToParse();

*/                    //  imageId = convertImageUriToFile(mMediaUri, CameraActivity);


                //  Create and excecute AsyncTask to load capture image

            //    new LoadImagesFromSDCard().execute("" + imageId);

            }
            else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ) {
               // Uri   mMediaUri = data.getData();
                Log.i("request code",requestCode+"");
                Log.i("media uri",mMediaUri.toString());
                imageId = convertImageUriToFile(mMediaUri, CameraActivity);


                //  Create and excecute AsyncTask to load capture image

                new LoadImagesFromSDCard().execute("" + imageId);
               // saveUserImageToParse();
            }
            //  previewCapturedImage();
            // add it to the Gallery
          /*  Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(mMediaUri);
            previewCapturedImage();
            sendBroadcast(mediaScanIntent);
        } else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(this, R.string.general_error, Toast.LENGTH_LONG).show();
       */
        }
       // Log.i("From photodialog", "" + mMediaUri + "" + data);

        else if ( resultCode == RESULT_CANCELED) {

            Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", mMediaUri);
    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
       mMediaUri = savedInstanceState.getParcelable("file_uri");

     //   passportphoto.setImageBitmap(bitmap);
        Log.i("From restore URI",""+mMediaUri);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sgin_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

                /*  public void upload_Dialog(View view) {

                      FragmentManager manager = getFragmentManager();

                      Components.UploadPhotoDialog dialog = new Components.UploadPhotoDialog();
                      dialog.show(manager, "dialog");

                  }*/


    private Uri getOutputMediaFileUri(int mediaType) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (isExternalStorageAvailable()) {
            // get the URI

            // 1. Get the external storage directory
            String appName = getString(R.string.app_name);
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    appName);

            // 2. Create our subdirectory
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.e(TAG, "Failed to create directory.");
                    return null;
                }
            }

            // 3. Create a file name
            // 4. Create the file
            File mediaFile;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = mediaStorageDir.getPath() + File.separator;
            if (mediaType == MEDIA_TYPE_IMAGE) {
                mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
            } else if (mediaType == MEDIA_TYPE_VIDEO) {
                mediaFile = new File(path + "VID_" + timestamp + ".mp4");
            } else {
                return null;
            }

            Log.d(TAG, "File: " + Uri.fromFile(mediaFile));


            // 5. Return the file's URI
            return Uri.fromFile(mediaFile);
        } else {
            return null;
        }


    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    private void previewCapturedImage() {
        try {
            // hide video preview


            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 4;

           bitmap = BitmapFactory.decodeFile(mMediaUri.getPath(),
                    options);

            passportphoto.setImageBitmap(bitmap);
            Log.i("ImageReport", "Image set but you did not recieve it");
            // imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    /************ Convert Image Uri path to physical path **************/

    public  String convertImageUriToFile ( Uri imageUri, Activity activity )  {

      //  Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String [] proj={
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };


         /*  public String getRealPathFromURI(Uri contentUri) {
    String res = null;
    String[] proj = { MediaStore.Images.Media.DATA };
    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
    if(cursor.moveToFirst()){;
       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
       res = cursor.getString(column_index);
    }
    cursor.close();
    return res;
}
            */



        Cursor   cursor = getContentResolver().query(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {


                imageDetails.setText("No Image");
            }
            else
            {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/

                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID     = cursor.getInt(columnIndex);

                    thumbID     = cursor.getInt(columnIndexThumb);

                    String Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            +" ImageID :"+imageID+"\n"
                            +" ThumbID :"+thumbID+"\n"
                            +" Path :"+Path+"\n";

                    // Show Captured Image detail on activity
                 //   imageDetails.setText( CapturedImageDetails );

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return ""+imageID;
    }







    /**
     * Async task for loading the images from the SD card.
     *
     * @author Android Example
     *
     */

    // Class with extends AsyncTask class

    public class LoadImagesFromSDCard  extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(SginUpActivity.this);

        Bitmap mBitmap;

        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
          //  Dialog.setMessage(" Loading image from Sdcard..");
         //   Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {

                /**  Uri.withAppendedPath Method Description
                 * Parameters
                 *    baseUri  Uri to append path segment to
                 *    pathSegment  encoded path segment to append
                 * Returns
                 *    a new Uri based on baseUri with the given segment appended to the path
                 */

                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);

                /**************  Decode an input stream into a bitmap. *********/
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    /********* Creates a new bitmap, scaled from an existing bitmap. ***********/

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {
                // Error fetching image, try to recover

                /********* Cancel execution of this task. **********/
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
     //       Dialog.dismiss();

            if(mBitmap != null)
            {

              //  Bitmap photo = BitmapFactory.decodeStream(mBitmap);
                Bitmap  refined_photo =  Bitmap.createScaledBitmap(mBitmap,
                        75, 75, true);
                passportphoto.setImageBitmap(refined_photo);
                saveUserImageToParse(refined_photo);
            }

        }

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  ////////////////////////////////////////////////////////////////////ASynTask 2 for restored instance state

    private Bitmap resize(Bitmap bp, int witdh, int height){
        return Bitmap.createScaledBitmap(bp, witdh, height, false);
    }
    public  void saveUserImageToParse(Bitmap m){
      //  byte fileBytes[] = Components.FileHelper.getByteArrayFromFile(this, mMediaUri);
       // byte fileBytes[] = readBytes()


        if(m !=null){
          byte []resultBitmap = getByteArray(m);
            ParseFile file = new ParseFile("userPhoto.png", resultBitmap);
           // String fileName = Components.FileHelper.getFileName(this, mMediaUri, mFileType);
            file.saveInBackground();
            newUser.put(ParseConstants.Current_USER_PHOTO, file);
            newUser.saveInBackground();


        }
    }

    public byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }
    public byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }
}
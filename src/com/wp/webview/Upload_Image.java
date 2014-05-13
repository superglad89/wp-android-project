package com.wp.webview;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class Upload_Image extends Activity implements OnClickListener{
    
    private TextView messageText;
    private Button uploadButton, btnselectpic;
    private ImageView imageview;
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    
    
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
       
    private String upLoadServerUri = null;
    private String imagepath=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.upload_image);
         
        uploadButton = (Button)findViewById(R.id.uploadButton);
        messageText  = (TextView)findViewById(R.id.messageText);
        btnselectpic = (Button)findViewById(R.id.button_selectpic);
        imageview = (ImageView)findViewById(R.id.imageView_pic);
        
        btnselectpic.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        upLoadServerUri = "http://192.168.0.101/linkedin/upload/UploadToServer.php";

    }
     
    
   
    
    
    @Override
	public void onClick(View arg0) {
		if(arg0==btnselectpic)
		{
			/*Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);*/
			
			startDialog();
		}
		else if (arg0==uploadButton) {
			
			 dialog = ProgressDialog.show(Upload_Image.this, "", "Uploading file...", true);
			 messageText.setText("uploading started.....");
			 new Thread(new Runnable() {
                 public void run() {
                      
                	 if(imagepath!=null)
                	 {
                		 uploadFile(imagepath);
                	 }
                	 else
                	 {
                		finish();
                	 }
                      
                                              
                 }
               }).start();     
		}
		
	} 
    
    
    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                Intent.ACTION_GET_CONTENT, null);
                        pictureActionIntent.setType("image/*");
                        pictureActionIntent.putExtra("return-data", true);
                        startActivityForResult(pictureActionIntent,
                                GALLERY_PICTURE);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(pictureActionIntent,
                                CAMERA_REQUEST);

                    }
                });
        myAlertDialog.show();
    }
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    
    	/*if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath(); 
          
            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);
            Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
            imageview.setImageBitmap(bitmap);
            messageText.setText("Uploading file path:" +imagepath);
	    	
	    }*/
    	
    	
    	super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    // our BitmapDrawable for the thumbnail
                    BitmapDrawable bmpDrawable = null;
                    // try to retrieve the image using the data from the intent
                    Cursor cursor = getContentResolver().query(data.getData(),
                            null, null, null, null);
                    if (cursor != null) {

                        cursor.moveToFirst();
                        
                        Uri selectedImageUri = data.getData();
                        imagepath = getPath(selectedImageUri);
                       // Bitmap bitmap=BitmapFactory.decodeFile(imagepath);

                       // int idx = cursor.getColumnIndex(ImageColumns.DATA);
                       // String fileSrc = cursor.getString(idx);
                        bitmap = BitmapFactory.decodeFile(imagepath); // load
                                                                            // preview
                                                                            // image
                        bitmap = Bitmap.createScaledBitmap(bitmap,
                                100, 100, false);
                        // bmpDrawable = new BitmapDrawable(bitmapPreview);
                        imageview.setImageBitmap(bitmap);
                    } else {

                        bmpDrawable = new BitmapDrawable(getResources(), data
                                .getData().getPath());
                        imageview.setImageDrawable(bmpDrawable);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Cancelled",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("data")) {

                    // retrieve the bitmap from the intent
                    bitmap = (Bitmap) data.getExtras().get("data");
                    Cursor cursor = getContentResolver()
                            .query(Media.EXTERNAL_CONTENT_URI,
                                    new String[] {
                                            Media.DATA,
                                            Media.DATE_ADDED,
                                            MediaStore.Images.ImageColumns.ORIENTATION },
                                    Media.DATE_ADDED, null, "date_added ASC");
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            Uri uri = Uri.parse(cursor.getString(cursor
                                    .getColumnIndex(Media.DATA)));
                            imagepath = uri.toString();
                        } while (cursor.moveToNext());
                        cursor.close();
                    }

                    Log.e("path of the image from camera ====> ",
                    		imagepath);


                    bitmap = Bitmap.createScaledBitmap(bitmap, 100,
                            100, false);
                    // update the image view with the bitmap
                    imageview.setImageBitmap(bitmap);
                } else if (data.getExtras() == null) {

                    Toast.makeText(getApplicationContext(),
                            "No extras to retrieve!", Toast.LENGTH_SHORT)
                            .show();

                    BitmapDrawable thumbnail = new BitmapDrawable(
                            getResources(), data.getData().getPath());

                    // update the image view with the newly created drawable
                    imageview.setImageDrawable(thumbnail);

                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    	
    	
    }
   public String getPath(Uri uri) {
    	        String[] projection = { MediaStore.Images.Media.DATA };
    	        Cursor cursor = managedQuery(uri, projection, null, null, null);
    	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    	        cursor.moveToFirst();
    	        return cursor.getString(column_index);
    	    }
	    
    public int uploadFile(String sourceFileUri) {
          
    	  
    	  String fileName = sourceFileUri;
 
          HttpURLConnection conn = null;
          DataOutputStream dos = null;  
          String lineEnd = "\r\n";
          String twoHyphens = "--";
          String boundary = "*****";
          int bytesRead, bytesAvailable, bufferSize;
          byte[] buffer;
          int maxBufferSize = 1 * 1024 * 1024; 
          File sourceFile = new File(sourceFileUri); 
          
          if (!sourceFile.isFile()) {
        	  
	           dialog.dismiss(); 
	           
	           Log.e("uploadFile", "Source File not exist :"+imagepath);
	           
	           runOnUiThread(new Runnable() {
	               public void run() {
	            	   messageText.setText("Source File not exist :"+ imagepath);
	               }
	           }); 
	           
	           return 0;
           
          }
          else
          {
	           try { 
	        	   
	            	 // open a URL connection to the Servlet
	               FileInputStream fileInputStream = new FileInputStream(sourceFile);
	               URL url = new URL(upLoadServerUri);
	               
	               // Open a HTTP  connection to  the URL
	               conn = (HttpURLConnection) url.openConnection(); 
	               conn.setDoInput(true); // Allow Inputs
	               conn.setDoOutput(true); // Allow Outputs
	               conn.setUseCaches(false); // Don't use a Cached Copy
	               conn.setRequestMethod("POST");
	               conn.setRequestProperty("Connection", "Keep-Alive");
	               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	               conn.setRequestProperty("uploaded_file", fileName); 
	               
	               dos = new DataOutputStream(conn.getOutputStream());
	     
	               dos.writeBytes(twoHyphens + boundary + lineEnd); 
	               dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
	            		                     + fileName + "\"" + lineEnd);
	               
	               dos.writeBytes(lineEnd);
	     
	               // create a buffer of  maximum size
	               bytesAvailable = fileInputStream.available(); 
	     
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               buffer = new byte[bufferSize];
	     
	               // read file and write it into form...
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	                 
	               while (bytesRead > 0) {
	            	   
	                 dos.write(buffer, 0, bufferSize);
	                 bytesAvailable = fileInputStream.available();
	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
	                 
	                }
	     
	               // send multipart form data necesssary after file data...
	               dos.writeBytes(lineEnd);
	               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	     
	               // Responses from the server (code and message)
	               serverResponseCode = conn.getResponseCode();
	               String serverResponseMessage = conn.getResponseMessage();
	                
	               Log.i("uploadFile", "HTTP Response is : " 
	            		   + serverResponseMessage + ": " + serverResponseCode);
	               
	               if(serverResponseCode == 200){
	            	   
	                   runOnUiThread(new Runnable() {
	                        public void run() {
	                        	/*String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                      		          +" F:/wamp/wamp/www/uploads";
	                        	messageText.setText(msg);*/
	                            Toast.makeText(Upload_Image.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
	                            
	                            Intent i = new Intent(Upload_Image.this, Profile.class);
	                			startActivity(i);
	                        }
	                    });                
	               }    
	               
	              // Toast.makeText(Upload_Image.this, "File Upload Failed.", Toast.LENGTH_SHORT).show();
	               
	               //close the streams //
	               fileInputStream.close();
	               dos.flush();
	               dos.close();
	                
	          } catch (MalformedURLException ex) {
	        	  
	              dialog.dismiss();  
	              ex.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	  messageText.setText("MalformedURLException Exception : check script url.");
	                      Toast.makeText(Upload_Image.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	                  }
	              });
	              
	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	          } catch (Exception e) {
	        	  
	              dialog.dismiss();  
	              e.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	  messageText.setText("Got Exception : see logcat ");
	                      Toast.makeText(Upload_Image.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
	                  }
	              });
	              Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);  
	          }
	          dialog.dismiss();       
	          return serverResponseCode; 
	          
           } // End else block 
         }

	
}
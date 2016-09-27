package com.javinindia.citymallsbusiness.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.activity.CustomPhotoGalleryActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ashish on 15-09-2016.
 */
public class AddNewOfferFragment extends BaseFragment implements View.OnClickListener {
    private String day = "";
    private String month = "";
    private String year = "";
    private String hour = "";
    private String min = "";
    private String sec = "";

    AppCompatTextView txtChooseCategory, txtAddProductImages, btnStartTime, btnEndTime;
    AppCompatEditText etProductTitle, etProductDiscription;
    AppCompatButton btnSubmitOffer;
    HorizontalScrollView horizontalScrollView;
    private OnCallBackMallListener callback;

    private RequestQueue requestQueue;
    private ImageView imageView;
    private Bitmap yourbitmap;
    private LinearLayout lnrImages;
    public ArrayList<String> map = new ArrayList<String>();
    private ArrayList<String> imagesPathList;
    private String[] imagesPath;
    private int count = 0;
    private int tempId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnCallBackMallListener {
        void OnCallBackMall();
    }

    public void setMyCallBackListener(OnCallBackMallListener callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.scroll1);
        lnrImages = (LinearLayout) view.findViewById(R.id.lnrImages);
        txtChooseCategory = (AppCompatTextView) view.findViewById(R.id.txtChooseCategory);
        txtAddProductImages = (AppCompatTextView) view.findViewById(R.id.txtAddProductImages);
        etProductTitle = (AppCompatEditText) view.findViewById(R.id.etProductTitle);
        etProductDiscription = (AppCompatEditText) view.findViewById(R.id.etProductDiscription);
        btnStartTime = (AppCompatTextView) view.findViewById(R.id.etStartTime);
        btnEndTime = (AppCompatTextView) view.findViewById(R.id.etEndTime);
        btnSubmitOffer = (AppCompatButton) view.findViewById(R.id.btnSubmitOffer);
        btnSubmitOffer.setOnClickListener(this);
        btnStartTime.setOnClickListener(this);
        btnEndTime.setOnClickListener(this);
        txtAddProductImages.setOnClickListener(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.add_new_offer_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitOffer:
                activity.onBackPressed();
                callback.OnCallBackMall();
                break;
            case R.id.etStartTime:
                int flag1 = 0;
                methodOpenDatePicker(flag1);
                break;
            case R.id.etEndTime:
                int flag2 = 1;
                methodOpenDatePicker(flag2);
                break;
            case R.id.txtAddProductImages:
                methodAddImages();
                break;
        }

    }

    private void methodOpenDatePicker(final int flag) {
        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (flag == 0) {
                            btnStartTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        } else {
                            btnEndTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                        day = Integer.toString(dayOfMonth);
                        month = Integer.toString(monthOfYear + 1);
                        AddNewOfferFragment.this.year = Integer.toString(year);
                    }
                }, mYear, mMonth, mDay);
        long now = System.currentTimeMillis() - 1000;
        datePickerDialog.getDatePicker().setMinDate(now);
        // c.add(c.DAY_OF_MONTH, 31);
        //  datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    private void methodAddImages() {
        final CharSequence[] options = {"Take from camera", "Select from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take from camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Select from gallery")) {
                    Intent intent = new Intent(activity, CustomPhotoGalleryActivity.class);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    bitmap = decodeFile(f.getAbsolutePath());
                    try {
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    imageView = new ImageView(activity);
                    imageView.setImageBitmap(bitmap);
                    imageView.setPadding(8, 8, 8, 8);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setAdjustViewBounds(true);
                    lnrImages.addView(imageView);
                    final File finalF = f;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            methodToShowDialogForDelete(v);
                            map.remove(finalF.getAbsolutePath());
                        }
                    });
                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    //f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    String capture = f.getAbsolutePath();
                    map.add(capture);
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                imagesPathList = new ArrayList<String>();
                imagesPath = data.getStringExtra("data").split("\\|");

                for (int i = 0; i < imagesPath.length; i++) {
                    map.add(imagesPath[i]);
                    imagesPathList.add(imagesPath[i]);
                    yourbitmap = decodeFile(imagesPath[i]);
                    imageView = new ImageView(activity);
                    imageView.setImageBitmap(yourbitmap);
                    imageView.setPadding(8, 8, 8, 8);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setAdjustViewBounds(true);
                    lnrImages.addView(imageView);
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            methodToShowDialogForDelete(v);
                            map.remove(imagesPath[finalI]);
                        }

                    });

                }
            }
        }
    }

    public Bitmap decodeFile(String filePath) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        final int REQUIRED_SIZE = 1024;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
        return bitmap;
    }

    private void methodToShowDialogForDelete(final View v) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                activity);
        alertDialogBuilder.setTitle("Delete Image");
        alertDialogBuilder.setMessage("Do you really want to delete this image");
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lnrImages.removeView(v);
            }
        });
        alertDialogBuilder
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

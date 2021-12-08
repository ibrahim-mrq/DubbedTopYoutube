package com.mrq.library.ImagePopup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mrq.library.ImageZoom.ImageZoom;
import com.mrq.library.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * create by Ibrahim Mrq
 * 10/June/2021
 */

public class ImagePopup extends androidx.appcompat.widget.AppCompatImageView {
    private Context context;
    private PopupWindow popupWindow;
    private View layout;
    private ImageZoom imageView;
    private float dimAmount = 0.8f;
    private int windowHeight = 0;
    private int windowWidth = 0;
    private boolean imageOnClickClose = false;
    private boolean hideCloseIcon = false;
    private boolean fullScreen = false;
    private int backgroundColor = Color.parseColor("#ffffff");

    public ImagePopup(Context context) {
        super(context);
        this.context = context;
    }

    public ImagePopup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public float getDimAmount() {
        return dimAmount;
    }

    public void setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setImageOnClickClose(boolean imageOnClickClose) {
        this.imageOnClickClose = imageOnClickClose;
    }

    public boolean isImageOnClickClose() {
        return imageOnClickClose;
    }

    public boolean isHideCloseIcon() {
        return hideCloseIcon;
    }

    public void setHideCloseIcon(boolean hideCloseIcon) {
        this.hideCloseIcon = hideCloseIcon;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public void initiatePopup(Drawable drawable) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.custom_popup, (ViewGroup) findViewById(R.id.popup));
            layout.setBackgroundColor(getBackgroundColor());
            imageView = (ImageZoom) layout.findViewById(R.id.imageView);
            imageView.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initiatePopupWithPicasso(String imageUrl) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.custom_popup, (ViewGroup) findViewById(R.id.popup));
            layout.setBackgroundColor(getBackgroundColor());
            imageView = (ImageZoom) layout.findViewById(R.id.imageView);
            Picasso.get().load(imageUrl).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ImagePopup ", e.getMessage());
        }
    }

    public void initiatePopupWithPicasso(Uri imageUri) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.custom_popup, (ViewGroup) findViewById(R.id.popup));
            layout.setBackgroundColor(getBackgroundColor());
            imageView = (ImageZoom) layout.findViewById(R.id.imageView);
            Picasso.get().load(imageUri).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ImagePopup ", e.getMessage());
        }
    }

    public void initiatePopupWithPicasso(File imageFile) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.custom_popup, (ViewGroup) findViewById(R.id.popup));
            layout.setBackgroundColor(getBackgroundColor());
            imageView = (ImageZoom) layout.findViewById(R.id.imageView);
            Picasso.get().load(imageFile).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ImagePopup ", e.getMessage());
        }
    }

    public void setLayoutOnTouchListener(OnTouchListener onTouchListener) {
        layout.setOnTouchListener(onTouchListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnImageTouchListener(OnTouchListener onTouchListener) {
        imageView.setOnTouchListener(onTouchListener);
    }

    public void setOnImageClickListener(OnClickListener onClickListener) {
        imageView.setOnClickListener(onClickListener);
    }

    public void viewPopup() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        if (isFullScreen()) {
            popupWindow = new PopupWindow(layout, (width), (height), true);
        } else {
            if (windowHeight != 0 || windowWidth != 0) {
                width = windowWidth;
                height = windowHeight;
                popupWindow = new PopupWindow(layout, (width), (height), true);
            } else {
                popupWindow = new PopupWindow(layout, (int) (width * .8), (int) (height * .6), true);
            }
        }

        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
        ImageView closeIcon = (ImageView) layout.findViewById(R.id.closeBtn);

        if (isHideCloseIcon()) {
            closeIcon.setVisibility(View.GONE);
        }
        closeIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        if (isImageOnClickClose()) {
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        }
        dimBehind(popupWindow);
    }

    public void dimBehind(PopupWindow popupWindow) {
        try {
            View container;
            if (popupWindow.getBackground() == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    container = (View) popupWindow.getContentView().getParent();
                } else {
                    container = popupWindow.getContentView();
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    container = (View) popupWindow.getContentView().getParent().getParent();
                } else {
                    container = (View) popupWindow.getContentView().getParent();
                }
            }
            Context context = popupWindow.getContentView().getContext();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = dimAmount;
            wm.updateViewLayout(container, p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

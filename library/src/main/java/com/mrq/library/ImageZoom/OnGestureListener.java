package com.mrq.library.ImageZoom;

/**
 * create by Ibrahim Mrq
 * 10/June/2021
 */

interface OnGestureListener {

    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);

    void onScale(float scaleFactor, float focusX, float focusY, float dx, float dy);
}
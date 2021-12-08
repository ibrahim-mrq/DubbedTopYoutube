package com.mrq.library.ImageZoom;

import android.graphics.RectF;

/**
 * create by Ibrahim Mrq
 * 10/June/2021
 */

public interface OnMatrixChangedListener {

    /**
     * Callback for when the Matrix displaying the Drawable has changed. This could be because
     * the View's bounds have changed, or the user has zoomed.
     *
     * @param rect - Rectangle displaying the Drawable's new bounds.
     */
    void onMatrixChanged(RectF rect);
}

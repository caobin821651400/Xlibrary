package com.cb.qrcode.library.core;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 扫描SurfaceView,主要处理扫描的基本操作
 */
public abstract class BarcodeScannerView extends FrameLayout implements Camera.PreviewCallback {
    private CameraWrapper mCameraWrapper;
    private CameraPreview mPreview;
    private Rect mFramingRect;//扫码框所占区域
    private Rect mFramingRectInPreview;
    private CameraHandlerThread mCameraHandlerThread;
    private Boolean mFlashState;
    private boolean mAutoFocusState = true;
    protected boolean mCameraIsRelease = true;

    public BarcodeScannerView(Context context) {
        this(context, null);
    }

    public BarcodeScannerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BarcodeScannerView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);

    }

    /**
     * 添加SurfaceView
     *
     * @param cameraWrapper
     */
    public final void setupLayout(CameraWrapper cameraWrapper) {
        removeAllViews();
        mPreview = new CameraPreview(getContext(), cameraWrapper, this);
        addView(mPreview);
    }

    /**
     * 设置扫描框的rect
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setScanRect(int left, int top, int right, int bottom) {
        mFramingRect = new Rect(left, top, right, bottom);
    }

    /**
     * 初始化camera
     *
     * @param cameraId
     */
    public void startCamera(int cameraId) {
        if (mCameraHandlerThread == null) {
            mCameraHandlerThread = new CameraHandlerThread(this);
        }
        mCameraHandlerThread.startCamera(cameraId);
    }

    /**
     * 自动对焦
     *
     * @param cameraWrapper
     */
    public void setupCameraPreview(CameraWrapper cameraWrapper) {
        mCameraWrapper = cameraWrapper;
        if (mCameraWrapper != null) {
            setupLayout(mCameraWrapper);
            if (mFlashState != null) {
                setFlash(mFlashState);
            }
            setAutoFocus(mAutoFocusState);
        }
    }

    /**
     *
     */
    public void startCamera() {
        mCameraIsRelease = false;
        startCamera(CameraUtils.getDefaultCameraId());
    }

    /**
     * 停止相机采集
     */
    public void stopCamera() {
        mCameraIsRelease = true;
        if (mCameraWrapper != null) {
            mCameraWrapper.mCamera.setPreviewCallback(null);
            mPreview.stopCameraPreview();
            mPreview.setCamera(null, null);
            mCameraWrapper.mCamera.release();
            mCameraWrapper = null;
        }
        if (mCameraHandlerThread != null) {
            mCameraHandlerThread.quit();
            mCameraHandlerThread = null;
        }
    }

    /**
     *
     */
    public void stopCameraPreview() {
        if (mPreview != null) {
            mPreview.stopCameraPreview();
        }
    }

    protected void resumeCameraPreview() {
        if (mPreview != null) {
            mPreview.showCameraPreview();
        }
    }

    /**
     * 采集解析区域
     *
     * @param previewWidth
     * @param previewHeight
     * @return
     */
    public synchronized Rect getFramingRectInPreview(int previewWidth, int previewHeight) {
        if (mFramingRectInPreview == null) {
            int viewFinderViewWidth = mFramingRect.width();
            int viewFinderViewHeight = mFramingRect.height();
            if (mFramingRect == null || viewFinderViewWidth == 0 || viewFinderViewHeight == 0) {
                return null;
            }

            Rect rect = new Rect(mFramingRect);

            if (previewWidth < viewFinderViewWidth) {
                rect.left = rect.left * previewWidth / viewFinderViewWidth;
                rect.right = rect.right * previewWidth / viewFinderViewWidth;
            }

            if (previewHeight < viewFinderViewHeight) {
                rect.top = rect.top * previewHeight / viewFinderViewHeight;
                rect.bottom = rect.bottom * previewHeight / viewFinderViewHeight;
            }

            mFramingRectInPreview = rect;
        }
        return mFramingRectInPreview;
    }

    /**
     * 设置闪光灯装填
     *
     * @param flag 开 关
     */
    public void setFlash(boolean flag) {
        mFlashState = flag;
        if (mCameraWrapper != null && CameraUtils.isFlashSupported(mCameraWrapper.mCamera)) {

            Camera.Parameters parameters = mCameraWrapper.mCamera.getParameters();
            if (flag) {
                if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                    return;
                }
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            } else {
                if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)) {
                    return;
                }
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            }
            mCameraWrapper.mCamera.setParameters(parameters);
        }
    }

    /**
     * 获取闪光灯状态
     *
     * @return
     */
    public boolean getFlash() {
        if (mCameraWrapper != null && CameraUtils.isFlashSupported(mCameraWrapper.mCamera)) {
            Camera.Parameters parameters = mCameraWrapper.mCamera.getParameters();
            if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 设置自动对焦
     *
     * @param state
     */
    public void setAutoFocus(boolean state) {
        mAutoFocusState = state;
        if (mPreview != null) {
            mPreview.setAutoFocus(state);
        }
    }
}

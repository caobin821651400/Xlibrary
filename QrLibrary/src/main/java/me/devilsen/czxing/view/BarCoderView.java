package me.devilsen.czxing.view;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import me.devilsen.czxing.camera.CameraSurface;
import me.devilsen.czxing.camera.CameraUtil;
import me.devilsen.czxing.util.BarCodeUtil;

/**
 * @author : dongSen
 * date : 2019-06-29 15:35
 * desc : 二维码扫描界面，包括Surface和Box
 */
abstract class BarCoderView extends FrameLayout implements Camera.PreviewCallback {

    private static final int NO_CAMERA_ID = -1;
    private static final int DEFAULT_ZOOM_SCALE = 4;
    private static final long ONE_HUNDRED_MILLISECONDS = 100_000_000;
    private final static long DELAY_STEP_TIME = 10000000;

    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private Camera mCamera;
    CameraSurface mCameraSurface;
    ScanBoxView mScanBoxView;

    protected boolean mSpotAble;
    private int scanSequence;

    protected ScanListener mScanListener;

    private long processLastTime;
    private long mDelayTime = 3 * ONE_HUNDRED_MILLISECONDS;

    public BarCoderView(Context context) {
        this(context, null);
    }

    public BarCoderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarCoderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackground(null);
        mCameraSurface = new CameraSurface(context);
        mCameraSurface.setPreviewListener(new CameraSurface.SurfacePreviewListener() {
            @Override
            public void onStartPreview() {
                setPreviewCallback();
            }
        });

        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mCameraSurface, params);

        mScanBoxView = new ScanBoxView(context);
        addView(mScanBoxView, params);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCameraSurface.setScanBoxPoint(mScanBoxView.getScanBoxCenter());
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        long now = System.nanoTime();
        if (Math.abs(now - processLastTime) < mDelayTime) {
            return;
        }
        processLastTime = now;

        try {
            Rect scanBoxRect = mScanBoxView.getScanBoxRect();
            int scanBoxSize = mScanBoxView.getScanBoxSizeExpand();
            int expandTop = mScanBoxView.getExpandTop();
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            int left;
            int top;
            int rowWidth = size.width;
            int rowHeight = size.height;
            // 这里需要把得到的数据也翻转
            if (CameraUtil.isPortrait(getContext())) {
                left = scanBoxRect.top - expandTop;
                top = scanBoxRect.left;
            } else {
                left = scanBoxRect.left;
                top = scanBoxRect.top - expandTop;
            }
            onPreviewFrame(data, left, top, scanBoxSize, scanBoxSize, rowWidth);

            if (scanSequence % 4 == 0) {
                onPreviewFrame(data, 0, 0, rowWidth, rowHeight, rowWidth);
            }
            scanSequence++;

//            BarCodeUtil.d("scan sequence " + scanSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onPreviewFrame(byte[] data, int left, int top, int width, int height, int rowWidth);

    public void setScanListener(ScanListener listener) {
        mScanListener = listener;
    }

    public void startScan() {
        mSpotAble = true;
        openCamera();
        setPreviewCallback();
    }

    public void stopScan() {
        mSpotAble = false;

        if (mCamera == null) {
            return;
        }
        try {
            mCamera.setPreviewCallback(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openCamera() {
        openCamera(mCameraId);
    }

    public void openCamera(int cameraFacing) {
        if (mCamera != null || Camera.getNumberOfCameras() == 0) {
            return;
        }
        int ultimateCameraId = findCameraIdByFacing(cameraFacing);
        if (ultimateCameraId != NO_CAMERA_ID) {
            startCameraById(ultimateCameraId);
            return;
        }

        if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            ultimateCameraId = findCameraIdByFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else if (cameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            ultimateCameraId = findCameraIdByFacing(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (ultimateCameraId != NO_CAMERA_ID) {
            startCameraById(ultimateCameraId);
        }
    }

    private int findCameraIdByFacing(int cameraFacing) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int cameraId = 0; cameraId < Camera.getNumberOfCameras(); cameraId++) {
            try {
                Camera.getCameraInfo(cameraId, cameraInfo);
                if (cameraInfo.facing == cameraFacing) {
                    return cameraId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return NO_CAMERA_ID;
    }

    private void startCameraById(int cameraId) {
        try {
            mCameraId = cameraId;
            mCamera = Camera.open(cameraId);
            mCameraSurface.setCamera(mCamera);
        } catch (Exception e) {
            e.printStackTrace();
            if (mScanListener != null) {
                mScanListener.onOpenCameraError();
            }
        }
    }

    /**
     * 添加摄像头获取图像数据的回调
     */
    private void setPreviewCallback() {
        if (mSpotAble && mCameraSurface.isPreviewing()) {
            try {
                mCamera.setPreviewCallback(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭摄像头预览
     */
    public void closeCamera() {
        try {
            stopSpotAndHiddenRect();
            if (mCamera != null) {
                mCameraSurface.closeFlashlight();
                mCameraSurface.stopCameraPreview();
                mCameraSurface.setCamera(null);
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止识别，并且隐藏扫描框
     */
    public void stopSpotAndHiddenRect() {
        stopScan();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 设置时间回调处理间隔
     *
     * @param queueSize 阻塞线程中正在处理的线程数
     */
    void setQueueSize(int queueSize) {
        if (queueSize == 0 && mDelayTime > ONE_HUNDRED_MILLISECONDS) {
            mDelayTime -= DELAY_STEP_TIME;
        }

        if (queueSize > 1) {
            mDelayTime += DELAY_STEP_TIME * 2;
        }

//        BarCodeUtil.d("delay time : " + mDelayTime / 1000000);
    }

    public ScanBoxView getScanBox() {
        return mScanBoxView;
    }

    public void onDestroy() {
        closeCamera();
        mScanListener = null;
    }

}

package com.example.arcoretest.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.arcoretest.R;

import java.io.File;

import java.io.IOException;
import java.util.List;
public class GeoFragment extends Fragment {

    private SurfaceView sv;
    private SurfaceHolder surfaceHolder;
    private HolderCallback holderCallback;
    private Camera camera;
    private ImageView pictureButton;
    private FrameLayout frameLayout;
    private File photoFile;
    private Context context = getActivity();
    private ImageView currentImage;
    final int CAMERA_ID = 0;
    private int degrees;
    private Bitmap photoBitmap;
    final boolean FULL_SCREEN = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.geo_fragment_layout, null);

    }

    @Override
    public void onStart() {
        super.onStart();
        sv = getView().findViewById(R.id.surface_view);
        surfaceHolder = sv.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        pictureButton = (ImageView) getView().findViewById(R.id.picture_button);
        currentImage = (ImageView) getView().findViewById(R.id.current_image);

        holderCallback = new HolderCallback();
        surfaceHolder.addCallback(holderCallback);

        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        try {
                            PictureFragment pictureFragment = new PictureFragment();

                            photoBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            if(photoBitmap.getHeight()<photoBitmap.getWidth())
                                rotatePhoto();
                            pictureFragment.setBitmap(photoBitmap);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_frame, pictureFragment).commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


    }





    class HolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            camera.stopPreview();
            setCameraDisplayOrientation(CAMERA_ID);
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }


    }

    void setPreviewSize(boolean fullScreen) {

        // получаем размеры экрана
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        boolean widthIsMax = display.getWidth() > display.getHeight();

        // определяем размеры превью камеры
        Camera.Size size = camera.getParameters().getPreviewSize();

        RectF rectDisplay = new RectF();
        RectF rectPreview = new RectF();

        // RectF экрана, соотвествует размерам экрана
        rectDisplay.set(0, 0, display.getWidth(), display.getHeight());

        // RectF первью
        if (widthIsMax) {
            // превью в горизонтальной ориентации
            rectPreview.set(0, 0, size.width, size.height);
        } else {
            // превью в вертикальной ориентации
            rectPreview.set(0, 0, size.height, size.width);
        }

        Matrix matrix = new Matrix();
        // подготовка матрицы преобразования
        if (!fullScreen) {
            // если превью будет "втиснут" в экран (второй вариант из урока)
            matrix.setRectToRect(rectPreview, rectDisplay,
                    Matrix.ScaleToFit.START);
        } else {
            // если экран будет "втиснут" в превью (третий вариант из урока)
            matrix.setRectToRect(rectDisplay, rectPreview,
                    Matrix.ScaleToFit.START);
            matrix.invert(matrix);
        }
        // преобразование
        matrix.mapRect(rectPreview);

        // установка размеров surface из получившегося преобразования
        sv.getLayoutParams().height = (int) (rectPreview.bottom);
        sv.getLayoutParams().width = (int) (rectPreview.right);
    }

    void setCameraDisplayOrientation(int cameraId) {
        // определяем насколько повернут экран от нормального положения
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result = 0;

        // получаем инфо по камере cameraId
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        // задняя камера
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            result = ((360 - degrees) + info.orientation);
        } else
            // передняя камера
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = ((360 - degrees) - info.orientation);
                result += 360;
            }
        result = result % 360;
        camera.setDisplayOrientation(result);
    }


    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open(CAMERA_ID);
        setPreviewSize(FULL_SCREEN);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setJpegQuality(100);

        List<Camera.Size> supportedSizes = parameters.getSupportedPictureSizes();
        Camera.Size sizePicture = (supportedSizes.get(0));
        parameters.setPictureSize(2048, 1536);

        camera.setParameters(parameters);

        /*Camera.Parameters params = camera.getParameters();
        List<Camera.Size> supportedSizes = params.getSupportedPictureSizes();
        Camera.Size sizePicture = (supportedSizes.get(0));
        params.setPictureSize(sizePicture.width, sizePicture.height);
        camera.setParameters(params);*/

    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null)
            camera.release();
        camera = null;
    }

    public void rotatePhoto() {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        photoBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, photoBitmap.getWidth(), photoBitmap.getHeight(),
                matrix, true);

    }


}

package com.example.arcoretest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PictureFragment extends Fragment {

    private ImageView distanceImage;
    private View topView;
    private RelativeLayout relativeLayout_top;
    private RelativeLayout relativeLayout_bottom;
    private int windowwidth;
    private int windowheight;
    private int top;
    private int bot;
    Context context = getActivity();
    private TextView showPix;
    private Bitmap photoBitmap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.picture_fragment_layout, null);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        distanceImage = (ImageView) getActivity().findViewById(R.id.iv_distance);

        showPix = (TextView) getView().findViewById(R.id.showPix);
        topView = getView().findViewById(R.id.view_top);
        relativeLayout_top = (RelativeLayout) getView().findViewById(R.id.rl_top);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout_top.getLayoutParams();
        layoutParams.topMargin = windowheight/3;
        relativeLayout_top.setLayoutParams(layoutParams);


        relativeLayout_bottom = (RelativeLayout) getView().findViewById(R.id.rl_bottom);
        RelativeLayout.LayoutParams layoutParamsBottom = (RelativeLayout.LayoutParams) relativeLayout_bottom.getLayoutParams();
        layoutParamsBottom.topMargin = windowheight - windowheight/3;
        relativeLayout_bottom.setLayoutParams(layoutParamsBottom);
        //setImage();





        relativeLayout_top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout_top.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();

                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight/2) {
                            y_cord = windowheight/2;
                        }

                        layoutParams.topMargin = y_cord - 1;
                        top = layoutParams.topMargin;
                        relativeLayout_top.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        relativeLayout_bottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout_bottom.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();

                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight) {
                            y_cord = windowheight;
                        } else {
                            if (y_cord < windowheight/2)
                                y_cord = windowheight/2;
                        }

                        layoutParams.topMargin = y_cord - 1;
                        bot = layoutParams.topMargin;
                        relativeLayout_bottom.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });*/

    }

    @Override
    public void onStart(){
        super.onStart();
        windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        distanceImage = (ImageView) getActivity().findViewById(R.id.iv_distance);

        showPix = (TextView) getView().findViewById(R.id.showPix);
        topView = getView().findViewById(R.id.view_top);
        relativeLayout_top = (RelativeLayout) getView().findViewById(R.id.rl_top);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout_top.getLayoutParams();
        layoutParams.topMargin = windowheight/3;
        relativeLayout_top.setLayoutParams(layoutParams);


        relativeLayout_bottom = (RelativeLayout) getView().findViewById(R.id.rl_bottom);
        RelativeLayout.LayoutParams layoutParamsBottom = (RelativeLayout.LayoutParams) relativeLayout_bottom.getLayoutParams();
        layoutParamsBottom.topMargin = windowheight - windowheight/3;
        relativeLayout_bottom.setLayoutParams(layoutParamsBottom);
        setImage();



        relativeLayout_top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout_top.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();

                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight/2) {
                            y_cord = windowheight/2;
                        }

                        layoutParams.topMargin = y_cord - 1;
                        top = layoutParams.topMargin;
                        relativeLayout_top.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        relativeLayout_bottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout_bottom.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();

                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight) {
                            y_cord = windowheight;
                        } else {
                            if (y_cord < windowheight/2)
                                y_cord = windowheight/2;
                        }

                        layoutParams.topMargin = y_cord - 1;
                        bot = layoutParams.topMargin;
                        relativeLayout_bottom.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void setImage() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Bitmap data = photoBitmap;
        RectF rectDisplay = new RectF();
        rectDisplay.set(0, 0, display.getWidth(), display.getHeight());
        RectF rectPreview = new RectF();
        rectPreview.set(0, 0, data.getHeight(), data.getWidth());

        Matrix matrix2 = new Matrix();
        matrix2.setRectToRect(rectDisplay, rectPreview,
                Matrix.ScaleToFit.START);
        matrix2.invert(matrix2);

        matrix2.mapRect(rectPreview);
        distanceImage.getLayoutParams().height = (int) (rectPreview.bottom);
        distanceImage.getLayoutParams().width = (int) (rectPreview.right);


        distanceImage.setImageBitmap(data);
    }

    public void setBitmap(Bitmap bitmap) {
        photoBitmap = bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();

    }





}

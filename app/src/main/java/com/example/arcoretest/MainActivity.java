package com.example.arcoretest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;

import static java.lang.Math.cos;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "666";
    private static final double MIN_OPENGL_VERSION = 3.0;
    ArFragment arFragment;
    ModelRenderable testModel;
    ModelRenderable tubeModel;
    ViewRenderable testViewRenderable;
    ModelRenderable tubeRenderable;
    private double mLant = 55.790612;
    private double mLong = 49.123078;
    private double pointLant = 55.741623;
    private double pointLong = 49.221095;
    private double northDif;
    private double coordNorth;
    private double longDif;
    private double coordLong;
    private MainActivityPresenter mainActivityPresenter;
    private float zCoord;
    private Anchor anchor;
    int z;

    private ButtonRectangle waterButton;
    private ButtonRectangle electricityButton;
    private ButtonRectangle dataButton;
    private ButtonRectangle gasButton;

    private static final double LATITUDE_COEF = 111134.861111;
    private static final double LONGITUDE_COEF = 111321.377778;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!checkIsSupportedDeviceOrFinish(this))
            return;

        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        waterButton = findViewById(R.id.button_water);
        electricityButton = findViewById(R.id.button_electricity);
        dataButton = findViewById(R.id.button_data);
        gasButton = findViewById(R.id.button_gas);




        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setWaterObjectsToVrMap(new LinkedList<>());
            }
        });

        electricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setElectricityObjectsToVrMap(new LinkedList<>());
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mainActivityPresenter = new MainActivityPresenter(this);
        ModelRenderable.builder()
                .setSource(this, Uri.parse("untitled.sfb"))
                .build()

                .thenAccept(renderable -> testModel = renderable)
                .exceptionally(throwable -> {
                    Toast toast =
                            Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });

        /*arFragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                Frame frame = arFragment.getArSceneView().getArFrame();
                HitResult hitResult = frame.hitTest()
            }
        });
*/
        /*northDif = pointLant - mLant;
        coordNorth = northDif*LATITUDE_COEF;

        longDif = pointLong - mLong;
        coordLong = cos(mLant)*LONGITUDE_COEF*longDif;*/

        arFragment.setOnTapArPlaneListener(
                (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {
                    if (testModel == null) {
                        return;
                    }
                    /*hitresult.getHitPose().extractTranslation()
                    hitresult.getHitPose().compose(new Pose())*/
                    anchor = hitresult.createAnchor();
                    zCoord = plane.getExtentZ();
                    //setWaterObjectsToVrMap(new LinkedList<>());
                   // mainActivityPresenter.activityIsReady();
                }
        );
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }


    public void showToast() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setWaterObjectsToVrMap(List<LocalWaterObject> list) {
        TestData testData = new TestData();
        final List<LocalWaterObject> list1 = testData.getTestWaterObjects();
        for (int i = 0; i <list1.size(); i++) {
             z = i;

//Тут добавляю табличку с инфой ёклмн
            TransformableNode infoNodeStart;
            infoNodeStart = new TransformableNode(arFragment.getTransformationSystem());
            TransformableNode infoNodeEnd;
            infoNodeEnd = new TransformableNode(arFragment.getTransformationSystem());

            TransformableNode tubeNode;

            ViewRenderable.builder()
                    .setView(this, R.layout.point_info_layout)
                    .build().thenAccept(new Consumer<ViewRenderable>() {
                        @Override
                        public void accept(ViewRenderable viewRenderable) {
                            infoNodeStart.setRenderable(viewRenderable);
                            infoNodeEnd.setRenderable(viewRenderable);
                            ((TextView) viewRenderable.getView().findViewById(R.id.water_type)).setText(list1.get(z).getType());
                            ((TextView) viewRenderable.getView().findViewById(R.id.water_depth)).setText("Глубина: " + list1.get(z).getDepth());
                            ((TextView) viewRenderable.getView().findViewById(R.id.water_company)).setText("Обслуживающая организация: " + list1.get(z).getOwner());
                            ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_name)).setText("Проведенные работы: " + list1.get(z).getWorkInfo());
                            ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_date)).setText("Дата работ: " + list1.get(z).getWorkDate());
                        }
                    });


            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            infoNodeStart.setParent(anchorNode);
            infoNodeStart.setWorldPosition(list1.get(i).getFullCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            infoNodeStart.select();

            AnchorNode anchorNode1 = new AnchorNode(anchor);
            anchorNode1.setParent(arFragment.getArSceneView().getScene());
            infoNodeEnd.setParent(anchorNode);
            infoNodeEnd.setWorldPosition(list1.get(i).getEndCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            infoNodeEnd.select();

            /*MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                    .thenAccept(
                            material -> {
                                tubeRenderable =
                                        ShapeFactory.makeCylinder(0.5f, 2, new Vector3(1f, -1f, 1f), material); });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderable);*/
            /*Quaternion q1 = anchorNode2.getLocalRotation();
            Quaternion q2 = Quaternion.axisAngle(new Vector3(0, 1f, 0f), .2f);
            anchorNode2.setLocalRotation(Quaternion.multiply(q1, q2));
            tubeNode.setLocalRotation(Quaternion.multiply(q1,q2));*/

            //tubeNode.setWorldPosition(list1.get(i).getEndCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            /*tubeNode.select();
            Quaternion q1 = anchorNode2.getLocalRotation();
            Quaternion q2 = Quaternion.axisAngle(new Vector3(0, 1f, 0f), .2f);

            tubeNode.setLocalRotation(Quaternion.multiply(q1,q2));*/
            //tubeNode.getRotationController().setRotationRateDegrees(90);



        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setElectricityObjectsToVrMap(List<LocalElectricityObject> list) {
        TestData testData = new TestData();
        final List<LocalElectricityObject> list1 = testData.getTestElectricityObjects();
        for (int i = 0; i <list1.size(); i++) {
            z = i;

//Тут добавляю табличку с инфой ёклмн
            TransformableNode infoNodeStart;
            infoNodeStart = new TransformableNode(arFragment.getTransformationSystem());
            TransformableNode infoNodeEnd;
            infoNodeEnd = new TransformableNode(arFragment.getTransformationSystem());

            TransformableNode tubeNode;

            ViewRenderable.builder()
                    .setView(this, R.layout.electricity_info_layout)
                    .build().thenAccept(new Consumer<ViewRenderable>() {
                @Override
                public void accept(ViewRenderable viewRenderable) {
                    infoNodeStart.setRenderable(viewRenderable);
                    infoNodeEnd.setRenderable(viewRenderable);
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_type)).setText(list1.get(z).getType());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_depth)).setText("Глубина: " + list1.get(z).getDepth());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_company)).setText("Обслуживающая организация: " + list1.get(z).getOwner());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_last_work_name)).setText("Проведенные работы: " + list1.get(z).getWorkInfo());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_last_work_date)).setText("Дата работ: " + list1.get(z).getWorkDate());
                }
            });


            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            infoNodeStart.setParent(anchorNode);
            infoNodeStart.setWorldPosition(list1.get(i).getFullCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            infoNodeStart.select();

            AnchorNode anchorNode1 = new AnchorNode(anchor);
            anchorNode1.setParent(arFragment.getArSceneView().getScene());
            infoNodeEnd.setParent(anchorNode);
            infoNodeEnd.setWorldPosition(list1.get(i).getEndCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            infoNodeEnd.select();

            /*MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                    .thenAccept(
                            material -> {
                                tubeRenderable =
                                        ShapeFactory.makeCylinder(0.5f, 2, new Vector3(1f, -1f, 1f), material); });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderable);*/
            /*Quaternion q1 = anchorNode2.getLocalRotation();
            Quaternion q2 = Quaternion.axisAngle(new Vector3(0, 1f, 0f), .2f);
            anchorNode2.setLocalRotation(Quaternion.multiply(q1, q2));
            tubeNode.setLocalRotation(Quaternion.multiply(q1,q2));*/

            //tubeNode.setWorldPosition(list1.get(i).getEndCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            /*tubeNode.select();
            Quaternion q1 = anchorNode2.getLocalRotation();
            Quaternion q2 = Quaternion.axisAngle(new Vector3(0, 1f, 0f), .2f);

            tubeNode.setLocalRotation(Quaternion.multiply(q1,q2));*/
            //tubeNode.getRotationController().setRotationRateDegrees(90);



        }

    }




    public float getzCoord() {
        return zCoord;
    }




}

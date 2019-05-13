package com.example.arcoretest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
    private LinkedList currentObjects;
    int z;

    Context context = this;

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

        currentObjects = new LinkedList();


        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //testBualt();
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

    /*@RequiresApi(api = Build.VERSION_CODES.N)
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

            //Рисую цилиндр
            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                    .thenAccept(
                            material -> {
                                tubeRenderable =
                                        ShapeFactory
                                                .makeCylinder(0.1f, (float) Math.sqrt(
                                                        Math.pow(list1.get(z).getEndCoordinate().x-list1.get(z).getFullCoordinate().x,2) + Math.pow(list1.get(z).getEndCoordinate().z-list1.get(z).getFullCoordinate().z,2)),
                                                        new Vector3((list1.get(z).getEndCoordinate().x+list1.get(z).getFullCoordinate().x)/2, -1, (list1.get(z).getEndCoordinate().z+list1.get(z).getFullCoordinate().z)/2), material); });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderable);

            tubeNode.setWorldPosition(list1.get(i).getEndCoordinate());

            tubeNode.select();
            //Тут вроде разность векторов
            Vector3 vector = Vector3.subtract(list1.get(i).getEndCoordinate(), list1.get(i).getFullCoordinate());

            Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());

// Rotate 90° along the right vector (1, 0, 0)
            Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
            tubeNode.setWorldRotation(worldRotation);

        }

    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setWaterObjectsToVrMap(List<LocalWaterObject> list) {
        TestData testData = new TestData();
        final List<LocalWaterObject> list1 = testData.getTestWaterObjects();
        for (int i = 0; i <list1.size(); i++) {
            z = i;

//Тут добавляю табличку с инфой ёклмн
            LocalWaterObject waterObject = list1.get(i);
            /*waterObject.setFullCoordinate(new Vector3(1, -1, 1));
            waterObject.setEndCoordinate(new Vector3(2, -1, 2));
            waterObject.setDepth(new Random().nextInt(30));
            waterObject.setOwner("ЖилСтройОрг");
            waterObject.setType("Вода");
            waterObject.setWorkDate(new Date().toString());
            waterObject.setWorkInfo("Прокладка труб");*/

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
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_type)).setText(waterObject.getType());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_depth)).setText("Глубина: " + waterObject.getDepth());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_company)).setText("Обслуживающая организация: " + waterObject.getOwner());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_name)).setText("Проведенные работы: " + waterObject.getWorkInfo());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_date)).setText("Дата работ: " + waterObject.getWorkDate());
                }
            });


            /*AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            infoNodeStart.setParent(anchorNode);*/
            //infoNodeStart.setWorldPosition(waterObject.getFullCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            //infoNodeStart.select();

            /*AnchorNode anchorNode1 = new AnchorNode(anchor);
            anchorNode1.setParent(arFragment.getArSceneView().getScene());
            infoNodeEnd.setParent(anchorNode);*/
            //infoNodeEnd.setWorldPosition(waterObject.getEndCoordinate());
            //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
            //infoNodeEnd.select();



            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                    .thenAccept(
                            material -> {
                                tubeRenderable =
                                        ShapeFactory
                                                .makeCylinder(0.1f, (float) Math.sqrt(
                                                        Math.pow(waterObject.getEndCoordinate().x-waterObject.getFullCoordinate().x,2) + Math.pow(waterObject.getEndCoordinate().z-waterObject.getFullCoordinate().z,2)),
                                                        new Vector3((waterObject.getEndCoordinate().x+waterObject.getFullCoordinate().x)/2, -1, (waterObject.getEndCoordinate().z+waterObject.getFullCoordinate().z)/2), material); });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderable);

//Возможно эта строчка не нужна.
            //tubeNode.setWorldPosition(waterObject.getEndCoordinate());

            //tubeNode.select();
            //Тут вроде разность векторов
            Vector3 vector = Vector3.subtract(waterObject.getEndCoordinate(), waterObject.getFullCoordinate());

            //Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());

// Rotate 90° along the right vector (1, 0, 0)
            //Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
            tubeNode.setWorldRotation(Quaternion.axisAngle(getPerpen(vector), 75));
            tubeNode.setWorldPosition(new Vector3(waterObject.getFullCoordinate().x, -1, waterObject.getFullCoordinate().z));

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




    //Метод для тестирования поворота модели трубы
    public void testBualt() {
        LocalWaterObject waterObject = new LocalWaterObject();
        waterObject.setFullCoordinate(new Vector3(1, -1, 1));
        waterObject.setEndCoordinate(new Vector3(2, -1, 2));
        waterObject.setDepth(new Random().nextInt(30));
        waterObject.setOwner("ЖилСтройОрг");
        waterObject.setType("Вода");
        waterObject.setWorkDate(new Date().toString());
        waterObject.setWorkInfo("Прокладка труб");

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
                ((TextView) viewRenderable.getView().findViewById(R.id.water_type)).setText(waterObject.getType());
                ((TextView) viewRenderable.getView().findViewById(R.id.water_depth)).setText("Глубина: " + waterObject.getDepth());
                ((TextView) viewRenderable.getView().findViewById(R.id.water_company)).setText("Обслуживающая организация: " + waterObject.getOwner());
                ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_name)).setText("Проведенные работы: " + waterObject.getWorkInfo());
                ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_date)).setText("Дата работ: " + waterObject.getWorkDate());
            }
        });


        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        infoNodeStart.setParent(anchorNode);
        infoNodeStart.setWorldPosition(waterObject.getFullCoordinate());
        //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
        infoNodeStart.select();

        AnchorNode anchorNode1 = new AnchorNode(anchor);
        anchorNode1.setParent(arFragment.getArSceneView().getScene());
        infoNodeEnd.setParent(anchorNode);
        infoNodeEnd.setWorldPosition(waterObject.getEndCoordinate());
        //Log.i("DATA", list1.get(i).getEndCoordinate() + "");
        infoNodeEnd.select();



        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> {
                            tubeRenderable =
                                    ShapeFactory
                                            .makeCylinder(0.1f, (float) Math.sqrt(
                                                    Math.pow(waterObject.getEndCoordinate().x-waterObject.getFullCoordinate().x,2) + Math.pow(waterObject.getEndCoordinate().z-waterObject.getFullCoordinate().z,2)),
                                                    new Vector3((waterObject.getEndCoordinate().x+waterObject.getFullCoordinate().x)/2, -1, (waterObject.getEndCoordinate().z+waterObject.getFullCoordinate().z)/2), material); });

        AnchorNode anchorNode2 = new AnchorNode(anchor);
        anchorNode2.setParent(arFragment.getArSceneView().getScene());
        tubeNode = new TransformableNode(arFragment.getTransformationSystem());
        tubeNode.setParent(anchorNode2);
        tubeNode.setRenderable(tubeRenderable);

//Возможно эта строчка не нужна.
        //tubeNode.setWorldPosition(waterObject.getEndCoordinate());

        tubeNode.select();
        //Тут вроде разность векторов
        Vector3 vector = Vector3.subtract(waterObject.getEndCoordinate(), waterObject.getFullCoordinate());

        //Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());

// Rotate 90° along the right vector (1, 0, 0)
        //Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
        tubeNode.setWorldRotation(Quaternion.axisAngle(getPerpen(vector), 75));
        tubeNode.setWorldPosition(new Vector3(waterObject.getFullCoordinate().x, -3, waterObject.getFullCoordinate().z));


    }

    public Vector3 getPerpen(Vector3 vector3) {
        return new Vector3(-vector3.z/vector3.x,0, 1);
    }




    public float getzCoord() {
        return zCoord;
    }




}

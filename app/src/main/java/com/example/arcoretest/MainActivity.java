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
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    private double mLant = 55.741593;
    private double mLong = 49.221140;
    private double pointLant = 55.741623;
    private double pointLong = 49.221095;
    private double northDif;
    private double coordNorth;
    private double longDif;
    private double coordLong;

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
        NetworkService.getInstance().getCityWebApi().getWaterObjects(55.790612, 49.123078)
                .enqueue(new Callback<List<WaterObject>>() {
                    @Override
                    public void onResponse(Call<List<WaterObject>> call, Response<List<WaterObject>> response) {
                        Toast.makeText(MainActivity.this, "ПРИШЛО", Toast.LENGTH_SHORT).show();
                        Log.i("666", "" + response.body().get(0).getStartCoordinateX() + ", " + response.body().get(0).getEndCoordinateY());
                    }

                    @Override
                    public void onFailure(Call<List<WaterObject>> call, Throwable t) {
                        Log.i("First object", "ет не то");
                    }
                });

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
        northDif = pointLant - mLant;
        coordNorth = northDif*LATITUDE_COEF;
        //Log.i("666", "northDif: " + northDif);

        longDif = pointLong - mLong;
        coordLong = cos(mLant)*LONGITUDE_COEF*longDif;

        arFragment.setOnTapArPlaneListener(
                (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {
                    if (testModel == null) {
                        return;
                    }
                    /*hitresult.getHitPose().extractTranslation()
                    hitresult.getHitPose().compose(new Pose())*/
                    Anchor anchor = hitresult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    //anchorNode.setWorldPosition(new Vector3(1500, 1500, 1500));
                    //anchorNode.getAnchor().getPose().extractRotation();
                    TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
                    model.setParent(anchorNode);
                    model.setRenderable(testModel);
                    model.select();
                    model.setWorldPosition(new Vector3((float) coordLong, -1.5f,(float) coordNorth));
                    Log.i("666", model.getWorldPosition().toString());

                    /*Log.i("Coordi", model.getWorldPosition().toString());
                    Toast.makeText(this, "" + model.getWorldPosition().toString(), Toast.LENGTH_SHORT).show();
                    model.setWorldPosition(new Vector3(0.0000001f,0.0000001f,0.0000001f));*/

                    /*TransformableNode model1 = new TransformableNode(arFragment.getTransformationSystem());
                    model1.setParent(anchorNode);
                    model1.setRenderable(testModel);
                    model1.select();
                    model1.setWorldPosition(new Vector3(0.0000001f,0.0000001f,3.0000001f));*/
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




}

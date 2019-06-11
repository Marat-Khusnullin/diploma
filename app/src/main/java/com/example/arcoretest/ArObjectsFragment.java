package com.example.arcoretest;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ArObjectsFragment extends Fragment {
    ArFragment arFragment;
    Context context;
    private ButtonRectangle waterButton;
    private ButtonRectangle electricityButton;
    private ButtonRectangle dataButton;
    private ButtonRectangle gasButton;
    private LinkedList<TransformableNode> currentObjects;
    private boolean isActive = false;
    private ArObjectsFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ar_objects_fragment_layout, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments

        arFragment = (ArFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        presenter = new ArObjectsFragmentPresenter(this);
        waterButton = getActivity().findViewById(R.id.button_water);
        electricityButton = getActivity().findViewById(R.id.button_electricity);
        dataButton = getActivity().findViewById(R.id.button_data);
        gasButton = getActivity().findViewById(R.id.button_gas);

        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //testBualt();
                if(!isActive) {
                    presenter.getLocalWaterObjects();
                    isActive = true;
                } else {
                    for (int i = 0; i <currentObjects.size(); i++) {
                        if(currentObjects.get(i).getScene()!=null)
                        currentObjects.get(i).getScene().onRemoveChild(currentObjects.get(i).getParent());
                        currentObjects.get(i).setRenderable(null);
                    }
                    currentObjects.clear();
                    isActive = false;
                }
            }
        });

        /*electricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive) {
                    setElectricityObjectsToVrMap(new LinkedList<>());
                    isActive=true;
                } else {
                    for (int i = 0; i <currentObjects.size(); i++) {
                        if(currentObjects.get(i).getScene()!=null)
                        currentObjects.get(i).getScene().onRemoveChild(currentObjects.get(i).getParent());
                        currentObjects.get(i).setRenderable(null);
                    }
                    currentObjects.clear();
                    isActive = false;
                }
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
        });*/
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setWaterObjectsToVrMap(List<LocalWaterObject> list1) {
        TestData testData = new TestData();
       // final List<LocalWaterObject> list1 = NetworkService.getInstance().getCityWebApi().getWaterObjects();
        for (int i = 0; i <list1.size(); i++) {
            z = i;

//Тут добавляю табличку с инфой ёклмн
            LocalWaterObject waterObject = list1.get(i);

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

            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                    .thenAccept(
                            material -> {
                                tubeRenderable =
                                        ShapeFactory
                                                .makeCylinder(0.1f, (float) Math.sqrt(
                                                        Math.pow(waterObject.getEndCoordinate().x-waterObject.getFullCoordinate().x,2) + Math.pow(waterObject.getEndCoordinate().z-waterObject.getFullCoordinate().z,2)),
                                                        new Vector3(0, 0, 0), material); });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderable);
            currentObjects.add(tubeNode);

            tubeNode.setOnTapListener(new Node.OnTapListener() {
                @Override
                public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    infoNodeStart.setParent(anchorNode);
                    infoNodeStart.setWorldPosition(waterObject.getFullCoordinate());
                    currentObjects.add(infoNodeStart);
                }
            });
//Возможно эта строчка не нужна.
            //tubeNode.setWorldPosition(waterObject.getEndCoordinate());

            //tubeNode.select();
            //Тут вроде разность векторов
            Vector3 vector = Vector3.subtract(waterObject.getEndCoordinate(), waterObject.getFullCoordinate());
            Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());
// Rotate 90° along the right vector (1, 0, 0)
            Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
            tubeNode.setWorldRotation(worldRotation);
            tubeNode.setWorldPosition(new Vector3(waterObject.getFullCoordinate().x, -1, waterObject.getFullCoordinate().z));

        }



    }





}

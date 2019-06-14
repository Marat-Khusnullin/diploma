package com.example.arcoretest;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ArObjectsFragment extends Fragment {
    private ArFragment arFragment;
    Context context;
    private ButtonRectangle waterButton;
    private ButtonRectangle electricityButton;
    private ButtonRectangle dataButton;
    private ButtonRectangle gasButton;
    private LinkedList<TransformableNode> currentObjects;
    private boolean isActive = false;
    private ArObjectsFragmentPresenter presenter;
    ModelRenderable tubeRenderableWater;
    ModelRenderable tubeRenderableElectricity;
    ModelRenderable tubeRenderableData;
    ModelRenderable tubeRenderableGas;
    private Anchor anchor;
    private int z;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ar_objects_fragment_layout, null);

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        arFragment = (ArFragment) this.getChildFragmentManager().findFragmentById(R.id.ux_fragment);
        presenter = new ArObjectsFragmentPresenter(this);
        waterButton = getActivity().findViewById(R.id.button_water);
        electricityButton = getActivity().findViewById(R.id.button_electricity);
        dataButton = getActivity().findViewById(R.id.button_data);
        gasButton = getActivity().findViewById(R.id.button_gas);

        currentObjects = new LinkedList<TransformableNode>();

        waterButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (!isActive) {
                    //presenter.getLocalWaterObjects();
                    setWaterObjectsToVrMap(new LinkedList<>());
                    isActive = true;
                } else {
                    for (int i = 0; i < currentObjects.size(); i++) {
                        if (currentObjects.get(i).getScene() != null)
                            currentObjects.get(i).getScene().onRemoveChild(currentObjects.get(i).getParent());
                        currentObjects.get(i).setRenderable(null);
                    }
                    currentObjects.clear();
                    isActive = false;
                }
            }
        });

        electricityButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(!isActive) {
                    setDataObjectsToVrMap(new LinkedList<>());
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

        gasButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(!isActive) {
                    setGasObjectsToVrMap(new LinkedList<>());
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

        arFragment.setOnTapArPlaneListener(
                (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {
                    anchor = hitresult.createAnchor();


                }
        );
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setWaterObjectsToVrMap(List<LocalWaterObject> list1) {
        list1 = new TestData().getTestWaterObjects();
        for (int i = 0; i < list1.size(); i++) {
            z = i;
            Log.i("Coordinates", list1.get(i).getFullCoordinate().toString() + " " + list1.get(i).getEndCoordinate().toString());
            LocalWaterObject waterObject = list1.get(i);

            TransformableNode infoNodeStart;
            infoNodeStart = new TransformableNode(arFragment.getTransformationSystem());

            TransformableNode tubeNode;
            ViewRenderable.builder()
                    .setView(getActivity(), R.layout.point_info_layout)
                    .build().thenAccept(new Consumer<ViewRenderable>() {
                @Override
                public void accept(ViewRenderable viewRenderable) {
                    infoNodeStart.setRenderable(viewRenderable);
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_type)).setText(waterObject.getType());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_depth)).setText("Глубина: " + waterObject.getDepth());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_company)).setText("Обслуживающая организация: " + waterObject.getOwner());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_name)).setText("Проведенные работы: " + waterObject.getWorkInfo());
                    ((TextView) viewRenderable.getView().findViewById(R.id.water_last_work_date)).setText("Дата работ: " + waterObject.getWorkDate());
                }
            });

            MaterialFactory.makeOpaqueWithColor(getActivity(), new Color(android.graphics.Color.BLUE))
                    .thenAccept(
                            material -> {
                                tubeRenderableWater =
                                        ShapeFactory
                                                .makeCylinder(0.1f, (float) Math.sqrt(
                                                        Math.pow(waterObject.getEndCoordinate().x - waterObject.getFullCoordinate().x, 2) + Math.pow(waterObject.getEndCoordinate().z - waterObject.getFullCoordinate().z, 2)),
                                                        new Vector3(0, 0, 0), material);
                            });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderableWater);
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

            Vector3 vector = Vector3.subtract(waterObject.getEndCoordinate(), waterObject.getFullCoordinate());
            Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());
            Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
            tubeNode.setWorldRotation(worldRotation);
            tubeNode.setWorldPosition(new Vector3(waterObject.getEndCoordinate().x, -1, waterObject.getEndCoordinate().z));

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setElectricityObjectsToVrMap(List<LocalElectricityObject> list) {
        List<LocalElectricityObject> list1 = new TestData().getTestElectricityObjects();
        for (int i = 0; i < list1.size(); i++) {
            z = i;
            Log.i("Coordinates", list1.get(i).getFullCoordinate().toString() + " " + list1.get(i).getEndCoordinate().toString());
            LocalElectricityObject waterObject = list1.get(i);

            TransformableNode infoNodeStart;
            infoNodeStart = new TransformableNode(arFragment.getTransformationSystem());

            TransformableNode tubeNode;
            ViewRenderable.builder()
                    .setView(getActivity(), R.layout.electricity_info_layout)
                    .build().thenAccept(new Consumer<ViewRenderable>() {
                @Override
                public void accept(ViewRenderable viewRenderable) {
                    infoNodeStart.setRenderable(viewRenderable);
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_type)).setText(waterObject.getType());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_depth)).setText("Глубина: " + waterObject.getDepth());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_company)).setText("Обслуживающая организация: " + waterObject.getOwner());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_last_work_name)).setText("Проведенные работы: " + waterObject.getWorkInfo());
                    ((TextView) viewRenderable.getView().findViewById(R.id.electricity_last_work_date)).setText("Дата работ: " + waterObject.getWorkDate());
                }
            });

            MaterialFactory.makeOpaqueWithColor(getActivity(), new Color(android.graphics.Color.YELLOW))
                    .thenAccept(
                            material -> {
                                tubeRenderableElectricity =
                                        ShapeFactory
                                                .makeCylinder(0.05f, (float) Math.sqrt(
                                                        Math.pow(waterObject.getEndCoordinate().x - waterObject.getFullCoordinate().x, 2) + Math.pow(waterObject.getEndCoordinate().z - waterObject.getFullCoordinate().z, 2)),
                                                        new Vector3(0, 0, 0), material);
                            });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderableElectricity);
            currentObjects.add(tubeNode);

            tubeNode.setOnTapListener(new Node.OnTapListener() {
                @Override
                public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    infoNodeStart.setParent(anchorNode);
                    infoNodeStart.setWorldPosition(waterObject.getEndCoordinate());
                    currentObjects.add(infoNodeStart);
                }
            });

            Vector3 vector = Vector3.subtract(waterObject.getEndCoordinate(), waterObject.getFullCoordinate());
            Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());

            Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
            tubeNode.setWorldRotation(worldRotation);
            tubeNode.setWorldPosition(new Vector3(waterObject.getEndCoordinate().x, -1, waterObject.getEndCoordinate().z));

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDataObjectsToVrMap(List<LocalDataObject> list1) {
        list1 = new TestData().getTestDataObjects();
        for (int i = 0; i < list1.size(); i++) {
            z = i;
            Log.i("Coordinates", list1.get(i).getFullCoordinate().toString() + " " + list1.get(i).getEndCoordinate().toString());

            LocalDataObject waterObject = list1.get(i);

            TransformableNode infoNodeStart;
            infoNodeStart = new TransformableNode(arFragment.getTransformationSystem());

            TransformableNode tubeNode;
            ViewRenderable.builder()
                    .setView(getActivity(), R.layout.data_info_layout)
                    .build().thenAccept(new Consumer<ViewRenderable>() {
                @Override
                public void accept(ViewRenderable viewRenderable) {
                    infoNodeStart.setRenderable(viewRenderable);
                    ((TextView) viewRenderable.getView().findViewById(R.id.data_type)).setText(waterObject.getType());
                    ((TextView) viewRenderable.getView().findViewById(R.id.data_depth)).setText("Глубина: " + waterObject.getDepth());
                    ((TextView) viewRenderable.getView().findViewById(R.id.data_company)).setText("Обслуживающая организация: " + waterObject.getOwner());
                    ((TextView) viewRenderable.getView().findViewById(R.id.data_last_work_name)).setText("Проведенные работы: " + waterObject.getWorkInfo());
                    ((TextView) viewRenderable.getView().findViewById(R.id.data_last_work_date)).setText("Дата работ: " + waterObject.getWorkDate());
                }
            });



            MaterialFactory.makeOpaqueWithColor(getActivity(), new Color(android.graphics.Color.BLACK))
                    .thenAccept(
                            material -> {
                                tubeRenderableData =
                                        ShapeFactory
                                                .makeCylinder(0.05f, (float) Math.sqrt(
                                                        Math.pow(waterObject.getEndCoordinate().x - waterObject.getFullCoordinate().x, 2) + Math.pow(waterObject.getEndCoordinate().z - waterObject.getFullCoordinate().z, 2)),
                                                        new Vector3(0, 0, 0), material);
                            });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderableData);
            currentObjects.add(tubeNode);

            tubeNode.setOnTapListener(new Node.OnTapListener() {
                @Override
                public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    infoNodeStart.setParent(anchorNode);
                    infoNodeStart.setWorldPosition(waterObject.getEndCoordinate());
                    currentObjects.add(infoNodeStart);
                }
            });



            Vector3 vector = Vector3.subtract(waterObject.getEndCoordinate(), waterObject.getFullCoordinate());
            Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());

            Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
            tubeNode.setWorldRotation(worldRotation);
            tubeNode.setWorldPosition(new Vector3(waterObject.getEndCoordinate().x, -1, waterObject.getEndCoordinate().z));

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setGasObjectsToVrMap(List<LocalGasObject> list1) {
        list1 = new TestData().getTestGasObjects();
        for (int i = 0; i < list1.size(); i++) {
            z = i;
            Log.i("Coordinates", list1.get(i).getFullCoordinate().toString() + " " + list1.get(i).getEndCoordinate().toString());

            LocalGasObject waterObject = list1.get(i);

            TransformableNode infoNodeStart;
            infoNodeStart = new TransformableNode(arFragment.getTransformationSystem());

            TransformableNode tubeNode;
            ViewRenderable.builder()
                    .setView(getActivity(), R.layout.gas_info_layout)
                    .build().thenAccept(new Consumer<ViewRenderable>() {
                @Override
                public void accept(ViewRenderable viewRenderable) {
                    infoNodeStart.setRenderable(viewRenderable);
                    ((TextView) viewRenderable.getView().findViewById(R.id.gas_type)).setText(waterObject.getType());
                    ((TextView) viewRenderable.getView().findViewById(R.id.gas_depth)).setText("Глубина: " + waterObject.getDepth());
                    ((TextView) viewRenderable.getView().findViewById(R.id.gas_company)).setText("Обслуживающая организация: " + waterObject.getOwner());
                    ((TextView) viewRenderable.getView().findViewById(R.id.gas_last_work_name)).setText("Проведенные работы: " + waterObject.getWorkInfo());
                    ((TextView) viewRenderable.getView().findViewById(R.id.gas_last_work_date)).setText("Дата работ: " + waterObject.getWorkDate());
                }
            });



            MaterialFactory.makeOpaqueWithColor(getActivity(), new Color(android.graphics.Color.GREEN))
                    .thenAccept(
                            material -> {
                                tubeRenderableGas =
                                        ShapeFactory
                                                .makeCylinder(0.05f, (float) Math.sqrt(
                                                        Math.pow(waterObject.getEndCoordinate().x - waterObject.getFullCoordinate().x, 2) + Math.pow(waterObject.getEndCoordinate().z - waterObject.getFullCoordinate().z, 2)),
                                                        new Vector3(0, 0, 0), material);
                            });

            AnchorNode anchorNode2 = new AnchorNode(anchor);
            anchorNode2.setParent(arFragment.getArSceneView().getScene());
            tubeNode = new TransformableNode(arFragment.getTransformationSystem());
            tubeNode.setParent(anchorNode2);
            tubeNode.setRenderable(tubeRenderableGas);
            currentObjects.add(tubeNode);

            tubeNode.setOnTapListener(new Node.OnTapListener() {
                @Override
                public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    infoNodeStart.setParent(anchorNode);
                    infoNodeStart.setWorldPosition(waterObject.getEndCoordinate());
                    currentObjects.add(infoNodeStart);
                }
            });



            Vector3 vector = Vector3.subtract(waterObject.getEndCoordinate(), waterObject.getFullCoordinate());
            Quaternion lookRotation = Quaternion.lookRotation(vector, Vector3.up());

            Quaternion worldRotation = Quaternion.multiply(lookRotation, Quaternion.axisAngle(Vector3.right(), 90));
            tubeNode.setWorldRotation(worldRotation);
            tubeNode.setWorldPosition(new Vector3(waterObject.getEndCoordinate().x, -1, waterObject.getEndCoordinate().z));

        }
    }




}

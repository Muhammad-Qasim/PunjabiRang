package com.example.myfyp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfyp.R;
import com.example.myfyp.camera;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


public class Camera_frag extends Fragment implements View.OnClickListener {

    Button capture;
    ArFragment arFragment;
    private ModelRenderable burgerRenderable, biryaniRenderable, saagRenderable, shammiKababRenderable,
                            kurtaRenderable, phulkariRenderable, suthanRenderable, ghagraRenderable,
                            dholRenderable, fluteRenderable, dhotaraRenderable, chimtaRenderable,
                            badshahiMosqueRenderable, mirePakistanRenderable, hiranMinarRenderable, iqbalsquareRenderable, lahoreforteRenderable,
                            luddiRenderable, bhangraRenderable, kikliRenderable, giddahRenderable;

    ImageView burger, biryani, saag, makai_roti , shammi_kabab,
            kurta,phulkari, suthan, ghagra,
            dhol, flute, dhotara, chimta,
            badshahi_mosque, minare_pakistan, hiran_minar, iqbal_square, lahore_forte,
            luddi, bhangra, kikli, giddah;

    View arrayView[];
    ViewRenderable name_models;

    int selected = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_camera_frag, container, false);

        arFragment = (ArFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.ar_fragment);


        burger = (ImageView)view.findViewById(R.id.burger);
        biryani = (ImageView)view.findViewById(R.id.biryani);
        saag = (ImageView)view.findViewById(R.id.saag);
        makai_roti = (ImageView)view.findViewById(R.id.makai_roti);
        shammi_kabab = (ImageView)view.findViewById(R.id.shamikabab);
        kurta = (ImageView)view.findViewById(R.id.kurta);
        suthan = (ImageView)view.findViewById(R.id.suthan);
        phulkari = (ImageView)view.findViewById(R.id.phulkari);
        ghagra = (ImageView)view.findViewById(R.id.ghagra);
        dhol = (ImageView)view.findViewById(R.id.dhol);
        dhotara = (ImageView)view.findViewById(R.id.dhotara);
        chimta = (ImageView)view.findViewById(R.id.chimta);
        flute = (ImageView)view.findViewById(R.id.flute);
        minare_pakistan = (ImageView)view.findViewById(R.id.minarepakistan);
        iqbal_square = (ImageView)view.findViewById(R.id.iqbalsquare);
        hiran_minar = (ImageView)view.findViewById(R.id.hiranminar);
        badshahi_mosque = (ImageView)view.findViewById(R.id.badshahimosque);
        lahore_forte = (ImageView)view.findViewById(R.id.lahoreforte);
        luddi = (ImageView)view.findViewById(R.id.ludi);
        giddah = (ImageView)view.findViewById(R.id.gidha);
        kikli = (ImageView)view.findViewById(R.id.kikli);
        bhangra = (ImageView)view.findViewById(R.id.bhangra);


        setArrayView();
        setClickListener();
        setupModel();


        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if (selected == 1){
                    Anchor anchor= hitResult.createAnchor();
                    AnchorNode anchorNode= new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    createModel(anchorNode, selected);
                }
            }
        });
        /*
        Intent intent=new Intent(getContext(), camera.class);
        startActivity(intent);*/

        return view;
    }

    private void createModel(AnchorNode anchorNode, int selected) {

        if(selected == 1){
            TransformableNode burger= new TransformableNode(arFragment.getTransformationSystem());
            burger.setParent(anchorNode);
            burger.setRenderable(burgerRenderable);
            burger.select();

        }
    }

    private void setClickListener() {

        for (int i = 0; i < arrayView.length; i++) {
            arrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView() {

        arrayView = new View[]{burger};
    }

    private void setupModel() {
        ModelRenderable.builder()
                .setSource(getContext(), R.raw.hamburger)
                .build().thenAccept(renderable -> burgerRenderable = renderable)
                .exceptionally(throwable -> {

                        Toast.makeText(getActivity(), "Unable to load model", Toast.LENGTH_SHORT).show();
                        return null;
                         }
                );

    }
    @Override
    public void onClick(View v) {

    }
}


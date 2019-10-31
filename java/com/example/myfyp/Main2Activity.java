package com.example.myfyp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {

    private ArFragment fragment;

    private PointerDrawable pointer = new PointerDrawable();
    private boolean isTracking;
    private boolean isHitting;
    FloatingActionButton camera;


    //For Animation Model

    FloatingActionButton animation;
    private ModelAnimator animator;
    private int nextAnimation;
    ModelRenderable animationluddi,  animationbhangra, animationjhumar, animationgiddah,animationsammi ;
  //  private TransformableNode transformableNode;
    private AnchorNode anchorNode;

    List<HitResult> hits;


    //    ArFragment arFragment;
    Button capturebutton;



/*    private ModelRenderable burgerRenderable, biryaniRenderable, saagRenderable, shammiKababRenderable,
            kurtaRenderable, phulkariRenderable, suthanRenderable, ghagraRenderable,
            dholRenderable, fluteRenderable, dhotaraRenderable, chimtaRenderable,
            badshahiMosqueRenderable, mirePakistanRenderable, hiranMinarRenderable, iqbalsquareRenderable, lahoreforteRenderable,
            luddiRenderable, bhangraRenderable, kikliRenderable, giddahRenderable;

    ImageView burger, biryani, saag, makai_roti , shammi_kabab,
            kurta,phulkari, suthan, ghagra,
            dhol, flute, dhotara, chimta,
            badshahi_mosque, minare_pakistan, hiran_minar, iqbal_square, lahore_forte,
            luddi, bhangra, kikli, giddah;*/

    View arrayView[];
    ViewRenderable name_models;

    int selected = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

   //     arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

        animation=findViewById(R.id.btn_animation);

        camera= findViewById(R.id.camera);

        fragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);



        fragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            fragment.onUpdate(frameTime);


            onUpdate();


        });

        initializeGallery();
        camera.setOnClickListener(view -> takePhoto());

/*

        animation.setEnabled(false);
        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animator== null || !animator.isRunning()){

                    AnimationData data= animationjhumar.getAnimationData(nextAnimation);
                    nextAnimation= (nextAnimation+1)%animationjhumar.getAnimationDataCount();
                    animator= new ModelAnimator(data, animationjhumar);
                    animator.start();
                }
            }
        });
*/


    }

    private void onUpdate() {
        boolean trackingChanged = updateTracking();
        View contentView = findViewById(android.R.id.content);
        if (trackingChanged) {
            if (isTracking) {
                contentView.getOverlay().add(pointer);
            } else {
                contentView.getOverlay().remove(pointer);

            }
            contentView.invalidate();
        }

        if (isTracking) {
            boolean hitTestChanged = updateHitTest();
            if (hitTestChanged) {
                pointer.setEnabled(isHitting);
                contentView.invalidate();
            }
        }
    }

    private boolean updateHitTest() {

            Frame frame = fragment.getArSceneView().getArFrame();
            android.graphics.Point pt = getScreenCenter();
            List<HitResult> hits;
            boolean wasHitting = isHitting;
            isHitting = false;
            if (frame != null) {
                hits = frame.hitTest(pt.x, pt.y);
                for (HitResult hit : hits) {
                    Trackable trackable = hit.getTrackable();
                    if (trackable instanceof Plane &&
                            ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                        isHitting = true;
                        break;
                    }
                }
            }
           return wasHitting != isHitting;
    }

    private android.graphics.Point getScreenCenter() {
        View vw = findViewById(android.R.id.content);
        return new android.graphics.Point(vw.getWidth()/2, vw.getHeight()/2);
    }
    private boolean updateTracking() {
        Frame frame = fragment.getArSceneView().getArFrame();
        boolean wasTracking = isTracking;
        isTracking = frame != null &&
                frame.getCamera().getTrackingState() == TrackingState.TRACKING;
        return isTracking != wasTracking;
    }

    private void initializeGallery() {
        LinearLayout gallery = findViewById(R.id.gallary_layout);




        CircleImageView flute = new CircleImageView(this);
        flute.setImageResource(R.drawable.flute);
        flute.setContentDescription("Flute");
        flute.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("model.sfb"));
        });
        gallery.addView(flute);


        CircleImageView trumpet = new CircleImageView(this);
        trumpet.setImageResource(R.drawable.trumpet);
        trumpet.setContentDescription("Trumpet");
        trumpet.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("trumpt.sfb"));
        });
        gallery.addView(trumpet);



        CircleImageView baja = new CircleImageView(this);
        baja.setImageResource(R.drawable.baja);
        baja.setContentDescription("Baja");
        baja.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("baja.sfb"));
        });
        gallery.addView(baja);


        CircleImageView dholki = new CircleImageView(this);
        dholki.setImageResource(R.drawable.dholki);
        dholki.setContentDescription("Dholki");
        dholki.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("dholki.sfb"));
        });
        gallery.addView(dholki);



        CircleImageView clarinet = new CircleImageView(this);
        clarinet.setImageResource(R.drawable.clarinet);
        clarinet.setContentDescription("clarinet");
        clarinet.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("clarinet.sfb"));
        });
        gallery.addView(clarinet);


        CircleImageView tabla = new CircleImageView(this);
        tabla.setImageResource(R.drawable.tabla);
        tabla.setContentDescription("Tabla");
        tabla.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("tabla.sfb"));
        });
        gallery.addView(tabla);





        CircleImageView badshahiMosque = new CircleImageView(this);
        badshahiMosque.setImageResource(R.drawable.badshahimosque);
        badshahiMosque.setContentDescription("Badshahi Mosque");
        badshahiMosque.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("masjid.sfb"));
        });
        gallery.addView(badshahiMosque);

        CircleImageView iqbalTomb = new CircleImageView(this);
        iqbalTomb.setImageResource(R.drawable.iqbaltomb);
        iqbalTomb.setContentDescription("Iqbal Tomb");
        iqbalTomb.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("iqbaltomb1.sfb"))
            ;});
        gallery.addView(iqbalTomb);


        CircleImageView lahoregate = new CircleImageView(this);
        lahoregate.setImageResource(R.drawable.lahoreforte);
        lahoregate.setContentDescription("Lahore Gate");
        lahoregate.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("lahorigate.sfb"));
        });
        gallery.addView(lahoregate);



        CircleImageView minaePakistan = new CircleImageView(this);
        minaePakistan.setImageResource(R.drawable.minarepakistan);
        minaePakistan.setContentDescription("Minar e Pakistan");
        minaePakistan.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("menarepakistan.sfb"));
        });
        gallery.addView(minaePakistan);




/*
        CircleImageView lassi = new CircleImageView(this);
        lassi.setImageResource(R.drawable.makaikeroti);
        lassi.setContentDescription("Lassi");
        lassi.setOnClickListener(view ->{
            removePreviousAnchors();
            addObject(Uri.parse("lassi1.sfb"));
        });
        gallery.addView(lassi);
*/



        CircleImageView jhumar = new CircleImageView(this);
        jhumar.setImageResource(R.drawable.jhumardance);
        jhumar.setContentDescription("jhumar");
        jhumar.setOnClickListener(view ->{
            removePreviousAnchors();
    //        addObject(Uri.parse("jhumar_anim.sfb"));

            jhumaranimationFunction();
        });
        gallery.addView(jhumar);


        CircleImageView giddah = new CircleImageView(this);
        giddah.setImageResource(R.drawable.gidhadance);
        giddah.setContentDescription("giddah");
        giddah.setOnClickListener(view ->{
            removePreviousAnchors();
           // addObject(Uri.parse("giddah_anim.sfb"));
            giddahanimationFunction();
        });
        gallery.addView(giddah);


        CircleImageView luddi = new CircleImageView(this);
        luddi.setImageResource(R.drawable.luddidance);
        luddi.setContentDescription("Luddi");
        luddi.setOnClickListener(view ->{
            removePreviousAnchors();
            luddianimationFunction();
           // animationFunction();
 //           addObject(Uri.parse("luddidance.sfb"));
        });

        gallery.addView(luddi);


        CircleImageView sammi = new CircleImageView(this);
        sammi.setImageResource(R.drawable.sammidance);
        sammi.setContentDescription("Sammi");
        sammi.setOnClickListener(view ->{
            removePreviousAnchors();
           //   addObject(Uri.parse("sammi_anim.sfb"));
            sammianimationFunction();
        });
        gallery.addView(sammi);


        CircleImageView bhangra = new CircleImageView(this);
        bhangra.setImageResource(R.drawable.bhangradance);
        bhangra.setContentDescription("Bhangra");
        bhangra.setOnClickListener(view ->{
            removePreviousAnchors();
          //  addObject(Uri.parse("bhangra_anim.sfb"));
            bhangraanimationFunction();
        });
        gallery.addView(bhangra);



}
    private void addObject(Uri model) {
        Frame frame = fragment.getArSceneView().getArFrame();
        android.graphics.Point pt = getScreenCenter();
        if (frame != null) {
            hits = frame.hitTest(pt.x, pt.y);
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane &&
                        ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                         placeObject(fragment, hit.createAnchor(), model);
                         break;

                }
            }
        }
    }
    private void placeObject(ArFragment fragment, Anchor anchor, Uri model) {
        CompletableFuture<Void> renderableFuture =
                ModelRenderable.builder()
                        .setSource(fragment.getContext(), model)
                        .build()
                        .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
                        .exceptionally((throwable -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(throwable.getMessage())
                                    .setTitle("error!");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return null;
                        }));
    }
    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable) {
        Session session= fragment.getArSceneView().getSession();

        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.getScaleController().setMinScale(0.35f);
        node.getScaleController().setMaxScale(0.48f);

        node.setRenderable(renderable);
        node.setParent(anchorNode);

        fragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();

//        removePreviousAnchors();

    }


    public void jhumaranimationFunction(){

        HitResult hitResult;

        fragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if(animationjhumar == null){
                    return;
                }
                Anchor anchor= hitResult.createAnchor();
                if(anchorNode== null){
                    anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(fragment.getArSceneView().getScene());


                    TransformableNode transformableNode= new TransformableNode(fragment.getTransformationSystem());

                    transformableNode.getScaleController().setMinScale(0.35f);
                    transformableNode.getScaleController().setMaxScale(0.38f);
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(animationjhumar);
                }
            }
        });


        fragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                if(anchorNode == null){
                    if(animation.isEnabled()){
                        animation.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                        animation.setEnabled(false);

                    }

                }
                else {
                    if(!animation.isEnabled()){
                        animation.setBackgroundTintList(ContextCompat.getColorStateList(Main2Activity.this, R.color.colorPrimary));
                        animation.setEnabled(true);
                    }
                }

            }
        });

        animation.setEnabled(false);
        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animator== null || !animator.isRunning()){

                    AnimationData data= animationjhumar.getAnimationData(nextAnimation);
                    nextAnimation= (nextAnimation+1)%animationjhumar.getAnimationDataCount();
                    animator= new ModelAnimator(data, animationjhumar);
                    animator.start();
                }
            }
        });

        setupModel();
    }



    private void setupModel(){

        ModelRenderable.builder()
                .setSource(this, R.raw.jhumar_anim)
                .build()
                .thenAccept(renderable -> animationjhumar = renderable)
                .exceptionally(throwable ->
                        {
                            Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

    }

    public void sammianimationFunction(){

        HitResult hitResult;

        fragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if(animationsammi == null){
                    return;
                }
                Anchor anchor= hitResult.createAnchor();
                if(anchorNode== null){
                    anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(fragment.getArSceneView().getScene());


                    TransformableNode transformableNode= new TransformableNode(fragment.getTransformationSystem());

                    transformableNode.getScaleController().setMinScale(0.35f);
                    transformableNode.getScaleController().setMaxScale(0.38f);
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(animationsammi);
                }
            }
        });


        fragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                if(anchorNode == null){
                    if(animation.isEnabled()){
                        animation.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                        animation.setEnabled(false);
                    }
                }
                else {
                    if(!animation.isEnabled()){
                        animation.setBackgroundTintList(ContextCompat.getColorStateList(Main2Activity.this, R.color.colorPrimary));
                        animation.setEnabled(true);
                    }
                }

            }
        });

        animation.setEnabled(false);
        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animator== null || !animator.isRunning()){

                    AnimationData data= animationsammi.getAnimationData(nextAnimation);
                    nextAnimation= (nextAnimation+1)%animationsammi.getAnimationDataCount();
                    animator= new ModelAnimator(data, animationsammi);
                    animator.start();
                }
            }
        });
        sammisetupModel();
    }



    private void sammisetupModel(){

        ModelRenderable.builder()
                .setSource(this, R.raw.sammi_anim)
                .build()
                .thenAccept(renderable -> animationsammi = renderable)
                .exceptionally(throwable ->
                        {
                            Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

    }


    public void bhangraanimationFunction(){

        HitResult hitResult;

        fragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if(animationbhangra == null){
                    return;
                }
                Anchor anchor= hitResult.createAnchor();
                if(anchorNode== null){
                    anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(fragment.getArSceneView().getScene());


                    TransformableNode transformableNode= new TransformableNode(fragment.getTransformationSystem());

                    transformableNode.getScaleController().setMinScale(0.35f);
                    transformableNode.getScaleController().setMaxScale(0.38f);
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(animationbhangra);
                }
            }
        });


        fragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                if(anchorNode == null){
                    if(animation.isEnabled()){
                        animation.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                        animation.setEnabled(false);
                    }
                }
                else {
                    if(!animation.isEnabled()){
                        animation.setBackgroundTintList(ContextCompat.getColorStateList(Main2Activity.this, R.color.colorPrimary));
                        animation.setEnabled(true);
                    }
                }

            }
        });

        animation.setEnabled(false);
        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animator== null || !animator.isRunning()){

                    AnimationData data= animationbhangra.getAnimationData(nextAnimation);
                    nextAnimation= (nextAnimation+1)%animationbhangra.getAnimationDataCount();
                    animator= new ModelAnimator(data, animationbhangra);
                    animator.start();
                }
            }
        });
        bhangrasetupModel();
    }



    private void bhangrasetupModel(){

        ModelRenderable.builder()
                .setSource(this, R.raw.bhangra_anim)
                .build()
                .thenAccept(renderable -> animationbhangra = renderable)
                .exceptionally(throwable ->
                        {
                            Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

    }




    public void luddianimationFunction(){

        HitResult hitResult;

        fragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if(animationluddi == null){
                    return;
                }
                Anchor anchor= hitResult.createAnchor();
                if(anchorNode== null){
                    anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(fragment.getArSceneView().getScene());


                    TransformableNode transformableNode= new TransformableNode(fragment.getTransformationSystem());

                    transformableNode.getScaleController().setMinScale(0.35f);
                    transformableNode.getScaleController().setMaxScale(0.38f);
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(animationluddi);
                }
            }
        });


        fragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                if(anchorNode == null){
                    if(animation.isEnabled()){
                        animation.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                        animation.setEnabled(false);
                    }
                }
                else {
                    if(!animation.isEnabled()){
                        animation.setBackgroundTintList(ContextCompat.getColorStateList(Main2Activity.this, R.color.colorPrimary));
                        animation.setEnabled(true);
                    }
                }

            }
        });

        animation.setEnabled(false);
        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animator== null || !animator.isRunning()){

                    AnimationData data= animationluddi.getAnimationData(nextAnimation);
                    nextAnimation= (nextAnimation+1)%animationluddi.getAnimationDataCount();
                    animator= new ModelAnimator(data, animationluddi);
                    animator.start();
                }
            }
        });
        luddisetupModel();
    }



    private void luddisetupModel(){

        ModelRenderable.builder()
                .setSource(this, R.raw.luddi_anim)
                .build()
                .thenAccept(renderable -> animationluddi = renderable)
                .exceptionally(throwable ->
                        {
                            Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

    }




    public void giddahanimationFunction(){

        HitResult hitResult;

        fragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if(animationgiddah == null){
                    return;
                }
                Anchor anchor= hitResult.createAnchor();
                if(anchorNode== null){
                    anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(fragment.getArSceneView().getScene());


                    TransformableNode transformableNode= new TransformableNode(fragment.getTransformationSystem());

                    transformableNode.getScaleController().setMinScale(0.35f);
                    transformableNode.getScaleController().setMaxScale(0.38f);
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(animationgiddah);
                }
            }
        });


        fragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                if(anchorNode == null){
                    if(animation.isEnabled()){
                        animation.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                        animation.setEnabled(false);
                    }
                }
                else {
                    if(!animation.isEnabled()){
                        animation.setBackgroundTintList(ContextCompat.getColorStateList(Main2Activity.this, R.color.colorPrimary));
                        animation.setEnabled(true);
                    }
                }

            }
        });

        animation.setEnabled(false);
        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animator== null || !animator.isRunning()){

                    AnimationData data= animationgiddah.getAnimationData(nextAnimation);
                    nextAnimation= (nextAnimation+1)%animationgiddah.getAnimationDataCount();
                    animator= new ModelAnimator(data, animationgiddah);
                    animator.start();
                }
            }
        });
        giddahsetupModel();
    }



    private void giddahsetupModel(){

        ModelRenderable.builder()
                .setSource(this, R.raw.giddah_anim)
                .build()
                .thenAccept(renderable -> animationgiddah = renderable)
                .exceptionally(throwable ->
                        {
                            Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

    }



    private void removePreviousAnchors() { List<Node> nodeList = new ArrayList<>(fragment.getArSceneView().getScene().getChildren());
        for (Node childNode : nodeList)
        { if (childNode instanceof AnchorNode)
        { if (((AnchorNode) childNode).getAnchor() != null)
        { ((AnchorNode) childNode).getAnchor().detach();
            ((AnchorNode) childNode).setParent(null); } } } }


    private String generateFilename() {
        String date =
                new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "Sceneform/" + date + "_screenshot.jpg";
    }

    private void saveBitmapToDisk(Bitmap bitmap, String filename) throws IOException {

        File out = new File(filename);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        else {
            try (FileOutputStream outputStream = new FileOutputStream(filename);
                 ByteArrayOutputStream outputData = new ByteArrayOutputStream()) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData);
                outputData.writeTo(outputStream);
                outputStream.flush();
                Toast.makeText(Main2Activity.this, " iS in Flush", Toast.LENGTH_SHORT).show();
                outputStream.close();
            } catch (IOException ex) {
                throw new IOException("Failed to save bitmap to disk", ex);
        }
        }


    }

    private void takePhoto() {
        final String filename = generateFilename();
        ArSceneView view = fragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(Main2Activity.this, e.toString(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Photo saved", Snackbar.LENGTH_LONG);
                snackbar.setAction("Open in Photos", v -> {
                    File photoFile = new File(filename);

                    Uri photoURI = FileProvider.getUriForFile(Main2Activity.this,
                            Main2Activity.this.getPackageName() + ".ar.codelab.name.provider",
                            photoFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW, photoURI);
                    intent.setDataAndType(photoURI, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                });
                snackbar.show();
            } else {
                Toast toast = Toast.makeText(Main2Activity.this,
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }

}





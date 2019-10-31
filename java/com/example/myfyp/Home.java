package com.example.myfyp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfyp.Fragments.AboutUs_Fragment;
import com.example.myfyp.Fragments.Camera_frag;
import com.example.myfyp.Fragments.Help_Fragment;
import com.example.myfyp.Fragments.Home_frag;
import com.example.myfyp.Fragments.My_UploadsFragment;
import com.example.myfyp.Fragments.NotificationFragment;
import com.example.myfyp.Fragments.Privacy_Fragment;
import com.example.myfyp.Fragments.ProfileFragment;
import com.example.myfyp.Fragments.Search_frag;
import com.example.myfyp.Fragments.Settings_Fragment;
import com.example.myfyp.Fragments.SuggestionFragment;
import com.example.myfyp.Fragments.Visitors_GalaryFragment;
import com.example.myfyp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    Fragment selectedFragment=null;


    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    String profileid;

    TextView userName;
    TextView email;
    ImageView userImage;
    CircleImageView profile_Image_Header;

    TextView user_nav_header;
    TextView email_nav_header;


    Dialog guestDialogue;
    private Context mcontext;
    String currentuser;
    private DatabaseReference databaseReference;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mcontext= getApplicationContext();

        guestDialogue = new Dialog(this);

     ////////////
/*

        firebaseAuth= FirebaseAuth.getInstance();
        currentuser= firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        View navHeaderView=navigationView.inflateHeaderView(R.layout.drawer_header);
        email_nav_header= (TextView) navHeaderView.findViewById(R.id.email_nh_tv);
        user_nav_header=(TextView)navHeaderView.findViewById(R.id.name_nh_tv);
        profile_Image_Header= (CircleImageView) navHeaderView.findViewById(R.id.userImage_nv);
*/

        ////////////////////

    //    firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

/*

        userName= findViewById(R.id.name_nh_tv);
        email= findViewById(R.id.email_nh_tv);
        profile_Image= findViewById(R.id.userImage_nv);
*/


//        userInfo();
        updateNav();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout=findViewById(R.id.drawer_layout);

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Home_frag()).commit();

    ///////////////////

/*
        databaseReference.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name= dataSnapshot.child("username").getValue().toString();
                    String email= dataSnapshot.child("Email").getValue().toString();
                    String image= dataSnapshot.child("imageurl").getValue().toString();


                    user_nav_header.setText(name);
                    email_nav_header.setText(email);
                    Glide.with(getApplicationContext()).load(image).into(profile_Image_Header);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
    ////////////////////
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.profile_menu:
                        if(firebaseUser == null){

                            showpopup();}
                            else {
                                setTitle("Profile");

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ProfileFragment()).commit();

          /*              Intent intent=new Intent(Home.this, ProfileEdit.class);
                        startActivity(intent);
*/

/*

                        Intent intent=new Intent(Home.this, EditProfileActivity.class);
                        startActivity(intent);
*/
                        }

                      break;

                    case R.id.my_uploads_menu:
                        if(firebaseUser == null){

                            showpopup();
                        }
                            else {
                                setTitle("My Uploads");
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new My_UploadsFragment()).commit();

                            break;
                        }
                    case R.id.visitor_galary_menu:

                        if(firebaseUser == null){

                         showpopup();

                        }
                        else {
                            setTitle("Visitors Gallery");
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Visitors_GalaryFragment()).commit();
                        }
        /*                intent=new Intent(Home.this, VisitorsGallery.class);
                        startActivity(intent);
        */
                      break;

                    case R.id.notification_b_option:
                        setTitle("Notification");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new NotificationFragment()).commit();
                        break;


                    case R.id.suggestion_menu:
                            setTitle("Suggestion");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new SuggestionFragment()).commit();
                        break;


                    case R.id.privacy_menu_menu:
                            setTitle("Privacy Settings");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Privacy_Fragment()).commit();
                        break;

                    case R.id.help_menu:
                        setTitle("Help Center");
                //        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Help_Fragment()).commit();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Help_Fragment()).commit();

                        break;
                        
                    case R.id.about_us_menu:
                            setTitle("About us");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new AboutUs_Fragment()).commit();
                        break;

                    case R.id.logout_menu:
                        firebaseAuth.getInstance().signOut();
                        finish();
                        Intent intent=new Intent(Home.this, Signin.class);
                        startActivity(intent);
                      break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(doubleBackToExitPressedOnce){
            super.onBackPressed();


            finishAffinity();
            finish();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double Tab to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.home_btm_nav:
                    setTitle("Home");
                    selectedFragment = new Home_frag();
                    break;

                case R.id.camera_btm_nav:
                    setTitle("Camera");
       //             selectedFragment = new Camera_frag();
        Intent intent= new Intent(Home.this, Main2Activity.class);
        startActivity(intent);
                    break;

                case R.id.visitors_Galary_btm_nav:
                    if(firebaseUser == null){
                        showpopup();
                    }
                    else {
                        setTitle("Visitors Gallery");
                        selectedFragment = new Visitors_GalaryFragment();
                    }
                    break;
           /*     case R.id.options_btm_nav:
                    PopupMenu popup = new PopupMenu(Home.this, findViewById(R.id.options_btm_nav));
                    MenuInflater inflater= popup.getMenuInflater();
                    inflater.inflate(R.menu.bottom_options, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){

                                case R.id.about_us_b_option:
                                    setTitle("About Us");
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new AboutUs_Fragment()).commit();
                                    break;
                                case R.id.help_center_b_option:
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Help_Fragment()).commit();

                                    //          startActivity(new Intent(Home.this,EditProfileActivity.class));
                                    break;
                                case R.id.logout_b_option:

                                    firebaseAuth.getInstance().signOut();
                                    finish();
                                    Intent intent=new Intent(Home.this, Signin.class);
                                    startActivity(intent);



                                    break;
                            }

                            return false;
                        }
                    });
*/
                  /* LayoutInflater layoutInflater= (LayoutInflater) Home.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view= layoutInflater.inflate(R.layout.guest_popup, null);
                    PopupMenu popupMenu= new PopupMenu(Home.this, view );
                    MenuInflater inflater= popupMenu.getMenuInflater();

                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.about_us_b_option:
                                    break;
                                case R.id.help_center_b_option:
                                    break;
                                case R.id.logout_b_option:
                                    break;
                            }
                            return false;
                        }
                    });
*/

//                    break;

            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            }
            return true;

        }
    };

   /*private void userInfo(){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
  //              if (dataSnapshot.exists()){
  //                  User user = dataSnapshot.getValue(User.class);
//                    String image =dataSnapshot.child("imageUrl").getValue().toString();
     //               Glide.with(getApplicationContext()).load(imageUrl).into(profile_Image);
                }
*//*
                if (getApplicationContext() == null){
                    return;
                }
                User user = dataSnapshot.getValue(User.class);
*//**//*

//                Glide.with(getApplicationContext()).load(user.getImageurl()).into(userImage);
 //               userName.setText(user.getUsername());
   //            email.setText(user.getEmail());

*//*
            }


                @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/


   public void showpopup(){
       Button login;
       Button signup;
       PopupWindow popupWindow;

       // Dismiss popup when tab outside it

    /*   boolean focusable= true;

       RelativeLayout relativeLayout1;

       LayoutInflater layoutInflater= (LayoutInflater) getSystemService( LAYOUT_INFLATER_SERVICE);
       View view= layoutInflater.inflate(R.layout.guest_popup, null);
       //          relativeLayout1= (RelativeLayout)findViewById(R.id.popup_guest);
       popupWindow= new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, focusable);
       popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);

       login= (Button)view.findViewById(R.id.login_guest);
       signup=(Button)view.findViewById(R.id.signup_guest);

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Home.this , Signin.class);
               startActivity(intent);
               finish();
           }
       });
       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Home.this , SignUp.class);
               startActivity(intent);
               finish();
           }
       });
*/

       guestDialogue.setContentView(R.layout.guest_popup);
       login= (Button)guestDialogue.findViewById(R.id.login_guest_btn);
       signup=(Button)guestDialogue.findViewById(R.id.signup_guest_btn);
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Home.this , Signin.class);
               startActivity(intent);
               finish();
           }
       });

   signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Home.this , SignUp.class);
            startActivity(intent);
            finish();
        }
    });
   guestDialogue.show();
}

public void updateNav(){
       NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
       View headerView= navigationView.getHeaderView(0);
       final TextView userheader= headerView.findViewById(R.id.name_nh_tv_h);
       final TextView emailheader= headerView.findViewById(R.id.email_nh_tv);
       final CircleImageView profileHeader= headerView.findViewById(R.id.userImage_nv_h);

       if(firebaseUser != null){


    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);

                    userheader.setText(user.getUsername());
            emailheader.setText(user.getEmail());

            Glide.with(Home.this).load(user.getImageurl()).into(profileHeader);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
       }

}
      }


package com.ss.gamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Policy extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        TextView mMessageWindow=findViewById(R.id.messageWindow);
        mAuth=FirebaseAuth.getInstance();
        String message="Privacy Policy\n" +
                "Siddharth sharma and Lekh Nath Jha built the Gamoney app as a Free app. This SERVICE is provided by them at no cost and is intended for use as is.\n" +
                "\n" +
                "This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.\n" +
                "\n" +
                "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                "\n" +
                "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at Gamoney unless otherwise defined in this Privacy Policy.\n" +
                "\n" +
                "Information Collection and Use\n" +
                "\n" +
                "For a better experience, while using our Service, We may require you to provide us with certain personally identifiable information, including but not limited to faceboook profile. The information that we request will be retained on your device and is not collected by us in any way.\n" +
                "\n" +
                "The app does use third party services that may collect information used to identify you.\n" +
                "\n" +
                "Link to privacy policy of third party service providers used by the app\n" +
                "\n" +
                "Google Play Services\n" +
                "AdMob\n" +
                "Google Analytics for Firebase\n" +
                "Firebase Crashlytics\n" +
                "Facebook\n" +
                "Log Data\n" +
                "\n" +
                "We want to inform you that whenever you use our Service, in a case of an error in the app we collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.\n" +
                "\n" +
                "Cookies\n" +
                "\n" +
                "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
                "\n" +
                "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.\n" +
                "\n" +
                "Service Providers\n" +
                "\n" +
                "I may employ third-party companies and individuals due to the following reasons:\n" +
                "\n" +
                "To facilitate our Service;\n" +
                "To provide the Service on our behalf;\n" +
                "To perform Service-related services; or\n" +
                "To assist us in analyzing how our Service is used.\n" +
                "We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.\n" +
                "\n" +
                "Security\n" +
                "\n" +
                "We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.\n" +
                "\n" +
                "Links to Other Sites\n" +
                "\n" +
                "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.\n" +
                "\n" +
                "Children’s Privacy\n" +
                "\n" +
                "These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided me with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that we will be able to do necessary actions.\n" +
                "\n" +
                "Changes to This Privacy Policy\n" +
                "\n" +
                "We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page.\n" +
                "\n" +
                "This policy is effective as of 2020-08-21\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at posttosiddharthsharma@gmail.com and jhasameer40@gmail.com";
        mMessageWindow.setText(message);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_policy);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()){
            case R.id.nav_tournamentinfo:
                Intent intent4 = new Intent(getApplicationContext(),Tournaments.class);
                startActivity(intent4);
                break;

            case R.id.nav_Contactus:
                Intent intent1 = new Intent(getApplicationContext(),ContactUs.class);
                startActivity(intent1);
                break;

            case R.id.nav_Faq:
                Intent intent2 = new Intent(getApplicationContext(),Faq.class);
                startActivity(intent2);
                break;

            case R.id.nav_policy:
                break;

            case R.id.nav_Supportus:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;

            case R.id.nav_Logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(Policy.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;

            case R.id.nav_Share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Download This Application Now:- https://play.google.com/store/apps/details?id=com.battlerooms.rooms&hl=en";
                String sharesub = "Gamoney App";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(shareIntent, "Share Using"));
                break;

            case R.id.nav_Resetpassword:
                final EditText password = new EditText(this);
                AlertDialog.Builder resetpassword = new AlertDialog.Builder(Policy.this);
                resetpassword.setTitle("Reset Password");
                resetpassword.setMessage("Enter your Email");
                resetpassword.setView(password);
                resetpassword.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = password.getText().toString();
                        if (mail.matches("")) {
                            Toast.makeText(getApplicationContext(), "Please enter Your email", Toast.LENGTH_SHORT).show();
                        } else {
                            mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Policy.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                }
                            });
                        }
                    }
                });
                resetpassword.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                resetpassword.show();
                break;

            case R.id.joined_tournament:
                Intent intent5 = new Intent(getApplicationContext(), joinedTournament.class);
                startActivity(intent5);
                finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
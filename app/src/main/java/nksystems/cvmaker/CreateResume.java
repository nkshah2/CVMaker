package nksystems.cvmaker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.parser.Line;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import github.chenupt.springindicator.SpringIndicator;
import nksystems.cvmaker.adapter.ResumeAdapter;
import nksystems.cvmaker.createresume.AchievementDetails;
import nksystems.cvmaker.createresume.CertificationDetails;
import nksystems.cvmaker.createresume.EducationDetails;
import nksystems.cvmaker.createresume.ExperienceDetails;
import nksystems.cvmaker.createresume.ExtraCurricularDetails;
import nksystems.cvmaker.createresume.PersonalDetails;
import nksystems.cvmaker.createresume.PersonalInterestDetails;
import nksystems.cvmaker.createresume.SkillDetails;

public class CreateResume extends AppCompatActivity {

    Menu menuOptions;
    int backCount=0,totalSections=9;
    RelativeLayout root;
    ImageButton addCertification,removeCertification,addExperience,removeExperience,addAchievement,removeAchievement,addSkill,removeSkill,addEC,removeEC;
    EditText txtObjective, txtName, txtFilename, txtTemplate;
    DatabaseQueries dbQueries;
    FloatingActionButton btnSave;
    LinearLayout ten,twelve,llHobbyTab,llHobbyInformation,college,diploma,graduate,certificationTab,certificationInformation, experienceInformation, experienceTab,achievementsTab, achievementsInformation, skillsTab, skillsInformation,ECTab, ECInformation;
    int certCount=1,expCount=1,achieveCount=1,skillCount=1,ecCount=1;
    Spinner mySpinner,spinnerTemplate,spinnerHighestEdu,spinnerTitle;
    EditText etFileName,etObjective,etFName,etLName,etDOB,etEmail,etCode,etPhone,etAdress;
    EditText etTenthCGPA,etTenthSchool,etTenthBoard,etTenthYOP,etTwelfthCGPA,etTwelfthInsti,etTwelfthBoard,etTwelfthYOP;
    EditText etDipCGPA,etDipInsti,etDipBoard,etDipYOP,etCollegeCGPA,etCollegeDegree,etCollegeBoard,etCollegeYOP,etCollegeCOllege;
    EditText etPgCGPA,etPgBoard,etPgYOP,etPgCollege,etPgDegree;
    EditText etCertTitle1,etCertYear1,etCertTitle2,etCertYear2,etCertTitle3,etCertYear3,etCertTitle4,etCertYear4,etCertTitle5,etCertYear5,etCertTitle6,etCertYear6;
    EditText etCoName1, etDuration1, etRole1, etProDesc1;
    EditText etCoName2, etDuration2, etRole2, etProDesc2;
    EditText etCoName3, etDuration3, etRole3, etProDesc3;
    EditText etCoName4, etDuration4, etRole4, etProDesc4;
    EditText etCoName5, etDuration5, etRole5, etProDesc5;
    EditText etCoName6, etDuration6, etRole6, etProDesc6;
    EditText etAchieveDesc1,etAchieveDesc2,etAchieveDesc3,etAchieveDesc4,etAchieveDesc5,etAchieveDesc6;
    EditText etDomain1,etSkill1,etDomain2,etSkill2,etDomain3,etSkill3,etDomain4,etSkill4,etDomain5,etSkill5,etDomain6,etSkill6;
    EditText etEC1,etEC2,etEC3,etEC4,etEC5,etEC6,etHobby;
    String filename,template,firstName,lastName,objective,title,DOB,email,ext,phone,adress;
    String highestEdu,tenthCGPA,tenthSchool,tenthBoard,tenthYOP,twelfthCGPA,twelfthInstitute,twelfthBoard,twelfthYOP,dipCGPA,
            dipInsti,dipBoard,dipYOP,collegeCGPA,collegeInsti,collegeBoard,collegeYOP,collegeDegree,pgCGPA,pgInsti,pgBoard,
            pgYOP,pgDegree;
    String certTitle1,certYear1,certTitle2,certYear2,certTitle3,certYear3,certTitle4,certYear4,certTitle5,certYear5,certTitle6,
            certYear6;
    String coName1,duration1,role1,proDesc1,coName2,duration2,role2,proDesc2,coName3,duration3,role3,proDesc3,coName4,duration4,
            role4,proDesc4,coName5,duration5,role5,proDesc5,coName6,duration6,role6,proDesc6;
    String achieveDesc1,achieveDesc2,achieveDesc3,achieveDesc4,achieveDesc5,achieveDesc6;
    String domain1,skill1,domain2,skill2,domain3,skill3,domain4,skill4,domain5,skill5,domain6,skill6;
    String ec1,ec2,ec3,ec4,ec5,ec6;
    String personalInterest;
    Calendar c= Calendar.getInstance();
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
    private ResumeAdapter resumeAdapter;
    ViewPager viewPager;
    private String[] resumeTabs={"Personal Information","Education Details","Certification","Experience","Achievements","Skills",
            "Extra-Curriculars","Personal Interests"};
    ViewPager pagerRoot;
    PersonalDetails personalDetails;
    EducationDetails educationDetails;
    CertificationDetails certificationDetails;
    ExperienceDetails experienceDetails;
    AchievementDetails achievementDetails;
    SkillDetails skillDetails;
    ExtraCurricularDetails extraCurricularDetails;
    PersonalInterestDetails personalInterestDetails;
    private android.support.v7.app.ActionBar actionBar;
    DatabaseObject dbObject;
    SQLiteDatabase theme;
    @Override
    public void onBackPressed() {
        if(backCount==0){
            backCount++;
            Toast.makeText(this,"You will lose any unsaved data. \n Press the back button to go back anyways.",Toast.LENGTH_LONG).show();
        }else {
            finish();
            startActivity(new Intent(CreateResume.this,ActivityMain.class));
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        menuOptions=menu;

        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int currentItem = viewPager.getCurrentItem();

        String title = item.toString();

        if(title.equals("Next")){
            Toast.makeText(CreateResume.this, "Next CurrentItem: " + currentItem, Toast.LENGTH_SHORT).show();
            if(currentItem == 6){
                viewPager.setCurrentItem(currentItem+1);
                findViewById(R.id.appNext).setVisibility(View.GONE);
            }else{
                viewPager.setCurrentItem(currentItem+1);
                findViewById(R.id.appNext).setVisibility(View.VISIBLE);
            }
            menuOptions.getItem(1).setTitle(resumeTabs[viewPager.getCurrentItem()]);
        }
        else if(title.equals("Previous")){
            Toast.makeText(CreateResume.this, "Previous CurrentItem: " + currentItem, Toast.LENGTH_SHORT).show();
            if(currentItem == 6){
                viewPager.setCurrentItem(currentItem-1);
                findViewById(R.id.appNext).setVisibility(View.GONE);
            }else{
                viewPager.setCurrentItem(currentItem-1);
                findViewById(R.id.appNext).setVisibility(View.VISIBLE);
            }
            menuOptions.getItem(1).setTitle(resumeTabs[viewPager.getCurrentItem()]);
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbObject=new DatabaseObject(this);
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();
        String currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));

        switch (currentTheme.toLowerCase()){
            case "red":
                setTheme(R.style.redTheme);
                break;
            case "pink":
                setTheme(R.style.pinkTheme);
                break;
            case "purple":
                setTheme(R.style.purpleTheme);
                break;
            case "blue":
                setTheme(R.style.blueTheme);
                break;
            case "green":
                setTheme(R.style.greenTheme);
                break;
            case "yellow":
                setTheme(R.style.yellowTheme);
                break;
            case "orange":
                setTheme(R.style.orangeTheme);
                break;
            case "grey":
                setTheme(R.style.greyTheme);
                break;
        }


        setContentView(R.layout.common_resume_layout);



        dbQueries=new DatabaseQueries(CreateResume.this);
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_bold_white_48dp);
//        actionBar.setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.resume_main);
        resumeAdapter = new ResumeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(resumeAdapter);


        UnderlinePageIndicator indicator=(UnderlinePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                setTitle(resumeTabs[viewPager.getCurrentItem()]);
            }

            @Override
            public void onPageSelected(int position){

            }

            @Override
            public void onPageScrollStateChanged(int state){

            }
        });

        viewPager.setOffscreenPageLimit(totalSections);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        personalDetails = new PersonalDetails();
        educationDetails= new EducationDetails();
        certificationDetails = new CertificationDetails();
        experienceDetails = new ExperienceDetails();
        achievementDetails = new AchievementDetails();
        skillDetails = new SkillDetails();
        extraCurricularDetails = new ExtraCurricularDetails();
        personalInterestDetails = new PersonalInterestDetails();

        fragmentTransaction.commit();

//        /*LinearLayout personalInformation= (LinearLayout) findViewById(R.id.llPersonalTab);
//        personalInformation.setOnClickListener(new View.OnClickListener() {
//
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//
//
//                final LinearLayout personalInfo=(LinearLayout)findViewById(R.id.llPersonalInformation);
//                if(!isOpen) {
//                    isOpen=true;
//                    personalInfo.setVisibility(View.VISIBLE);
//                    personalInfo.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    personalInfo.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            personalInfo.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });

//        LinearLayout educationInformaiton= (LinearLayout) findViewById(R.id.llEducationTab);
//        educationInformaiton.setOnClickListener(new View.OnClickListener() {
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//                final LinearLayout educationInfo=(LinearLayout)findViewById(R.id.llEducationInformation);
//                if(!isOpen) {
//                    isOpen=true;
//                    educationInfo.setVisibility(View.VISIBLE);
//                    educationInfo.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    educationInfo.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            educationInfo.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });

//        ten=(LinearLayout)findViewById(R.id.ll10th) ;
//        twelve=(LinearLayout)findViewById(R.id.ll12th) ;
//        diploma=(LinearLayout)findViewById(R.id.llDiploma) ;
//        college=(LinearLayout)findViewById(R.id.llCollege) ;
//        graduate=(LinearLayout)findViewById(R.id.llPostGrad) ;
//        mySpinner=(Spinner)findViewById(R.id.spinnerEducation);
//        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:
//                        ten.setVisibility(View.GONE);
//                        twelve.setVisibility(View.GONE);
//                        diploma.setVisibility(View.GONE);
//                        college.setVisibility(View.GONE);
//                        graduate.setVisibility(View.GONE);
//                        break;
//                    case 1:
//                        ten.setVisibility(View.VISIBLE);
//                        twelve.setVisibility(View.GONE);
//                        diploma.setVisibility(View.GONE);
//                        college.setVisibility(View.GONE);
//                        graduate.setVisibility(View.GONE);
//                        break;
//                    case 2:
//                        ten.setVisibility(View.VISIBLE);
//                        twelve.setVisibility(View.VISIBLE);
//                        diploma.setVisibility(View.GONE);
//                        college.setVisibility(View.GONE);
//                        graduate.setVisibility(View.GONE);
//                        break;
//                    case 3:
//                        ten.setVisibility(View.VISIBLE);
//                        twelve.setVisibility(View.VISIBLE);
//                        diploma.setVisibility(View.VISIBLE);
//                        college.setVisibility(View.GONE);
//                        graduate.setVisibility(View.GONE);
//                        break;
//                    case 4:
//                        ten.setVisibility(View.VISIBLE);
//                        twelve.setVisibility(View.VISIBLE);
//                        diploma.setVisibility(View.VISIBLE);
//                        college.setVisibility(View.VISIBLE);
//                        graduate.setVisibility(View.GONE);
//                        break;
//                    case 5:
//                        ten.setVisibility(View.VISIBLE);
//                        twelve.setVisibility(View.VISIBLE);
//                        diploma.setVisibility(View.VISIBLE);
//                        college.setVisibility(View.VISIBLE);
//                        graduate.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        certificationTab=(LinearLayout)findViewById(R.id.llCertificatesTab);
//        certificationInformation=(LinearLayout)findViewById(R.id.llCartificationInformation);
//        certificationTab.setOnClickListener(new View.OnClickListener() {
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//                if(!isOpen) {
//                    isOpen=true;
//                    certificationInformation.setVisibility(View.VISIBLE);
//                    certificationInformation.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    certificationInformation.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            certificationInformation.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });
//
//        experienceTab=(LinearLayout)findViewById(R.id.llExperienceTab);
//        experienceInformation=(LinearLayout)findViewById(R.id.llExperienceInformation);
//        experienceTab.setOnClickListener(new View.OnClickListener() {
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//                if(!isOpen) {
//                    isOpen=true;
//                    experienceInformation.setVisibility(View.VISIBLE);
//                    experienceInformation.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    experienceInformation.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            experienceInformation.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });
//
//        achievementsTab=(LinearLayout)findViewById(R.id.llAchievementsTab);
//        achievementsInformation=(LinearLayout)findViewById(R.id.llAchievementsInformation);
//        achievementsTab.setOnClickListener(new View.OnClickListener() {
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//                if(!isOpen) {
//                    isOpen=true;
//                    achievementsInformation.setVisibility(View.VISIBLE);
//                    achievementsInformation.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    achievementsInformation.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            achievementsInformation.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });
//
//        skillsTab=(LinearLayout)findViewById(R.id.llSkillsTab);
//        skillsInformation=(LinearLayout)findViewById(R.id.llSkillsInformation);
//        skillsTab.setOnClickListener(new View.OnClickListener() {
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//                if(!isOpen) {
//                    isOpen=true;
//                    skillsInformation.setVisibility(View.VISIBLE);
//                    skillsInformation.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    skillsInformation.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            skillsInformation.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });
//
//        ECTab=(LinearLayout)findViewById(R.id.llECTab);
//        ECInformation=(LinearLayout)findViewById(R.id.llECInformation);
//        ECTab.setOnClickListener(new View.OnClickListener() {
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//                if(!isOpen) {
//                    isOpen=true;
//                    ECInformation.setVisibility(View.VISIBLE);
//                    ECInformation.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    ECInformation.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            ECInformation.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });
//
//        llHobbyTab=(LinearLayout)findViewById(R.id.llHobbyTab);
//        llHobbyInformation=(LinearLayout)findViewById(R.id.llHobbyInformation);
//        llHobbyTab.setOnClickListener(new View.OnClickListener() {
//            Boolean isOpen=false;
//            @Override
//            public void onClick(View v) {
//                if(!isOpen) {
//                    isOpen=true;
//                    llHobbyInformation.setVisibility(View.VISIBLE);
//                    llHobbyInformation.startAnimation(slide_down);
//                }else{
//                    isOpen=false;
//                    llHobbyInformation.startAnimation(slide_up);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//
//                            llHobbyInformation.setVisibility(View.GONE);
//                            // Actions to do after 10 seconds
//                        }
//                    }, 200);
//
//                }
//            }
//        });
//
//        addCertification=(ImageButton)findViewById(R.id.addCertification);
//        removeCertification=(ImageButton)findViewById(R.id.removeCertification);
       /* addCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.llCert1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.llCert2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.llCert3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.llCert4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.llCert5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.llCert6);


                if(certCount<7){
                    certCount+=1;
                    if(certCount>6){
                        certCount=6;
                    }else if(certCount<1){
                        certCount=1;
                    }
                    switch (certCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.GONE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });


        removeCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.llCert1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.llCert2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.llCert3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.llCert4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.llCert5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.llCert6);

                if(certCount<7){
                    certCount-=1;
                    if(certCount>6){
                        certCount=6;
                    }else if(certCount<1){
                        certCount=1;
                    }
                    switch (certCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.GONE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeCertification.setVisibility(View.VISIBLE);
                            addCertification.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        addAchievement=(ImageButton)findViewById(R.id.addAchievement);
        removeAchievement=(ImageButton)findViewById(R.id.removeAchievement);
        addAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.achievment1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.achievment2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.achievment3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.achievment4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.achievment5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.achievment6);


                if(achieveCount<7){
                    achieveCount+=1;
                    if(achieveCount>6){
                        achieveCount=6;
                    }else if(achieveCount<1){
                        achieveCount=1;
                    }
                    switch (achieveCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.GONE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });


        removeAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.achievment1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.achievment2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.achievment3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.achievment4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.achievment5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.achievment6);

                if(achieveCount<7){
                    achieveCount-=1;
                    if(achieveCount>6){
                        achieveCount=6;
                    }else if(achieveCount<1){
                        achieveCount=1;
                    }
                    switch (achieveCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.GONE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeAchievement.setVisibility(View.VISIBLE);
                            addAchievement.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        addEC=(ImageButton)findViewById(R.id.addEC);
        removeEC=(ImageButton)findViewById(R.id.removeEC);
        addEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cert1=(EditText) findViewById(R.id.etEC1);
                EditText cert2=(EditText) findViewById(R.id.etEC2);
                EditText cert3=(EditText) findViewById(R.id.etEC3);
                EditText cert4=(EditText) findViewById(R.id.etEC4);
                EditText cert5=(EditText) findViewById(R.id.etEC5);
                EditText cert6=(EditText) findViewById(R.id.etEC6);


                if(ecCount<7){
                    ecCount+=1;
                    if(ecCount>6){
                        ecCount=6;
                    }else if(ecCount<1){
                        ecCount=1;
                    }
                    switch (ecCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.GONE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });


        removeEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cert1=(EditText) findViewById(R.id.etEC1);
                EditText cert2=(EditText) findViewById(R.id.etEC2);
                EditText cert3=(EditText) findViewById(R.id.etEC3);
                EditText cert4=(EditText) findViewById(R.id.etEC4);
                EditText cert5=(EditText) findViewById(R.id.etEC5);
                EditText cert6=(EditText) findViewById(R.id.etEC6);

                if(ecCount<7){
                    ecCount-=1;
                    if(ecCount>6){
                        ecCount=6;
                    }else if(ecCount<1){
                        ecCount=1;
                    }
                    switch (ecCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.GONE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeEC.setVisibility(View.VISIBLE);
                            addEC.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        addExperience=(ImageButton)findViewById(R.id.addExperience);
        removeExperience=(ImageButton)findViewById(R.id.removeExperience);
        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.llExp1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.llExp2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.llExp3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.llExp4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.llExp5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.llExp6);


                if(expCount<7){
                    expCount+=1;
                    if(expCount>6){
                        expCount=6;
                    }else if(expCount<1){
                        expCount=1;
                    }
                    switch (expCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.GONE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });


        removeExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.llExp1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.llExp2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.llExp3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.llExp4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.llExp5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.llExp6);

                if(expCount<7){
                    expCount-=1;
                    if(expCount>6){
                        expCount=6;
                    }else if(expCount<1){
                        expCount=1;
                    }
                    switch (expCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.GONE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeExperience.setVisibility(View.VISIBLE);
                            addExperience.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });*/

       /* addSkill=(ImageButton)findViewById(R.id.addSkills);
        removeSkill=(ImageButton)findViewById(R.id.removeSkills);
        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.llSkills1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.llSkills2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.llSkills3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.llSkills4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.llSkills5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.llSkills6);


                if(skillCount<7){
                    skillCount+=1;
                    if(skillCount>6){
                        skillCount=6;
                    }else if(skillCount<1){
                        skillCount=1;
                    }
                    switch (skillCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.GONE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });


        removeSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cert1=(LinearLayout)findViewById(R.id.llSkills1);
                LinearLayout cert2=(LinearLayout)findViewById(R.id.llSkills2);
                LinearLayout cert3=(LinearLayout)findViewById(R.id.llSkills3);
                LinearLayout cert4=(LinearLayout)findViewById(R.id.llSkills4);
                LinearLayout cert5=(LinearLayout)findViewById(R.id.llSkills5);
                LinearLayout cert6=(LinearLayout)findViewById(R.id.llSkills6);

                if(skillCount<7){
                    skillCount-=1;
                    if(skillCount>6){
                        skillCount=6;
                    }else if(skillCount<1){
                        skillCount=1;
                    }
                    switch (skillCount){
                        case 1:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.GONE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.GONE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.GONE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.GONE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.GONE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.GONE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.VISIBLE);
                            break;

                        case 6:
                            cert1.setVisibility(View.VISIBLE);
                            cert2.setVisibility(View.VISIBLE);
                            cert3.setVisibility(View.VISIBLE);
                            cert4.setVisibility(View.VISIBLE);
                            cert5.setVisibility(View.VISIBLE);
                            cert6.setVisibility(View.VISIBLE);
                            removeSkill.setVisibility(View.VISIBLE);
                            addSkill.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
*/
//        txtObjective=(EditText)findViewById(R.id.txtObjective);
//        txtName=(EditText)findViewById(R.id.txtName);
//        txtFilename=(EditText)findViewById(R.id.txtFilename);
//        txtTemplate=(EditText)findViewById(R.id.txtTemplate);

        /*if(getIntent().getExtras().getBoolean("forward")==false) {

        }else{

            txtName.setText(getIntent().getExtras().getString("name"));
            txtObjective.setText(getIntent().getExtras().getString("objective"));
            txtFilename.setText(getIntent().getExtras().getString("filename"));
            template=getIntent().getExtras().getString("template");
            txtTemplate.setText(getIntent().getExtras().getString("template"));
        }*/

        btnSave = (FloatingActionButton) findViewById(R.id.btnSaveFab);

        Drawable myDrawable=btnSave.getBackground();
        int color;


        switch (currentTheme.toLowerCase()){
            case "red":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.redColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.redColorAccent));
                break;
            case "pink":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.pinkColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.pinkColorAccent));
                break;
            case "purple":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.purpleColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.purpleColorAccent));
                break;
            case "blue":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.blueColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.blueColorAccent));
                break;
            case "green":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.greenColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.greenColorAccent));
                break;
            case "yellow":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.yellowColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.yellowColorAccent));
                break;
            case "orange":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.orangeColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.orangeColorAccent));
                break;
            case "grey":
                myDrawable.clearColorFilter();
                color= ContextCompat.getColor(this,R.color.greyColorAccent);
                myDrawable.setColorFilter(color, PorterDuff.Mode.CLEAR);
                myDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                indicator.setSelectedColor(getResources().getColor(R.color.greyColorAccent));
                break;
        }
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                        etFileName=(EditText) findViewById(R.id.etFileName);
                        spinnerTemplate=(Spinner) findViewById(R.id.spinnerTemplate);
                        etObjective=(EditText) findViewById(R.id.etObjective);
                        spinnerTitle=(Spinner) findViewById(R.id.spinnerTitle);
                        etFName=(EditText) findViewById(R.id.etFirstName);
                        etLName=(EditText) findViewById(R.id.etLastName);
                        etDOB=(EditText) findViewById(R.id.etDOB);
                        etEmail=(EditText) findViewById(R.id.etEmail);
                        etCode=(EditText) findViewById(R.id.etCode);
                        etPhone=(EditText) findViewById(R.id.etPhone);
                        etAdress=(EditText) findViewById(R.id.etAdress);

                        spinnerHighestEdu=(Spinner)findViewById(R.id.spinnerEducation);
                        etTenthCGPA=(EditText)findViewById(R.id.etTenthGrade);
                        etTenthSchool=(EditText)findViewById(R.id.etTenthSchool);
                        etTenthBoard=(EditText)findViewById(R.id.etTenthBoard);
                        etTenthYOP=(EditText)findViewById(R.id.etTenthYOP);
                        etTwelfthCGPA=(EditText)findViewById(R.id.etTwelfthCGPA);
                        etTwelfthInsti=(EditText)findViewById(R.id.etTwelfthInstitute);
                        etTwelfthBoard=(EditText)findViewById(R.id.etTwelfthBoard);
                        etTwelfthYOP=(EditText)findViewById(R.id.etTwelfthYOP);
                        etDipCGPA=(EditText)findViewById(R.id.etDipCGPA);
                        etDipInsti=(EditText)findViewById(R.id.etDipInsti);
                        etDipBoard=(EditText)findViewById(R.id.etDipBoard);
                        etDipYOP=(EditText)findViewById(R.id.etDipYOP);
                        etCollegeCGPA=(EditText)findViewById(R.id.etCollegeCGPA);
                        etCollegeDegree=(EditText)findViewById(R.id.etCollegeDegree);
                        etCollegeBoard=(EditText)findViewById(R.id.etCollegeBoard);
                        etCollegeYOP=(EditText)findViewById(R.id.etCollegeYOP);
                        etCollegeCOllege=(EditText)findViewById(R.id.etCollegeCollege);
                        etPgCollege=(EditText)findViewById(R.id.etPgCollege);
                        etPgBoard=(EditText)findViewById(R.id.etPgBoard);
                        etPgCGPA=(EditText)findViewById(R.id.etPgCGPA);
                        etPgDegree=(EditText)findViewById(R.id.etPgDegree);
                        etPgYOP=(EditText)findViewById(R.id.etPgYOP);
                        ten=(LinearLayout)findViewById(R.id.ll10th) ;
                        twelve=(LinearLayout)findViewById(R.id.ll12th) ;
                        diploma=(LinearLayout)findViewById(R.id.llDiploma) ;
                        college=(LinearLayout)findViewById(R.id.llCollege) ;
                        graduate=(LinearLayout)findViewById(R.id.llPostGrad) ;

                        etCertTitle1=(EditText)findViewById(R.id.etCertTitle1);
                        etCertYear1=(EditText)findViewById(R.id.etCertYear1);
                        etCertTitle2=(EditText)findViewById(R.id.etCertTitle2);
                        etCertYear2=(EditText)findViewById(R.id.etCertYear2);
                        etCertTitle3=(EditText)findViewById(R.id.etCertTitle3);
                        etCertYear3=(EditText)findViewById(R.id.etCertYear3);
                        etCertTitle4=(EditText)findViewById(R.id.etCertTitle4);
                        etCertYear4=(EditText)findViewById(R.id.etCertYear4);
                        etCertTitle5=(EditText)findViewById(R.id.etCertTitle5);
                        etCertYear5=(EditText)findViewById(R.id.etCertYear5);
                        etCertTitle6=(EditText)findViewById(R.id.etCertTitle6);
                        etCertYear6=(EditText)findViewById(R.id.etCertYear6);

                        etCoName1=(EditText)findViewById(R.id.etCoName1);
                        etDuration1=(EditText)findViewById(R.id.etDuration1);
                        etRole1=(EditText)findViewById(R.id.etRole1);
                        etProDesc1=(EditText)findViewById(R.id.etProDesc1);
                        etCoName2=(EditText)findViewById(R.id.etCoName2);
                        etDuration2=(EditText)findViewById(R.id.etDuration2);
                        etRole2=(EditText)findViewById(R.id.etRole2);
                        etProDesc2=(EditText)findViewById(R.id.etProDesc2);
                        etCoName3=(EditText)findViewById(R.id.etCoName3);
                        etDuration3=(EditText)findViewById(R.id.etDuration3);
                        etRole3=(EditText)findViewById(R.id.etRole3);
                        etProDesc3=(EditText)findViewById(R.id.etProDesc3);
                        etCoName4=(EditText)findViewById(R.id.etCoName4);
                        etDuration4=(EditText)findViewById(R.id.etDuration4);
                        etRole4=(EditText)findViewById(R.id.etRole4);
                        etProDesc4=(EditText)findViewById(R.id.etProDesc4);
                        etCoName5=(EditText)findViewById(R.id.etCoName5);
                        etDuration5=(EditText)findViewById(R.id.etDuration5);
                        etRole5=(EditText)findViewById(R.id.etRole5);
                        etProDesc5=(EditText)findViewById(R.id.etProDesc5);
                        etCoName6=(EditText)findViewById(R.id.etCoName6);
                        etDuration6=(EditText)findViewById(R.id.etDuration6);
                        etRole6=(EditText)findViewById(R.id.etRole6);
                        etProDesc6=(EditText)findViewById(R.id.etProDesc6);

                        etAchieveDesc1=(EditText)findViewById(R.id.etAchieveDesc1);
                        etAchieveDesc2=(EditText)findViewById(R.id.etAchieveDesc2);
                        etAchieveDesc3=(EditText)findViewById(R.id.etAchieveDesc3);
                        etAchieveDesc4=(EditText)findViewById(R.id.etAchieveDesc4);
                        etAchieveDesc5=(EditText)findViewById(R.id.etAchieveDesc5);
                        etAchieveDesc6=(EditText)findViewById(R.id.etAchieveDesc6);

                        etDomain1=(EditText)findViewById(R.id.etDomain1);
                        etSkill1=(EditText)findViewById(R.id.etSkill1);
                        etDomain2=(EditText)findViewById(R.id.etDomain2);
                        etSkill2=(EditText)findViewById(R.id.etSkill2);
                        etDomain3=(EditText)findViewById(R.id.etDomain3);
                        etSkill3=(EditText)findViewById(R.id.etSkill3);
                        etDomain4=(EditText)findViewById(R.id.etDomain4);
                        etSkill4=(EditText)findViewById(R.id.etSkill4);
                        etDomain5=(EditText)findViewById(R.id.etDomain5);
                        etSkill5=(EditText)findViewById(R.id.etSkill5);
                        etDomain6=(EditText)findViewById(R.id.etDomain6);
                        etSkill6=(EditText)findViewById(R.id.etSkill6);

                        etEC1=(EditText)findViewById(R.id.etEC1);
                        etEC2=(EditText)findViewById(R.id.etEC2);
                        etEC3=(EditText)findViewById(R.id.etEC3);
                        etEC4=(EditText)findViewById(R.id.etEC4);
                        etEC5=(EditText)findViewById(R.id.etEC5);
                        etEC6=(EditText)findViewById(R.id.etEC6);

                        etHobby=(EditText)findViewById(R.id.etHobbies);

                        String[] personal= new String[9];
                        personal[0]=etFileName.getText().toString();
                        personal[1]=etObjective.getText().toString();
                        personal[2]=etFName.getText().toString();
                        personal[3]=etLName.getText().toString();
                        personal[4]=etDOB.getText().toString();
                        personal[5]=etEmail.getText().toString();
                        personal[6]=etCode.getText().toString();
                        personal[7]=etPhone.getText().toString();
                        personal[8]=etAdress.getText().toString();

                        int nullCount=0;
                        for(int i=0;i<personal.length;i++){
                            if(personal[i].equals("")){
                                nullCount++;
                            }
                        }

                        if(nullCount==0) {

                            Date currentDate= c.getTime();
                            Date enteredDate= null;
                            try {
                                enteredDate = dateFormat.parse(etDOB.getText().toString());
                            } catch (ParseException e) {

                            }
                            if(!enteredDate.after(currentDate)) {


                                try {

                                    DatabaseQueries db = new DatabaseQueries(CreateResume.this);
                                    SQLiteDatabase d = db.returnInstance();


                                    File myDirectory = createDirectory();
                                    Image imgFile = takeScreenshot();
                                    File myFile = createPdf(myDirectory);
                                    finish();
                                    Intent intent= new Intent(CreateResume.this,ActivityMain.class);
                                    intent.putExtra("openPDF",true);
                                    intent.putExtra("file",myFile);
                                    startActivity(intent);

                                } catch (Exception e) {
                                    throw new RuntimeException("Error generating file: ", e);
                                }
                            }else{
                                Toast.makeText(CreateResume.this, "Enter Valid Birthdate", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(CreateResume.this,"Filename/Personal details are mandatory",Toast.LENGTH_LONG).show();
                        }

            }
        });
    }

    public String getEditDetails(DatabaseQueries dbQueries, Intent intent){
        String filename=intent.getStringExtra("filename");
        Cursor personalCursor= dbQueries.selectPersonalInformation(filename);
        personalCursor.moveToFirst();
        String fileid=personalCursor.getString(personalCursor.getColumnIndex("fileid"));
        return filename+"~"+fileid;
    }

    //    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);

//        Intent intent= getIntent();
//        if(intent.getBooleanExtra("isEdit",false)){

            /*LayoutInflater inflater=(LayoutInflater)CreateResume.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View root=inflater.inflate(R.layout.resume_personal_details_activity,null);*/

            /*etFileName=(EditText)findViewById(R.id.etFileName);
            spinnerTemplate=(Spinner)findViewById(R.id.spinnerTemplate);
            etObjective=(EditText)findViewById(R.id.etObjective);
            spinnerTitle=(Spinner)findViewById(R.id.spinnerTitle);
            etFName=(EditText)findViewById(R.id.etFirstName);
            etLName=(EditText)findViewById(R.id.etLastName);
            etDOB=(EditText)findViewById(R.id.etDOB);
            etEmail=(EditText)findViewById(R.id.etEmail);
            etCode=(EditText)findViewById(R.id.etCode);
            etPhone=(EditText)findViewById(R.id.etPhone);
            etAdress=(EditText)findViewById(R.id.etAdress);*/


            /*spinnerHighestEdu=(Spinner)findViewById(R.id.spinnerEducation);
            etTenthCGPA=(EditText)findViewById(R.id.etTenthGrade);
            etTenthSchool=(EditText)findViewById(R.id.etTenthSchool);
            etTenthBoard=(EditText)findViewById(R.id.etTenthBoard);
            etTenthYOP=(EditText)findViewById(R.id.etTenthYOP);
            etTwelfthCGPA=(EditText)findViewById(R.id.etTwelfthCGPA);
            etTwelfthInsti=(EditText)findViewById(R.id.etTwelfthInstitute);
            etTwelfthBoard=(EditText)findViewById(R.id.etTwelfthBoard);
            etTwelfthYOP=(EditText)findViewById(R.id.etTwelfthYOP);
            etDipCGPA=(EditText)findViewById(R.id.etDipCGPA);
            etDipInsti=(EditText)findViewById(R.id.etDipInsti);
            etDipBoard=(EditText)findViewById(R.id.etDipBoard);
            etDipYOP=(EditText)findViewById(R.id.etDipYOP);
            etCollegeCGPA=(EditText)findViewById(R.id.etCollegeCGPA);
            etCollegeDegree=(EditText)findViewById(R.id.etCollegeDegree);
            etCollegeBoard=(EditText)findViewById(R.id.etCollegeBoard);
            etCollegeYOP=(EditText)findViewById(R.id.etCollegeYOP);
            etCollegeCOllege=(EditText)findViewById(R.id.etCollegeCollege);
            etPgCollege=(EditText)findViewById(R.id.etPgCollege);
            etPgBoard=(EditText)findViewById(R.id.etPgBoard);
            etPgCGPA=(EditText)findViewById(R.id.etPgCGPA);
            etPgDegree=(EditText)findViewById(R.id.etPgDegree);
            etPgYOP=(EditText)findViewById(R.id.etPgYOP);*/

            /*etCertTitle1=(EditText)findViewById(R.id.etCertTitle1);
            etCertYear1=(EditText)findViewById(R.id.etCertYear1);
            etCertTitle2=(EditText)findViewById(R.id.etCertTitle2);
            etCertYear2=(EditText)findViewById(R.id.etCertYear2);
            etCertTitle3=(EditText)findViewById(R.id.etCertTitle3);
            etCertYear3=(EditText)findViewById(R.id.etCertYear3);
            etCertTitle4=(EditText)findViewById(R.id.etCertTitle4);
            etCertYear4=(EditText)findViewById(R.id.etCertYear4);
            etCertTitle5=(EditText)findViewById(R.id.etCertTitle5);
            etCertYear5=(EditText)findViewById(R.id.etCertYear5);
            etCertTitle6=(EditText)findViewById(R.id.etCertTitle6);
            etCertYear6=(EditText)findViewById(R.id.etCertYear6);*/

            /*etCoName1=(EditText)findViewById(R.id.etCoName1);
            etDuration1=(EditText)findViewById(R.id.etDuration1);
            //etProName1=(EditText)findViewById(R.id.etProName1);
            etRole1=(EditText)findViewById(R.id.etRole1);
            //etRoleDesc1=(EditText)findViewById(R.id.etRoleDesc1);
            etProDesc1=(EditText)findViewById(R.id.etProDesc1);
            etCoName2=(EditText)findViewById(R.id.etCoName2);
            etDuration2=(EditText)findViewById(R.id.etDuration2);
            //etProName2=(EditText)findViewById(R.id.etProName2);
            etRole2=(EditText)findViewById(R.id.etRole2);
            //etRoleDesc2=(EditText)findViewById(R.id.etRoleDesc2);
            etProDesc2=(EditText)findViewById(R.id.etProDesc2);
            etCoName3=(EditText)findViewById(R.id.etCoName3);
            etDuration3=(EditText)findViewById(R.id.etDuration3);
            //etProName3=(EditText)findViewById(R.id.etProName3);
            etRole3=(EditText)findViewById(R.id.etRole3);
            //etRoleDesc3=(EditText)findViewById(R.id.etRoleDesc3);
            etProDesc3=(EditText)findViewById(R.id.etProDesc3);
            etCoName4=(EditText)findViewById(R.id.etCoName4);
            etDuration4=(EditText)findViewById(R.id.etDuration4);
            //etProName4=(EditText)findViewById(R.id.etProName4);
            etRole4=(EditText)findViewById(R.id.etRole4);
            //etRoleDesc4=(EditText)findViewById(R.id.etRoleDesc4);
            etProDesc4=(EditText)findViewById(R.id.etProDesc4);
            etCoName5=(EditText)findViewById(R.id.etCoName5);
            etDuration5=(EditText)findViewById(R.id.etDuration5);
            //etProName5=(EditText)findViewById(R.id.etProName5);
            etRole5=(EditText)findViewById(R.id.etRole5);
            //etRoleDesc5=(EditText)findViewById(R.id.etRoleDesc5);
            etProDesc5=(EditText)findViewById(R.id.etProDesc5);
            etCoName6=(EditText)findViewById(R.id.etCoName6);
            etDuration6=(EditText)findViewById(R.id.etDuration6);
            //etProName6=(EditText)findViewById(R.id.etProName6);
            etRole6=(EditText)findViewById(R.id.etRole6);
            //etRoleDesc6=(EditText)findViewById(R.id.etRoleDesc6);
            etProDesc6=(EditText)findViewById(R.id.etProDesc6);*/

            /*//etAchieveTitle1=(EditText)findViewById(R.id.etAchieveTitle1);
            etAchieveDesc1=(EditText)findViewById(R.id.etAchieveDesc1);
            //etAchieveTitle2=(EditText)findViewById(R.id.etAchieveTitle2);
            etAchieveDesc2=(EditText)findViewById(R.id.etAchieveDesc2);
            //etAchieveTitle3=(EditText)findViewById(R.id.etAchieveTitle3);
            etAchieveDesc3=(EditText)findViewById(R.id.etAchieveDesc3);
            //etAchieveTitle4=(EditText)findViewById(R.id.etAchieveTitle4);
            etAchieveDesc4=(EditText)findViewById(R.id.etAchieveDesc4);
            //etAchieveTitle5=(EditText)findViewById(R.id.etAchieveTitle5);
            etAchieveDesc5=(EditText)findViewById(R.id.etAchieveDesc5);
            //etAchieveTitle6=(EditText)findViewById(R.id.etAchieveTitle6);
            etAchieveDesc6=(EditText)findViewById(R.id.etAchieveDesc6);*/

            /*etDomain1=(EditText)findViewById(R.id.etDomain1);
            etSkill1=(EditText)findViewById(R.id.etSkill1);
            etDomain2=(EditText)findViewById(R.id.etDomain2);
            etSkill2=(EditText)findViewById(R.id.etSkill2);
            etDomain3=(EditText)findViewById(R.id.etDomain3);
            etSkill3=(EditText)findViewById(R.id.etSkill3);
            etDomain4=(EditText)findViewById(R.id.etDomain4);
            etSkill4=(EditText)findViewById(R.id.etSkill4);
            etDomain5=(EditText)findViewById(R.id.etDomain5);
            etSkill5=(EditText)findViewById(R.id.etSkill5);
            etDomain6=(EditText)findViewById(R.id.etDomain6);
            etSkill6=(EditText)findViewById(R.id.etSkill6);*/

            /*etEC1=(EditText)findViewById(R.id.etEC1);
            etEC2=(EditText)findViewById(R.id.etEC2);
            etEC3=(EditText)findViewById(R.id.etEC3);
            etEC4=(EditText)findViewById(R.id.etEC4);
            etEC5=(EditText)findViewById(R.id.etEC5);
            etEC6=(EditText)findViewById(R.id.etEC6);

            etHobby=(EditText)findViewById(R.id.etHobbies);*/

            /*String filename=intent.getStringExtra("filename");
            Cursor personalCursor= dbQueries.selectPersonalInformation(filename);
            personalCursor.moveToFirst();
            String fileid=personalCursor.getString(personalCursor.getColumnIndex("fileid"));*/

            /*Cursor piCursor= dbQueries.selectPersonalInterests(fileid);*/
            /*Cursor ecCursor= dbQueries.selectECInformation(fileid);*/
            /*Cursor skillCursor= dbQueries.selectSkillInformation(fileid);*/
            /*Cursor achievementCursor= dbQueries.selectAchieveInformation(fileid);*/
            /*Cursor experienceCursor= dbQueries.selectExpInformation(fileid);*/
            /*Cursor certificationCursor= dbQueries.selectCertificationInformation(fileid);*/
            /*Cursor educationCursor= dbQueries.selectEducationInformation(fileid);*/

            /*piCursor.moveToFirst();
            ecCursor.moveToFirst();
            skillCursor.moveToFirst();
            achievementCursor.moveToFirst();
            experienceCursor.moveToFirst();
            certificationCursor.moveToFirst();
            educationCursor.moveToFirst();*/

            /*String title=personalCursor.getString(personalCursor.getColumnIndex("title"));
            String firstname=personalCursor.getString(personalCursor.getColumnIndex("firstname"));
            String lastname=personalCursor.getString(personalCursor.getColumnIndex("lastname"));
            String dateofbirth=personalCursor.getString(personalCursor.getColumnIndex("dateofbirth"));
            String email=personalCursor.getString(personalCursor.getColumnIndex("email"));
            String phoneext=personalCursor.getString(personalCursor.getColumnIndex("phoneext"));
            String phoneno=personalCursor.getString(personalCursor.getColumnIndex("phoneno"));
            String adress=personalCursor.getString(personalCursor.getColumnIndex("address"));
            String objective=personalCursor.getString(personalCursor.getColumnIndex("objective"));
            String template=personalCursor.getString(personalCursor.getColumnIndex("template"));*/



            /*String highestEdu,tenthCGPA,tenthSchool,tenthBoard,tenthYOP,twelfthCGPA,twelfthInstitute,twelfthBoard,twelfthYOP,dipCGPA,
                    dipInsti,dipBoard,dipYOP,collegeCGPA,collegeInsti,collegeBoard,collegeYOP,collegeDegree,pgCGPA,pgInsti,pgBoard,
                    pgYOP,pgDegree;

            highestEdu=educationCursor.getString(educationCursor.getColumnIndex("highestedu"));
            tenthCGPA=educationCursor.getString(educationCursor.getColumnIndex("tenthgrade"));
            tenthSchool=educationCursor.getString(educationCursor.getColumnIndex("tenthinsti"));
            tenthBoard=educationCursor.getString(educationCursor.getColumnIndex("tenthboard"));
            tenthYOP=educationCursor.getString(educationCursor.getColumnIndex("tenthyop"));
            twelfthCGPA=educationCursor.getString(educationCursor.getColumnIndex("twelfthgrade"));
            twelfthInstitute=educationCursor.getString(educationCursor.getColumnIndex("twelfthinsti"));
            twelfthBoard=educationCursor.getString(educationCursor.getColumnIndex("twelfthboard"));
            twelfthYOP=educationCursor.getString(educationCursor.getColumnIndex("twelfthyop"));
            dipCGPA=educationCursor.getString(educationCursor.getColumnIndex("diplomagrade"));
            dipInsti=educationCursor.getString(educationCursor.getColumnIndex("diplomainsti"));
            dipBoard=educationCursor.getString(educationCursor.getColumnIndex("diplomaboard"));
            dipYOP=educationCursor.getString(educationCursor.getColumnIndex("diplomayop"));
            collegeCGPA=educationCursor.getString(educationCursor.getColumnIndex("gradgrade"));
            collegeInsti=educationCursor.getString(educationCursor.getColumnIndex("gradinsti"));
            collegeBoard=educationCursor.getString(educationCursor.getColumnIndex("gradcollege"));
            collegeYOP=educationCursor.getString(educationCursor.getColumnIndex("gradyop"));
            collegeDegree=educationCursor.getString(educationCursor.getColumnIndex("graddegree"));
            pgCGPA=educationCursor.getString(educationCursor.getColumnIndex("pggrade"));
            pgInsti=educationCursor.getString(educationCursor.getColumnIndex("pginsti"));
            pgBoard=educationCursor.getString(educationCursor.getColumnIndex("pgcollege"));
            pgYOP=educationCursor.getString(educationCursor.getColumnIndex("pgyop"));
            pgDegree=educationCursor.getString(educationCursor.getColumnIndex("pgdegree"));*/



            /*String certTitle1,certYear1,certTitle2,certYear2,certTitle3,certYear3,certTitle4,certYear4,certTitle5,certYear5,certTitle6,
                    certYear6;

            certTitle1=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle1"));
            certYear1=certificationCursor.getString(certificationCursor.getColumnIndex("certyear1"));
            certTitle2=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle2"));
            certYear2=certificationCursor.getString(certificationCursor.getColumnIndex("certyear2"));
            certTitle3=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle3"));
            certYear3=certificationCursor.getString(certificationCursor.getColumnIndex("certyear3"));
            certTitle4=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle4"));
            certYear4=certificationCursor.getString(certificationCursor.getColumnIndex("certyear4"));
            certTitle5=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle5"));
            certYear5=certificationCursor.getString(certificationCursor.getColumnIndex("certyear5"));
            certTitle6=certificationCursor.getString(certificationCursor.getColumnIndex("certtitle6"));
            certYear6=certificationCursor.getString(certificationCursor.getColumnIndex("certyear6"));*/



            /*String coName1,duration1,proName1,role1,roleDesc1,proDesc1,coName2,duration2,proName2,role2,roleDesc2,proDesc2,coName3,
                    duration3,proName3,role3,roleDesc3,proDesc3,coName4,duration4,proName4,role4,roleDesc4,proDesc4,coName5,duration5,
                    proName5,role5,roleDesc5,proDesc5,coName6,duration6,proName6,role6,roleDesc6,proDesc6;

            coName1=experienceCursor.getString(experienceCursor.getColumnIndex("companyname1"));
            duration1=experienceCursor.getString(experienceCursor.getColumnIndex("duration1"));
            role1=experienceCursor.getString(experienceCursor.getColumnIndex("role1"));
            proDesc1=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc1"));
            coName2=experienceCursor.getString(experienceCursor.getColumnIndex("companyname2"));
            duration2=experienceCursor.getString(experienceCursor.getColumnIndex("duration2"));
            role2=experienceCursor.getString(experienceCursor.getColumnIndex("role2"));
            proDesc2=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc2"));
            coName3=experienceCursor.getString(experienceCursor.getColumnIndex("companyname3"));
            duration3=experienceCursor.getString(experienceCursor.getColumnIndex("duration3"));
            role3=experienceCursor.getString(experienceCursor.getColumnIndex("role3"));
            proDesc3=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc3"));
            coName4=experienceCursor.getString(experienceCursor.getColumnIndex("companyname4"));
            duration4=experienceCursor.getString(experienceCursor.getColumnIndex("duration4"));
            role4=experienceCursor.getString(experienceCursor.getColumnIndex("role4"));
            proDesc4=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc4"));
            coName5=experienceCursor.getString(experienceCursor.getColumnIndex("companyname5"));
            duration5=experienceCursor.getString(experienceCursor.getColumnIndex("duration5"));
            role5=experienceCursor.getString(experienceCursor.getColumnIndex("role5"));
            proDesc5=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc5"));
            coName6=experienceCursor.getString(experienceCursor.getColumnIndex("companyname6"));
            duration6=experienceCursor.getString(experienceCursor.getColumnIndex("duration6"));
            role6=experienceCursor.getString(experienceCursor.getColumnIndex("role6"));
            proDesc6=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc6"));*/

            /*String achieveTitle1,achieveDesc1,achieveTitle2,achieveDesc2,achieveTitle3,achieveDesc3,achieveTitle4,achieveDesc4,
                    achieveTitle5,achieveDesc5,achieveTitle6,achieveDesc6;*/
            /*String domain1,skill1,domain2,skill2,domain3,skill3,domain4,skill4,domain5,skill5,domain6,skill6;*/
            /*String ec1,ec2,ec3,ec4,ec5,ec6;*/
            /*String personalInterest;*/



            /*achieveDesc1=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc1"));
            achieveDesc2=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc2"));
            achieveDesc3=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc3"));
            achieveDesc4=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc4"));
            achieveDesc5=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc5"));
            achieveDesc6=achievementCursor.getString(achievementCursor.getColumnIndex("achievedesc6"));*/



            /*domain1=skillCursor.getString(skillCursor.getColumnIndex("skillarea1"));
            skill1=skillCursor.getString(skillCursor.getColumnIndex("skillset1"));
            domain2=skillCursor.getString(skillCursor.getColumnIndex("skillarea2"));
            skill2=skillCursor.getString(skillCursor.getColumnIndex("skillset2"));
            domain3=skillCursor.getString(skillCursor.getColumnIndex("skillarea3"));
            skill3=skillCursor.getString(skillCursor.getColumnIndex("skillset3"));
            domain4=skillCursor.getString(skillCursor.getColumnIndex("skillarea4"));
            skill4=skillCursor.getString(skillCursor.getColumnIndex("skillset4"));
            domain5=skillCursor.getString(skillCursor.getColumnIndex("skillarea5"));
            skill5=skillCursor.getString(skillCursor.getColumnIndex("skillset5"));
            domain6=skillCursor.getString(skillCursor.getColumnIndex("skillarea6"));
            skill6=skillCursor.getString(skillCursor.getColumnIndex("skillset6"));*/



            /*ec1=ecCursor.getString(ecCursor.getColumnIndex("extracurr1"));
            ec2=ecCursor.getString(ecCursor.getColumnIndex("extracurr2"));
            ec3=ecCursor.getString(ecCursor.getColumnIndex("extracurr3"));
            ec4=ecCursor.getString(ecCursor.getColumnIndex("extracurr4"));
            ec5=ecCursor.getString(ecCursor.getColumnIndex("extracurr5"));
            ec6=ecCursor.getString(ecCursor.getColumnIndex("extracurr6"));*/

            /*etFileName.setText(filename);
            int titlePosition=0;
            int templatePosition=0;
            if(title.equals("Mr.")){
                titlePosition=0;
            }else if(title.equals("Ms.")){
                titlePosition=1;
            }else if(title.equals("Mrs.")){
                titlePosition=2;
            }else if(title.equals("Dr.")){
                titlePosition=3;
            }*/

            /*if(template.equals("Default")){
                templatePosition=0;

            }else if(template.equals("NMIMS")){
                templatePosition=1;

            }else if(template.equals("Harvard")){

                templatePosition=2;
            }
            spinnerTemplate.setSelection(templatePosition);
            etObjective.setText(objective);
            spinnerTitle.setSelection(titlePosition);
            etFName.setText(firstname);
            etLName.setText(lastname);
            etDOB.setText(dateofbirth);
            etEmail.setText(email);
            etCode.setText(phoneext);
            etPhone.setText(phoneno);
            etAdress.setText(adress);*/

            /*ten=(LinearLayout)findViewById(R.id.ll10th) ;
            twelve=(LinearLayout)findViewById(R.id.ll12th) ;
            diploma=(LinearLayout)findViewById(R.id.llDiploma) ;
            college=(LinearLayout)findViewById(R.id.llCollege) ;
            graduate=(LinearLayout)findViewById(R.id.llPostGrad) ;
            int eduPosition=0;
            if(highestEdu.equals("10th")){
                eduPosition=1;
                ten.setVisibility(View.VISIBLE);
                twelve.setVisibility(View.INVISIBLE);
                diploma.setVisibility(View.INVISIBLE);
                college.setVisibility(View.INVISIBLE);
                graduate.setVisibility(View.INVISIBLE);
            }else if(highestEdu.equals("12th(High School)")){
                eduPosition=2;
                ten.setVisibility(View.VISIBLE);
                twelve.setVisibility(View.VISIBLE);
                diploma.setVisibility(View.INVISIBLE);
                college.setVisibility(View.INVISIBLE);
                graduate.setVisibility(View.INVISIBLE);
            }else if(highestEdu.equals("Diploma(10 + 3)")){
                eduPosition=3;
                ten.setVisibility(View.VISIBLE);
                twelve.setVisibility(View.INVISIBLE);
                diploma.setVisibility(View.VISIBLE);
                college.setVisibility(View.INVISIBLE);
                graduate.setVisibility(View.INVISIBLE);
            }else if(highestEdu.equals("Under Graduate")){
                eduPosition=4;
                if(twelfthCGPA.equals("")&&!dipCGPA.equals("")){
                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.VISIBLE);
                    diploma.setVisibility(View.INVISIBLE);
                    college.setVisibility(View.VISIBLE);
                    graduate.setVisibility(View.INVISIBLE);
                }else if(!twelfthCGPA.equals("")&&dipCGPA.equals("")){

                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.INVISIBLE);
                    diploma.setVisibility(View.VISIBLE);
                    college.setVisibility(View.VISIBLE);
                    graduate.setVisibility(View.INVISIBLE);

                }
            }else if(highestEdu.equals("Post Graduate")){
                eduPosition=5;
                if(twelfthCGPA.equals("")&&!dipCGPA.equals("")){
                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.VISIBLE);
                    diploma.setVisibility(View.INVISIBLE);
                    college.setVisibility(View.VISIBLE);
                    graduate.setVisibility(View.VISIBLE);
                }else if(!twelfthCGPA.equals("")&&dipCGPA.equals("")){

                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.INVISIBLE);
                    diploma.setVisibility(View.VISIBLE);
                    college.setVisibility(View.VISIBLE);
                    graduate.setVisibility(View.VISIBLE);

                }
            }*/
            /*spinnerHighestEdu.setSelection(eduPosition);
            etTenthCGPA.setText(tenthCGPA);
            etTenthSchool.setText(tenthSchool);
            etTenthBoard.setText(tenthBoard);
            etTenthYOP.setText(tenthYOP);
            etTwelfthCGPA.setText(twelfthCGPA);
            etTwelfthInsti.setText(twelfthInstitute);
            etTwelfthBoard.setText(twelfthBoard);
            etTwelfthYOP.setText(twelfthYOP);
            etDipCGPA.setText(dipCGPA);
            etDipInsti.setText(dipInsti);
            etDipBoard.setText(dipBoard);
            etDipYOP.setText(dipYOP);
            etCollegeCGPA.setText(collegeCGPA);
            etCollegeDegree.setText(collegeDegree);
            etCollegeBoard.setText(collegeBoard);
            etCollegeYOP.setText(collegeYOP);
            etCollegeCOllege.setText(collegeInsti);
            etPgCollege.setText(pgInsti);
            etPgBoard.setText(pgBoard);
            etPgCGPA.setText(pgCGPA);
            etPgDegree.setText(pgDegree);
            etPgYOP.setText(pgYOP);*/

            /*etCertTitle1.setText(certTitle1);
            etCertYear1.setText(certYear1);
            etCertTitle2.setText(certTitle2);
            etCertYear2.setText(certYear2);
            etCertTitle3.setText(certTitle3);
            etCertYear3.setText(certYear3);
            etCertTitle4.setText(certTitle4);
            etCertYear4.setText(certYear4);
            etCertTitle5.setText(certTitle5);
            etCertYear5.setText(certYear5);
            etCertTitle6.setText(certTitle6);
            etCertYear6.setText(certYear6);*/

            /*etCoName1.setText(coName1);
            etDuration1.setText(duration1);
            etRole1.setText(role1);
            etProDesc1.setText(proDesc1);
            etCoName2.setText(coName2);
            etDuration2.setText(duration2);
            etRole2.setText(role2);
            etProDesc2.setText(proDesc2);
            etCoName3.setText(coName3);
            etDuration3.setText(duration3);
            etRole3.setText(role3);
            etProDesc3.setText(proDesc3);
            etCoName4.setText(coName4);
            etDuration4.setText(duration4);
            etRole4.setText(role4);
            etProDesc4.setText(proDesc4);
            etCoName5.setText(coName5);
            etDuration5.setText(duration5);
            etRole5.setText(role5);
            etProDesc5.setText(proDesc5);
            etCoName6.setText(coName6);
            etDuration6.setText(duration6);
            etRole6.setText(role6);
            etProDesc6.setText(proDesc6);*/

            /*etAchieveDesc1.setText(achieveDesc1);
            etAchieveDesc2.setText(achieveDesc2);
            etAchieveDesc3.setText(achieveDesc3);
            etAchieveDesc4.setText(achieveDesc4);
            etAchieveDesc5.setText(achieveDesc5);
            etAchieveDesc6.setText(achieveDesc6);*/

            /*etDomain1.setText(domain1);
            etSkill1.setText(skill1);
            etDomain2.setText(domain2);
            etSkill2.setText(skill2);
            etDomain3.setText(domain3);
            etSkill3.setText(skill3);
            etDomain4.setText(domain4);
            etSkill4.setText(skill4);
            etDomain5.setText(domain5);
            etSkill5.setText(skill5);
            etDomain6.setText(domain6);
            etSkill6.setText(skill6);*/

            /*etEC1.setText(ec1);
            etEC2.setText(ec2);
            etEC3.setText(ec3);
            etEC4.setText(ec4);
            etEC5.setText(ec5);
            etEC6.setText(ec6);

            etHobby.setText(personalInterest);*/

            /*LinearLayout cert2=(LinearLayout)findViewById(R.id.llCert2);
            LinearLayout cert3=(LinearLayout)findViewById(R.id.llCert3);
            LinearLayout cert4=(LinearLayout)findViewById(R.id.llCert4);
            LinearLayout cert5=(LinearLayout)findViewById(R.id.llCert5);
            LinearLayout cert6=(LinearLayout)findViewById(R.id.llCert6);
            if(!certTitle2.equals("")){
                cert2.setVisibility(View.VISIBLE);
                if(!certTitle3.equals("")){
                    cert3.setVisibility(View.VISIBLE);
                    if(!certTitle4.equals("")){
                        cert4.setVisibility(View.VISIBLE);
                        if(!certTitle5.equals("")){
                            cert5.setVisibility(View.VISIBLE);
                            if(!certTitle6.equals("")){
                                cert6.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }*/

            /*LinearLayout ach2=(LinearLayout)findViewById(R.id.achievment2);
            LinearLayout ach3=(LinearLayout)findViewById(R.id.achievment3);
            LinearLayout ach4=(LinearLayout)findViewById(R.id.achievment4);
            LinearLayout ach5=(LinearLayout)findViewById(R.id.achievment5);
            LinearLayout ach6=(LinearLayout)findViewById(R.id.achievment6);

            if(!achieveDesc2.equals("")){
                ach2.setVisibility(View.VISIBLE);
                if(!achieveDesc3.equals("")){
                    ach3.setVisibility(View.VISIBLE);
                    if(!achieveDesc4.equals("")){
                        ach4.setVisibility(View.VISIBLE);
                        if(!achieveDesc5.equals("")){
                            ach5.setVisibility(View.VISIBLE);
                            if(!achieveDesc6.equals("")){
                                ach6.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }*/

            /*LinearLayout exp2=(LinearLayout)findViewById(R.id.llExp2);
            LinearLayout exp3=(LinearLayout)findViewById(R.id.llExp3);
            LinearLayout exp4=(LinearLayout)findViewById(R.id.llExp4);
            LinearLayout exp5=(LinearLayout)findViewById(R.id.llExp5);
            LinearLayout exp6=(LinearLayout)findViewById(R.id.llExp6);

            if(!coName2.equals("")){
                exp2.setVisibility(View.VISIBLE);
                if(!coName3.equals("")){
                    exp3.setVisibility(View.VISIBLE);
                    if(!coName4.equals("")){
                        exp4.setVisibility(View.VISIBLE);
                        if(!coName5.equals("")){
                            exp5.setVisibility(View.VISIBLE);
                            if(!coName6.equals("")){
                                exp6.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }*/

            /*LinearLayout sk2=(LinearLayout)findViewById(R.id.llSkills2);
            LinearLayout sk3=(LinearLayout)findViewById(R.id.llSkills3);
            LinearLayout sk4=(LinearLayout)findViewById(R.id.llSkills4);
            LinearLayout sk5=(LinearLayout)findViewById(R.id.llSkills5);
            LinearLayout sk6=(LinearLayout)findViewById(R.id.llSkills6);

            if(!domain2.equals("")){
                sk2.setVisibility(View.VISIBLE);
                if(!domain3.equals("")){
                    sk3.setVisibility(View.VISIBLE);
                    if(!domain4.equals("")){
                        sk4.setVisibility(View.VISIBLE);
                        if(!domain5.equals("")){
                            sk5.setVisibility(View.VISIBLE);
                            if(!domain6.equals("")){
                                sk6.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }*/

            /*EditText e2=(EditText) findViewById(R.id.etEC2);
            EditText e3=(EditText) findViewById(R.id.etEC3);
            EditText e4=(EditText) findViewById(R.id.etEC4);
            EditText e5=(EditText) findViewById(R.id.etEC5);
            EditText e6=(EditText) findViewById(R.id.etEC6);

            if(!ec2.equals("")){
                e2.setVisibility(View.VISIBLE);
                if(!ec3.equals("")){
                    e3.setVisibility(View.VISIBLE);
                    if(!ec4.equals("")){
                        e4.setVisibility(View.VISIBLE);
                        if(!ec5.equals("")){
                            e5.setVisibility(View.VISIBLE);
                            if(!ec6.equals("")){
                                e6.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }*/

            /*switch (spinnerHighestEdu.getSelectedItemPosition()){
                case 0:
                    ten.setVisibility(View.GONE);
                    twelve.setVisibility(View.GONE);
                    diploma.setVisibility(View.GONE);
                    college.setVisibility(View.GONE);
                    graduate.setVisibility(View.GONE);
                    break;
                case 1:
                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.GONE);
                    diploma.setVisibility(View.GONE);
                    college.setVisibility(View.GONE);
                    graduate.setVisibility(View.GONE);
                    break;
                case 2:
                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.VISIBLE);
                    diploma.setVisibility(View.GONE);
                    college.setVisibility(View.GONE);
                    graduate.setVisibility(View.GONE);
                    break;
                case 3:
                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.VISIBLE);
                    diploma.setVisibility(View.VISIBLE);
                    college.setVisibility(View.GONE);
                    graduate.setVisibility(View.GONE);
                    break;
                case 4:
                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.VISIBLE);
                    diploma.setVisibility(View.VISIBLE);
                    college.setVisibility(View.VISIBLE);
                    graduate.setVisibility(View.GONE);
                    break;
                case 5:
                    ten.setVisibility(View.VISIBLE);
                    twelve.setVisibility(View.VISIBLE);
                    diploma.setVisibility(View.VISIBLE);
                    college.setVisibility(View.VISIBLE);
                    graduate.setVisibility(View.VISIBLE);
                    break;
            }*/

//        }
//    }

    private File createDirectory(){
        File myDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker");
        if(!myDirectory.exists()){
            myDirectory.mkdir();
        }
        return myDirectory;
    }

    private File createPdf(File myDirectory) throws DocumentException, IOException{
        File f = new File(myDirectory, etFileName.getText().toString().replaceAll("'","")+".pdf");
        FileOutputStream fos = new FileOutputStream(f);
        Document pdfDocument = new Document(PageSize.A4);
        PdfWriter.getInstance(pdfDocument,fos);
        pdfDocument.open();

        filename=etFileName.getText().toString();
        template= spinnerTemplate.getSelectedItem().toString();
        firstName=etFName.getText().toString();
        lastName=etLName.getText().toString();
        objective=etObjective.getText().toString();
        title=spinnerTitle.getSelectedItem().toString();
        DOB= etDOB.getText().toString();
        email= etEmail.getText().toString();
        ext=etCode.getText().toString();
        phone=etPhone.getText().toString();
        adress=etAdress.getText().toString();

        highestEdu=spinnerHighestEdu.getSelectedItem().toString();
        tenthCGPA=etTenthCGPA.getText().toString();
        tenthSchool=etTenthSchool.getText().toString();
        tenthBoard=etTenthBoard.getText().toString();
        tenthYOP=etTenthYOP.getText().toString();
        twelfthCGPA=etTwelfthCGPA.getText().toString();
        twelfthInstitute=etTwelfthInsti.getText().toString();
        twelfthBoard=etTwelfthBoard.getText().toString();
        twelfthYOP=etTwelfthYOP.getText().toString();
        dipCGPA=etDipCGPA.getText().toString();
        dipInsti=etDipInsti.getText().toString();
        dipBoard=etDipBoard.getText().toString();
        dipYOP=etDipYOP.getText().toString();
        collegeCGPA=etCollegeCGPA.getText().toString();
        collegeInsti=etCollegeCOllege.getText().toString();
        collegeBoard=etCollegeBoard.getText().toString();
        collegeYOP=etCollegeYOP.getText().toString();
        collegeDegree=etCollegeDegree.getText().toString();
        pgCGPA=etPgCGPA.getText().toString();
        pgInsti=etPgCollege.getText().toString();
        pgBoard=etPgBoard.getText().toString();
        pgYOP=etPgYOP.getText().toString();
        pgDegree=etPgDegree.getText().toString();

        certTitle1=etCertTitle1.getText().toString();
        certYear1=etCertYear1.getText().toString();
        certTitle2=etCertTitle2.getText().toString();
        certYear2=etCertYear2.getText().toString();
        certTitle3=etCertTitle3.getText().toString();
        certYear3=etCertYear3.getText().toString();
        certTitle4=etCertTitle4.getText().toString();
        certYear4=etCertYear4.getText().toString();
        certTitle5=etCertTitle5.getText().toString();
        certYear5=etCertYear5.getText().toString();
        certTitle6=etCertTitle6.getText().toString();
        certYear6=etCertYear6.getText().toString();

        coName1=etCoName1.getText().toString();
        duration1=etDuration1.getText().toString();
        role1=etRole1.getText().toString();
        proDesc1=etProDesc1.getText().toString();
        coName2=etCoName2.getText().toString();
        duration2=etDuration2.getText().toString();
        role2=etRole2.getText().toString();
        proDesc2=etProDesc2.getText().toString();
        coName3=etCoName3.getText().toString();
        duration3=etDuration3.getText().toString();
        role3=etRole3.getText().toString();
        proDesc3=etProDesc3.getText().toString();
        coName4=etCoName4.getText().toString();
        duration4=etDuration4.getText().toString();
        role4=etRole4.getText().toString();
        proDesc4=etProDesc4.getText().toString();
        coName5=etCoName5.getText().toString();
        duration5=etDuration5.getText().toString();
        role5=etRole5.getText().toString();
        proDesc5=etProDesc5.getText().toString();
        coName6=etCoName6.getText().toString();
        duration6=etDuration6.getText().toString();
        role6=etRole6.getText().toString();
        proDesc6=etProDesc6.getText().toString();

        achieveDesc1=etAchieveDesc1.getText().toString();
        achieveDesc2=etAchieveDesc2.getText().toString();
        achieveDesc3=etAchieveDesc3.getText().toString();
        achieveDesc4=etAchieveDesc4.getText().toString();
        achieveDesc5=etAchieveDesc5.getText().toString();
        achieveDesc6=etAchieveDesc6.getText().toString();

        domain1=etDomain1.getText().toString();
        skill1=etSkill1.getText().toString();
        domain2=etDomain2.getText().toString();
        skill2=etSkill2.getText().toString();
        domain3=etDomain3.getText().toString();
        skill3=etSkill3.getText().toString();
        domain4=etDomain4.getText().toString();
        skill4=etSkill4.getText().toString();
        domain5=etDomain5.getText().toString();
        skill5=etSkill5.getText().toString();
        domain6=etDomain6.getText().toString();
        skill6=etSkill6.getText().toString();

        ec1=etEC1.getText().toString();
        ec2=etEC2.getText().toString();
        ec3=etEC3.getText().toString();
        ec4=etEC4.getText().toString();
        ec5=etEC5.getText().toString();
        ec6=etEC6.getText().toString();

        personalInterest=etHobby.getText().toString();

        dbQueries = new DatabaseQueries(CreateResume.this);
        dbQueries.addPersonalInfo(filename,objective,firstName,lastName,template,DOB,email,ext,phone,adress,title);
        dbQueries.addEducationInfo(filename,highestEdu,tenthCGPA,tenthSchool,tenthBoard,tenthYOP,twelfthCGPA,twelfthInstitute,
                twelfthBoard,twelfthYOP,dipCGPA,dipInsti, dipBoard, dipYOP,collegeCGPA,collegeDegree,collegeBoard,collegeYOP,
                collegeInsti,pgCGPA,pgDegree,pgBoard,pgYOP,pgInsti);
        dbQueries.addCertificationInfo(filename,certTitle1,certYear1,certTitle2,certYear2,certTitle3,certYear3,certTitle4,certYear4,
                certTitle5,certYear5,certTitle6,certYear6);
        dbQueries.addWorkExperienceInfo(filename,coName1,duration1,role1,proDesc1,coName2,duration2,role2,proDesc2,coName3,duration3,role3,proDesc3,coName4,duration4,role4,proDesc4,
                coName5,duration5,role5,proDesc5,coName6,duration6,role6,proDesc6);
        dbQueries.addAchievementsInfo(filename,achieveDesc1,achieveDesc2,achieveDesc3,achieveDesc4,achieveDesc5,achieveDesc6);
        dbQueries.addSkillsInfo(filename,domain1,skill1,domain2,skill2,domain3,skill3,domain4,skill4,domain5,skill5,domain6,skill6);
        dbQueries.addExtraCurricularsInfo(filename,ec1,ec2,ec3,ec4,ec5,ec6);
        dbQueries.addPersonalInterestInfo(filename,personalInterest);
        Cursor myCursor = dbQueries.getFileContent(filename);
        myCursor.moveToFirst();
        if(myCursor.getString(myCursor.getColumnIndex("template")) != null){
            if(myCursor.getString(myCursor.getColumnIndex("template")).equals("Default")){
                createDefaultTemplate(pdfDocument, myCursor);
            }
            else if(myCursor.getString(myCursor.getColumnIndex("template")).equals("NMIMS")){
                createNMIMSTemplate(pdfDocument, myCursor);
            }
            else if(myCursor.getString(myCursor.getColumnIndex("template")).equals("Harvard")){
                createHarvardTemplate(pdfDocument, myCursor);
            }
        }
        else{
            float[] columnWidths = {1, 5, 5};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.GRAY);
            PdfPCell cell = new PdfPCell(new Phrase("TEMPLATE IS NULL", font));
            cell.setBackgroundColor(GrayColor.GRAYBLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);
            pdfDocument.add(new Paragraph(txtObjective.getText().toString()));
            pdfDocument.add(new Paragraph(txtName.getText().toString()));
            pdfDocument.close();
        }
        fos.close();
        return f;
    }

    public void viewPdf(File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        /*Intent mainIntent= new Intent(CreateResume.this,ActivityMain.class);
        mainIntent.putExtra("isForPDF",true);
        mainIntent.putExtra("file",file);
        finish();*/
        startActivity(intent);


    }

    private Image takeScreenshot() throws Exception{
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = v1.getDrawingCache();

        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        Image imageFile= Image.getInstance(fos.toByteArray());

        fos.flush();
        fos.close();
        return imageFile;
    }

    private void createDefaultTemplate(Document pdfDocument, Cursor myCursor) throws DocumentException{
        if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){
            Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                    myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            paraName.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraName);
        }
        if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("email")).length()>0){
            Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")) + " | " +
                    myCursor.getString(myCursor.getColumnIndex("phoneext"))+" "+myCursor.getString(myCursor.getColumnIndex("phoneno")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraEmail.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraEmail);
        }
        if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
            Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraAddress.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraAddress);
        }
        if(myCursor.getString(myCursor.getColumnIndex("objective")) != null &&
                myCursor.getString(myCursor.getColumnIndex("objective")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("objective")).length() > 0){
            pdfDocument.add(Chunk.NEWLINE);
            Paragraph paraObjectiveHead = new Paragraph("OBJECTIVE", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraObjectiveHead);
            LineSeparator lsObjective = new LineSeparator();
            pdfDocument.add(new Chunk(lsObjective));
            Paragraph paraObjective = new Paragraph(myCursor.getString(myCursor.getColumnIndex("objective")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraObjective);
        }
        if(myCursor.getString(myCursor.getColumnIndex("highestedu")) != null &&
                !myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Select") &&
                myCursor.getString(myCursor.getColumnIndex("highestedu")).length() > 0){
            pdfDocument.add(Chunk.NEWLINE);
            Paragraph paraEduHead = new Paragraph("EDUCATION", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraEduHead);
            LineSeparator lsEducation = new LineSeparator();
            pdfDocument.add(new Chunk(lsEducation));

            float[] columnWidths = {2,5,3,2,2};
            PdfPTable eduTable = new PdfPTable(columnWidths);
            eduTable.setWidthPercentage(100);
            eduTable.getDefaultCell().setUseAscender(true);
            eduTable.getDefaultCell().setUseDescender(true);

            PdfPCell eduHeader1 = new PdfPCell(new Phrase("Degree", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader1);

            PdfPCell eduHeader2 = new PdfPCell(new Phrase("Institute", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader2);

            PdfPCell eduHeader3 = new PdfPCell(new Phrase("Board/University", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader3);

            PdfPCell eduHeader4 = new PdfPCell(new Phrase("Year", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader4.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader4);

            PdfPCell eduHeader5 = new PdfPCell(new Phrase("Grade", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader5.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader5);

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgdegree")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgcollege")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pginsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pggrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("graddegree")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradcollege")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradinsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradgrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                if(myCursor.getString(myCursor.getColumnIndex("diplomainsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("diplomainsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("diplomainsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("Diploma", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomainsti")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomaboard")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomayop")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomagrade")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }

                if(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthboard")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthyop")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthgrade")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("10") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("10th", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthinsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthboard")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthgrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }
            pdfDocument.add(eduTable);
            /*Paragraph paraEducation = new Paragraph("Some Education", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraEducation);*/
        }
         if(myCursor.getString(myCursor.getColumnIndex("certtitle1")) != null &&
                 myCursor.getString(myCursor.getColumnIndex("certtitle1")) != "" &&
                 myCursor.getString(myCursor.getColumnIndex("certtitle1")).length() > 0){
             pdfDocument.add(Chunk.NEWLINE);
             Paragraph paraCertHead = new Paragraph("CERTIFICATIONS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
             pdfDocument.add(paraCertHead);
             LineSeparator lsCertifications = new LineSeparator();
             pdfDocument.add(new Chunk(lsCertifications));
             List CertList = new List(12);
             CertList.setListSymbol("\u2022");
             ListItem CertListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle1")) + ", " +
                     myCursor.getString(myCursor.getColumnIndex("certyear1")),
                     new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
             CertList.add(CertListItem1);

             for(int i=2;i<7;i++){
                 if(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != null &&
                         myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != "" &&
                         myCursor.getString(myCursor.getColumnIndex("certtitle"+i)).length() > 0){
                     ListItem CertListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) + ", " +
                             myCursor.getString(myCursor.getColumnIndex("certyear"+i)),
                             new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                     CertList.add(CertListItem);
                 }
                 else{
                     break;
                 }
             }
             pdfDocument.add(CertList);
            /*Paragraph paraCertifications = new Paragraph("Java, C++, Spring MVC, HTML, CSS, Javascript, Oracle, MySql, PostgreSQL, Jasper Reports",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraCertifications);*/
        }
        if(myCursor.getString(myCursor.getColumnIndex("companyname1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("companyname1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("companyname1")).length() > 0){
            pdfDocument.add(Chunk.NEWLINE);
            Paragraph paraExpHead = new Paragraph("WORK EXPERIENCE", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraExpHead);
            LineSeparator lsWorkExp = new LineSeparator();
            pdfDocument.add(new Chunk(lsWorkExp));

            float[] columnWidths = {5, 5};
            PdfPTable workTable = new PdfPTable(columnWidths);
            workTable.setWidthPercentage(100);
            workTable.getDefaultCell().setUseAscender(true);
            workTable.getDefaultCell().setUseDescender(true);

            PdfPCell workHeader1 = new PdfPCell(new Phrase("Company", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader1);
            PdfPCell workHeader2 = new PdfPCell(new Phrase("Description", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader2);

            PdfPCell workCell1 = new PdfPCell();
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname1")),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)).length() > 0){

                    PdfPCell workCellCom = new PdfPCell();
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellCom.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellCom);

                    PdfPCell workCellDes = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellDes.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellDes);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(workTable);
        }
        if(myCursor.getString(myCursor.getColumnIndex("achievedesc1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("achievedesc1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("achievedesc1")).length() > 0){
            pdfDocument.add(Chunk.NEWLINE);
            Paragraph paraAchieveHead = new Paragraph("ACHIEVEMENTS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraAchieveHead);
            LineSeparator lsAchievements = new LineSeparator();
            pdfDocument.add(new Chunk(lsAchievements));
            List AchList = new List(12);
            AchList.setListSymbol("\u2022");
            ListItem AchListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            AchList.add(AchListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)).length() > 0){
                    ListItem AchListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    AchList.add(AchListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(AchList);
        }
        if(myCursor.getString(myCursor.getColumnIndex("skillarea1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("skillarea1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("skillarea1")).length() > 0){
            pdfDocument.add(Chunk.NEWLINE);
            Paragraph paraTSHead = new Paragraph("TECHNICAL SKILLS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraTSHead);
            LineSeparator lsTSkills = new LineSeparator();
            pdfDocument.add(new Chunk(lsTSkills));

            float[] columnWidths = {3, 5};
            PdfPTable skillsTable = new PdfPTable(columnWidths);
            skillsTable.setWidthPercentage(100);
            skillsTable.getDefaultCell().setUseAscender(true);
            skillsTable.getDefaultCell().setUseDescender(true);

            /*PdfPCell skillAreaHeader1 = new PdfPCell(new Phrase("Domain",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaHeader1);
            PdfPCell skillAreaHeader2 = new PdfPCell(new Phrase("Skill Set",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaHeader2);*/

            PdfPCell skillAreaCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillarea1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaCell1);

            PdfPCell skillSetCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillset1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            skillSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillSetCell1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("skillarea"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("skillarea"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("skillarea"+i)).length() > 0){

                    PdfPCell skillAreaCell = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillarea"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                    skillAreaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    skillsTable.addCell(skillAreaCell);

                    PdfPCell skillSetCell = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillset"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    skillSetCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    skillsTable.addCell(skillSetCell);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(skillsTable);
        }
        if(myCursor.getString(myCursor.getColumnIndex("extracurr1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("extracurr1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("extracurr1")).length() > 0){
            pdfDocument.add(Chunk.NEWLINE);
            Paragraph paraAct = new Paragraph("EXTRA CURRICULAR ACTIVITIES", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraAct);
            LineSeparator lsActivities = new LineSeparator();
            pdfDocument.add(new Chunk(lsActivities));
            List ECList = new List(12);
            ECList.setListSymbol("\u2022");
            ListItem ECListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            ECList.add(ECListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)).length() > 0){
                    ListItem ECListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    ECList.add(ECListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(ECList);
        }
        if(myCursor.getString(myCursor.getColumnIndex("interestdesc")) != null &&
                myCursor.getString(myCursor.getColumnIndex("interestdesc")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("interestdesc")).length() > 0){
            pdfDocument.add(Chunk.NEWLINE);
            Paragraph paraPI = new Paragraph("PERSONAL INTERESTS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraPI);
            LineSeparator lsPI = new LineSeparator();
            pdfDocument.add(new Chunk(lsPI));
            Paragraph paraInterests = new Paragraph(myCursor.getString(myCursor.getColumnIndex("interestdesc")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraInterests);
        }
        pdfDocument.close();
    }

    private void createNMIMSTemplate(Document pdfDocument, Cursor myCursor) throws DocumentException{
        float[] columnWidths = {1, 5, 5};
        if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){
            Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                    myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            paraName.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraName);
        }
        if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("email")).length()>0){
            Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")) + " | " +
                    myCursor.getString(myCursor.getColumnIndex("phoneext"))+" "+myCursor.getString(myCursor.getColumnIndex("phoneno")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraEmail.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraEmail);
        }
        if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
            Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraAddress.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraAddress);
        }
        if(myCursor.getString(myCursor.getColumnIndex("objective")) != null &&
                myCursor.getString(myCursor.getColumnIndex("objective")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("objective")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("OBJECTIVE", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);
            Paragraph paraObjective = new Paragraph(myCursor.getString(myCursor.getColumnIndex("objective")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraObjective);
        }
        if(myCursor.getString(myCursor.getColumnIndex("highestedu")) != null &&
                !myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Select") &&
                myCursor.getString(myCursor.getColumnIndex("highestedu")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("EDUCATION", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);

            float[] columnEduWidths = {2,5,3,2,2};
            PdfPTable eduTable = new PdfPTable(columnEduWidths);
            eduTable.setWidthPercentage(100);
            eduTable.getDefaultCell().setUseAscender(true);
            eduTable.getDefaultCell().setUseDescender(true);

            PdfPCell eduHeader1 = new PdfPCell(new Phrase("Degree", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduHeader1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            eduTable.addCell(eduHeader1);

            PdfPCell eduHeader2 = new PdfPCell(new Phrase("Institute", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduHeader2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            eduTable.addCell(eduHeader2);

            PdfPCell eduHeader3 = new PdfPCell(new Phrase("Board/University", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduHeader3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            eduTable.addCell(eduHeader3);

            PdfPCell eduHeader4 = new PdfPCell(new Phrase("Year", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader4.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduHeader4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            eduTable.addCell(eduHeader4);

            PdfPCell eduHeader5 = new PdfPCell(new Phrase("Grade", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader5.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduHeader5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            eduTable.addCell(eduHeader5);

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgdegree")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgcollege")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pginsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pggrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("graddegree")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradcollege")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradinsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradgrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                if(myCursor.getString(myCursor.getColumnIndex("diplomainsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("diplomainsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("diplomainsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("Diploma", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomainsti")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomaboard")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomayop")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomagrade")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }

                if(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthboard")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthyop")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthgrade")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("10") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("10th", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthinsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthboard")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthgrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }
            pdfDocument.add(eduTable);
            /*Paragraph paraEducation = new Paragraph("Some Education", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraEducation);*/
        }
        if(myCursor.getString(myCursor.getColumnIndex("certtitle1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("certtitle1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("certtitle1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("CERTIFICATIONS", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);

            List CertList = new List(12);
            CertList.setListSymbol("\u2022");
            ListItem CertListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle1")) + ", " +
                    myCursor.getString(myCursor.getColumnIndex("certyear1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            CertList.add(CertListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)).length() > 0){
                    ListItem CertListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) + ", " +
                            myCursor.getString(myCursor.getColumnIndex("certyear"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    CertList.add(CertListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(CertList);
            /*Paragraph paraCertifications = new Paragraph("Java, C++, Spring MVC, HTML, CSS, Javascript, Oracle, MySql, PostgreSQL, Jasper Reports",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraCertifications);*/
        }
        if(myCursor.getString(myCursor.getColumnIndex("companyname1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("companyname1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("companyname1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("WORK EXPERIENCE", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);

            float[] columnWidthsWork = {5, 5};
            PdfPTable workTable = new PdfPTable(columnWidthsWork);
            workTable.setWidthPercentage(100);
            workTable.getDefaultCell().setUseAscender(true);
            workTable.getDefaultCell().setUseDescender(true);

            PdfPCell workHeader1 = new PdfPCell(new Phrase("Company", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workHeader1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            workTable.addCell(workHeader1);
            PdfPCell workHeader2 = new PdfPCell(new Phrase("Description", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workHeader2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            workTable.addCell(workHeader2);

            PdfPCell workCell1 = new PdfPCell();
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname1")),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)).length() > 0){

                    PdfPCell workCellCom = new PdfPCell();
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellCom.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellCom);

                    PdfPCell workCellDes = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellDes.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellDes);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(workTable);
            /*Paragraph paraWorkExp = new Paragraph("Some Work Experience", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraWorkExp);*/
        }
        if(myCursor.getString(myCursor.getColumnIndex("achievedesc1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("achievedesc1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("achievedesc1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("ACHIEVEMENTS", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);

            List AchList = new List(12);
            AchList.setListSymbol("\u2022");
            ListItem AchListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            AchList.add(AchListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)).length() > 0){
                    ListItem AchListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    AchList.add(AchListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(AchList);
            /*Paragraph paraAchievements = new Paragraph("Represented India in the Second International Open Junior Karate-Do Championship, held in Canada in June '02 and was awarded the Japanese Black Belt",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraAchievements);*/
        }
        if(myCursor.getString(myCursor.getColumnIndex("skillarea1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("skillarea1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("skillarea1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("TECHNICAL SKILLS", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);

            float[] columnWidthsSkills = {3, 5};
            PdfPTable skillsTable = new PdfPTable(columnWidthsSkills);
            skillsTable.setWidthPercentage(100);
            skillsTable.getDefaultCell().setUseAscender(true);
            skillsTable.getDefaultCell().setUseDescender(true);

            /*PdfPCell skillAreaHeader1 = new PdfPCell(new Phrase("Domain",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaHeader1);
            PdfPCell skillAreaHeader2 = new PdfPCell(new Phrase("Skill Set",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaHeader2);*/

            PdfPCell skillAreaCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillarea1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaCell1);

            PdfPCell skillSetCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillset1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            skillSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillSetCell1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("skillarea"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("skillarea"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("skillarea"+i)).length() > 0){

                    PdfPCell skillAreaCell = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillarea"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                    skillAreaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    skillsTable.addCell(skillAreaCell);

                    PdfPCell skillSetCell = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillset"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    skillSetCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    skillsTable.addCell(skillSetCell);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(skillsTable);
            /*Paragraph paraTSkills = new Paragraph("Java, C++, Spring MVC, HTML, CSS, Javascript, Oracle, MySql, PostgreSQL, Jasper Reports",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraTSkills);*/
        }
        if(myCursor.getString(myCursor.getColumnIndex("extracurr1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("extracurr1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("extracurr1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("EXTRA CURRICULAR ACTIVITIES", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);

            List ECList = new List(12);
            ECList.setListSymbol("\u2022");
            ListItem ECListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            ECList.add(ECListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)).length() > 0){
                    ListItem ECListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    ECList.add(ECListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(ECList);
            /*Paragraph paraActivities = new Paragraph("Attended 10 Annual Karate Training Camps of All India Gojukai Karate-Do (I.K.G.A), held in Pune and was awarded the Indian Black Belt",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraActivities);*/
        }
        if(myCursor.getString(myCursor.getColumnIndex("interestdesc")) != null &&
                myCursor.getString(myCursor.getColumnIndex("interestdesc")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("interestdesc")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cell = new PdfPCell(new Phrase("PERSONAL INTERESTS", font));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            pdfDocument.add(table);
            Paragraph paraInterests = new Paragraph(myCursor.getString(myCursor.getColumnIndex("interestdesc")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraInterests);
        }
        pdfDocument.close();
    }

    private void createHarvardTemplate(Document pdfDocument, Cursor myCursor) throws DocumentException{
        if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){
            Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                    myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            paraName.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraName);
        }
        if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
            Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraAddress.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraAddress);
        }
        if(myCursor.getString(myCursor.getColumnIndex("phoneno")) != null &&
                myCursor.getString(myCursor.getColumnIndex("phoneno")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("phoneno")).length()>0){
            Paragraph paraContact = new Paragraph(myCursor.getString(myCursor.getColumnIndex("phoneext")) + " " +
                    myCursor.getString(myCursor.getColumnIndex("phoneno")), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraContact.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraContact);
        }
        if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("email")).length()>0){
            Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraEmail.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraEmail);
        }

        if(myCursor.getString(myCursor.getColumnIndex("objective")) != null &&
                myCursor.getString(myCursor.getColumnIndex("objective")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("objective")).length()>0){
            pdfDocument.add(new Paragraph(" "));
            Paragraph paraObjective = new Paragraph(myCursor.getString(myCursor.getColumnIndex("objective")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL));
            pdfDocument.add(paraObjective);
        }

        float[] columnWidths = {3, 7};
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);

        if(myCursor.getString(myCursor.getColumnIndex("highestedu")) != null &&
                !myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Select") &&
                myCursor.getString(myCursor.getColumnIndex("highestedu")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable tableEdu = new PdfPTable(columnWidths);
            tableEdu.setWidthPercentage(100);
            tableEdu.getDefaultCell().setUseAscender(true);
            tableEdu.getDefaultCell().setUseDescender(true);

            PdfPCell headercellEdu = new PdfPCell(new Phrase("EDUCATION", headerFont));
            headercellEdu.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellEdu.setPaddingRight(8.0f);
            headercellEdu.setBorder(Rectangle.NO_BORDER);
            tableEdu.addCell(headercellEdu);

            float[] columnEduWidths = {2,5,3,2,2};
            PdfPTable eduTable = new PdfPTable(columnEduWidths);
            eduTable.setWidthPercentage(100);
            eduTable.getDefaultCell().setUseAscender(true);
            eduTable.getDefaultCell().setUseDescender(true);

            PdfPCell eduHeader1 = new PdfPCell(new Phrase("Degree", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader1);

            PdfPCell eduHeader2 = new PdfPCell(new Phrase("Institute", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader2);

            PdfPCell eduHeader3 = new PdfPCell(new Phrase("Board/University", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader3);

            PdfPCell eduHeader4 = new PdfPCell(new Phrase("Year", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader4.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader4);

            PdfPCell eduHeader5 = new PdfPCell(new Phrase("Grade", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader5.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader5);

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgdegree")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgcollege")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pginsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pggrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("graddegree")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradcollege")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradinsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradgrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                if(myCursor.getString(myCursor.getColumnIndex("diplomainsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("diplomainsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("diplomainsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("Diploma", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomainsti")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomaboard")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomayop")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomagrade")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }

                if(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthboard")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthyop")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthgrade")),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("10") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("10th", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthinsti")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthboard")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthyop")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthgrade")),
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            //PdfPCell contentcellEdu = new PdfPCell(new Phrase("Add your education details here.", contentFont));
            PdfPCell contentcellEdu = new PdfPCell(eduTable);
            contentcellEdu.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellEdu.setPaddingLeft(8.0f);
            contentcellEdu.setBorder(Rectangle.NO_BORDER);
            tableEdu.addCell(contentcellEdu);
            pdfDocument.add(tableEdu);
        }
        if(myCursor.getString(myCursor.getColumnIndex("certtitle1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("certtitle1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("certtitle1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);

            PdfPCell headercellExp = new PdfPCell(new Phrase("CERTIFICATIONS", headerFont));
            headercellExp.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellExp.setPaddingRight(8.0f);
            headercellExp.setBorder(Rectangle.NO_BORDER);
            table.addCell(headercellExp);

            List CertList = new List(12);
            CertList.setListSymbol("\u2022");
            ListItem CertListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle1")) + ", " +
                    myCursor.getString(myCursor.getColumnIndex("certyear1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            CertList.add(CertListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)).length() > 0){
                    ListItem CertListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) + ", " +
                            myCursor.getString(myCursor.getColumnIndex("certyear"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    CertList.add(CertListItem);
                }
                else{
                    break;
                }
            }

            //PdfPCell contentcellExp = new PdfPCell(new Phrase("Add your professional experience here.", contentFont));
            PdfPCell contentcellExp = new PdfPCell();
            contentcellExp.addElement(CertList);
            contentcellExp.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellExp.setPaddingLeft(8.0f);
            contentcellExp.setBorder(Rectangle.NO_BORDER);
            table.addCell(contentcellExp);
            pdfDocument.add(table);
        }
        if(myCursor.getString(myCursor.getColumnIndex("companyname1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("companyname1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("companyname1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);

            PdfPCell headercellExp = new PdfPCell(new Phrase("PROFESSIONAL EXPERIENCE", headerFont));
            headercellExp.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellExp.setPaddingRight(8.0f);
            headercellExp.setBorder(Rectangle.NO_BORDER);
            table.addCell(headercellExp);

            float[] columnWidthsWork = {5, 5};
            PdfPTable workTable = new PdfPTable(columnWidthsWork);
            workTable.setWidthPercentage(100);
            workTable.getDefaultCell().setUseAscender(true);
            workTable.getDefaultCell().setUseDescender(true);

            PdfPCell workHeader1 = new PdfPCell(new Phrase("Company", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader1);
            PdfPCell workHeader2 = new PdfPCell(new Phrase("Description", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader2);

            PdfPCell workCell1 = new PdfPCell();
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname1")),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)).length() > 0){

                    PdfPCell workCellCom = new PdfPCell();
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname1")),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellCom.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellCom);

                    PdfPCell workCellDes = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    workCellDes.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellDes);
                }
                else{
                    break;
                }
            }

            //PdfPCell contentcellExp = new PdfPCell(new Phrase("Add your professional experience here.", contentFont));
            PdfPCell contentcellExp = new PdfPCell(workTable);
            contentcellExp.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellExp.setPaddingLeft(8.0f);
            contentcellExp.setBorder(Rectangle.NO_BORDER);
            table.addCell(contentcellExp);
            pdfDocument.add(table);
        }

        if(myCursor.getString(myCursor.getColumnIndex("achievedesc1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("achievedesc1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("achievedesc1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable tableAchieve = new PdfPTable(columnWidths);
            tableAchieve.setWidthPercentage(100);
            tableAchieve.getDefaultCell().setUseAscender(true);
            tableAchieve.getDefaultCell().setUseDescender(true);

            PdfPCell headercellAchieve = new PdfPCell(new Phrase("ACHIEVEMENTS", headerFont));
            headercellAchieve.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellAchieve.setPaddingRight(8.0f);
            headercellAchieve.setBorder(Rectangle.NO_BORDER);
            tableAchieve.addCell(headercellAchieve);

            List AchList = new List(12);
            AchList.setListSymbol("\u2022");
            ListItem AchListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            AchList.add(AchListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)).length() > 0){
                    ListItem AchListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    AchList.add(AchListItem);
                }
                else{
                    break;
                }
            }

            //PdfPCell contentcellAchieve = new PdfPCell(new Phrase("Represented India in the Second International Open Junior Karate-Do Championship, held in Canada in June '02 and was awarded the Japanese Black Belt", contentFont));
            PdfPCell contentcellAchieve = new PdfPCell();
            contentcellAchieve.addElement(AchList);
            contentcellAchieve.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellAchieve.setPaddingLeft(8.0f);
            contentcellAchieve.setBorder(Rectangle.NO_BORDER);
            tableAchieve.addCell(contentcellAchieve);
            pdfDocument.add(tableAchieve);
        }

        if(myCursor.getString(myCursor.getColumnIndex("skillarea1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("skillarea1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("skillarea1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable tableSkill = new PdfPTable(columnWidths);
            tableSkill.setWidthPercentage(100);
            tableSkill.getDefaultCell().setUseAscender(true);
            tableSkill.getDefaultCell().setUseDescender(true);

            PdfPCell headercellSkill = new PdfPCell(new Phrase("TECHNICAL SKILLS", headerFont));
            headercellSkill.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellSkill.setPaddingRight(8.0f);
            headercellSkill.setBorder(Rectangle.NO_BORDER);
            tableSkill.addCell(headercellSkill);

            float[] columnWidthsSkills = {3, 5};
            PdfPTable skillsTable = new PdfPTable(columnWidthsSkills);
            skillsTable.setWidthPercentage(100);
            skillsTable.getDefaultCell().setUseAscender(true);
            skillsTable.getDefaultCell().setUseDescender(true);

           /* PdfPCell skillAreaHeader1 = new PdfPCell(new Phrase("Domain",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaHeader1);
            PdfPCell skillAreaHeader2 = new PdfPCell(new Phrase("Skill Set",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaHeader2);*/

            PdfPCell skillAreaCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillarea1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaCell1);

            PdfPCell skillSetCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillset1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            skillSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillSetCell1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("skillarea"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("skillarea"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("skillarea"+i)).length() > 0){

                    PdfPCell skillAreaCell = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillarea"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                    skillAreaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    skillsTable.addCell(skillAreaCell);

                    PdfPCell skillSetCell = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("skillset"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                    skillSetCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    skillsTable.addCell(skillSetCell);
                }
                else{
                    break;
                }
            }

            //PdfPCell contentcellSkill = new PdfPCell(new Phrase("Java, C++, Spring MVC, HTML, CSS, Javascript, Oracle, MySql, PostgreSQL, Jasper Reports", contentFont));
            PdfPCell contentcellSkill = new PdfPCell(skillsTable);
            contentcellSkill.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellSkill.setPaddingLeft(8.0f);
            contentcellSkill.setBorder(Rectangle.NO_BORDER);
            tableSkill.addCell(contentcellSkill);
            pdfDocument.add(tableSkill);
        }

        if(myCursor.getString(myCursor.getColumnIndex("extracurr1")) != null &&
                myCursor.getString(myCursor.getColumnIndex("extracurr1")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("extracurr1")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable tableAct = new PdfPTable(columnWidths);
            tableAct.setWidthPercentage(100);
            tableAct.getDefaultCell().setUseAscender(true);
            tableAct.getDefaultCell().setUseDescender(true);

            PdfPCell headercellAct = new PdfPCell(new Phrase("EXTRA CURRICULAR ACTIVITIES", headerFont));
            headercellAct.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellAct.setPaddingRight(8.0f);
            headercellAct.setBorder(Rectangle.NO_BORDER);
            tableAct.addCell(headercellAct);

            List ECList = new List(12);
            ECList.setListSymbol("\u2022");
            ListItem ECListItem1 = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr1")),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            ECList.add(ECListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)).length() > 0){
                    ListItem ECListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)),
                            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
                    ECList.add(ECListItem);
                }
                else{
                    break;
                }
            }

            //PdfPCell contentcellAct = new PdfPCell(new Phrase("Attended 10 Annual Karate Training Camps of All India Gojukai Karate-Do (I.K.G.A), held in Pune and was awarded the Indian Black Belt", contentFont));
            PdfPCell contentcellAct = new PdfPCell(new Phrase("Attended 10 Annual Karate Training Camps of All India Gojukai Karate-Do (I.K.G.A), held in Pune and was awarded the Indian Black Belt", contentFont));
            contentcellAct.addElement(ECList);
            contentcellAct.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellAct.setPaddingLeft(8.0f);
            contentcellAct.setBorder(Rectangle.NO_BORDER);
            tableAct.addCell(contentcellAct);
            pdfDocument.add(tableAct);
        }

        if(myCursor.getString(myCursor.getColumnIndex("interestdesc")) != null &&
                myCursor.getString(myCursor.getColumnIndex("interestdesc")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("interestdesc")).length() > 0){
            pdfDocument.add(new Paragraph(" "));
            PdfPTable tablePI = new PdfPTable(columnWidths);
            tablePI.setWidthPercentage(100);
            tablePI.getDefaultCell().setUseAscender(true);
            tablePI.getDefaultCell().setUseDescender(true);

            PdfPCell headercellPI = new PdfPCell(new Phrase("PERSONAL INTERESTS", headerFont));
            headercellPI.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellPI.setPaddingRight(8.0f);
            headercellPI.setBorder(Rectangle.NO_BORDER);
            tablePI.addCell(headercellPI);

            PdfPCell contentcellPI = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("interestdesc")), contentFont));
            contentcellPI.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellPI.setPaddingLeft(8.0f);
            contentcellPI.setBorder(Rectangle.NO_BORDER);
            tablePI.addCell(contentcellPI);
            pdfDocument.add(tablePI);
        }
        pdfDocument.close();
    }

    /*private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
    }*/




}

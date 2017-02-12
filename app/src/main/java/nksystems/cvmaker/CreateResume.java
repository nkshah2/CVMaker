package nksystems.cvmaker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
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
import nksystems.cvmaker.createresume.UploadProfilePhoto;

public class CreateResume extends AppCompatActivity {

    Menu menuOptions;
    int backCount=0,totalSections=9;
    Font notoFont= FontFactory.getFont("assets/fonts/NotoSans-Regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
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

    ViewPager pagerRoot;
    PersonalDetails personalDetails;
    EducationDetails educationDetails;
    CertificationDetails certificationDetails;
    ExperienceDetails experienceDetails;
    AchievementDetails achievementDetails;
    SkillDetails skillDetails;
    ExtraCurricularDetails extraCurricularDetails;
    PersonalInterestDetails personalInterestDetails;
    UploadProfilePhoto uploadProfilePhoto;
    private android.support.v7.app.ActionBar actionBar;
    DatabaseObject dbObject;
    SQLiteDatabase theme;
    ImageView profilePhoto;

    @Override
    public void onBackPressed() {
        if(backCount==0){
            backCount++;
            Toast.makeText(this,CreateResume.this.getResources().getString(R.string.resumeBackWarning),Toast.LENGTH_LONG).show();
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

    String currentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbObject=new DatabaseObject(this);
        theme=dbObject.getConnection();
        Cursor cursor=theme.rawQuery("select * from database_theme",null);
        cursor.moveToFirst();
        currentTheme= cursor.getString(cursor.getColumnIndex("current_theme"));

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

            private String[] resumeTabs=CreateResume.this.getResources().getStringArray(R.array.resumePages);

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
        uploadProfilePhoto = new UploadProfilePhoto();

        fragmentTransaction.commit();



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
                        profilePhoto=(ImageView)findViewById(R.id.imgPreview);

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
                                Toast.makeText(CreateResume.this, CreateResume.this.getResources().getString(R.string.resumeEmailWarning), Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(CreateResume.this,CreateResume.this.getResources().getString(R.string.resumePersonalWarning),Toast.LENGTH_LONG).show();
                        }

            }
        });

        final Button myButton=new Button(this);
        myButton.setBackgroundColor(Color.TRANSPARENT);
        myButton.setClickable(false);
        myButton.setText("");
        myButton.setEnabled(false);
        myButton.setVisibility(View.GONE);

        Display display= getWindowManager().getDefaultDisplay();
        Point point= new Point();
        display.getSize(point);

        Cursor tutorialCheck=theme.rawQuery("select * from tutorial",null);
        tutorialCheck.moveToFirst();
        String isFormNeeded=tutorialCheck.getString(tutorialCheck.getColumnIndex("form"));


        if(isFormNeeded.equalsIgnoreCase("yes")) {

            theme.execSQL("update tutorial set form='no'");


        }
    }

    public String getEditDetails(DatabaseQueries dbQueries, Intent intent){
        String filename=intent.getStringExtra("filename");
        Cursor personalCursor= dbQueries.selectPersonalInformation(filename);
        personalCursor.moveToFirst();
        String fileid=personalCursor.getString(personalCursor.getColumnIndex("fileid"));
        return filename+"~"+fileid;
    }



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
        String profilePhotoName = String.valueOf(profilePhoto.getTag());
        if(((BitmapDrawable)profilePhoto.getDrawable()).getBitmap()==null){
            profilePhotoName="";
        }

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
        dbQueries.addProfilePhoto(filename,profilePhotoName);
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
        // If image is added
        if(myCursor.getString(myCursor.getColumnIndex("imagename")) != null &&
                myCursor.getString(myCursor.getColumnIndex("imagename")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("imagename")).length()>0){
            // If firstname or lastname or email or address is added
            if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("email")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("address")).length()>0)
                    ){
                // Then create a table with two columns. One for personal details, second for image
                float[] columnWidths = {6,4};
                PdfPTable titleTable = new PdfPTable(columnWidths);
                titleTable.setWidthPercentage(100);
                titleTable.getDefaultCell().setUseAscender(true);
                titleTable.getDefaultCell().setUseDescender(true);

                PdfPCell titleHeader1 = new PdfPCell();
                titleHeader1.setBorder(Rectangle.NO_BORDER);
                titleHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
                if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                        (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                                myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                                myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){

                    Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                            myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                            notoFont);
                    //notoFont
                    paraName.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraName);
                }
                if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("email")).length()>0){
                    Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")) + " | " +
                            myCursor.getString(myCursor.getColumnIndex("phoneext"))+" "+myCursor.getString(myCursor.getColumnIndex("phoneno")),
                            notoFont);
                    paraEmail.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraEmail);
                }
                if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
                    Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                            notoFont);
                    paraAddress.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraAddress);
                }
                titleTable.addCell(titleHeader1);


                try{
                    Image profile = Image.getInstance(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+
                            myCursor.getString(myCursor.getColumnIndex("imagename")));

                    profile.scaleToFit(130,130);
                    profile.setBorder(Rectangle.BOX);
                    profile.setBorderWidth(10);
                    profile.setAlignment(Element.ALIGN_CENTER);

                    PdfPCell titleHeader2 = new PdfPCell(profile);
                    titleHeader2.setBorder(Rectangle.NO_BORDER);
                    titleHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleTable.addCell(titleHeader2);
                }
                catch(Exception e){

                }

                pdfDocument.add(titleTable);
            }
            else{
                // If only image has been added
                try{
                    Image profile = Image.getInstance(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+
                            myCursor.getString(myCursor.getColumnIndex("imagename")));
                    profile.scaleToFit(130,130);
                    profile.setAlignment(Element.ALIGN_CENTER);
                    profile.setBorder(Rectangle.BOX);
                    profile.setBorderWidth(10);
                    pdfDocument.add(profile);
                }
                catch(Exception e){

                }
            }
        }
        else{
            // If image not added
            if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){

                Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                        myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                        notoFont);
                //notoFont
                paraName.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraName);
            }
            if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("email")).length()>0){
                Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")) + " | " +
                        myCursor.getString(myCursor.getColumnIndex("phoneext"))+" "+myCursor.getString(myCursor.getColumnIndex("phoneno")),
                        notoFont);
                paraEmail.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraEmail);
            }
            if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
                Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                        notoFont);
                paraAddress.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraAddress);
            }
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
                    notoFont);
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
                        notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgcollege")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pginsti")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pggrade")),
                        notoFont));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("graddegree")),
                        notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradcollege")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradinsti")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradgrade")),
                        notoFont));
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
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("Diploma", notoFont));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomainsti")),
                            notoFont));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomaboard")),
                            notoFont));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomayop")),
                            notoFont));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomagrade")),
                            notoFont));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }

                if(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th", notoFont));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")),
                            notoFont));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthboard")),
                            notoFont));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthyop")),
                            notoFont));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthgrade")),
                            notoFont));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("10") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("10th", notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthinsti")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthboard")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthgrade")),
                        notoFont));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }
            pdfDocument.add(eduTable);
            /*Paragraph paraEducation = new Paragraph("Some Education", notoFont);
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
                     notoFont);
             CertList.add(CertListItem1);

             for(int i=2;i<7;i++){
                 if(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != null &&
                         myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != "" &&
                         myCursor.getString(myCursor.getColumnIndex("certtitle"+i)).length() > 0){
                     ListItem CertListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) + ", " +
                             myCursor.getString(myCursor.getColumnIndex("certyear"+i)),
                             notoFont);
                     CertList.add(CertListItem);
                 }
                 else{
                     break;
                 }
             }
             pdfDocument.add(CertList);
            /*Paragraph paraCertifications = new Paragraph("Java, C++, Spring MVC, HTML, CSS, Javascript, Oracle, MySql, PostgreSQL, Jasper Reports",
                    notoFont);
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
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),notoFont));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),notoFont));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc1")),
                    notoFont));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)).length() > 0){

                    PdfPCell workCellCom = new PdfPCell();
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role"+i)),notoFont));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration"+i)),notoFont));
                    workCellCom.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellCom);

                    PdfPCell workCellDes = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc"+i)),
                            notoFont));
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
                    notoFont);
            AchList.add(AchListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)).length() > 0){
                    ListItem AchListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)),
                            notoFont);
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
                    notoFont));
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
                            notoFont));
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
                    notoFont);
            ECList.add(ECListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)).length() > 0){
                    ListItem ECListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)),
                            notoFont);
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
                    notoFont);
            pdfDocument.add(paraInterests);
        }
        pdfDocument.close();
    }

    private void createNMIMSTemplate(Document pdfDocument, Cursor myCursor) throws DocumentException{
        float[] columnWidths = {1, 5, 5};
        // If image is added
        if(myCursor.getString(myCursor.getColumnIndex("imagename")) != null &&
                myCursor.getString(myCursor.getColumnIndex("imagename")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("imagename")).length()>0){
            // If firstname or lastname or email or address is added
            if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("email")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("address")).length()>0)
                    ){
                // Then create a table with two columns. One for personal details, second for image
                float[] columnWidthsTitle = {6,4};
                PdfPTable titleTable = new PdfPTable(columnWidthsTitle);
                titleTable.setWidthPercentage(100);
                titleTable.getDefaultCell().setUseAscender(true);
                titleTable.getDefaultCell().setUseDescender(true);

                PdfPCell titleHeader1 = new PdfPCell();
                titleHeader1.setBorder(Rectangle.NO_BORDER);
                titleHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
                if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                        (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                                myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                                myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){

                    Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                            myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                            notoFont);
                    //notoFont
                    paraName.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraName);
                }
                if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("email")).length()>0){
                    Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")) + " | " +
                            myCursor.getString(myCursor.getColumnIndex("phoneext"))+" "+myCursor.getString(myCursor.getColumnIndex("phoneno")),
                            notoFont);
                    paraEmail.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraEmail);
                }
                if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
                    Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                            notoFont);
                    paraAddress.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraAddress);
                }
                titleTable.addCell(titleHeader1);


                try{
                    Image profile = Image.getInstance(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+
                            myCursor.getString(myCursor.getColumnIndex("imagename")));

                    profile.scaleToFit(130,130);
                    profile.setBorder(Rectangle.BOX);
                    profile.setBorderWidth(10);
                    profile.setAlignment(Element.ALIGN_CENTER);

                    PdfPCell titleHeader2 = new PdfPCell(profile);
                    titleHeader2.setBorder(Rectangle.NO_BORDER);
                    titleHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleTable.addCell(titleHeader2);
                }
                catch(Exception e){

                }

                pdfDocument.add(titleTable);
            }
            else{
                // If only image has been added
                try{
                    Image profile = Image.getInstance(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+
                            myCursor.getString(myCursor.getColumnIndex("imagename")));
                    profile.scaleToFit(130,130);
                    profile.setAlignment(Element.ALIGN_CENTER);
                    profile.setBorder(Rectangle.BOX);
                    profile.setBorderWidth(10);
                    pdfDocument.add(profile);
                }
                catch(Exception e){

                }
            }
        }
        else{
            // If image not added
            if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){

                Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                        myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                        notoFont);
                //notoFont
                paraName.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraName);
            }
            if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("email")).length()>0){

                Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")) + " | " +
                        myCursor.getString(myCursor.getColumnIndex("phoneext"))+" "+myCursor.getString(myCursor.getColumnIndex("phoneno")),
                        notoFont);
                paraEmail.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraEmail);
            }
            if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
                Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                        notoFont);
                paraAddress.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraAddress);
            }
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
                    notoFont);
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
                        notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgcollege")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pginsti")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pggrade")),
                        notoFont));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("graddegree")),
                        notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradcollege")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradinsti")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradgrade")),
                        notoFont));
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
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("Diploma", notoFont));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomainsti")),
                            notoFont));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomaboard")),
                            notoFont));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomayop")),
                            notoFont));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomagrade")),
                            notoFont));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }

                if(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th", notoFont));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")),
                            notoFont));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthboard")),
                            notoFont));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthyop")),
                            notoFont));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthgrade")),
                            notoFont));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("10") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("10th", notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthinsti")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthboard")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthgrade")),
                        notoFont));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }
            pdfDocument.add(eduTable);
            /*Paragraph paraEducation = new Paragraph("Some Education", notoFont);
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
                    notoFont);
            CertList.add(CertListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)).length() > 0){
                    ListItem CertListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) + ", " +
                            myCursor.getString(myCursor.getColumnIndex("certyear"+i)),
                            notoFont);
                    CertList.add(CertListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(CertList);
            /*Paragraph paraCertifications = new Paragraph("Java, C++, Spring MVC, HTML, CSS, Javascript, Oracle, MySql, PostgreSQL, Jasper Reports",
                    notoFont);
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
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),notoFont));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),notoFont));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc1")),
                    notoFont));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)).length() > 0){

                    PdfPCell workCellCom = new PdfPCell();
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname"+i)),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role"+i)),notoFont));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration"+i)),notoFont));
                    workCellCom.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellCom);

                    PdfPCell workCellDes = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc"+i)),
                            notoFont));
                    workCellDes.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellDes);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(workTable);
            /*Paragraph paraWorkExp = new Paragraph("Some Work Experience", notoFont);
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
                    notoFont);
            AchList.add(AchListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)).length() > 0){
                    ListItem AchListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)),
                            notoFont);
                    AchList.add(AchListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(AchList);
            /*Paragraph paraAchievements = new Paragraph("Represented India in the Second International Open Junior Karate-Do Championship, held in Canada in June '02 and was awarded the Japanese Black Belt",
                    notoFont);
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
                    notoFont));
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
                            notoFont));
                    skillSetCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    skillsTable.addCell(skillSetCell);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(skillsTable);
            /*Paragraph paraTSkills = new Paragraph("Java, C++, Spring MVC, HTML, CSS, Javascript, Oracle, MySql, PostgreSQL, Jasper Reports",
                    notoFont);
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
                    notoFont);
            ECList.add(ECListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)).length() > 0){
                    ListItem ECListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)),
                            notoFont);
                    ECList.add(ECListItem);
                }
                else{
                    break;
                }
            }
            pdfDocument.add(ECList);
            /*Paragraph paraActivities = new Paragraph("Attended 10 Annual Karate Training Camps of All India Gojukai Karate-Do (I.K.G.A), held in Pune and was awarded the Indian Black Belt",
                    notoFont);
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
                    notoFont);
            pdfDocument.add(paraInterests);
        }
        pdfDocument.close();
    }

    private void createHarvardTemplate(Document pdfDocument, Cursor myCursor) throws DocumentException{
        // If image is added
        if(myCursor.getString(myCursor.getColumnIndex("imagename")) != null &&
                myCursor.getString(myCursor.getColumnIndex("imagename")) != "" &&
                myCursor.getString(myCursor.getColumnIndex("imagename")).length()>0){
            // If firstname or lastname or email or address or phoneno is added
            if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("email")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("address")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("phoneno")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("phoneno")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("phoneno")).length()>0)
                    ){
                // Then create a table with two columns. One for personal details, second for image
                float[] columnWidthsTitle = {6,4};
                PdfPTable titleTable = new PdfPTable(columnWidthsTitle);
                titleTable.setWidthPercentage(100);
                titleTable.getDefaultCell().setUseAscender(true);
                titleTable.getDefaultCell().setUseDescender(true);

                PdfPCell titleHeader1 = new PdfPCell();
                titleHeader1.setBorder(Rectangle.NO_BORDER);
                titleHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
                if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                        (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                                myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                                myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){

                    Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                            myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                            notoFont);
                    //notoFont
                    paraName.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraName);
                }
                if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
                    Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                            notoFont);
                    paraAddress.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraAddress);
                }
                if(myCursor.getString(myCursor.getColumnIndex("phoneno")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("phoneno")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("phoneno")).length()>0){
                    Paragraph paraContact = new Paragraph(myCursor.getString(myCursor.getColumnIndex("phoneext")) + " " +
                            myCursor.getString(myCursor.getColumnIndex("phoneno")), notoFont);
                    paraContact.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraContact);
                }
                if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("email")).length()>0){
                    Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")),
                            notoFont);
                    paraEmail.setAlignment(Element.ALIGN_LEFT);
                    titleHeader1.addElement(paraEmail);
                }
                titleTable.addCell(titleHeader1);


                try{
                    Image profile = Image.getInstance(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+
                            myCursor.getString(myCursor.getColumnIndex("imagename")));

                    profile.scaleToFit(130,130);
                    profile.setBorder(Rectangle.BOX);
                    profile.setBorderWidth(10);
                    profile.setAlignment(Element.ALIGN_CENTER);

                    PdfPCell titleHeader2 = new PdfPCell(profile);
                    titleHeader2.setBorder(Rectangle.NO_BORDER);
                    titleHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleTable.addCell(titleHeader2);
                }
                catch(Exception e){

                }

                pdfDocument.add(titleTable);
            }
            else{
                // If only image has been added
                try{
                    Image profile = Image.getInstance(Environment.getExternalStorageDirectory()+File.separator+"CVMaker"+File.separator+
                            myCursor.getString(myCursor.getColumnIndex("imagename")));
                    profile.scaleToFit(130,130);
                    profile.setAlignment(Element.ALIGN_CENTER);
                    profile.setBorder(Rectangle.BOX);
                    profile.setBorderWidth(10);
                    pdfDocument.add(profile);
                }
                catch(Exception e){

                }
            }
        }
        else{
            // If image not added
            if((myCursor.getString(myCursor.getColumnIndex("firstname")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("firstname")).length()>0) ||
                    (myCursor.getString(myCursor.getColumnIndex("lastname")) != null &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")) != "" &&
                            myCursor.getString(myCursor.getColumnIndex("lastname")).length()>0)){

                Paragraph paraName = new Paragraph(myCursor.getString(myCursor.getColumnIndex("title"))+" "+
                        myCursor.getString(myCursor.getColumnIndex("firstname"))+" "+myCursor.getString(myCursor.getColumnIndex("lastname")),
                        notoFont);
                //notoFont
                paraName.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraName);
            }
            if(myCursor.getString(myCursor.getColumnIndex("address")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("address")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("address")).length()>0){
                Paragraph paraAddress = new Paragraph(myCursor.getString(myCursor.getColumnIndex("address")),
                        notoFont);
                paraAddress.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraAddress);
            }
            if(myCursor.getString(myCursor.getColumnIndex("phoneno")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("phoneno")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("phoneno")).length()>0){
                Paragraph paraContact = new Paragraph(myCursor.getString(myCursor.getColumnIndex("phoneext")) + " " +
                        myCursor.getString(myCursor.getColumnIndex("phoneno")), notoFont);
                paraContact.setAlignment(Element.ALIGN_LEFT);
                pdfDocument.add(paraContact);
            }
            if(myCursor.getString(myCursor.getColumnIndex("email")) != null &&
                    myCursor.getString(myCursor.getColumnIndex("email")) != "" &&
                    myCursor.getString(myCursor.getColumnIndex("email")).length()>0){

                Paragraph paraEmail = new Paragraph(myCursor.getString(myCursor.getColumnIndex("email")),
                        notoFont);
                paraEmail.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(paraEmail);
            }
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
                        notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgcollege")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pginsti")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pgyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("pggrade")),
                        notoFont));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("graddegree")),
                        notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradcollege")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradinsti")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("gradgrade")),
                        notoFont));
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
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("Diploma", notoFont));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomainsti")),
                            notoFont));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomaboard")),
                            notoFont));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomayop")),
                            notoFont));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("diplomagrade")),
                            notoFont));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }

                if(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != null &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("twelfthinsti")).length() > 0){
                    PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th", notoFont));
                    eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell1);

                    PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthinsti")),
                            notoFont));
                    eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell2);

                    PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthboard")),
                            notoFont));
                    eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell3);

                    PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthyop")),
                            notoFont));
                    eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell4);

                    PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("twelfthgrade")),
                            notoFont));
                    eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    eduTable.addCell(eduTenthCell5);
                }
            }

            if(myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("10") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("12") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Diploma") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Under Grad") ||
                    myCursor.getString(myCursor.getColumnIndex("highestedu")).contains("Post Grad")){

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("10th", notoFont));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthinsti")),
                        notoFont));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthboard")),
                        notoFont));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthyop")),
                        notoFont));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("tenthgrade")),
                        notoFont));
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
                    notoFont);
            CertList.add(CertListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("certtitle"+i)).length() > 0){
                    ListItem CertListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("certtitle"+i)) + ", " +
                            myCursor.getString(myCursor.getColumnIndex("certyear"+i)),
                            notoFont);
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
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),notoFont));
            workCell1.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),notoFont));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc1")),
                    notoFont));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("companyname"+i)).length() > 0){

                    PdfPCell workCellCom = new PdfPCell();
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("companyname1")),new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("role1")),notoFont));
                    workCellCom.addElement(new Phrase(myCursor.getString(myCursor.getColumnIndex("duration1")),notoFont));
                    workCellCom.setHorizontalAlignment(Element.ALIGN_LEFT);
                    workTable.addCell(workCellCom);

                    PdfPCell workCellDes = new PdfPCell(new Phrase(myCursor.getString(myCursor.getColumnIndex("projectdesc"+i)),
                            notoFont));
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
                    notoFont);
            AchList.add(AchListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)).length() > 0){
                    ListItem AchListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("achievedesc"+i)),
                            notoFont);
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
                    notoFont));
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
                            notoFont));
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
                    notoFont);
            ECList.add(ECListItem1);

            for(int i=2;i<7;i++){
                if(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != null &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)) != "" &&
                        myCursor.getString(myCursor.getColumnIndex("extracurr"+i)).length() > 0){
                    ListItem ECListItem = new ListItem(myCursor.getString(myCursor.getColumnIndex("extracurr"+i)),
                            notoFont);
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

package nksystems.cvmaker.createresume;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import nksystems.cvmaker.CreateResume;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.R;

/**
 * Created by Charmy on 15/09/2016.
 */
public class EducationDetails extends Fragment {

    LinearLayout ten,twelve,college,diploma,graduate;
    Spinner mySpinner,spinnerHighestEdu;
    DatabaseQueries dbQueries;
    View rootView;
    EditText etTenthCGPA,etTenthSchool,etTenthBoard,etTenthYOP,etTwelfthCGPA,etTwelfthInsti,etTwelfthBoard,etTwelfthYOP,etDipCGPA,
            etDipInsti,etDipBoard,etDipYOP,etCollegeCGPA,etCollegeDegree,etCollegeBoard,etCollegeYOP,etCollegeCOllege,etPgCollege,
            etPgBoard,etPgCGPA,etPgDegree,etPgYOP;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.resume_education_details_activity, container, false);

        ten=(LinearLayout)rootView.findViewById(R.id.ll10th) ;
        twelve=(LinearLayout)rootView.findViewById(R.id.ll12th) ;
        diploma=(LinearLayout)rootView.findViewById(R.id.llDiploma) ;
        college=(LinearLayout)rootView.findViewById(R.id.llCollege) ;
        graduate=(LinearLayout)rootView.findViewById(R.id.llPostGrad) ;
        mySpinner=(Spinner)rootView.findViewById(R.id.spinnerEducation);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dbQueries=new DatabaseQueries(EducationDetails.this.getContext());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateResume resume = new CreateResume();
        Boolean isEdit = getActivity().getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            String ctNameId = resume.getEditDetails(dbQueries,getActivity().getIntent());
            String fileid = ctNameId.split("~")[1];
            Cursor educationCursor = dbQueries.selectEducationInformation(fileid);
            educationCursor.moveToFirst();

            String highestEdu,tenthCGPA,tenthSchool,tenthBoard,tenthYOP,twelfthCGPA,twelfthInstitute,twelfthBoard,twelfthYOP,dipCGPA,
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
            pgDegree=educationCursor.getString(educationCursor.getColumnIndex("pgdegree"));

            ten=(LinearLayout)rootView.findViewById(R.id.ll10th) ;
            twelve=(LinearLayout)rootView.findViewById(R.id.ll12th) ;
            diploma=(LinearLayout)rootView.findViewById(R.id.llDiploma) ;
            college=(LinearLayout)rootView.findViewById(R.id.llCollege) ;
            graduate=(LinearLayout)rootView.findViewById(R.id.llPostGrad) ;
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
            }

            spinnerHighestEdu=(Spinner)rootView.findViewById(R.id.spinnerEducation);
            etTenthCGPA=(EditText)rootView.findViewById(R.id.etTenthGrade);
            etTenthSchool=(EditText)rootView.findViewById(R.id.etTenthSchool);
            etTenthBoard=(EditText)rootView.findViewById(R.id.etTenthBoard);
            etTenthYOP=(EditText)rootView.findViewById(R.id.etTenthYOP);
            etTwelfthCGPA=(EditText)rootView.findViewById(R.id.etTwelfthCGPA);
            etTwelfthInsti=(EditText)rootView.findViewById(R.id.etTwelfthInstitute);
            etTwelfthBoard=(EditText)rootView.findViewById(R.id.etTwelfthBoard);
            etTwelfthYOP=(EditText)rootView.findViewById(R.id.etTwelfthYOP);
            etDipCGPA=(EditText)rootView.findViewById(R.id.etDipCGPA);
            etDipInsti=(EditText)rootView.findViewById(R.id.etDipInsti);
            etDipBoard=(EditText)rootView.findViewById(R.id.etDipBoard);
            etDipYOP=(EditText)rootView.findViewById(R.id.etDipYOP);
            etCollegeCGPA=(EditText)rootView.findViewById(R.id.etCollegeCGPA);
            etCollegeDegree=(EditText)rootView.findViewById(R.id.etCollegeDegree);
            etCollegeBoard=(EditText)rootView.findViewById(R.id.etCollegeBoard);
            etCollegeYOP=(EditText)rootView.findViewById(R.id.etCollegeYOP);
            etCollegeCOllege=(EditText)rootView.findViewById(R.id.etCollegeCollege);
            etPgCollege=(EditText)rootView.findViewById(R.id.etPgCollege);
            etPgBoard=(EditText)rootView.findViewById(R.id.etPgBoard);
            etPgCGPA=(EditText)rootView.findViewById(R.id.etPgCGPA);
            etPgDegree=(EditText)rootView.findViewById(R.id.etPgDegree);
            etPgYOP=(EditText)rootView.findViewById(R.id.etPgYOP);

            spinnerHighestEdu.setSelection(eduPosition);
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
            etPgYOP.setText(pgYOP);

            switch (spinnerHighestEdu.getSelectedItemPosition()){
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
            }
        }
    }
}

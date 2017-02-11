package nksystems.cvmaker.createresume;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import nksystems.cvmaker.CreateResume;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.R;

/**
 * Created by Charmy on 15/09/2016.
 */
public class PersonalDetails extends Fragment {

    EditText date,etFileName,etObjective,etFName,etLName,etDOB,etEmail,etCode,etPhone,etAdress;
    Spinner spinnerTemplate,spinnerTitle;

    Calendar myCalendar;
    View rootView;
    DatabaseQueries dbQueries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.resume_personal_details_activity, container, false);



        date=(EditText)rootView.findViewById(R.id.etDOB);
        myCalendar= Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSelected =new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PersonalDetails.this.getContext(), dateSelected, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dbQueries=new DatabaseQueries(PersonalDetails.this.getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateResume resume = new CreateResume();

        Boolean isEdit = getActivity().getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            String ctNameId = resume.getEditDetails(dbQueries, getActivity().getIntent());
            String filename = ctNameId.split("~")[0];
            Cursor personalCursor= dbQueries.selectPersonalInformation(filename);
            personalCursor.moveToFirst();

            etFileName=(EditText)rootView.findViewById(R.id.etFileName);
            spinnerTemplate=(Spinner)rootView.findViewById(R.id.spinnerTemplate);
            etObjective=(EditText)rootView.findViewById(R.id.etObjective);
            spinnerTitle=(Spinner)rootView.findViewById(R.id.spinnerTitle);
            etFName=(EditText)rootView.findViewById(R.id.etFirstName);
            etLName=(EditText)rootView.findViewById(R.id.etLastName);
            etDOB=(EditText)rootView.findViewById(R.id.etDOB);
            etEmail=(EditText)rootView.findViewById(R.id.etEmail);
            etCode=(EditText)rootView.findViewById(R.id.etCode);
            etPhone=(EditText)rootView.findViewById(R.id.etPhone);
            etAdress=(EditText)rootView.findViewById(R.id.etAdress);

            String title=personalCursor.getString(personalCursor.getColumnIndex("title"));
            String firstname=personalCursor.getString(personalCursor.getColumnIndex("firstname"));
            String lastname=personalCursor.getString(personalCursor.getColumnIndex("lastname"));
            String dateofbirth=personalCursor.getString(personalCursor.getColumnIndex("dateofbirth"));
            String email=personalCursor.getString(personalCursor.getColumnIndex("email"));
            String phoneext=personalCursor.getString(personalCursor.getColumnIndex("phoneext"));
            String phoneno=personalCursor.getString(personalCursor.getColumnIndex("phoneno"));
            String adress=personalCursor.getString(personalCursor.getColumnIndex("address"));
            String objective=personalCursor.getString(personalCursor.getColumnIndex("objective"));
            String template=personalCursor.getString(personalCursor.getColumnIndex("template"));

            etFileName.setText(filename);

            int titlePosition=0;
            int templatePosition=0;
            if(title.equals("Mr.")||title.equals("M.")){
                titlePosition=0;
            }else if(title.equals("Ms.")||title.equals("Mlle.")){
                titlePosition=1;
            }else if(title.equals("Mrs.")||title.equals("Mme.")){
                titlePosition=2;
            }else if(title.equals("Dr.")){
                titlePosition=3;
            }

            if(template.equals("Default")){
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
            etAdress.setText(adress);
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
    }
}

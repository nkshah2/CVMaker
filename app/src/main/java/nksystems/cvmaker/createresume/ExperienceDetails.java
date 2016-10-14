package nksystems.cvmaker.createresume;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import nksystems.cvmaker.CreateResume;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.R;

/**
 * Created by Charmy on 15/09/2016.
 */
public class ExperienceDetails extends Fragment {

    EditText etCoName1, etDuration1, etRole1, etProDesc1;
    EditText etCoName2, etDuration2, etRole2, etProDesc2;
    EditText etCoName3, etDuration3, etRole3, etProDesc3;
    EditText etCoName4, etDuration4, etRole4, etProDesc4;
    EditText etCoName5, etDuration5, etRole5, etProDesc5;
    EditText etCoName6, etDuration6, etRole6, etProDesc6;
    View rootView;
    DatabaseQueries dbQueries;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.resume_experience_details_activity, container, false);
        dbQueries = new DatabaseQueries(ExperienceDetails.this.getContext());
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
            Cursor experienceCursor= dbQueries.selectExpInformation(fileid);
            experienceCursor.moveToFirst();

            String coName1,duration1,role1,proDesc1,coName2,duration2,role2,proDesc2,coName3,
                    duration3,role3,proDesc3,coName4,duration4,role4,proDesc4,coName5,duration5,
                    role5,proDesc5,coName6,duration6,role6,proDesc6;

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
            proDesc6=experienceCursor.getString(experienceCursor.getColumnIndex("projectdesc6"));

            etCoName1=(EditText)rootView.findViewById(R.id.etCoName1);
            etDuration1=(EditText)rootView.findViewById(R.id.etDuration1);
            etRole1=(EditText)rootView.findViewById(R.id.etRole1);
            etProDesc1=(EditText)rootView.findViewById(R.id.etProDesc1);
            etCoName2=(EditText)rootView.findViewById(R.id.etCoName2);
            etDuration2=(EditText)rootView.findViewById(R.id.etDuration2);
            etRole2=(EditText)rootView.findViewById(R.id.etRole2);
            etProDesc2=(EditText)rootView.findViewById(R.id.etProDesc2);
            etCoName3=(EditText)rootView.findViewById(R.id.etCoName3);
            etDuration3=(EditText)rootView.findViewById(R.id.etDuration3);
            etRole3=(EditText)rootView.findViewById(R.id.etRole3);
            etProDesc3=(EditText)rootView.findViewById(R.id.etProDesc3);
            etCoName4=(EditText)rootView.findViewById(R.id.etCoName4);
            etDuration4=(EditText)rootView.findViewById(R.id.etDuration4);
            etRole4=(EditText)rootView.findViewById(R.id.etRole4);
            etProDesc4=(EditText)rootView.findViewById(R.id.etProDesc4);
            etCoName5=(EditText)rootView.findViewById(R.id.etCoName5);
            etDuration5=(EditText)rootView.findViewById(R.id.etDuration5);
            etRole5=(EditText)rootView.findViewById(R.id.etRole5);
            etProDesc5=(EditText)rootView.findViewById(R.id.etProDesc5);
            etCoName6=(EditText)rootView.findViewById(R.id.etCoName6);
            etDuration6=(EditText)rootView.findViewById(R.id.etDuration6);
            etRole6=(EditText)rootView.findViewById(R.id.etRole6);
            etProDesc6=(EditText)rootView.findViewById(R.id.etProDesc6);

            etCoName1.setText(coName1);
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
            etProDesc6.setText(proDesc6);
        }
    }
}

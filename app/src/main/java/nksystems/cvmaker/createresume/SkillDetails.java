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
public class SkillDetails extends Fragment {

    View rootView;
    DatabaseQueries dbQueries;
    EditText etDomain1,etSkill1,etDomain2,etSkill2,etDomain3,etSkill3,etDomain4,etSkill4,etDomain5,etSkill5,etDomain6,etSkill6;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.resume_skills_activity, container, false);
        dbQueries = new DatabaseQueries(SkillDetails.this.getContext());
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
            Cursor skillCursor= dbQueries.selectSkillInformation(fileid);
            skillCursor.moveToFirst();

            String domain1,skill1,domain2,skill2,domain3,skill3,domain4,skill4,domain5,skill5,domain6,skill6;

            domain1=skillCursor.getString(skillCursor.getColumnIndex("skillarea1"));
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
            skill6=skillCursor.getString(skillCursor.getColumnIndex("skillset6"));

            etDomain1=(EditText)rootView.findViewById(R.id.etDomain1);
            etSkill1=(EditText)rootView.findViewById(R.id.etSkill1);
            etDomain2=(EditText)rootView.findViewById(R.id.etDomain2);
            etSkill2=(EditText)rootView.findViewById(R.id.etSkill2);
            etDomain3=(EditText)rootView.findViewById(R.id.etDomain3);
            etSkill3=(EditText)rootView.findViewById(R.id.etSkill3);
            etDomain4=(EditText)rootView.findViewById(R.id.etDomain4);
            etSkill4=(EditText)rootView.findViewById(R.id.etSkill4);
            etDomain5=(EditText)rootView.findViewById(R.id.etDomain5);
            etSkill5=(EditText)rootView.findViewById(R.id.etSkill5);
            etDomain6=(EditText)rootView.findViewById(R.id.etDomain6);
            etSkill6=(EditText)rootView.findViewById(R.id.etSkill6);

            etDomain1.setText(domain1);
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
            etSkill6.setText(skill6);
        }
    }
}

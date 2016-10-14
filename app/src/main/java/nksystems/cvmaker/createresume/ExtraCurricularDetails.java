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
public class ExtraCurricularDetails extends Fragment {

    View rootView;
    DatabaseQueries dbQueries;
    EditText etEC1,etEC2,etEC3,etEC4,etEC5,etEC6;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.resume_extra_curriculars_activity, container, false);
        dbQueries = new DatabaseQueries(ExtraCurricularDetails.this.getContext());
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
            Cursor ecCursor= dbQueries.selectECInformation(fileid);
            ecCursor.moveToFirst();

            String ec1,ec2,ec3,ec4,ec5,ec6;

            ec1=ecCursor.getString(ecCursor.getColumnIndex("extracurr1"));
            ec2=ecCursor.getString(ecCursor.getColumnIndex("extracurr2"));
            ec3=ecCursor.getString(ecCursor.getColumnIndex("extracurr3"));
            ec4=ecCursor.getString(ecCursor.getColumnIndex("extracurr4"));
            ec5=ecCursor.getString(ecCursor.getColumnIndex("extracurr5"));
            ec6=ecCursor.getString(ecCursor.getColumnIndex("extracurr6"));

            etEC1=(EditText)rootView.findViewById(R.id.etEC1);
            etEC2=(EditText)rootView.findViewById(R.id.etEC2);
            etEC3=(EditText)rootView.findViewById(R.id.etEC3);
            etEC4=(EditText)rootView.findViewById(R.id.etEC4);
            etEC5=(EditText)rootView.findViewById(R.id.etEC5);
            etEC6=(EditText)rootView.findViewById(R.id.etEC6);

            etEC1.setText(ec1);
            etEC2.setText(ec2);
            etEC3.setText(ec3);
            etEC4.setText(ec4);
            etEC5.setText(ec5);
            etEC6.setText(ec6);
        }
    }
}

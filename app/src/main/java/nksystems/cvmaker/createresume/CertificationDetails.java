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
public class CertificationDetails extends Fragment {

    View rootView;
    DatabaseQueries dbQueries;
    EditText etCertTitle1,etCertYear1,etCertTitle2,etCertYear2,etCertTitle3,etCertYear3,etCertTitle4,etCertYear4,etCertTitle5,etCertYear5,etCertTitle6,etCertYear6;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.resume_certification_details_activity, container, false);
        dbQueries = new DatabaseQueries(CertificationDetails.this.getContext());
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
            Cursor certificationCursor= dbQueries.selectCertificationInformation(fileid);
            certificationCursor.moveToFirst();

            String certTitle1,certYear1,certTitle2,certYear2,certTitle3,certYear3,certTitle4,certYear4,certTitle5,certYear5,certTitle6,
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
            certYear6=certificationCursor.getString(certificationCursor.getColumnIndex("certyear6"));

            etCertTitle1=(EditText)rootView.findViewById(R.id.etCertTitle1);
            etCertYear1=(EditText)rootView.findViewById(R.id.etCertYear1);
            etCertTitle2=(EditText)rootView.findViewById(R.id.etCertTitle2);
            etCertYear2=(EditText)rootView.findViewById(R.id.etCertYear2);
            etCertTitle3=(EditText)rootView.findViewById(R.id.etCertTitle3);
            etCertYear3=(EditText)rootView.findViewById(R.id.etCertYear3);
            etCertTitle4=(EditText)rootView.findViewById(R.id.etCertTitle4);
            etCertYear4=(EditText)rootView.findViewById(R.id.etCertYear4);
            etCertTitle5=(EditText)rootView.findViewById(R.id.etCertTitle5);
            etCertYear5=(EditText)rootView.findViewById(R.id.etCertYear5);
            etCertTitle6=(EditText)rootView.findViewById(R.id.etCertTitle6);
            etCertYear6=(EditText)rootView.findViewById(R.id.etCertYear6);

            etCertTitle1.setText(certTitle1);
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
            etCertYear6.setText(certYear6);
        }
    }
}

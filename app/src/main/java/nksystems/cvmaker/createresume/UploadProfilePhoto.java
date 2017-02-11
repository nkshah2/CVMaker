package nksystems.cvmaker.createresume;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import nksystems.cvmaker.CreateResume;
import nksystems.cvmaker.DatabaseQueries;
import nksystems.cvmaker.R;

/**
 * Created by Charmy on 11/02/2017.
 */

public class UploadProfilePhoto extends Fragment {

    View rootView;
    DatabaseQueries dbQueries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.resume_photo_upload, container, false);
        dbQueries=new DatabaseQueries(UploadProfilePhoto.this.getContext());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        CreateResume resume = new CreateResume();
        Boolean isEdit = getActivity().getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            Toast.makeText(UploadProfilePhoto.this.getContext(),"Something happens with profile photo",Toast.LENGTH_LONG).show();
        }
    }
}

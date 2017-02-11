package nksystems.cvmaker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nksystems.cvmaker.createresume.AchievementDetails;
import nksystems.cvmaker.createresume.CertificationDetails;
import nksystems.cvmaker.createresume.EducationDetails;
import nksystems.cvmaker.createresume.ExperienceDetails;
import nksystems.cvmaker.createresume.ExtraCurricularDetails;
import nksystems.cvmaker.createresume.PersonalDetails;
import nksystems.cvmaker.createresume.PersonalInterestDetails;
import nksystems.cvmaker.createresume.SkillDetails;
import nksystems.cvmaker.createresume.UploadProfilePhoto;

/**
 * Created by Charmy on 15/09/2016.
 */
public class ResumeAdapter extends FragmentPagerAdapter{

    public ResumeAdapter(FragmentManager fm){ super(fm); }

    @Override
    public Fragment getItem(int index){
        switch (index){
            case 0:
                return new PersonalDetails();
            case 1:
                return new EducationDetails();
            case 2:
                return new CertificationDetails();
            case 3:
                return new ExperienceDetails();
            case 4:
                return new AchievementDetails();
            case 5:
                return new SkillDetails();
            case 6:
                return new ExtraCurricularDetails();
            case 7:
                return new PersonalInterestDetails();
            case 8:
                return new UploadProfilePhoto();
        }
        return null;
    }

    @Override
    public int getCount(){
        return 9;
    }
}

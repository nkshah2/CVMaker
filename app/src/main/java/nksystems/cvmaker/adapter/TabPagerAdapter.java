package nksystems.cvmaker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nksystems.cvmaker.HomeTab;
import nksystems.cvmaker.StatisticsTab;
import nksystems.cvmaker.TemplatesTab;

/**
 * Created by Charmy on 05/06/2016.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm){ super(fm);}

    @Override
    public Fragment getItem(int index){

        switch (index){
            case 0:
                //HomeTab fragment activity
                return new HomeTab();
            case 1:
                //TemplatesTab fragement activity
                return new TemplatesTab();
            case 2:
                //StatisticsTab fragment activity
                return new StatisticsTab();
        }
        return null;
    }

    @Override
    public int getCount(){
        //get item count - equal to number of tabs
        return 3;
    }
}

package nksystems.cvmaker.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import nksystems.cvmaker.R;

/**
 * Created by Charmy on 25/06/2016.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private static LayoutInflater inflater = null;


    public int getCount(){
        return mThumbIds.length;
    }

    public Object getItem(int position){
        return mThumbIds[position];
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        TextView title = null;
        View vi=convertView;
        if(vi == null){
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(500,600));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
            vi = inflater.inflate(R.layout.custom_image_item, null);
            vi.setLayoutParams(new GridView.LayoutParams(500,600));
            vi.setPadding(8,8,8,8);
            imageView=(ImageView)vi.findViewById(R.id.ivDisplayImage);
            title=(TextView)vi.findViewById(R.id.tvTitle);
            imageView.setImageResource(mThumbIds[position]);
            title.setText(mThumbsTitles[position]);
        }
        else{
//            imageView = (ImageView) convertView;
        }



        return vi;
    }

    public ImageAdapter(Context context){

        mContext=context;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.default_sample,
            R.drawable.nmims_sample,
            R.drawable.harvard_sample
    };

    public String[] mThumbsTitles={
            "Default",
            "NMIMS",
            "Harvard"
    };
}

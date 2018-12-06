package com.bvkit.douglas.bvkit.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bvkit.douglas.bvkit.Model.Strip;
import com.bvkit.douglas.bvkit.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by DOUGLAS on 08/08/2017.
 */
public class StripAdapter extends BaseAdapter {
    Context context;
    ArrayList<Strip> stripList;
    private ArrayList<Strip> arraylist;

    //StripAdapter adapter;
    private static LayoutInflater inflater = null;

    public StripAdapter(Context context, ArrayList<Strip> stripList) {
        this.context = context;
        this.stripList = stripList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Strip>();
        this.arraylist.addAll(stripList);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return stripList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    String[] infos ;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null)
            convertView = inflater.inflate(R.layout.strip_grid, null);

        TextView dateTxt = (TextView) convertView.findViewById(R.id.date);
        TextView leuTxt = (TextView) convertView.findViewById(R.id.leu);
        TextView nitTxt = (TextView) convertView.findViewById(R.id.nit);
        TextView uroTxt = (TextView) convertView.findViewById(R.id.uro);
        TextView proTxt = (TextView) convertView.findViewById(R.id.pro);
        TextView phTxt = (TextView) convertView.findViewById(R.id.ph);
        TextView bloTxt = (TextView) convertView.findViewById(R.id.blo);
        TextView sgTxt = (TextView) convertView.findViewById(R.id.sg);
        TextView ketTxt = (TextView) convertView.findViewById(R.id.ket);
        TextView bilTxt = (TextView) convertView.findViewById(R.id.bil);
        TextView gluTxt = (TextView) convertView.findViewById(R.id.glu);

        Strip e = new Strip();
        e = stripList.get(position);
        dateTxt.setText(String.valueOf(e.getDate()));

        if (!TextUtils.isEmpty(e.getLeu()) && e.getLeu()!="null") {
             infos = e.getLeu().split(":");
            leuTxt.setText(String.valueOf("LEU " + infos[0]));
            leuTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            leuTxt.setText("LEU ");
            leuTxt.setBackgroundColor(Color.WHITE);
        }
        if (!TextUtils.isEmpty(e.getNit()) && e.getNit()!="null") {
            infos = e.getNit().split(":");
            nitTxt.setText(String.valueOf("NIT " + infos[0]));
            nitTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            nitTxt.setText("NIT");
            nitTxt.setBackgroundColor(Color.WHITE);
        }
        if (!TextUtils.isEmpty(e.getUro()) && e.getUro()!="null") {
            infos = e.getUro().split(":");
            uroTxt.setText(String.valueOf("URO " + infos[0]));
            uroTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            uroTxt.setText("URO");
            uroTxt.setBackgroundColor(Color.WHITE);
        }

        //nitTxt.setText(String.valueOf("NIT " + e.getNit()));
       // uroTxt.setText(String.valueOf("URO " + e.getUro()));
        //proTxt.setText(String.valueOf("PRO " + e.getPro()));

        if (!TextUtils.isEmpty(e.getPro()) && e.getPro()!="null") {
            infos = e.getPro().split(":");
            proTxt.setText(String.valueOf("PRO " + infos[0]));
            proTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            proTxt.setText("PRO");
            proTxt.setBackgroundColor(Color.WHITE);
        }
        //phTxt.setText(String.valueOf("Ph" + e.getPh()));
        if (!TextUtils.isEmpty(e.getPh()) && e.getPh()!="null") {
            infos = e.getPh().split(":");
            phTxt.setText(String.valueOf("pH " + infos[0]));
            phTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            phTxt.setText("pH");
            phTxt.setBackgroundColor(Color.WHITE);
        }
        //bloTxt.setText(String.valueOf("BLO" + e.getBlo()));
        if (!TextUtils.isEmpty(e.getBlo()) && e.getBlo()!="null") {
            infos = e.getBlo().split(":");
            bloTxt.setText(String.valueOf("BLO " + infos[0]));
            bloTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            bloTxt.setText("BLO");
            bloTxt.setBackgroundColor(Color.WHITE);
        }
       // sgTxt.setText(String.valueOf("SG " + e.getSg()));
        if (!TextUtils.isEmpty(e.getSg()) && e.getSg()!="null") {
            infos = e.getSg().split(":");
            sgTxt.setText(String.valueOf("SG " + infos[0]));
            sgTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            sgTxt.setText("SG");
            sgTxt.setBackgroundColor(Color.WHITE);
        }
       // ketTxt.setText(String.valueOf("KET " + e.getKet()));
        if (!TextUtils.isEmpty(e.getKet()) && e.getKet()!="null") {
            infos = e.getKet().split(":");
            ketTxt.setText(String.valueOf("KET " + infos[0]));
            ketTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            ketTxt.setText("KET");
            ketTxt.setBackgroundColor(Color.WHITE);
        }

       // bilTxt.setText(String.valueOf("BIL" + e.getBil()));
        if (!TextUtils.isEmpty(e.getBil()) && e.getBil()!="null") {
            infos = e.getBil().split(":");
            bilTxt.setText(String.valueOf("BIL " + infos[0]));
            bilTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            bilTxt.setText("BIL");
            bilTxt.setBackgroundColor(Color.WHITE);
        }
        //gluTxt.setText(String.valueOf("GLU " + e.getGlu()));
        if (!TextUtils.isEmpty(e.getGlu()) && e.getGlu()!="null") {
            infos = e.getGlu().split(":");
            gluTxt.setText(String.valueOf("GLU " + infos[0]));
            gluTxt.setBackgroundColor(Color.parseColor(infos[1]));
        } else {
            gluTxt.setText("GLU");
            gluTxt.setBackgroundColor(Color.WHITE);
        }
        final Strip Strip = stripList.get(position);
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        stripList.clear();
        if (charText.length() == 0) {
            stripList.addAll(arraylist);
        } else {
            for (Strip wp : arraylist) {
                if (wp.getDate().toLowerCase(Locale.getDefault()).contains(charText)) {
                    stripList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
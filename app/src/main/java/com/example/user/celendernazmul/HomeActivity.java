package com.example.user.celendernazmul;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NevadaSoft on 12/29/2015.
 */
public class HomeActivity extends Activity{

    public static ArrayList<KumheiModel> dataList = new ArrayList<KumheiModel>();
    public ArrayList<DataModel> data = new ArrayList<DataModel>();

    private Spinner spinner;
    EditText et_search;
    ListView lv_data;
    // String Year[];
    Context context;

    String year_received ;

    CustomBaseAdapter adapter;


    ArrayAdapter<String> yearAdapter;
    ArrayList<String> yearValue = new ArrayList<String>();
    public ArrayList<DataModel> appData = new ArrayList<DataModel>();

    public ArrayList<DataModel> CalenderData = new ArrayList<DataModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        context = this;
        adapter = new CustomBaseAdapter();
        ParseData();
        initView() ;
    }


    public void ParseData() {
        // Reading text file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            // br = new BufferedReader(new
            // InputStreamReader(getAssets().open("jsondata.txt")));
            // br = new BufferedReader(new
            // InputStreamReader(getAssets().open("change.json")));
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "data.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                // Log.e("name",":"+temp);
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
            String myJsonString = sb.toString();

            System.out.println("String json :" + myJsonString.toString());

            try {
                JSONObject jsonObjectMain = new JSONObject(myJsonString);

                JSONArray jsonArrayMain = jsonObjectMain.getJSONArray("events");

                // Year = new String[jsonArrayMain.length()];
                yearValue.clear();
                CalenderData.clear();
                dataList.clear();
                for (int i = 0; i < jsonArrayMain.length(); i++) {
                    data.clear();
                    JSONObject jsonObj = jsonArrayMain.getJSONObject(i);

                    KumheiModel kData = new KumheiModel();
                    kData.setYear(jsonObj.getString("Year"));

                    yearValue.add(jsonObj.getString("Year"));

                    JSONArray jsonArray = jsonObj.getJSONArray("list");
                    System.out.println("lenth of array :" + jsonArray.length());

                    ArrayList<DataModel> model = new ArrayList<DataModel>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jObj = jsonArray.getJSONObject(j);
                        DataModel dData = new DataModel();
                        dData.setDate(jObj.getString("date"));
                        dData.setName(jObj.getString("name"));
                        //here do something for retrive calender
                        String desc = jObj.getString("name");
                        boolean valueReceived = KumheiSharedPreferences.getBooleanValue(HomeActivity.this, desc);
                        Log.e("boolean value:", ""+valueReceived);
                        if(valueReceived){
                            dData.setIsTick(true);
                        }else{
                            dData.setIsTick(false);
                        }


                        data.add(dData);
                        model.add(dData);
//						kData.ad
                        //
                        CalenderData.add(dData);
                    }

                    kData.setDataList(model);
                    dataList.add(kData);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(" Year Size = " + yearValue.size());
            System.out.println("Array Size:.................................."
                    + dataList.size());
        }
    }


    private void initView() {

        spinner = (Spinner) findViewById(R.id.sp_year);

//		ArrayAdapter<String> dataAdapter

        yearAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearValue);
        yearAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(yearAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                // TODO Auto-generated method stub
                year_received = arg0.getItemAtPosition(position).toString();
                SearchByDate(arg0.getItemAtPosition(position).toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                SearchByName(et_search.getText().toString());

//				listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        lv_data = (ListView) findViewById(R.id.lv_data);
        lv_data.setAdapter(adapter);
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });


        lv_data.setOnTouchListener(new OnSwipeTouchListener(){

            int pos;

            @Override
            public boolean onTouch(View v, MotionEvent e1) {

                pos = lv_data.pointToPosition((int) e1.getX(), (int) e1.getY());
                return super.onTouch(v, e1);
            }
            public void onSwipeTop() {
                Toast.makeText(HomeActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(HomeActivity.this, "right", Toast.LENGTH_SHORT).show();
                Toast.makeText(HomeActivity.this, "pos :" + pos, Toast.LENGTH_SHORT).show();
                System.out.println("year :" + year_received);

                for(int i = 0 ; i<dataList.size(); i++){
                    if(dataList.get(i).year.equalsIgnoreCase(year_received)){
                        for(int k=0;k<dataList.get(i).getDataList().size();k++){
                            if(k==pos){
                                String des_received = dataList.get(i).getDataList().get(k).getName().toString();
                                String date_received = dataList.get(i).getDataList().get(k).getDate().toString();
                                System.out.println("des :" + des_received);
                                System.out.println("date :" + date_received);

                                String firstdate, lastdate ;

                                if(date_received.contains("to")) {


                                    String[] separated = date_received.split(" to");
                                    firstdate = separated[0];
                                    lastdate = separated[1].trim();

                                    //  String firstdate = date_received.substring(0,9);
                                    // String seconddate =date_received.substring(14,23);
                                    Log.e("firstdate", "seconddate" + firstdate);
                                    Log.e("firstdate", "seconddate" + lastdate);
                                    System.out.println("firstdate" + firstdate + "lastdate" + lastdate);
                                }

                                else {

                                    firstdate = date_received;
                                    lastdate = date_received;
                                }




                                //string  convert date
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Date convertedDate = new Date();
                                Date  convertedfirstdate = new Date();
                                Date  convertedlastdate = new Date();
                                try {
                                    convertedDate = dateFormat.parse(date_received);
                                    convertedfirstdate=dateFormat.parse(firstdate);
                                    convertedlastdate =dateFormat.parse(lastdate);
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                System.out.println(convertedDate);

                                // ccreate a event in clender
                                // Calendar cal = Calendar.getInstance();

                                // cal.setTime(convertedDate);

                                Calendar firstcal = Calendar.getInstance();

                                firstcal.setTime(convertedfirstdate);

                                Calendar lastcal = Calendar.getInstance();

                                lastcal.setTime(convertedlastdate);





                                //cal.setTime(convertedDate);
                                // System.out.println(convertedDate);


                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                //cal.setTime(date);
                                intent.setType("vnd.android.cursor.item/event");
                                //  intent.putExtra("beginTime", convertedDate);
                                intent.putExtra("beginTime", firstcal.getTimeInMillis());



                                intent.putExtra("allDay", true);
                                intent.putExtra("rrule", "FREQ=YEARLY");
                                intent.putExtra("endTime", lastcal.getTimeInMillis() + 60 * 60 * 1000);
                                intent.putExtra("title", des_received);
                                startActivity(intent);

                                //save this event to shared preference
                                KumheiSharedPreferences.setBooleanValue(HomeActivity.this, des_received, true);
                                //dataList.get(i).getDataList().get(k).getName().toString();
                                dataList.get(i).getDataList().get(k).setIsTick(true);
                                adapter.notifyDataSetChanged();
                                //startActivityForResult(intent, 100);
                            }
                        }
                    }
                }
            }
            public void onSwipeLeft() {
                Toast.makeText(HomeActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(HomeActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }


        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == Activity.RESULT_OK){
               /* try{

                }catch(Exception e){
                    e.printStackTrace() ;
                }*/
                Log.e("zin", "event successfully  created");
            }if(resultCode == Activity.RESULT_CANCELED){
                Log.e("zin", "event not  created");
            }

        }
    }


    public void SearchByDate(String date) {
        appData.clear();
        CalenderData.clear();
        for(int i=0;i<dataList.size();i++){

            if(dataList.get(i).year.equalsIgnoreCase(date)){

                System.out.println(" Year = "+date);
                System.out.println(" dataList = " +dataList.get(i).year);
                for(int k=0;k<dataList.get(i).getDataList().size();k++){
                    System.out.println(" Size = "+dataList.get(i).getDataList().size());
                    appData.add(dataList.get(i).getDataList().get(k));
                    CalenderData.add(dataList.get(i).getDataList().get(k));
                }
            }

            System.out.println(" appData.size() = "+appData.size());
        }

//		adapter.notifyDataSetChanged();
    }

    public void SearchByName(String name) {
        appData.clear();
        for(int i=0;i<CalenderData.size();i++){

            if(CalenderData.get(i).getName().toLowerCase().startsWith(name.toLowerCase())
                    || CalenderData.get(i).getName().toLowerCase().contains(name.toLowerCase())
                    || CalenderData.get(i).getDate().toLowerCase().startsWith(name.toLowerCase())
                    || CalenderData.get(i).getDate().toLowerCase().startsWith(name.toLowerCase())){

                appData.add(CalenderData.get(i));

            }
        }

//		adapter.notifyDataSetChanged();
    }



    public class CustomBaseAdapter extends BaseAdapter {
        // Context context;
        // List<RowItem> rowItems;

        public CustomBaseAdapter() {
            // this.context = context;
            // this.rowItems = items;
        }

        /* private view holder class */
        private class ViewHolder {
            TextView tvName, tvDate;
            ImageView imvTick ;
            int pos_holder ;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_row_view, null);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView
                        .findViewById(R.id.tvName);
                holder.tvDate = (TextView) convertView
                        .findViewById(R.id.tvDate);
                holder.imvTick = (ImageView)convertView.findViewById(R.id.imv_tick);
                holder.pos_holder = position ;

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DataModel rowItem = (DataModel) getItem(position);

            holder.tvName.setText(rowItem.getName());
            holder.tvDate.setText(rowItem.getDate());

            boolean boolData = appData.get(position).isTick();
            Log.e("inside adapter", ""+boolData);
            if(appData.get(holder.pos_holder).isTick()  ){
                holder.imvTick.setImageResource(R.drawable.tick);
            }
            else {
                holder.imvTick.setImageDrawable(null);
            }


            return convertView;
        }

        @Override
        public int getCount() {
            return appData.size();
        }

        @Override
        public Object getItem(int position) {
            return appData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return appData.indexOf(getItem(position));
        }
    }
}

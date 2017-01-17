package com.com.tigarty.api;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaidam.tigarty.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Amr Saidam on 12/2/2016.
 */

public class Dashboard {

    public final static String RESPONSE_KEY = "response";
    public final static String PROFITS_CHART_DESCRIPTION = "productOfHighestProfit";
    public final static String SALE_CHART_DESCRIPTION = "productOfTheHighestSale";
    Context context;
    FragmentManager fragmentManager;
    PieChart pieChart , pieChart2;
    LineChart lineChart;
    View view;

    public Dashboard(View view , Context context , FragmentManager fragmentManager  , PieChart pieChart , PieChart pieChart2  , LineChart lineChart )
    {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.pieChart = pieChart;
        this.pieChart2 = pieChart2;
        this.lineChart = lineChart;
        this.view = view;
    }



    public void getAllAnalyticsInformation() throws IOException, ClassNotFoundException {
        final Fragment fragment = fragmentManager.findFragmentById(R.id.content_menu);
        final Intent intent  =  new Intent();
        System.out.println(Constants.ANALYTIC_INFO+"?userId="+Constants.getUserId());
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.ANALYTIC_INFO+"?userId="+Constants.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Analtics "+response);
                        try {
                            saveAnalyticsInfoLocally(response.toString());
                            intent.putExtra(RESPONSE_KEY,response.toString());
                            initiatePieCharts(pieChart , generatePiChartContent(response.toString() ,PROFITS_CHART_DESCRIPTION));
                            initiatePieCharts(pieChart2 , generatePiChartContent(response.toString() ,SALE_CHART_DESCRIPTION));

                            System.out.println("()()()(()()()()()()()()()()()()()()()()()()()()()");
                            initiateLineChart(lineChart , generateLineChartEntries(response.toString()));
                            fragment.onActivityResult(1,1,intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        System.out.println("Here!@!@#!@#1");
//
//                        System.out.println("Response is : " + response);
//
//                        try {
//                            if (new JSONObject(response).getInt("status") == 200) {
//                                System.out.println("Status is 200");
//                                productName.setText(new JSONObject(response).getString("name"));
//                                productName.setEnabled(false);
//                                //  scanButton.setEnabled(true);
//                                // scanButton.setVisibility(View.GONE);
//                                return;
//                            }else if(flag == false)
//                                checkProductNameFromExternalAPI(Constants.PRODUCT_NAME_API, tempProductNumber, productName, flag);
//                            //  scanButton.setVisibility(View.GONE);
//
//                        } catch (JSONException e) {
//                            Toast.makeText(activity.getApplicationContext(), R.string.suddenlyError, Toast.LENGTH_LONG).show();
//                        }
//                        contentTxt.setText(productNumber);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                try {
                   // generatePiChartContent(restoreAnalyticsInfo() ,"productOfHighestProfit")
                    initiatePieCharts(pieChart ,generatePiChartContent(restoreAnalyticsInfo() ,PROFITS_CHART_DESCRIPTION));
                    initiatePieCharts(pieChart2 ,generatePiChartContent(restoreAnalyticsInfo() ,SALE_CHART_DESCRIPTION));
                    initiateLineChart(lineChart , generateLineChartEntries(restoreAnalyticsInfo()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intent.putExtra(RESPONSE_KEY,"error");
                if (error.getClass().getSimpleName().equalsIgnoreCase("NoConnectionError")) {
                    fragment.onActivityResult(-2, 1, intent);
                    Snackbar.make(view, R.string.internetError, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
//                Animation shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
//                shake.setDuration(690);
//                scanButton.startAnimation(shake);
                //  scanButton.setEnabled(false);

              //  Toast.makeText(context.getApplicationContext(),R.string.productNameError, Toast.LENGTH_LONG).show();
               // System.out.println("Connection Error : " + error.getClass().getSimpleName());
            }
        });


        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    private void saveAnalyticsInfoLocally(String jsonContent) throws IOException {

        String filePath = Environment.getExternalStorageDirectory()+"//"+Constants.ANALYTIC_DIR+"//"+Constants.ANALYTIC_FILE + ".json";
        File tigartyDir = new File(Environment.getExternalStorageDirectory(), "" + Constants.ANALYTIC_DIR);
        if (!tigartyDir.exists())
            tigartyDir.mkdirs();

        System.out.println(filePath);
        File analyticFile = new File(filePath);
//        if(!analyticFile.exists())
//            return;
        FileWriter fileWriter = new FileWriter(analyticFile);
        fileWriter.write(jsonContent);
        analyticFile.setWritable(false);
        fileWriter.flush();
        fileWriter.close();

    }

    private void initiateFragment(Fragment fragment, String tag) {
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(fragment,tag);
//        fragmentTransaction.replace(R.id.content_menu, fragment);
//        fragmentTransaction.commit();
    }

    private void initiatePieCharts(PieChart pieChart ,  ArrayList<PieEntry> xData)
    {




        pieChart.refreshDrawableState();


        //pieChart.clear();
        System.out.println(xData.size());
        System.out.println(xData.get(0).getValue());
//        ArrayList<PieEntry> xData = new ArrayList<>();
//        xData.add(new PieEntry(33, "productName"));
//        xData.add(new PieEntry(66, "Rest"));

        PieDataSet pieDataSet = new PieDataSet(xData, "data");
        // pieChart.notifyDataSetChanged();
        //examples
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(33, 187, 230));
        colors.add(Color.rgb(42, 255, 143));

        //specify your color
        pieDataSet.setColors(colors);


        //labels for data set
//        ArrayList<String> listOfLabels = new ArrayList<>();
//        listOfLabels.add("Amr");
//        listOfLabels.add("Saidam");
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.animateX(5000);
        pieChart.animateY(2000);

        //pieChart.getData().setDataSet(pieDataSet);
        pieChart.invalidate();
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);




    }

    private ArrayList<PieEntry> generatePiChartContent(String jsonContent , String chartDescription) throws JSONException {

        ArrayList<PieEntry> values = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonContent);
        System.out.println("id osn "+chartDescription);
        JSONObject productOfHighestProfit = new JSONObject(jsonObject.getString(chartDescription));

        // JSONObject jsonObject = new JSONObject(jsonArray.get(0));
        // Product product = ( Product)jsonArray.get(0);
        System.out.println("*****************************");
        System.out.println(productOfHighestProfit.getString("numberOfSale"));

        // System.out.println(jsonObject);

        if(chartDescription.equalsIgnoreCase(PROFITS_CHART_DESCRIPTION)) {
            int highestProfit = productOfHighestProfit.getInt("numberOfSale") * productOfHighestProfit.getInt("SingleUnitPrice");
            values.add(new PieEntry(highestProfit, productOfHighestProfit.getString("Name")));
            values.add(new PieEntry(jsonObject.getInt("allShopProfits"), "Rest"));
        }else
        {
            JSONObject productOfHighestSale = new JSONObject(jsonObject.getString(chartDescription));
            values.add(new PieEntry(productOfHighestSale.getInt("numberOfSale"),productOfHighestSale.getString("Name")));
            values.add(new PieEntry(jsonObject.getInt("allShopSales"), "Rest"));
        }
        return values;


    }

    public String restoreAnalyticsInfo() throws IOException, JSONException {
        String filePath = Environment.getExternalStorageDirectory()+"//"+Constants.ANALYTIC_DIR+"//"+Constants.ANALYTIC_FILE + ".json";
        File file = new File(filePath);
        if (!file.exists())
            return "{}";
        FileInputStream fileInputStream = new FileInputStream(file);

        int size  = fileInputStream.available();
        byte[] buffer = new byte[size];
        fileInputStream.read(buffer);
        String jsonContent = new String(buffer);

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(jsonContent);
       // StringBuilder jsonContent = new StringBuilder(buffer);
        //FileReader fileReader = new FileReader(new File(filePath));

//        fileReader.read();
//        JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(filePath)));
//        System.out.println("Result +(@#!$ ");
//        jsonReader.beginObject();
//        StringBuilder content = new StringBuilder();
//        while (jsonReader.hasNext())
//           // content.append(jsonReader.);
//            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        System.out.printf(content.toString());
//        fileReader.read();
       // String jsonContent = jsonReader.toString();
        //JSONObject jsonObject = new JSONObject(jsonReader.toString());
//
//        fileReader.close();
//        System.out.println("Result +(@#!$ "+jsonContent);
       // jsonReader.close();
        return jsonContent;
    }


    private void initiateLineChart(LineChart lineChart , ArrayList<Entry> xData)
    {
//        ArrayList<Entry> xData  = new ArrayList<>();
//        xData.add(new Entry(0,10));
//        xData.add(new Entry(1,20));
//        xData.add(new Entry(2,30));
//        xData.add(new Entry(3,40));
//        xData.add(new Entry(4,50));
//        xData.add(new Entry(5,60));
//        xData.add(new Entry(7,70));
//        xData.add(new Entry(8,50));
//        xData.add(new Entry(9,40));
//        xData.add(new Entry(10,10));
//        xData.add(new Entry(11,30));
//        xData.add(new Entry(12,40));
//        xData.add(new Entry(13,200));
//        xData.add(new Entry(14,0));
//        xData.add(new Entry(15,0));
//        xData.add(new Entry(16,0));
//        xData.add(new Entry(17,0));
//        xData.add(new Entry(18,0));
//        xData.add(new Entry(19,0));
//        xData.add(new Entry(20,0));

        LineDataSet lineDataSet = new LineDataSet(xData,"Current Month's profits");
        lineDataSet.setFillAlpha(200);
        lineDataSet.setDrawFilled(true);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);


        lineChart.setData(new LineData(lineDataSet));
        // lineChart.getDescription().setEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setForm(Legend.LegendForm.SQUARE);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
//        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(false);
        //lineChart.get
        lineChart.animate();

        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
    }

    private  ArrayList<Entry> generateLineChartEntries(String JsonContent) throws JSONException {

        ArrayList<Entry> values = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(JsonContent);
        JSONArray productOfHighestProfit = jsonObject.getJSONArray("netProfitsOfCurrentMonth");
//        JSONArray jsonArray = (JSONArray) productOfHighestProfit.get(0);
        System.out.println("Array size "+ productOfHighestProfit.length());

        for (int counter = 1 ; counter < productOfHighestProfit.length() ; counter++)
        {
            System.out.println(productOfHighestProfit.get(counter));
            values.add(new Entry(counter-1 , Float.parseFloat(""+productOfHighestProfit.get(counter))));
        }

        return values;
    }
}

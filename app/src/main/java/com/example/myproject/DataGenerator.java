package com.example.myproject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import net.ozaydin.serkan.easy_csv.EasyCsv;
import net.ozaydin.serkan.easy_csv.FileCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.example.myproject.Constants.FILE_DIRECTORY;
import static com.example.myproject.Constants.INPUT_FILE_NAME;
import static com.example.myproject.Constants.QUERY_FILE_NAME;
import static com.example.myproject.Constants.WRITE_PERMISSON_REQUEST_CODE;

public class DataGenerator {


    private List<String> headerList = new ArrayList<>();
    private List<String> dataList = new ArrayList<>();

    private String[] names = {"Smith", "Doe", "John"};

    static File fileValue;

    private IMainView imainView;
    public DataGenerator(IMainView imainView){
        this.imainView = imainView;
    }
    public List<String> prepareHeaderList() {
        headerList.add("LastName.FirstName.State.Phone/");
        return headerList;
    }

    public List<String> prepareValueList() {
        dataList.add("Doe.John.New York.(917) 958-1191/");
        dataList.add("Doe.John.California.(212) 234-1191/");
        dataList.add("John.Smith.New York.9179581191/");
        dataList.add("Bill.Gates.New York.(917) 358-1291/");
        dataList.add("Doe.John.Florida.(919) 234-1192/");
        return dataList;
    }

    private String prepareQueryFileBody() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            builder.append(names[i]);
            if (i != names.length) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public void generateTextFile(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(QUERY_FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(prepareQueryFileBody());
            outputStreamWriter.close();
            imainView.onQueryFileCreation();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void createCSVFile(final Context context, EasyCsv easyCsv){
        easyCsv.setSeparatorColumn("-");
        easyCsv.setSeperatorLine("/");
        easyCsv.createCsvFile(INPUT_FILE_NAME,prepareHeaderList(), prepareValueList(), WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {
            @Override
            public void onSuccess(File file) {
                fileValue = file;
                generateTextFile(context);
            }

            @Override
            public void onFail(String s) {

                System.out.println("error is "+s);
            }
        });
    }
}

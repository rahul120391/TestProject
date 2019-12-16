package com.example.myproject;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import net.ozaydin.serkan.easy_csv.EasyCsv;
import net.ozaydin.serkan.easy_csv.FileCallback;

import org.w3c.dom.ls.LSInput;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myproject.Constants.QUERY_FILE_NAME;
import static com.example.myproject.Constants.READ_EXTERNAL_STORAGE_REQUEST_CODE;
import static com.example.myproject.Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE;
import static com.example.myproject.Constants.WRITE_PERMISSON_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements IMainView {

    final DataGenerator generator = new DataGenerator(this);

    EasyCsv easyCsv;

    TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        txtResult = findViewById(R.id.txtResult);
        if(PermissionChecker.isWriteStoragePermissionGranted(this)){
            easyCsv = new EasyCsv(this);
            generator.createCSVFile(this,easyCsv);
        }
    }



    @Override
    public void onQueryFileCreation() {
        if(PermissionChecker.isReadStoragePermissionGranted(this)){
            String result=FileReader.resultData(this);
            txtResult.setText(result);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    String result=FileReader.resultData(this);
                    txtResult.setText(result);
                }else{
                }
                break;

            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                     easyCsv = new EasyCsv(this);
                      generator.createCSVFile(this,easyCsv);

                }else{
                }
                break;
        }
    }

}

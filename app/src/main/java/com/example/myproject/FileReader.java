package com.example.myproject;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myproject.Constants.QUERY_FILE_NAME;

public class FileReader {

    private FileReader(){

    }

    public static List<String> readTextFileData(Context context, String filename){
        List<String> listOfQueries = new ArrayList<>();
        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    listOfQueries.add(receiveString);
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return listOfQueries;
    }

    public static List<PhoneDirectory> readCsvFileData(File file){
        List<PhoneDirectory> list = new ArrayList<>();
        try{
            CSVReader reader = new CSVReader(new java.io.FileReader(DataGenerator.fileValue));
            List<String[]> lines=reader.readAll();
            for(int i=1;i<lines.size();i++){
                String value=lines.get(i)[0];
                String object[]=value.split("[.]");
                for (String s:object){
                    System.out.println("value"+s);
                }
                PhoneDirectory directory = new PhoneDirectory();
                directory.setFirstName(object[1]);
                directory.setLastName(object[0]);
                directory.setState(object[2]);
                directory.setPhone(object[3]);
                list.add(directory);
            }

        }
        catch (Exception e){
            System.out.println("exception is"+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static String resultData(Context context){
        Map<String,StringBuilder> map = new HashMap<>();
        List<PhoneDirectory> csvFileData=FileReader.readCsvFileData(DataGenerator.fileValue);
        List<String> textFileData = FileReader.readTextFileData(context,QUERY_FILE_NAME);
        for (String name:textFileData){

            for (PhoneDirectory directory:csvFileData){
                if(directory.getLastName().equalsIgnoreCase(name)){
                    String value = "Result is : "+ directory.getLastName()+","+directory.getFirstName()+","+directory.getState()+"."+directory.getPhone();
                    StringBuilder builder = new StringBuilder(value);
                    String key = "Matches For :" +name;
                    if(map.containsKey(key)){
                        StringBuilder builderr=map.get(key);
                        builderr.append("\n");
                        builderr.append(builder);
                        map.put(key,builderr);
                    }
                    else{
                        map.put(key,builder);
                    }
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,StringBuilder> entry:map.entrySet()){
            String key=entry.getKey();
            String value = entry.getValue().toString();
            builder.append(key);
            builder.append("\n");
            builder.append(value);
            builder.append("\n");
        }
        return builder.toString();
    }
}

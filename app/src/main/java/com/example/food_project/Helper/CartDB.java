package com.example.food_project.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.example.food_project.Domain.Foods;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;

//Lớp CartDB là một helper class dùng để lưu trữ và truy xuất danh sách
// các đối tượng Foods trong bộ nhớ tạm (SharedPreferences) của ứng dụng Android.
public class CartDB {
//    dùng để lưu trữ dữ liệu dưới dạng key-value trong bộ nhớ tạm của ứng dụng.
    private SharedPreferences preferences;

    //Khởi tạo đối tượng CartDB với một Context của ứng dụng (appContext).
    // Sử dụng PreferenceManager.getDefaultSharedPreferences để lấy đối tượng SharedPreferences mặc định của ứng dụng.
    public CartDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    //Trả về một ArrayList chứa các chuỗi được lưu trữ với key được cung cấp.
    // Các chuỗi này được tách ra từ một chuỗi lớn lưu trữ trong SharedPreferences sử dụng TextUtils.split với dấu phân cách đặc biệt (‚‗‚).
    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

//Trả về một ArrayList các đối tượng Foods được lưu trữ với key được cung cấp.
    public ArrayList<Foods> getListObject(String key){
//        Sử dụng Gson để chuyển đổi chuỗi JSON thành đối tượng Foods.
        Gson gson = new Gson();
//        Lấy danh sách các chuỗi JSON bằng phương thức getListString
        ArrayList<String> objStrings = getListString(key);
        ArrayList<Foods> playerList =  new ArrayList<Foods>();
////        Chuyển đổi từng chuỗi JSON thành đối tượng Foods và thêm vào danh sách.
        for(String jObjString : objStrings){
            Foods player  = gson.fromJson(jObjString,  Foods.class);
            playerList.add(player);
        }
        return playerList;
    }

//Lưu trữ một danh sách các chuỗi với key được cung cấp.
    public void putListString(String key, ArrayList<String> stringList) {
        //Kiểm tra key để đảm bảo không null (checkForNullKey).
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        //Kết hợp các chuỗi thành một chuỗi duy nhất sử dụng TextUtils.join với dấu phân cách đặc biệt (‚‗‚)
        // và lưu trữ chuỗi này vào SharedPreferences.
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

// Lưu trữ một danh sách các đối tượng Foods với key được cung cấp
    public void putListObject(String key, ArrayList<Foods> playerList){
        checkForNullKey(key);
        //Sử dụng Gson để chuyển đổi từng đối tượng Foods thành chuỗi JSON.
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for(Foods player: playerList){
            objStrings.add(gson.toJson(player));
        }
        //Lưu trữ danh sách các chuỗi JSON bằng phương thức putListString
        putListString(key, objStrings);
    }

    //Kiểm tra nếu key là null, ném ra ngoại lệ NullPointerException để ngăn chặn việc sử dụng key null.
    private void checkForNullKey(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }
}

package com.java.news.news.newsList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.java.news.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassActivity extends AppCompatActivity {

    // 分类栏信息
    GridView classView;
    SimpleAdapter classAdapter;
    List<Map<String, Object>> data_list;
    String[] classes=NewsActivity.classes;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_class);

        // 新闻分类选项栏
        classView = (GridView) findViewById(R.id.chooseClass_view);
        // 新建适配器
        data_list = new ArrayList<>();
        for (int i = 0; i < classes.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", classes[i]);
            data_list.add(map);
        }
        String[] from = {"text"};
        int[] to = {R.id.class_text};
        classAdapter = new SimpleAdapter(this, data_list, R.layout.class_item, from, to);

        gridSetting();
        classView.setAdapter(classAdapter);

        classView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        save.setTextColor(getResources().getColor(R.color.colorClassNotChoose));
                        save=view.findViewById(R.id.class_text);
                        save.setTextColor(getResources().getColor(R.color.colorClassChoose));
                    }
                });
        //让第一个变色
        classView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                save=classView.getChildAt(0).findViewById(R.id.class_text);
                save.setTextColor(getResources().getColor(R.color.colorClassChoose));
            }
        });
    }

    void gridSetting() {
//        int size = classes.length;
//        int length = 60;
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int gridviewWidth = (int) (size * (length + 4) * density);
//        int itemWidth = (int) (length * density);
//
//        LinearLayout.LayoutParams params =
//                new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
//        classView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
//        classView.setColumnWidth(itemWidth); // 设置列表项宽
//        classView.setHorizontalSpacing(5); // 设置列表项水平间距
//        classView.setStretchMode(GridView.NO_STRETCH);
//        classView.setNumColumns(auto); // 设置列数量=列表集合数
    }
}
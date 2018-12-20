package com.feng.weather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feng.weather.R;
import com.feng.weather.entity.City;
import com.feng.weather.entity.Province;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;

/**
 * author 丰
 * date   2018/12/10
 * desc
 */
public class SetCity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Province> provinceList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private List<City> usingCityList = new ArrayList<>();

    private ListView lvProvince;
    private ListView lvCity;

    private ArrayAdapter usingAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_city);

        lvProvince = findViewById(R.id.lv_province);
        lvCity = findViewById(R.id.lv_city);

        parseProvince();
        parseCity();

        lvProvince.setOnItemClickListener(this);
        lvCity.setOnItemClickListener(this);

        ArrayAdapter<Province> provinceAdapter = new ArrayAdapter<Province>(this,
                R.layout.list_item_city_province_name, provinceList);
        lvProvince.setAdapter(provinceAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_province:
                Province province = provinceList.get(position);
                usingCityList.clear();
                for (City city : cityList) {
                    if (province.getId().equals(city.getPid())) {
                        usingCityList.add(city);
                    }
                }
                if (usingAdapter == null) {
                    usingAdapter = new ArrayAdapter<>(this,
                            R.layout.list_item_city_province_name, usingCityList);
                    lvCity.setAdapter(usingAdapter);
                } else {
                    usingAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.lv_city:
                Intent intent = getIntent();
                intent.putExtra("cityName", usingCityList.get(position).getCityName());
                setResult(0, intent);
                finish();
                break;
        }
    }

    private void parseProvince() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(getAssets().open("Provinces.xml"));
            Element rootElement = document.getRootElement();

            List<Element> es = rootElement.elements();
            for (Element element : es) {
                Province province = new Province();
                province.setId(element.attributeValue("ID"));
                province.setProvinceName(element.attributeValue("ProvinceName"));
                provinceList.add(province);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseCity() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(getAssets().open("Cities.xml"));
            Element rootElement = document.getRootElement();

            List<Element> es = rootElement.elements();
            for (Element element : es) {
                City city = new City();
                city.setId(element.attributeValue("ID"));
                city.setPid(element.attributeValue("PID"));
                city.setCityName(element.attributeValue("CityName"));
                cityList.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

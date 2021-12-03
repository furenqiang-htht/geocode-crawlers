/**
 * Copyright © 2021. All rights reserved.
 *
 * @描述: biz_statistics_model
 * @Prject: DataHub
 * @Package: com.domain.module.statistics.model.entity
 * @ClassName: ModelEntity
 * @author: Cui WenKe
 * @date: 2021-07-27
 * @version: V1.0
 */
package com.furenqaing.jsoupcrawlers.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @ClassName: ModelEntity
 * @描述: shp存es
 * @author: Eric
 * @date: 2021年11月26日
 */
@Document(indexName = "location", type = "geo_point")
public class GeoCodeEntity {

    @GeoPointField
    private GeoPoint location;

    //名称
    private String text;

    private String id;

    //省份
    private String province;

    //真实城市
    private String city;

    //市县区
    private String Counties;

    //市县区
    private String areaCode;

    //电话
    private String phone;

    //区域
    private String area;

    //区域
    private String adress;

    //大分类
    private String maxClass;

    //小分类
    private String minClass;

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounties() {
        return Counties;
    }

    public void setCounties(String counties) {
        Counties = counties;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMaxClass() {
        return maxClass;
    }

    public void setMaxClass(String maxClass) {
        this.maxClass = maxClass;
    }

    public String getMinClass() {
        return minClass;
    }

    public void setMinClass(String minClass) {
        this.minClass = minClass;
    }
}

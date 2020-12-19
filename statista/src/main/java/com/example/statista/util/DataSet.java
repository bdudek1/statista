package com.example.statista.util;

import java.util.List;

public class DataSet {
    private Long id;
    private List<Double> dataList;

    public DataSet(Long id, List<Double> dataList){
        this.id = id;
        this.dataList = dataList;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<Double> getDataList() { return dataList; }

    public void setDataList(List<Double> dataList) { this.dataList = dataList; }
}

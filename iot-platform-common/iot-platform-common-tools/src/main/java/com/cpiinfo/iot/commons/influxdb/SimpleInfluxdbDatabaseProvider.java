package com.cpiinfo.iot.commons.influxdb;


public class SimpleInfluxdbDatabaseProvider implements InfluxdbDatabaseProvider {

    private String database;
    
    public SimpleInfluxdbDatabaseProvider(String database) {
        this.database = database;
    }
    
    @Override
    public String database() {
        return database;
    }
}

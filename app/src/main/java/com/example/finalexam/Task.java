package com.example.finalexam;

import java.util.Date;

public class Task {

    private int _id;
    private String _name;
    private Date _date;
    private boolean _completed;

    public Task() {}

    public Task(int id, String name, Date date, boolean completed) {
        this._id = id;
        this._name = name;
        this._date = date;
        this._completed = completed;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public boolean is_completed() {
        return _completed;
    }

    public void set_completed(boolean _completed) {
        this._completed = _completed;
    }
}

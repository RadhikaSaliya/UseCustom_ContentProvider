package com.user.canopas.content_provider_use;

/**
 * Created by canopas on 26/08/17.
 */

public class User {
    String name, _id;
    String contact_no;

    public User(String _id, String name, String contact_no) {
        this._id = _id;
        this.name = name;
        this.contact_no = contact_no;
    }

    public String getName() {
        return name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String get_id() {
        return _id;
    }
}

package com.example.core.CommonModels;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class StateModel implements Serializable {

    private int id;
    private String name;

    public StateModel() {

    }

    public StateModel(int id) {
        this.id = id;
    }

    public StateModel(String name) {
        this.name = name;
    }

    public StateModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        StateModel stateModel = (StateModel) obj;
        return (this.id == stateModel.id || (stateModel.name != null && this.name.equals(stateModel.name)));
    }
}

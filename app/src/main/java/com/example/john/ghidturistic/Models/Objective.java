package com.example.john.ghidturistic.Models;

import java.io.Serializable;

/**
 * Created by John on 3/31/2017.
 */

public class Objective implements Serializable{

    private String name;
    private String description;
    private Position position;


    public Objective() {

    }

    public Objective(String name, String description, Position position) {
        this.name = name;
        this.description = description;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        Objective objective=(Objective)obj;
        if(!this.name.equals(objective.getName())){
            return false;
        }
        if(!this.position.equals(objective.getPosition())){
            return false;
        }
        return true;
    }
}

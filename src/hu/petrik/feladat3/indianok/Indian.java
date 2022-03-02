package hu.petrik.feladat3.indianok;

import java.util.ArrayList;

public class Indian {

    private String name;
    private String tribe;
    private String gender;
    private int age;
    private ArrayList<String> inventory;

    public Indian(String name, String tribe, String gender, int age, ArrayList<String> inventory) {
        this.name = name;
        this.tribe = tribe;
        this.gender = gender;
        this.age = age;
        this.inventory = inventory;
    }
    public Indian(String sor) {
        String[] data = sor.split(";");
        this.name = data[0];
        this.tribe = data[1];
        this.gender = data[2].equals("n")?"Nő":"Férfi";
        this.age = Integer.parseInt(data[3]);
        this.inventory = new ArrayList<>();
        for (int i = 4; i < data.length; i++) {
            this.inventory.add(data[i]);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTribe() {
        return tribe;
    }

    public void setTribe(String tribe) {
        this.tribe = tribe;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    private String getInventoryString(){
        StringBuilder boby = new StringBuilder();
        for (String item : inventory) {
            boby.append(";").append(item);
        }
        return boby.toString();
    }

    @Override
    public String toString() {
        return "Indian{" +
                "name='" + name + '\'' +
                ", tribe='" + tribe + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", inventory=" + getInventoryString() +
                '}';
    }

    public String toCsv() {
        return name + ';' + tribe + ';' + gender + ';' +age + getInventoryString();
    }

}

package com.example.Playpalv2.franciscoClassesForRegistrationVersion;

public class DropOutMenusReg3 {
   DogBreeds dogBreeds = new DogBreeds();
   private String[]breeds = dogBreeds.getBreeds();

    private  static  final String[] sex = new String[]{
        "Male", "Female"
    };

    private String[]age = new String[30];

    private String[]weight = new String[200];


    public DropOutMenusReg3() {
        createAgeList();
        createWeightList();
    }

    private void createAgeList() {
        int j;
        for(int i = 0; i < 30; i++){
            j = i + 1;
            age[i]= j+"";
        }
    }

    private void createWeightList(){
        int j;
        for(int i = 0; i < 200; i++){
            j = i + 1;
            weight[i]= j+"";
        }
    }

    public  String[] getSexList(){
        return sex;
    }

    public  String[] getAgeList(){
       return age;
    }
    public String[] getBreeds(){
        return breeds;
    }

    public String[] getWeightList(){
        return weight;
    }
}

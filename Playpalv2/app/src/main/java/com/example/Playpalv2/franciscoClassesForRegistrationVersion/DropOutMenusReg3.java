package com.example.Playpalv2.franciscoClassesForRegistrationVersion;

public class DropOutMenusReg3 {
   DogBreeds dogBreeds = new DogBreeds();
   private String[]breeds = dogBreeds.getBreeds();

    private  static  final String[] sex = new String[]{
        "Male", "Female"
    };

    private String[]age = new String[40];

    private String[]weight = new String[400];

    private String[]energyLevel = new String[10];

    public DropOutMenusReg3() {
        createAgeList();
        createWeightList();
        createEnergyLevelList();
    }

    private void createAgeList() {
        int j;
        for(int i = 0; i < 40; i++){
            j = i + 1;
            age[i]= j+"";
        }
    }

    private void createEnergyLevelList(){
        int j;
        for(int i = 0; i < 10; i++){
            j = i + 1;
            energyLevel[i]= j + "";
        }
    }

    private void createWeightList(){
        int j;
        for(int i = 0; i < 400; i++){
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
    public String[] getEnergyLevel(){return energyLevel;}
}

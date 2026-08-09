// Secil Uluderya
// CSA Final Project
// 5/27/26

//Import statements
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CancerAnalysis {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner input = new Scanner(System.in);
        File f = new File("breast-cancer-wisconsin-data_data.csv");
        Scanner s = new Scanner(f);

        //Create patient ArrayList
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        String[] data;
        int count = 0;

        //Create patients with information from data
        while(s.hasNextLine()){
            if(count < 1){
                String line = s.nextLine();
                count++;
            }
            else {
                String line = s.nextLine();
                data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String diagnosis = data[1];
                double radius = Double.parseDouble(data[2]);
                double texture = Double.parseDouble(data[3]);
                double perimeter = Double.parseDouble(data[4]);
                double area = Double.parseDouble(data[5]);
                double smoothness = Double.parseDouble(data[6]);
                double concavity = Double.parseDouble(data[8]);
                double concavityPoints = Double.parseDouble(data[9]);
                Patient p = new Patient(id, diagnosis, radius, texture, perimeter, area, smoothness, concavity, concavityPoints);
                patientList.add(p);
            }
        }


        //Introduction
        System.out.println("This program uses a breast cancer data set from the University of Wisconsin to help classify a new tumor as low, medium, or high risk of malignancy.");
        System.out.println();
        System.out.println("The dataset used for this analysis contains metrics from 569 breast cancer patients. It includes tumor features like benign/malignant classification,");
        System.out.println("mean radius, mean texture, mean area, mean perimeter, and mean smoothness.");

        System.out.println("The data can be used to help doctors classify tumors as either benign or malignant, given an analysis of the differences in their features. Below, I");
        System.out.println("consider their elements to form conclusions about whether new tumors are low, medium, or high risk.");
        System.out.println();

        String runProgram = "Y";

        while(runProgram.equals("Y") || runProgram.equals("y")){
            int firstOption = 1;
            System.out.println();
            System.out.print("Please enter 1 for dataset metrics or 2 for new tumor classification: ");
            firstOption = input.nextInt();

            //Confirm valid input
            while(firstOption != 1 && firstOption != 2){
                System.out.println();
                System.out.print("Invalid input. Please enter either 1 or 2: ");
                firstOption = input.nextInt();
            }

            //Option 1 -- ANALYTICS
            if(firstOption == 1){
                System.out.println();
                System.out.println("Here are some general calculations based off the current set. They are used to assign a 'score' to a new tumor, which helps with classification.");
                System.out.println("Mean Radius Benign: " + calculateMeanRadius(patientList, "B"));
                System.out.println("Mean Radius Malignant: " + calculateMeanRadius(patientList, "M"));
                System.out.println();
                System.out.println("Mean Texture Benign: " + calculateMeanTexture(patientList, "B"));
                System.out.println("Mean Texture Malignant: " + calculateMeanTexture(patientList, "M"));
                System.out.println();
                System.out.println("Mean Perimeter Benign: " + calculateMeanPerimeter(patientList, "B"));
                System.out.println("Mean Perimeter Malignant: " + calculateMeanPerimeter(patientList, "M"));
                System.out.println();
                System.out.println("Mean Area Benign: " + calculateMeanArea(patientList, "B"));
                System.out.println("Mean Area Malignant: " + calculateMeanArea(patientList, "M"));
                System.out.println();
                System.out.println("Mean Smoothness Benign: " + calculateMeanSmoothness(patientList, "B"));
                System.out.println("Mean Smoothness Malignant: " + calculateMeanSmoothness(patientList, "M"));
                System.out.println();
                System.out.println("Mean Concavity Benign: " + calculateMeanConcavity(patientList, "B"));
                System.out.println("Mean Concavity Malignant: " + calculateMeanConcavity(patientList, "M"));
                System.out.println();
                System.out.println("Mean Concavity Points Benign: " + calculateMeanCP(patientList, "B"));
                System.out.println("Mean Concavity Points Malignant: " + calculateMeanCP(patientList, "M"));

                System.out.println();
                System.out.println("The scoring method used in this program is moderately capable of marking benign tumors as low risk and highly capable of marking malignant tumors");
                System.out.println("as high risk. The scoring system could be shifted slightly to increase overall accuracy, but is meaningfully set up this way in order to ensure");
                System.out.println("that malignant tumors do not go understated. Approximately 42.2% of truly benign tumors in this dataset are labeled low risk and about 5% are");
                System.out.println("labeled high risk, which results in some false alarms. However, 90.6% of truly malignant tumors are correctly labeled as high risk, with the rest");
                System.out.println("classifying as medium risk. This is especially important for clinical usage, as it is far better to have false positives than to risk not treating");
                System.out.println("a malignant tumor properly.");
                System.out.println();
                input.nextLine();
                System.out.print("Would you like to re-run this program? (Y/N): ");
                runProgram = input.nextLine();

                //Confirm valid input
                while(!(runProgram.equals("Y")) && !(runProgram.equals("y")) && !(runProgram.equals("N")) && !(runProgram.equals("n"))){
                    System.out.print("Invalid input. Please enter Y for yes or N for no: ");
                    runProgram = input.nextLine();
                }

            }

            //Option 2 -- CLASSIFICATION
            else if(firstOption == 2){
                System.out.println();
                String answer = "Y";

                while(answer.equals("Y") || answer.equals("y")){
                    System.out.print("Please enter the patient ID (integer form): ");
                    int id = input.nextInt();
                    System.out.println();
                    System.out.println("Please enter the following tumor information.");
                    System.out.print("Mean radius: ");
                    double radius = input.nextDouble();

                    System.out.print("Mean texture: ");
                    double texture = input.nextDouble();

                    System.out.print("Mean perimeter: ");
                    double perimeter = input.nextDouble();

                    System.out.print("Mean area: ");
                    double area = input.nextDouble();

                    System.out.print("Mean smoothness: ");
                    double smoothness = input.nextDouble();

                    System.out.print("Mean concavity: ");
                    double concavity = input.nextDouble();

                    System.out.print("Mean concavity points: ");
                    double concavityPoints = input.nextDouble();
                    input.nextLine();

                    System.out.println();
                    Patient p = new Patient(id, radius, texture, perimeter, area, smoothness, concavity, concavityPoints);
                    int score = calculateScore(p);
                    performClassification(p, score);

                    //Prompt for re-run
                    System.out.print("Would you like to enter a new patient? (Y/N): ");
                    answer = input.nextLine();

                    //Confirm valid input
                    while(!(answer.equals("Y")) && !(answer.equals("y")) && !(answer.equals("N")) && !(answer.equals("n"))){
                        System.out.print("Invalid input. Please enter Y for yes or N for no: ");
                        answer = input.nextLine();
                    }
                }

                System.out.println();
                System.out.print("Would you like to re-run this program? (Y/N): ");
                runProgram = input.nextLine();
                
                //Confirm valid input
                while(!(runProgram.equals("Y")) && !(runProgram.equals("y")) && !(runProgram.equals("N")) && !(runProgram.equals("n"))){
                    System.out.print("Invalid input. Please enter Y for yes or N for no: ");
                    runProgram = input.nextLine();
                }
            }
        }
    }

    //Calculates mean radius in dataset
    public static double calculateMeanRadius(ArrayList<Patient> arr, String s){
        if(s.equals("B")){
            double meanRadiusBenign = 0.0;
            int benignCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("B")){
                    benignCount++;
                    meanRadiusBenign += p.getRadius();
                }
            }
            return meanRadiusBenign / benignCount;
        }
        else{
            double meanRadiusMalignant = 0.0;
            int malignantCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("M")){
                    malignantCount++;
                    meanRadiusMalignant += p.getRadius();
                }
            }
            return meanRadiusMalignant / malignantCount;
        }
    }

    //Calculates mean texture in dataset
    public static double calculateMeanTexture(ArrayList<Patient> arr, String s){
        if(s.equals("B")){
            double meanTextureBenign = 0.0;
            int benignCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("B")){
                    benignCount++;
                    meanTextureBenign += p.getTexture();
                }
            }
            return meanTextureBenign / benignCount;
        }
        else{
            double meanTextureMalignant = 0.0;
            int malignantCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("M")){
                    malignantCount++;
                    meanTextureMalignant += p.getTexture();
                }
            }
            return meanTextureMalignant / malignantCount;
        }
    }

    //Calculates mean perimeter in dataset
    public static double calculateMeanPerimeter(ArrayList<Patient> arr, String s){
        if(s.equals("B")){
            double meanPerimeterBenign = 0.0;
            int benignCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("B")){
                    benignCount++;
                    meanPerimeterBenign += p.getPerimeter();
                }
            }
            return meanPerimeterBenign / benignCount;
        }
        else{
            double meanPerimeterMalignant = 0.0;
            int malignantCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("M")){
                    malignantCount++;
                    meanPerimeterMalignant += p.getPerimeter();
                }
            }
            return meanPerimeterMalignant / malignantCount;
        }
    }

    //Calculates mean area in dataset
    public static double calculateMeanArea(ArrayList<Patient> arr, String s){
        if(s.equals("B")){
            double meanAreaBenign = 0.0;
            int benignCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("B")){
                    benignCount++;
                    meanAreaBenign += p.getArea();
                }
            }
            return meanAreaBenign / benignCount;
        }
        else{
            double meanAreaMalignant = 0.0;
            int malignantCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("M")){
                    malignantCount++;
                    meanAreaMalignant+= p.getArea();
                }
            }
            return meanAreaMalignant / malignantCount;
        }
    }

    //Calculates mean smoothness in dataset
    public static double calculateMeanSmoothness(ArrayList<Patient> arr, String s){
        if(s.equals("B")){
            double meanSmoothnessBenign = 0.0;
            int benignCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("B")){
                    benignCount++;
                    meanSmoothnessBenign += p.getSmoothness();
                }
            }
            return meanSmoothnessBenign / benignCount;
        }
        else{
            double meanSmoothnessMalignant = 0.0;
            int malignantCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("M")){
                    malignantCount++;
                    meanSmoothnessMalignant += p.getSmoothness();
                }
            }
            return meanSmoothnessMalignant / malignantCount;
        }
    }

    //Calculates mean concavity in dataset
    public static double calculateMeanConcavity(ArrayList<Patient> arr, String s){
        if(s.equals("B")){
            double meanConcavityBenign = 0.0;
            int benignCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("B")){
                    benignCount++;
                    meanConcavityBenign += p.getConcavity();
                }
            }
            return meanConcavityBenign / benignCount;
        }
        else{
            double meanConcavityMalignant = 0.0;
            int malignantCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("M")){
                    malignantCount++;
                    meanConcavityMalignant += p.getConcavity();
                }
            }
            return meanConcavityMalignant / malignantCount;
        }
    }

    //Calculates mean concavity points in dataset
    public static double calculateMeanCP(ArrayList<Patient> arr, String s){
        if(s.equals("B")){
            double meanCPBenign = 0.0;
            int benignCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("B")){
                    benignCount++;
                    meanCPBenign += p.getConcavityPoints();
                }
            }
            return meanCPBenign / benignCount;
        }
        else{
            double meanCPMalignant = 0.0;
            int malignantCount = 0;
            for(int x = 0; x < arr.size(); x++){
                Patient p = arr.get(x);
                if(p.getDiagnosis().equals("M")){
                    malignantCount++;
                    meanCPMalignant += p.getConcavityPoints();
                }
            }
            return meanCPMalignant / malignantCount;
        }
    }

    //Calculates tumor score of a patient
    public static int calculateScore(Patient p){
        int score = 0;
        //Mean Radius -- NOT CONSIDERED, REDUNDANCY WITH AREA
        //Mean Perimeter -- NOT CONSIDERED, REDUNDANCY WITH AREA
        //Mean Smoothness -- NOT CONSIDERED, INSIGNIFICANT
        //Mean Texture
        double textureLowCutoff = 18.0;
        double textureHighCutoff = 21.0;
        //Mean Area
        double areaLowCutoff = 450.0;
        double areaHighCutoff = 950.0;
        //Mean Concavity
        double concavityLowCutoff = 0.046;
        double concavityHighCutoff = 0.161;
        //Mean Concavity Points
        double concavityPointsLowCutoff = 0.026;
        double concavityPointsHighCutoff = 0.088;

        double texture = p.getTexture();
        double area = p.getArea();
        double concavity = p.getConcavity();
        double concavityPoints = p.getConcavityPoints();

        //Score texture
        if(texture < textureLowCutoff){
            score += 0;
        }
        else {
            score++;
        }
        //Score area
        if(area < areaLowCutoff){
            score += 0;
        }
        else if(area < areaHighCutoff){
            score++;
        }
        else{
            score += 2;
        }
        //Score concavity
        if(concavity < concavityLowCutoff){
            score += 0;
        }
        else if(concavity < concavityHighCutoff){
            score++;
        }
        else{
            score += 2;
        }
        //Score concavity points
        if(concavityPoints < concavityPointsLowCutoff){
            score += 0;
        }
        else if(concavityPoints < concavityPointsHighCutoff){
            score++;
        }
        else{
            score += 2;
        }

        return score;
    }

    //Classifies risk level of tumor for patient p with score s
    public static void performClassification(Patient p, int s){
        //Score of 0-1 = low concern
        //Score of 2-4 = medium concern
        //Score of 5-7 = high concern
        if(s == 0 || s == 1){
            System.out.println(p.toString());
            System.out.println("Risk of Malignancy: LOW");
        }
        else if(s == 2 || s == 3){
            System.out.println(p.toString());
            System.out.println("Risk of Malignancy: MEDIUM");
        }
        else{
            System.out.println(p.toString());
            System.out.println("Risk of Malignancy: HIGH");
        }
    }
}

//Side notes:
//Did not include tumor perimeter and radius due to redundancy with area
//While this program is helpful, it must be further modified for clinical purposes. Some of that modification has been done with the high-concern skew
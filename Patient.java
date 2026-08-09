public class Patient {
    //Initialize variables
    private int patientId;
    private String diagnosis;
    private double radius;
    private double texture;
    private double perimeter;
    private double area;
    private double smoothness;
    private double concavity;
    private double concavityPoints;

    //Construct a patient with diagnosis known
    public Patient(int id, String d, double r, double t, double p, double a, double s, double c, double cp){
        patientId = id;
        diagnosis = d;
        radius = r;
        texture = t;
        perimeter = p;
        area = a;
        smoothness = s;
        concavity = c;
        concavityPoints = cp;
    }

    //Construct a patient with diagnosis unknown
    public Patient(int id, double r, double t, double p, double a, double s, double c, double cp){
        patientId = id;
        radius = r;
        texture = t;
        perimeter = p;
        area = a;
        smoothness = s;
        concavity = c;
        concavityPoints = cp;
    }

    public int getPatientId(){
        return patientId;
    }
    public String getDiagnosis(){
        return diagnosis;
    }
    public double getRadius(){
        return radius;
    }
    public double getTexture(){
        return texture;
    }
    public double getPerimeter(){
        return perimeter;
    }
    public double getArea(){
        return area;
    }
    public double getSmoothness(){
        return smoothness;
    }
    public double getConcavity(){
        return concavity;
    }
    public double getConcavityPoints(){
        return concavityPoints;
    }

    //Returns neat description of patient
    public String toString(){
        return "Patient " + patientId + " has a tumor with mean radius " + radius + ", mean texture " + texture + ", mean perimeter " + perimeter + ", mean area " + area + ", mean smoothness " + smoothness + " , mean concavity " + concavity + " , and mean concavity points of " + concavityPoints + ".";
    }
}

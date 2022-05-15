package spring.Models;

public class CourseData {
    private String code;
    private String alphaCode;
    private int numericCode;
    private String name;
    private double rate;
    private String date;
    private double inverseRate;

    public CourseData() {
    }

    public CourseData(String code, String alphaCode, int numericCode, String name, double rate, String date, double inverseRate) {
        this.code = code;
        this.alphaCode = alphaCode;
        this.numericCode = numericCode;
        this.name = name;
        this.rate = rate;
        this.date = date;
        this.inverseRate = inverseRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public int getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInverseRate() {
        return inverseRate;
    }

    public void setInverseRate(double inverseRate) {
        this.inverseRate = inverseRate;
    }
}

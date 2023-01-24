package basic;

/**
 * SpaceItem Constructor
 * 
 * @param entity      WorkingItem entity
 * @param conCode     WorkingItem country code
 * @param year        WorkingItem year
 * @param yearlyHours Working average annual working hours
 * @author J.Bian
 */

public class WorkingItem {
    private String entity;
    private String conCode;
    private String year;
    private double yearlyHours;

    public String getEntity() {
        return entity;
    }

    public WorkingItem(String entity, String conCode, String year, double yearlyLaunches) {
        this.entity = entity;
        this.conCode = conCode;
        this.year = year;
        this.yearlyHours = yearlyLaunches;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getYearlyHours() {
        return yearlyHours;
    }

    public void setYearlyHours(double yearlyHours) {
        this.yearlyHours = yearlyHours;
    }

}

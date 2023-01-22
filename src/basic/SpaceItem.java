package basic;

public class SpaceItem {
    String entity;
    String conCode;
    int year;
    int yearlyLaunches;

    public String getEntity() {
        return entity;
    }
    public SpaceItem(String entity, String conCode, int year, int yearlyLaunches) {
        this.entity = entity;
        this.conCode = conCode;
        this.year = year;
        this.yearlyLaunches = yearlyLaunches;
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
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getYearlyLaunches() {
        return yearlyLaunches;
    }
    public void setYearlyLaunches(int yearlyLaunches) {
        this.yearlyLaunches = yearlyLaunches;
    }

}

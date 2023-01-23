package basic;

public class SpaceItem {
    private String entity;
    private String conCode;
    private String year;
    private int yearlyLaunches;

    public String getEntity() {
        return entity;
    }
    public SpaceItem(String entity, String conCode, String year, int yearlyLaunches) {
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
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public int getYearlyLaunches() {
        return yearlyLaunches;
    }
    public void setYearlyLaunches(int yearlyLaunches) {
        this.yearlyLaunches = yearlyLaunches;
    }

}

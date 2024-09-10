package com.example.m322.utils.data;

/**
 * Represents a data set with information about a municipality.
 * 
 * @author Joshua Kunz
 * @version 1.0
 * @since 10.10.2024
 */
public class DataSet {
    private String gemeinde;
    private int bfs_nummer;
    private int jahr;
    private String indikator;
    private double wert;

    /**
     * Default constructor for DataSet.
     */
    public DataSet() {
    }

    /**
     * Constructor for DataSet with all parameters.
     *
     * @param gemeinde   the name of the municipality
     * @param bfs_nummer the BFS number of the municipality
     * @param jahr       the year of the data set
     * @param indikator  the indicator of the data set
     * @param wert       the value of the data set
     */
    public DataSet(String gemeinde, int bfs_nummer, int jahr, String indikator, double wert) {
        this.gemeinde = gemeinde;
        this.bfs_nummer = bfs_nummer;
        this.jahr = jahr;
        this.indikator = indikator;
        this.wert = wert;
    }

    /**
     * Constructor for DataSet with all parameters as Objects.
     *
     * @param gemeinde   the name of the municipality
     * @param bfs_nummer the BFS number of the municipality
     * @param jahr       the year of the data set
     * @param indikator  the indicator of the data set
     * @param wert       the value of the data set
     */
    public DataSet(Object gemeinde, Object bfs_nummer, Object jahr, Object indikator, Object wert) {
        this.gemeinde = (String) gemeinde;
        this.bfs_nummer = Integer.parseInt((String) bfs_nummer);
        this.jahr = Integer.parseInt((String) jahr);
        this.indikator = (String) indikator;
        this.wert = (Double) wert;
    }

    /**
     * Get the name of the municipality.
     *
     * @return the name of the municipality
     */
    public String getGemeinde() {
        return gemeinde;
    }

    /**
     * Set the name of the municipality.
     *
     * @param gemeinde the name of the municipality
     */
    public void setGemeinde(String gemeinde) {
        this.gemeinde = gemeinde;
    }

    /**
     * Get the BFS number of the municipality.
     *
     * @return the BFS number of the municipality
     */
    public int getBfs_nummer() {
        return bfs_nummer;
    }

    /**
     * Set the BFS number of the municipality.
     *
     * @param bfs_nummer the BFS number of the municipality
     */
    public void setBfs_nummer(int bfs_nummer) {
        this.bfs_nummer = bfs_nummer;
    }

    /**
     * Get the year of the data set.
     *
     * @return the year of the data set
     */
    public int getJahr() {
        return jahr;
    }

    /**
     * Set the year of the data set.
     *
     * @param jahr the year of the data set
     */
    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    /**
     * Get the indicator of the data set.
     *
     * @return the indicator of the data set
     */
    public String getIndikator() {
        return indikator;
    }

    /**
     * Set the indicator of the data set.
     *
     * @param indikator the indicator of the data set
     */
    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    /**
     * Get the value of the data set.
     *
     * @return the value of the data set
     */
    public double getWert() {
        return wert;
    }

    /**
     * Set the value of the data set.
     *
     * @param wert the value of the data set
     */
    public void setWert(double wert) {
        this.wert = wert;
    }

    /**
     * Generates a hash code for the DataSet object.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gemeinde == null) ? 0 : gemeinde.hashCode());
        result = prime * result + bfs_nummer;
        result = prime * result + jahr;
        result = prime * result + ((indikator == null) ? 0 : indikator.hashCode());
        long temp;
        temp = Double.doubleToLongBits(wert);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Checks if this DataSet object is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataSet other = (DataSet) obj;
        if (gemeinde == null) {
            if (other.gemeinde != null)
                return false;
        } else if (!gemeinde.equals(other.gemeinde))
            return false;
        if (bfs_nummer != other.bfs_nummer)
            return false;
        if (jahr != other.jahr)
            return false;
        if (indikator == null) {
            if (other.indikator != null)
                return false;
        } else if (!indikator.equals(other.indikator))
            return false;
        if (Double.doubleToLongBits(wert) != Double.doubleToLongBits(other.wert))
            return false;
        return true;
    }

    /**
     * Returns a string representation of the DataSet object.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "DataSet [gemeinde=" + gemeinde + ", bfs_nummer=" + bfs_nummer + ", jahr=" + jahr + ", indikator="
                + indikator + ", wert=" + wert + "]";
    }
}

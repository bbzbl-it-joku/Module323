package com.example.m322.data;

public class DataSet {
    private String gemeinde;
    private int bfs_nummer;
    private int jahr;
    private String indikator;
    private double wert;

    public DataSet() {
    }

    public DataSet(String gemeinde, int bfs_nummer, int jahr, String indikator, double wert) {
        this.gemeinde = gemeinde;
        this.bfs_nummer = bfs_nummer;
        this.jahr = jahr;
        this.indikator = indikator;
        this.wert = wert;
    }

    public DataSet(Object gemeinde, Object bfs_nummer, Object jahr, Object indikator, Object wert) {
        this.gemeinde = (String) gemeinde;
        this.bfs_nummer = Integer.parseInt((String) bfs_nummer);
        this.jahr = Integer.parseInt((String) jahr);
        this.indikator = (String) indikator;
        this.wert = (Double) wert;
    }

    public String getGemeinde() {
        return gemeinde;
    }

    public void setGemeinde(String gemeinde) {
        this.gemeinde = gemeinde;
    }

    public int getBfs_nummer() {
        return bfs_nummer;
    }

    public void setBfs_nummer(int bfs_nummer) {
        this.bfs_nummer = bfs_nummer;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public String getIndikator() {
        return indikator;
    }

    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    public double getWert() {
        return wert;
    }

    public void setWert(double wert) {
        this.wert = wert;
    }

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

    @Override
    public String toString() {
        return "DataSet [gemeinde=" + gemeinde + ", bfs_nummer=" + bfs_nummer + ", jahr=" + jahr + ", indikator="
                + indikator + ", wert=" + wert + "]";
    }
}

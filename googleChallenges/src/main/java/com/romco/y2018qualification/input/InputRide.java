package com.romco.y2018qualification.input;

public class InputRide {
    int id;
    int startX, startY;
    int endX, endY;
    
    int earliestStart;
    int latestFinish;
    
    public InputRide(int id, int startX, int startY, int endX, int endY, int earliestStart, int latestFinish) {
        this.id = id;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.earliestStart = earliestStart;
        this.latestFinish = latestFinish;
    }
    
    public int getStartX() {
        return startX;
    }
    
    @Override
    public String toString() {
        return  "{id="+id+
                ", s=" + startX +
                "," + startY +
                "; e=" + endX +
                "," + endY +
                "; erlStr=" + earliestStart +
                ", ltstFin=" + latestFinish + '}';
    }
    
    public int getId() {
        return id;
    }
    
    public int getStartY() {
        return startY;
    }
    
    public int getEndX() {
        return endX;
    }
    
    public int getEndY() {
        return endY;
    }
    
    public int getEarliestStart() {
        return earliestStart;
    }
    
    public int getLatestFinish() {
        return latestFinish;
    }
    
    public int getDistance() {
        return (endX - startX) + (endY - startY);
    }
}

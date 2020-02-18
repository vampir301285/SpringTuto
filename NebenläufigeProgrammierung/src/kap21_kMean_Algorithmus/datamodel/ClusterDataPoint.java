package kap21_kMean_Algorithmus.datamodel;

public class ClusterDataPoint extends DataPoint
{
  private int clusterId = 0;
  
  public ClusterDataPoint(double x, double y)
  {
    super(x,y);
  }
  
  public ClusterDataPoint(DataPoint p, int clusterId)
  {
    super(p.x,p.y);
    this.setClusterId( clusterId );
  }
  
  public int getClusterId()
  {
    return this.clusterId;
  }
  
  public void setClusterId(int id)
  {
    this.clusterId = id;
  }
  
  @Override
  public String toString()
  {
    return "("+x+"/"+y+": "+ this.getClusterId() + ")";
  }
}

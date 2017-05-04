package layer;

public class Processer{
	
	private int N = Integer.valueOf(System.getenv("NUM_REPLICAS"));
	private int fault=Integer.valueOf(System.getenv("FAULT"));;// fault is the f of the equations on consensus
	private int rid=0;
	private int wid=0;
	private int writeAck;
	
	public int getRid(){
		rid=rid+1;
		return rid;
	}
	
	
	
public boolean writeVerification(int sentwid){
		
		if(sentwid==wid){
			writeAck=writeAck+1;
			writeCheck();
			return false;
		}
		else if(sentwid>wid){
			wid=sentwid;
			return true;
		} 
		return false;
		
		//won't do nothing, not even write 	
		
	}
	public void writeCheck(){
		if ((N+fault)/2 == writeAck){
			//TODO send the writeReturn back to the client
			writeAck=0;
		}
		
	}
}
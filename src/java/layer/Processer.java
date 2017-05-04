package layer;

public class Processer{
	
	private int N = Integer.valueOf(System.getenv("NUM_REPLICAS"));
	private int fault=Integer.valueOf(System.getenv("FAULT"));;// fault is the f of the equations on consensus
	
	private int wid;
	private int writeAck;
	
	
	
	
public void writeVerification(int sentwid){
		
		if(sentwid==wid){
			writeAck=writeAck+1;
			writeCheck();
		}
		else if(sentwid>wid){
			//TODO:do nothing (proceed to writing)
		} 
		
		//won't do nothing, not even write 	
		
	}
	public void writeCheck(){
		if ((N+fault)/2 == writeAck){
			//TODO send the writeReturn back to the client
			writeAck=0;
		}
		
	}
}
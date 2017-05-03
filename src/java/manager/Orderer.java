package manager;

import java.util.LinkedList;
import envelope.Envelope;

class Orderer{
	
	private Integer rid;
	private Integer wts;
	//not sure if we can make the processes pass throw here before the function calls
	
	/*private LinkedList <Envelope> FIFO = new LinkedList<Envelope>();
	private Integer operationNumber;
	
	public void addFIFO(Envelope e){
		FIFO.add(e);
	}
	public Envelope getFIFO(){
		return FIFO.getFirst();
	}*/
	
	public Integer incrementRID(){
		rid=rid+1;
		return rid;
	}
	public Integer incrementWTS(){
		wts=wts+1;
		return wts;
	}
	
	
	//NEed a method that sends the Ack here in jax-ws lib
	public void Ack(){
		
	}
}
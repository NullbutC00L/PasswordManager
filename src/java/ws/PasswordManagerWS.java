package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import envelope.Envelope;
import exception.PasswordManagerException;
import exception.PubKeyAlreadyExistsException;

@WebService  
public interface PasswordManagerWS {  

  @WebMethod
  public Envelope register( Envelope envelope ) throws PasswordManagerException, PubKeyAlreadyExistsException;

  @WebMethod
  public Envelope put( Envelope envelope ) throws PasswordManagerException;

  @WebMethod
  public Envelope get( Envelope envelope ) throws PasswordManagerException;
}  

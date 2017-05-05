package manager;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import domain.DomainCredentials;
import exception.CredentialsNotFoundException;
import exception.PasswordManagerException;
import exception.PubKeyAlreadyExistsException;
import exception.PubKeyNotFoundException;
import exception.UserAlreadyOnDomainException;

public class Manager {

  private HashMap<ByteBuffer, HashMap<ByteBuffer, ArrayList<DomainCredentials>>> _pubKeys = new HashMap<ByteBuffer, HashMap<ByteBuffer, ArrayList<DomainCredentials>>>();

  private HashMap<ByteBuffer, ArrayList<DomainCredentials>> getPubKey(byte[] pubKey) {
    return _pubKeys.get(ByteBuffer.wrap(pubKey));
  }

  private void addPubKey(byte[] pubKey) {
    _pubKeys.putIfAbsent(ByteBuffer.wrap(pubKey), new HashMap< ByteBuffer, ArrayList<DomainCredentials>>());
  }

  private ArrayList<DomainCredentials> getDomain(byte[] pubKey, byte[] domain) {
    if ( getPubKey(pubKey) == null )
      return null;

    return getPubKey(pubKey).get(ByteBuffer.wrap(domain));
  }

  private void addDomain(byte[] pubKey, byte[] domain) {
    getPubKey(pubKey).putIfAbsent(ByteBuffer.wrap(domain), new ArrayList<DomainCredentials>());
  }

  public byte[][] searchPassword(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerException{
    if ( getPubKey(pubKey) == null )
      throw new PubKeyNotFoundException();

    ArrayList<DomainCredentials> domainList = getDomain(pubKey, domain); 

    if ( domainList == null )
      throw new CredentialsNotFoundException();

    for (DomainCredentials dc : domainList) {
      if(Arrays.equals(dc.getUsername(), username)){
        byte[] ts = new String(""+dc.getTimeStamp()).getBytes();
        return new byte[][] { dc.getPassword(), dc.getTripletHash(), ts, dc.getSignature() };
      }
    }

    throw new CredentialsNotFoundException();
  }


  public void insert(byte[] pubKey, byte[] domain, byte[] username, byte[] password, byte[] tripletHash, int timeStamp, byte[] signature) throws PasswordManagerException {

    if ( getPubKey(pubKey) == null )
      throw new PubKeyNotFoundException();

    try {
      searchPassword(pubKey, domain, username);
      throw new UserAlreadyOnDomainException();
    }
    catch(CredentialsNotFoundException e){
      addDomain(pubKey, domain);
      getDomain(pubKey, domain).add(new DomainCredentials(username, password, tripletHash, timeStamp, signature));
    }
  } 

  public void register(byte[] pubKey) throws PasswordManagerException{
    if( getPubKey(pubKey) != null )
      throw new PubKeyAlreadyExistsException();

    addPubKey(pubKey);
  } 

  //public void delete(byte[] pubKey, byte[] domain, byte[] username, byte[] password) throws PasswordManagerException{
  //if ( getPubKey(pubKey) == null )
  //throw new PubKeyNotFoundException();

  //ArrayList<DomainCredentials> domainList = getDomain(pubKey, domain);

  //if ( domainList == null )
  //throw new CredentialsNotFoundException();

  //for (DomainCredentials dc : domainList) {
  //if(Arrays.equals(dc.getUsername(), username) && Arrays.equals(dc.getPassword(), password)){
  //domainList.remove(dc);
  //return;
  //}
  //}

  //throw new CredentialsNotFoundException();
  //}
}

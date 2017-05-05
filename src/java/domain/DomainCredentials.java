package domain;

public class DomainCredentials {
  private byte[] _username;
  private byte[] _password;
  private byte[] _tripleHash;
  private int _timeStamp;
  private byte[] _signature;

  public DomainCredentials(byte[] username, byte[] password, byte[] tripletHash, int timeStamp, byte[] signature){
    _username = username;
    _password = password;
    _tripleHash = tripletHash;
    _timeStamp = timeStamp;
    _signature = signature;
  }

  public byte[] getUsername(){
    return _username;
  }

  public byte[] getPassword(){
    return _password;
  }

  public byte[] getTripletHash(){
    return _tripleHash;
  }

  public int getTimeStamp(){
    return _timeStamp;
  }

  public byte[] getSignature(){
    return _signature;
  }
}

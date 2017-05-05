package domain;

public class DomainCredentials {
  private byte[] _username;
  private byte[] _password;
  private byte[] _tripleHash;

  public DomainCredentials(byte[] username, byte[] password, byte[] tripletHash){
    _username = username;
    _password = password;
    _tripleHash = tripletHash;
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
}

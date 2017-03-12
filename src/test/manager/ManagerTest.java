package manager;

import exception.CredentialsNotFoundException;
import exception.PubKeyAlreadyExistsException;

import static org.junit.Assert.*;

import org.junit.Test;

public class ManagerTest {
  private Manager _manager = new Manager();

  @Test(expected = CredentialsNotFoundException.class)
  public void searchEmpty() {
    _manager.searchPassword("should".getBytes(), "return".getBytes(), "exception".getBytes());
  }
  
  @Test
  public void registerSuccess() {
    _manager.register("thisIsAPublicKey".getBytes());
  }

  @Test
  public void insertSuccess() {
    _manager.register("thisIsAPublicKey".getBytes());
    _manager.insert("thisIsAPublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
    byte[] res = _manager.searchPassword("thisIsAPublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    assertArrayEquals("password".getBytes(), res);
  }
  
  @Test(expected = PubKeyAlreadyExistsException.class)
  public void registerFail() {
    _manager.register("thisIsAPublicKey".getBytes());
    _manager.register("thisIsAPublicKey".getBytes());
  }
}

package manager;

import exception.CredentialsNotFoundException;
import exception.PubKeyAlreadyExistsException;
import exception.PubKeyNotFoundException;
import exception.UserAlreadyOnDomainException;

import static org.junit.Assert.*;

import org.junit.Test;

public class ManagerTest {
  private Manager _manager = new Manager();

  @Test(expected = PubKeyNotFoundException.class)
    public void searchNotRegistered() {
      _manager.searchPassword("should".getBytes(), "return".getBytes(), "exception".getBytes());
    }

  @Test(expected = CredentialsNotFoundException.class)
    public void searchWhileEmpty() {
      _manager.register("PublicKey".getBytes());
      _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    }

  @Test(expected = CredentialsNotFoundException.class)
    public void searchNotFound() {
      _manager.register("PublicKey".getBytes());
      _manager.insert("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes(), "password".getBytes());
      _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    }

  public void searchSuccess() {
    _manager.register("PublicKey".getBytes());
    _manager.insert("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes(), "password".getBytes());
    byte[] password = _manager.searchPassword("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes());
    assertArrayEquals("password".getBytes(), password);
  }

  @Test(expected = PubKeyNotFoundException.class)
    public void insertNotRegistered() {
      _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
    }

  @Test
  public void insertSuccess() {
    _manager.register("PublicKey".getBytes());
    _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
    byte[] res = _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    assertArrayEquals("password".getBytes(), res);
  }

  @Test(expected = UserAlreadyOnDomainException.class)
    public void insertTwice() {
      _manager.register("PublicKey".getBytes());
      _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
      _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "newPassword".getBytes());
    }

  @Test(expected = PubKeyNotFoundException.class)
    public void deleteNotRegistered() {
      _manager.delete("PublicKey".getBytes(), "should".getBytes(), "return".getBytes(), "exception".getBytes());
    }

  @Test(expected = CredentialsNotFoundException.class)
    public void deleteWhileEmpty() {
      _manager.register("PublicKey".getBytes());
      _manager.delete("PublicKey".getBytes(), "should".getBytes(), "return".getBytes(), "exception".getBytes());
    }

  @Test(expected = CredentialsNotFoundException.class)
    public void deleteNotFound() {
      _manager.register("PublicKey".getBytes());
      _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
      _manager.delete("PublicKey".getBytes(), "instagram".getBytes(), "return".getBytes(), "exception".getBytes());
    }

  @Test
  public void deleteSuccess() {
    _manager.register("PublicKey".getBytes());
    _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
    _manager.delete("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
  }

  @Test
  public void registerSuccess() {
    _manager.register("PublicKey".getBytes());
  }

  @Test(expected = PubKeyAlreadyExistsException.class)
    public void registerTwice() {
      _manager.register("PublicKey".getBytes());
      _manager.register("PublicKey".getBytes());
    }
}

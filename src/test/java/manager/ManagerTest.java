package manager;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import exception.CredentialsNotFoundException;
import exception.PasswordManagerException;
import exception.PubKeyAlreadyExistsException;
import exception.PubKeyNotFoundException;
import exception.UserAlreadyOnDomainException;

public class ManagerTest {
  private Manager _manager = new Manager();

  @Test(expected = PubKeyNotFoundException.class)
    public void searchNotRegistered() throws PasswordManagerException {
      _manager.searchPassword("should".getBytes(), "return".getBytes(), "exception".getBytes());
    }

  @Test(expected = CredentialsNotFoundException.class)
    public void searchWhileEmpty() throws PasswordManagerException {
      _manager.register("PublicKey".getBytes());
      _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    }

  @Test(expected = CredentialsNotFoundException.class)
    public void searchNotFound() throws PasswordManagerException{
      _manager.register("PublicKey".getBytes());
      _manager.insert("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
      _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    }

  public void searchSuccess() throws PasswordManagerException{
    _manager.register("PublicKey".getBytes());
    _manager.insert("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
    byte[][] password = _manager.searchPassword("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes());
    assertArrayEquals("password".getBytes(), password[0]);
  }

  @Test(expected = PubKeyNotFoundException.class)
    public void insertNotRegistered() throws PasswordManagerException{
      _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
    }

  @Test
  public void insertSuccess() throws PasswordManagerException{
    _manager.register("PublicKey".getBytes());
    _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
    byte[][] res = _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    assertArrayEquals("password".getBytes(), res[0]);
  }

  @Test(expected = UserAlreadyOnDomainException.class)
    public void insertTwice() throws PasswordManagerException{
      _manager.register("PublicKey".getBytes());
      _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
      _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "newPassword".getBytes(), "tripletHash".getBytes());
    }

  //@Test(expected = PubKeyNotFoundException.class)
    //public void deleteNotRegistered() {
      //_manager.delete("PublicKey".getBytes(), "should".getBytes(), "return".getBytes(), "exception".getBytes());
    //}

  //@Test(expected = CredentialsNotFoundException.class)
    //public void deleteWhileEmpty() {
      //_manager.register("PublicKey".getBytes());
      //_manager.delete("PublicKey".getBytes(), "should".getBytes(), "return".getBytes(), "exception".getBytes());
    //}

  //@Test(expected = CredentialsNotFoundException.class)
    //public void deleteNotFound() {
      //_manager.register("PublicKey".getBytes());
      //_manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
      //_manager.delete("PublicKey".getBytes(), "instagram".getBytes(), "return".getBytes(), "exception".getBytes());
    //}

  //@Test
  //public void deleteSuccess() {
    //_manager.register("PublicKey".getBytes());
    //_manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
    //_manager.delete("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes());
  //}

  @Test
  public void registerSuccess() throws PasswordManagerException{
    _manager.register("PublicKey".getBytes());
  }

  @Test(expected = PubKeyAlreadyExistsException.class)
    public void registerTwice() throws PasswordManagerException{
      _manager.register("PublicKey".getBytes());
      _manager.register("PublicKey".getBytes());
    }
}

package manager;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.CredentialsNotFoundException;
import exception.PasswordManagerException;
import exception.PubKeyAlreadyExistsException;
import exception.PubKeyNotFoundException;
import exception.UserAlreadyOnDomainException;

public class ManagerTest {
  private Manager _manager = new Manager();

   @Rule
	public ExpectedException exception = ExpectedException.none();
   
  @Test
    public void searchNotRegistered() throws PasswordManagerException {
	  exception.expect(PubKeyNotFoundException.class);
      
	  _manager.searchPassword("should".getBytes(), "return".getBytes(), "exception".getBytes());
    }

  @Test
    public void searchWhileEmpty() throws PasswordManagerException {
	  exception.expect(CredentialsNotFoundException.class);
	  
	  _manager.register("PublicKey".getBytes());
      _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    }

  @Test
    public void searchNotFound() throws PasswordManagerException {
	  exception.expect(CredentialsNotFoundException.class);
	  
	  _manager.register("PublicKey".getBytes());
      _manager.insert("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
      _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    }

  public void searchSuccess() throws PasswordManagerException {
    _manager.register("PublicKey".getBytes());
    _manager.insert("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
    byte[][] password = _manager.searchPassword("PublicKey".getBytes(), "instagram".getBytes(), "username".getBytes());
    assertArrayEquals("password".getBytes(), password[0]);
  }

  @Test
    public void insertNotRegistered() throws PasswordManagerException {
	  exception.expect(PubKeyNotFoundException.class);
	  
	  _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
    }

  @Test
  public void insertSuccess() throws PasswordManagerException {
    _manager.register("PublicKey".getBytes());
    _manager.insert("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes(), "password".getBytes(), "tripletHash".getBytes());
    byte[][] res = _manager.searchPassword("PublicKey".getBytes(), "facebook".getBytes(), "username".getBytes());
    assertArrayEquals("password".getBytes(), res[0]);
  }

  @Test
    public void insertTwice() throws PasswordManagerException {
	  exception.expect(UserAlreadyOnDomainException.class);
	  
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
  public void registerSuccess() throws PasswordManagerException {
    _manager.register("PublicKey".getBytes());
  }

  @Test
    public void registerTwice() throws PasswordManagerException {
	  exception.expect(PubKeyAlreadyExistsException.class);
	  
      _manager.register("PublicKey".getBytes());
      _manager.register("PublicKey".getBytes());
    }
}

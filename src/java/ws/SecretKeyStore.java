package ws;

import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.crypto.SecretKey;

public class SecretKeyStore {
	
	private HashMap<ByteBuffer,SecretKey > sercretKeys = new HashMap<ByteBuffer, SecretKey>();
	
	protected SecretKey get(byte[] pubKey) {
		return sercretKeys.get(ByteBuffer.wrap(pubKey));
	}
	
	protected void put(byte[] pubKey, SecretKey secretKey) {
		sercretKeys.putIfAbsent(ByteBuffer.wrap(pubKey), secretKey);
	}
}

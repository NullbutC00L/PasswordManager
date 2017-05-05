
package ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="usernameHash" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="domainHash" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="tripletHash" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="counter" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="publicKey" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="wts" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "message", propOrder = {
    "password",
    "usernameHash",
    "domainHash",
    "tripletHash",
    "counter",
    "publicKey",
    "wts",
    "rid"
})
public class Message {

    protected byte[] password;
    protected byte[] usernameHash;
    protected byte[] domainHash;
    protected byte[] tripletHash;
    protected int counter;
    protected byte[] publicKey;
    protected int wts;
    protected int rid;

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPassword(byte[] value) {
        this.password = value;
    }

    /**
     * Gets the value of the usernameHash property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getUsernameHash() {
        return usernameHash;
    }

    /**
     * Sets the value of the usernameHash property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setUsernameHash(byte[] value) {
        this.usernameHash = value;
    }

    /**
     * Gets the value of the domainHash property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDomainHash() {
        return domainHash;
    }

    /**
     * Sets the value of the domainHash property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDomainHash(byte[] value) {
        this.domainHash = value;
    }

    /**
     * Gets the value of the tripletHash property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getTripletHash() {
        return tripletHash;
    }

    /**
     * Sets the value of the tripletHash property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setTripletHash(byte[] value) {
        this.tripletHash = value;
    }

    /**
     * Gets the value of the counter property.
     * 
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Sets the value of the counter property.
     * 
     */
    public void setCounter(int value) {
        this.counter = value;
    }

    /**
     * Gets the value of the publicKey property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the value of the publicKey property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPublicKey(byte[] value) {
        this.publicKey = value;
    }

    /**
     * Gets the value of the wts property.
     * 
     */
    public int getWts() {
        return wts;
    }

    /**
     * Sets the value of the wts property.
     * 
     */
    public void setWts(int value) {
        this.wts = value;
    }

    /**
     * Gets the value of the rid property.
     * 
     */
    public int getRid() {
        return rid;
    }

    /**
     * Sets the value of the rid property.
     * 
     */
    public void setRid(int value) {
        this.rid = value;
    }

}

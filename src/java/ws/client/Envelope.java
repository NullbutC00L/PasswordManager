
package ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for envelope complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="envelope">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://ws/}message"/>
 *         &lt;element name="HMAC" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="DHPublicKey" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "envelope", propOrder = {
    "message",
    "hmac",
    "dhPublicKey"
})
public class Envelope {

    @XmlElement(required = true)
    protected Message message;
    @XmlElement(name = "HMAC", required = true)
    protected byte[] hmac;
    @XmlElement(name = "DHPublicKey", required = true)
    protected byte[] dhPublicKey;

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link Message }
     *     
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link Message }
     *     
     */
    public void setMessage(Message value) {
        this.message = value;
    }

    /**
     * Gets the value of the hmac property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHMAC() {
        return hmac;
    }

    /**
     * Sets the value of the hmac property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHMAC(byte[] value) {
        this.hmac = value;
    }

    /**
     * Gets the value of the dhPublicKey property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDHPublicKey() {
        return dhPublicKey;
    }

    /**
     * Sets the value of the dhPublicKey property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDHPublicKey(byte[] value) {
        this.dhPublicKey = value;
    }

}


package ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PasswordManagerException_QNAME = new QName("http://ws/", "PasswordManagerException");
    private final static QName _PutResponse_QNAME = new QName("http://ws/", "putResponse");
    private final static QName _GetResponse_QNAME = new QName("http://ws/", "getResponse");
    private final static QName _RegisterResponse_QNAME = new QName("http://ws/", "registerResponse");
    private final static QName _Get_QNAME = new QName("http://ws/", "get");
    private final static QName _PubKeyAlreadyExistsException_QNAME = new QName("http://ws/", "PubKeyAlreadyExistsException");
    private final static QName _Put_QNAME = new QName("http://ws/", "put");
    private final static QName _Register_QNAME = new QName("http://ws/", "register");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PasswordManagerException }
     * 
     */
    public PasswordManagerException createPasswordManagerException() {
        return new PasswordManagerException();
    }

    /**
     * Create an instance of {@link PutResponse }
     * 
     */
    public PutResponse createPutResponse() {
        return new PutResponse();
    }

    /**
     * Create an instance of {@link GetResponse }
     * 
     */
    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link Get }
     * 
     */
    public Get createGet() {
        return new Get();
    }

    /**
     * Create an instance of {@link PubKeyAlreadyExistsException }
     * 
     */
    public PubKeyAlreadyExistsException createPubKeyAlreadyExistsException() {
        return new PubKeyAlreadyExistsException();
    }

    /**
     * Create an instance of {@link Put }
     * 
     */
    public Put createPut() {
        return new Put();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link Envelope }
     * 
     */
    public Envelope createEnvelope() {
        return new Envelope();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PasswordManagerException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "PasswordManagerException")
    public JAXBElement<PasswordManagerException> createPasswordManagerException(PasswordManagerException value) {
        return new JAXBElement<PasswordManagerException>(_PasswordManagerException_QNAME, PasswordManagerException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "putResponse")
    public JAXBElement<PutResponse> createPutResponse(PutResponse value) {
        return new JAXBElement<PutResponse>(_PutResponse_QNAME, PutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "getResponse")
    public JAXBElement<GetResponse> createGetResponse(GetResponse value) {
        return new JAXBElement<GetResponse>(_GetResponse_QNAME, GetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Get }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "get")
    public JAXBElement<Get> createGet(Get value) {
        return new JAXBElement<Get>(_Get_QNAME, Get.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PubKeyAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "PubKeyAlreadyExistsException")
    public JAXBElement<PubKeyAlreadyExistsException> createPubKeyAlreadyExistsException(PubKeyAlreadyExistsException value) {
        return new JAXBElement<PubKeyAlreadyExistsException>(_PubKeyAlreadyExistsException_QNAME, PubKeyAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Put }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "put")
    public JAXBElement<Put> createPut(Put value) {
        return new JAXBElement<Put>(_Put_QNAME, Put.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

}

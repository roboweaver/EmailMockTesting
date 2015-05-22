package com.accuweaver.email;

import static java.lang.System.getProperties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import static javax.mail.Message.RecipientType.TO;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import static javax.mail.Session.getInstance;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * EmailSender class handles the work of sending an email
 *
 * @author rweaver
 */
public class EmailSender {

    private InternetAddress toAddress;
    private InternetAddress fromAddress;
    private String subject;
    private Message msg;
    private Session session;
    private Transport transport;

    /**
     * Constructor ...
     *
     * @param fromAddress From address
     * @param toAddress To address
     * @param subject Subject of message
     * @throws AddressException
     */
    EmailSender(String fromAddress, String toAddress, String subject) throws AddressException, MessagingException {
        this.fromAddress = new InternetAddress(fromAddress);
        this.toAddress = new InternetAddress(toAddress);
        this.subject = subject;
    }

    /**
     * Send the message ...
     *
     * @param textMessage text body
     * @param htmlMessage HTML body
     * @throws MessagingException
     */
    public void sendMessage(String textMessage, String htmlMessage) throws MessagingException {

        setMsg(new MimeMessage(getSession()));
        getMsg().setFrom(getFromAddress());
        getMsg().addRecipient(TO, getToAddress());
        getMsg().setSubject(getSubject());

        Multipart mp = new MimeMultipart("alternative");
        {
            //setup text part of message
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(textMessage, "us-ascii");
            mp.addBodyPart(textPart);

            //setup html part of message
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlMessage, "text/html");
            mp.addBodyPart(htmlPart);
        }

        getMsg().setContent(mp);
        getMsg().saveChanges();

        if (connect()) {
            try {
                getTransport().sendMessage(getMsg(), getMsg().getAllRecipients());
            } catch (MessagingException ex) {
                LOG.log(Level.SEVERE, "Problem with sending message", ex);
                throw ex;
            } finally {
                this.close();
            }
        } else {
            throw new MessagingException("No connection");
        }

    }

    /**
     * Close the email transport ...
     */
    public void close() {
        if (this.getTransport() != null) {
            if (this.getTransport().isConnected()) {
                try {
                    getTransport().close();
                } catch (MessagingException ex) {
                    LOG.log(Level.SEVERE, "Error closing the email connection", ex);
                }
            }
        }
    }

    /**
     * Get the transport ...
     *
     * @return the transport
     */
    public Transport getTransport() {
        if (transport == null) {
            try {
                transport = getSession().getTransport("smtp");
            } catch (NoSuchProviderException ex) {
                LOG.log(Level.SEVERE, "Can't get transport", ex);
            }
        }
        return transport;
    }

    /**
     * Set the transport ...
     *
     * @param transport the transport to set
     */
    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get the session ...
     *
     * @return the session
     */
    public Session getSession() {
        if (session == null) {
            session = getInstance(getProperties());
        }
        return session;
    }

    /**
     * Set the session ...
     *
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Get the to address ...
     *
     * @return the toAddress
     */
    public InternetAddress getToAddress() {
        return toAddress;
    }

    /**
     * Set the to address ...
     *
     * @param toAddress the toAddress to set
     */
    public void setToAddress(InternetAddress toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * Get the from address
     *
     * @return the fromAddress
     */
    public InternetAddress getFromAddress() {
        return fromAddress;
    }

    /**
     * Set the from address
     *
     * @param fromAddress the fromAddress to set
     */
    public void setFromAddress(InternetAddress fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * Get the subject string ....
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject string
     *
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get the message object
     *
     * @return the msg
     */
    public Message getMsg() {
        return msg;
    }

    /**
     * Set the message object ...
     *
     * @param msg the msg to set
     */
    public void setMsg(Message msg) {
        this.msg = msg;
    }
    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(EmailSender.class.getName());

    /**
     * Returns true if we are still connected ...
     *
     * @return
     */
    public boolean isConnected() {
        boolean connected = false;
        if (this.getTransport() != null){
            connected = this.getTransport().isConnected();
        }
        return connected;
    }

    /**
     * Connect
     *
     * @return
     */
    public boolean connect() {
        boolean connected = false;

        try {
            if (this.getTransport() != null) {
                this.getTransport().connect();
                connected = true;
            }
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, "Error connecting", ex);
        } catch (IllegalStateException ex){
            Logger.getLogger(EmailSender.class.getName()).log(Level.WARNING, "Already connected", ex);
            connected = true;
        }
        return connected;
    }

}

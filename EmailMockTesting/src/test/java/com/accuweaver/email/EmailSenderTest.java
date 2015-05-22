package com.accuweaver.email;

import java.sql.ResultSet;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rweaver
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ResultSet.class})
@PowerMockIgnore("javax.management.*")
public class EmailSenderTest {

    private EmailSender instance;
    private static Session session;

    private static final String LOCALHOST = "127.0.0.1";
    private static GreenMail mailServer;
    private static Properties props;

    public EmailSenderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        mailServer = new GreenMail(ServerSetupTest.SMTP);
        mailServer.start();
        
        // create the javax.mail stack with session, message and transport ..
        props = System.getProperties();
        props.put("mail.smtp.host", LOCALHOST);
        props.put("mail.smtp.port", ServerSetupTest.SMTP.getPort());
        session = Session.getInstance(props, null);
        
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws MessagingException {

        instance = new EmailSender("foo@bar.com", "bar@foo.com", "Subject line");
        instance.setSession(session);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of sendMessage method, of class EmailSender.
     *
     * We expect this to fail because we have no connection.
     * @throws javax.mail.MessagingException
     */
    @Test
    public void testSendMessage() throws MessagingException{
        System.out.println("sendMessage");
        String textMessage = "Foo";
        String htmlMessage = "Bar";
        instance.getTransport().connect(LOCALHOST, ServerSetupTest.SMTP.getPort(), null, null);

        instance.sendMessage(textMessage, htmlMessage);
    }

    /**
     * Test of close method, of class EmailSender.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        instance.close();
    }

    /**
     * Test of getTransport method, of class EmailSender.
     */
    @Test
    @Ignore
    public void testGetTransport() {
        System.out.println("getTransport");
        Transport expResult = null;
        Transport result = instance.getTransport();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTransport method, of class EmailSender.
     */
    @Test
    public void testSetTransport() {
        System.out.println("setTransport");
        Transport transport = null;
        instance.setTransport(transport);
    }

    /**
     * Test of getSession method, of class EmailSender.
     */
    @Test
    public void testGetSession() {
        System.out.println("getSession");
        Session expResult = null;
        Session result = instance.getSession();
    }

    /**
     * Test of setSession method, of class EmailSender.
     */
    @Test
    public void testSetSession() {
        System.out.println("setSession");
        Session session = null;
        instance.setSession(session);
    }

    /**
     * Test of getToAddress method, of class EmailSender.
     *
     * @throws javax.mail.internet.AddressException
     */
    @Test
    public void testGetToAddress() throws AddressException {
        System.out.println("getToAddress");
        InternetAddress expResult = new InternetAddress("bar@foo.com");
        InternetAddress result = instance.getToAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of setToAddress method, of class EmailSender.
     */
    @Test
    public void testSetToAddress() {
        System.out.println("setToAddress");
        InternetAddress toAddress = null;
        instance.setToAddress(toAddress);
    }

    /**
     * Test of getFromAddress method, of class EmailSender.
     */
    @Test
    public void testGetFromAddress() throws AddressException {
        System.out.println("getFromAddress");
        InternetAddress expResult = new InternetAddress("foo@bar.com");
        InternetAddress result = instance.getFromAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFromAddress method, of class EmailSender.
     */
    @Test
    public void testSetFromAddress() {
        System.out.println("setFromAddress");
        InternetAddress fromAddress = null;
        instance.setFromAddress(fromAddress);
    }

    /**
     * Test of getSubject method, of class EmailSender.
     */
    @Test
    public void testGetSubject() {
        System.out.println("getSubject");
        String expResult = "Subject line";
        String result = instance.getSubject();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSubject method, of class EmailSender.
     */
    @Test
    public void testSetSubject() {
        System.out.println("setSubject");
        String subject = "";
        instance.setSubject(subject);
    }

    /**
     * Test of getMsg method, of class EmailSender.
     */
    @Test
    public void testGetMsg() {
        System.out.println("getMsg");
        Message expResult = null;
        Message result = instance.getMsg();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMsg method, of class EmailSender.
     */
    @Test
    public void testSetMsg() {
        System.out.println("setMsg");
        Message msg = null;
        instance.setMsg(msg);
    }

    /**
     * Test of isConnected method, of class EmailSender.
     */
    @Test
    public void testIsConnected() {
        System.out.println("isConnected");
        boolean expResult = false;
        boolean result = instance.isConnected();
        assertEquals(expResult, result);
    }

    /**
     * Test of connect method, of class EmailSender.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        boolean expResult = true;
        try {
            instance.getTransport().connect(LOCALHOST, ServerSetupTest.SMTP.getPort(), null, null);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean result = instance.connect();
        assertEquals(expResult, result);
    }

}

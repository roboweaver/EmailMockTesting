# EmailMockTesting
Example of using GreenMail and mocking to unit test a Java EMail class

Most web applications I've done have some sort of class that will send email, and in the past I've struggled with how to write a valid test for them.

Typically I would either set up a local SMTP server, or rely on actually sending the email to some address I control.

The problem with that approach is that it is really reliant on the email server setup, and doesn't lend itself to continuous integration.

I've tried mocking the calls that would send the email, which has the limitation of not actually telling me whether the email would have worked or not.

Enter GreenMail (http://www.icegreen.com/greenmail/) which was written to do exactly this. It is a test suite of email servers for testing your code.

This project is an example of using GreenMail and mocking to test sending email. The email class was a refactor of a long procedural bit of code that was entirely untestable (except with the integration type testing I described before).

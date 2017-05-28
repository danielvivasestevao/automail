# automail
Send one mail to multiple recipients without CC and BCC. One sender address, one recipient address for each mail.


A friend of mine needed to send out a mail to a whole bunch of people asking for gigs for his band. However, he didn't want to use Cc and Bcc for that, because he'd have to specify one main recipient all other recipients would see, which would've looked weird. Instead he copy and pasted the same mail, added the same attachment, copy and pasted the next recipient's address... for six hours.

So I created this command line tool which does exactly that. Just put the relevant data for the mail -- the subject, the text and a list of recipients -- as text files (subject.txt, text.txt, recipients.txt) into the same folder as the generated Jar file. In order to start the magic, use the following command in the directory of the Jar file:

`java -jar automail-0.1-SNAPSHOT-jar-with-dependencies.jar "<username>" "<password with escaped special characters>" "<path to attachment>" "<sender address>"`

And automail will start sending out one mail per minute.

At the time of writing, the program only works for Web.de addresses, although support for Hotmail/MSN is built in, but the source code has to be adjusted in order to use it. If the need arises, I will incorporate configurations for other mail service providers and another parameter to choose your provider.

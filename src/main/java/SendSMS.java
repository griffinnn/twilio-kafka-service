import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {

    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";


    public static void SendTwilioSMS(String recipientNumber, String fromNumber, String messageText){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(recipientNumber),
                new PhoneNumber(fromNumber),
                messageText).create();

        System.out.println("--------------------------");
        System.out.println("SMS dispatched to Twilio");
        System.out.println("Message SID is: " + message.getSid());

    }

//    public static void main(String[] args) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        SendTwilioSMS("07711047857","+44 1925 320583", "Sent from IntelliJ");
//    }

}

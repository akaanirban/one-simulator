package core;
import java.util.ArrayList;

public class PayloadMessage extends Message {

    /** Payload of the message i.e. content */
    private double payload;
    /** Tag associated with the message */
    private ArrayList<Integer> messageTag = new ArrayList<Integer>();

    /**
     * Creates a new Message with payload and tag corresponding to the nodes.
     * Extends the Message class
     * @param from Who the message is (originally) from
     * @param to Who the message is (originally) to
     * @param id Message identifier (must be unique for message but
     * 	will be the same for all replicates of the message)
     * @param size Size of the message (in bytes)
     */

    public PayloadMessage(DTNHost from, DTNHost to, String id, int size, double content, ArrayList<Integer> tag){
        super(from, to, id, size);
        this.messageTag = tag;
        this.payload =  content;
    }

    /** Return the payload*/
    public double getPayload() {
        return payload;
    }

    /** Return the tag of the message*/
    public ArrayList<Integer> getMessageTag() {
        return messageTag;
    }
}

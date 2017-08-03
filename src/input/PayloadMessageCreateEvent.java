package input;

import core.DTNHost;
import core.Message;
import core.PayloadMessage;
import core.World;

import java.util.ArrayList;

public class PayloadMessageCreateEvent extends MessageCreateEvent {
    private int size;
    private int responseSize;
    /** Payload of the message i.e. content */
    private double payload;
    /** Tag associated with the message */
    private ArrayList<Integer> messageTag = new ArrayList<Integer>();
    /**
     * Creates a message creation event with a optional response request and payload
     * @param from The creator of the message
     * @param to Where the message is destined to
     * @param id ID of the message
     * @param size Size of the message
     * @param responseSize Size of the requested response message or 0 if
     * no response is requested
     * @param time Time, when the message is created
     */
    public PayloadMessageCreateEvent(int from, int to, String id, int size,
                                     int responseSize, double time, double content, ArrayList tag) {
        super(from, to, id,size, responseSize, time, content, tag);
        /*this.size = size;
        this.responseSize = responseSize;
        this.payload = content;
        this.messageTag = tag;*/
    }


    /**
     * Creates the message this event represents.
     */
    @Override
    public void processEvent(World world) {
        DTNHost to = world.getNodeByAddress(this.toAddr);
        DTNHost from = world.getNodeByAddress(this.fromAddr);

        Message m = new PayloadMessage(from, to, this.id, this.size, this.payload, this.messageTag);
        m.setResponseSize(this.responseSize);
        from.createNewMessage(m);
    }

    @Override
    public String toString() {
        return super.toString() + " [" + fromAddr + "->" + toAddr + "] " +
                "size:" + size + " CREATE" + messageTag.toString();
    }
}

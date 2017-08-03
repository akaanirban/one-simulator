package report;

import core.DTNHost;
import core.Message;
import core.SimError;
import core.UpdateListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Node Phi matrix. Reports the energy level of all
 * (or only some, see {@link #REPORTED_NODES}) nodes every
 * configurable-amount-of seconds (see {@link #GRANULARITY}).
 * To get the last result, set granularity to (simulationtime -1) second
 */
public class MessagesPerHostReport extends SnapshotReport
        implements UpdateListener {

    public static final String HEADER =
            "# Content and TAG of the messages in the vehicles ";
    public MessagesPerHostReport(){
        write(HEADER);
    }
    @Override
    protected void writeSnapshot(DTNHost h) {
        double payload = h.getAggregateMessage() != null? h.getAggregateMessage().getPayload(): 0;
        ArrayList tag = h.getAggregateMessage() != null? h.getAggregateMessage().getMessageTag(): new ArrayList(Collections.nCopies(1,0));
       //write("#Node: "+ h.toString() + ", Aggregate Payload: " + payload + " Tag: " + tag);
        write("#Node: "+ h.toString());
        //String Messages = "";
        for(Message m : h.getMessageCollection()){ //this is the Phi matrix
            //Messages = Messages + " " + m.getId()+ "|";
            write(m.getMessageTag()+": "+ m.getPayload());
            //write(m.getMessageTag().subList(10, m.getMessageTag().size())+" "+ m.getPayload());
        }
        //int noofmessages = h.getNrofMessages();
        //write(h.toString() + " noofmessages = " +  format(noofmessages) + Messages);
        write("\n");
    }

}

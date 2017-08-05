package routing;

import core.*;
import input.MessageEventGenerator;
import util.Tuple;

import java.util.*;

public class PayloadMessageRouter extends ActiveRouter {
    /**
     * Constructor. Creates a new message router based on the settings in
     * the given Settings object. Based on Epidemic router
     * @param s The settings object
     */
    public PayloadMessageRouter(Settings s) {
        super(s);
        //TODO: read&use epidemic router specific settings (if any)
    }

    //TODO: read and use a different implementation than blindly copying epidemic router

    /**From paper: Decentralised Context sharing in Vehicular Delay Tolerant Networks with
     * Compressed Sensing, Xiew. K et. al., ICDCS, 2016*/
    /**
     * returns an aggregate message build from a reference message and rest of the messages*/
    public Message generateAggregateMessage(Message newMessage){
        DTNHost thisHost = getHost();
        Random rand = new Random();
        //String id = "AGG" + (rand.nextInt(50) + 1);
        String id = "AGG" + thisHost.getAddress()+ "_"+ newMessage.getTo().getAddress()  ;
        Message newAggregateMessage = new Message(thisHost,newMessage.getTo(),id,
                newMessage.getSize(), newMessage.getResponseSize(),
                new ArrayList<Integer>(Collections.nCopies(newMessage.getMessageTag().size(),0)));


        /*Algorithm from the paper by reshuffling the messages*/
        int totalMessages = getMessageCollection().size();
        List<Message> allMessages = new ArrayList(getMessageCollection());
        int i = rand.nextInt(totalMessages)+1;
        int L = i;
        int j;
        while (i < L + totalMessages){
            if(i != totalMessages){
                j = i % totalMessages;
            }
            else{
                j = totalMessages;
            }
            newAggregateMessage.copyFrom(redundancyAvoidance(newAggregateMessage, allMessages.get(j-1)));
            i = i + 1;
        }

        ///*Iterate normally over all the messages*/
        //for(Message m : getMessageCollection()){
        //    newAggregateMessage.copyFrom(redundancyAvoidance(newAggregateMessage, m));
        //    //newAggregateMessage = redundancyAvoidance(newAggregateMessage, m);
        //}
        return newAggregateMessage;
    }



    // return first message id there is redundancy else returns an aggregate message
    public Message redundancyAvoidance(Message aggregate, Message b){
        int tag_size = b.getMessageTag().size();
        for(int i =0; i<tag_size; i++){
            if(aggregate.getMessageTag().get(i) == b.getMessageTag().get(i))
                // Message a and b has redundant information
                if(aggregate.getMessageTag().get(i)==1)
                    return aggregate;
        }
        for(int i =0; i<tag_size; i++){
            aggregate.getMessageTag().
                    set(i, (aggregate.getMessageTag().get(i)+ b.getMessageTag().get(i)));
        }
        aggregate.setPayload(aggregate.getPayload()+ b.getPayload());
        return aggregate;
    }

    @Override
    public Message messageTransferred(String id, DTNHost from) {
        Message m = super.messageTransferred(id, from);
        /**
         * create a n aggregate message here in the host and
         * sets the aggregate message value of host.
         * */
        //get the new aggregate message for this router Host only if this is not a router
        if(!this.getHost().getHotspotFlag()){
            Message newAggregateMessage = generateAggregateMessage(m);
            if (newAggregateMessage != null){
                //set the new aggregate message in the Host property
                this.getHost().setAggregateMessage(newAggregateMessage);
                //add the new aggregate message in the host message buffer
                this.createNewMessage(newAggregateMessage);
            }
        }


        /**
         *  N.B. With application support the following if-block
         *  becomes obsolete, and the response size should be configured
         *  to zero.
         */
        // check if msg was for this host and a response was requested
        if (m.getTo() == getHost() && m.getResponseSize() > 0) {
            // generate a response message
            Message res = new Message(this.getHost(),m.getFrom(),
                    RESPONSE_PREFIX+m.getId(), m.getResponseSize(), m.getPayload(), m.getMessageTag());
            this.createNewMessage(res);
            this.getMessage(RESPONSE_PREFIX+m.getId()).setRequest(m);
        }

        return m;
        //return newAggregateMessage;
    }


    /**
     * Returns a list of message-connections tuples of the messages whose
     * recipient is some host that we're connected to at the moment.
     * @return a list of message-connections tuples
     */
    protected List<Tuple<Message, Connection>> getMessagesForConnected() {
       /*if (getNrofMessages() == 0 || getConnections().size() == 0) {
			 //no messages -> empty list
            return new ArrayList<Tuple<Message, Connection>>(0);
        }


        List<Tuple<Message, Connection>> forTuples =
                new ArrayList<Tuple<Message, Connection>>();
        for (Message m : getMessageCollection()) {
            for (Connection con : getConnections()) {
                DTNHost to = con.getOtherNode(getHost());
                //if (m.getTo() == to) {
                    forTuples.add(new Tuple<Message, Connection>(m,con));
                //}
            }
        }
*/


        if (this.getHost().getAggregateMessage() == null || getConnections().size() == 0){
            return new ArrayList<Tuple<Message, Connection>>(0);
        }
        List<Tuple<Message, Connection>> forTuples =
                new ArrayList<Tuple<Message, Connection>>();
        /*Send the aggregate message to all connections that the host is connected,
        thus effectively sending a single message per encounter*/
        for(Connection con: getConnections()){
            DTNHost to = con.getOtherNode(getHost());
            forTuples.add(new Tuple<Message, Connection>(this.getHost().getAggregateMessage(), con));
            /*forTuples.add(new Tuple<Message, Connection>(this.getHost().getAggregateMessage()
                    , new VBRConnection(this.getHost(), this.getHost().getInterface(1),
                    this.getHost().getAggregateMessage().getTo(),this.getHost().getAggregateMessage().getTo().getInterface(1) ) {
            }));*/
        }
        return forTuples;
    }

    /**
     * Copy constructor.
     * @param p The router prototype where setting values are copied from
     */
    protected PayloadMessageRouter(PayloadMessageRouter p) {
        super(p);
        //TODO: copy epidemic settings here (if any)
    }

    @Override
    public void update() {
        super.update();
        if (isTransferring() || !canStartTransfer()) {
            return; // transferring, don't try other connections yet
        }

        // Try first the messages that can be delivered to final recipient
        if (exchangeDeliverableMessages() != null) {
            //System.out.println("here");
            return; // started a transfer, don't try others (yet)
        }

        // then try any/all message to any/all connection
        this.tryAllMessagesToAllConnections();
    }

    /**
     * Shuffles a messages list so the messages are in random order.
     * @param messages The list to sort and shuffle
     */
    @Override
    protected void shuffleMessages(List<Message> messages) {
        if (messages.size() <= 1) {
            return; // nothing to shuffle
        }

        Random rng = new Random(SimClock.getIntTime());
        Collections.shuffle(messages, rng);
    }

    @Override
    public PayloadMessageRouter replicate() {
        return new PayloadMessageRouter(this);
    }

}

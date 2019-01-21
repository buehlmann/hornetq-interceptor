package ch.puzzle.messaging;

import java.util.UUID;
import java.util.logging.Logger;

import org.hornetq.api.core.Interceptor;
import org.hornetq.api.core.Message;
import org.hornetq.core.protocol.core.Packet;
import org.hornetq.core.protocol.core.impl.wireformat.SessionSendMessage;
import org.hornetq.spi.core.protocol.RemotingConnection;

public class AddUniqueIdInterceptor implements Interceptor{
    public static final String PROPERTY_KEY = "_AMQ_DUPL_ID";
    private static final Logger log = Logger.getLogger(AddUniqueIdInterceptor.class.getName());
    @Override
    public boolean intercept(Packet packet, RemotingConnection connection) {
        try {
            if (packet instanceof SessionSendMessage) {
                Message message = ((SessionSendMessage) packet).getMessage();
                String uid = message.getStringProperty(PROPERTY_KEY);
                if(uid == null || "".equals(uid)) {
                    String uuid = UUID.randomUUID().toString();
                    message.putStringProperty("_AMQ_DUPL_ID", uuid);
                    log.fine("Added JMS Property _AMQ_DUPL_ID=" + uuid + " to message " + message);
                } else {
                    log.finer("JMS property " + PROPERTY_KEY + " already set for message " + message +". Keeping existing value " + uid);
                }
            }
        } finally {
            return true; // continue in chain
        }
    }
}

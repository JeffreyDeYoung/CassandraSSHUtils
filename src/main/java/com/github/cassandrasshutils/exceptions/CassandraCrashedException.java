package com.github.cassandrasshutils.exceptions;

/**
 * Exception that indicates that Cassandra has crashed on a particular node.
 * @author Jeffrey DeYoung
 */
public class CassandraCrashedException extends Exception
{

    /**
     * Default error message for this exception.
     */
    private static final String ERROR_MESSAGE = "Cassandra has crashed: ";
    
    /**
     * Hostname on which Cassandra has crashed.
     */
    private final String hostName;
    
    /**
     * Constructor to be used when we don't have a root cause exception.
     * @param hostName Host Name of the node that has a crashed Cassandra. NOT an error message.
     */
    public CassandraCrashedException(String hostName)
    {
        super(ERROR_MESSAGE + hostName);
        this.hostName = hostName;
    }

    
    /**
     * Constructor to be used when we have a root cause exception.
     * @param cause Root cause of the Cassandra crash.
     * @param hostName Host Name of the node that has a crashed Cassandra. NOT an error message.
     */
    public CassandraCrashedException(Throwable cause, String hostName)
    {
        super(ERROR_MESSAGE + hostName, cause);
        this.hostName = hostName;
    }

    /**
     * Hostname on which Cassandra has crashed.
     * @return the hostName
     */
    public String getHostName()
    {
        return hostName;
    }
    
}

package com.github.cassandrasshutils.exceptions;

/**
 * Runtime Exception that indicates that Cassandra cannot be controlled on a
 * particular node due to permissions problems.
 *
 * @author Jeffrey DeYoung
 */
public class CassandraPermissionsException extends RuntimeException
{

    /**
     * Default error message for this exception.
     */
    private static final String ERROR_MESSAGE = "Permissions Problem Controlling Cassandra: ";

    /**
     * Hostname on which Cassandra is unable to be controlled due to permissions
     * issues.
     */
    private final String hostName;

    /**
     * Constructor to be used when we don't have a root cause exception.
     *
     * @param hostName Host Name of the node with the issue. NOT an error
     * message.
     */
    public CassandraPermissionsException(String hostName)
    {
        super(ERROR_MESSAGE + hostName);
        this.hostName = hostName;
    }

    /**
     * Hostname on which Cassandra has issues being controlled due to
     * permissions problems.
     *
     * @return the hostName
     */
    public String getHostName()
    {
        return hostName;
    }

}

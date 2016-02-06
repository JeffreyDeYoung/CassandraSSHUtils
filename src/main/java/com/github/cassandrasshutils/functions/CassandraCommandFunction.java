/*
 * Copyright 2016 jeffrey.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cassandrasshutils.functions;

import com.github.cassandrasshutils.command.RemoteCommandDao;
import com.github.cassandrasshutils.exceptions.CassandraCrashedException;
import com.github.cassandrasshutils.exceptions.CassandraPermissionsException;
import com.github.cassandrasshutils.exceptions.ConnectionException;
import java.io.IOException;

/**
 * Object for interacting with the Cassandra service on a node.
 *
 * @author jeffrey
 */
public class CassandraCommandFunction
{

    /**
     * Command line command to start Cassandra.
     */
    private static final String CASSANDRA_START_COMMAND = "service cassandra start";

    /**
     * Command line command to stop Cassandra.
     */
    private static final String CASSANDRA_STOP_COMMAND = "service cassandra stop";

    /**
     * Command line command to restart Cassandra.
     */
    private static final String CASSANDRA_RESTART_COMMAND = "service cassandra restart";

    /**
     * Command line command to get the service status of Cassandra.
     */
    private static final String CASSANDRA_STATUS_COMMAND = "service cassandra status";

    /**
     * Response message that indicates Cassandra is running.
     */
    private static final String EXPECTED_RUNNING_MESSAGE = "* Cassandra is running";

    /**
     * Response message that indicates that something has gone wrong with
     * Cassandra and it is not running, and probably cannot be started.
     */
    private static final String CASSANDRA_CRASHED_MESSAGE = "* could not access pidfile for Cassandra";

    /**
     * Response message substring that indicates that there is a permissions
     * problem controlling Cassandra.
     */
    private static final String CASSANDRA_PERMISSIONS_PROBLEM_MESSAGE = "Permission denied";

    /**
     * Starts an Cassandra service using the specified RemoteCommandDao.
     *
     * @param command RemoteCommandDao that is <b>already connected</b> to the
     * server you wish to start the Cassandra instance on.
     * @throws ConnectionException If we can't connect or there is an connection
     * problem to the server.
     * @throws IOException If there is an IO issue talking to the server.
     * @throws CassandraCrashedException If Cassandra has crashed (probably
     * unrecoverable) during startup.
     * @return True if Cassandra was started; false if it failed to start.
     */
    public static boolean startCassandra(RemoteCommandDao command) throws ConnectionException, IOException, CassandraCrashedException
    {
        if (isCassandraRunning(command))
        {
            return true;// don't try to start cassandra again if it's already running; will just slow us down
        }
        //cassandra is not currently running
        command.sendCommand(CASSANDRA_START_COMMAND);//make the call to start cassandra
        try
        {
            Thread.sleep(30000);//Sleep to let cassandra finish starting up
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return isCassandraRunning(command);//check to see if we succeeded
    }

    /**
     * Stops an Cassandra service using the specified RemoteCommandDao.
     *
     * @param command RemoteCommandDao that is <b>already connected</b> to the
     * server you wish to stop the Cassandra instance on.
     * @throws ConnectionException If we can't connect or there is an connection
     * problem to the server.
     * @throws IOException If there is an IO issue talking to the server.
     * @throws CassandraCrashedException If Cassandra has crashed (probably
     * unrecoverable) during startup.
     * @return True if Cassandra was stopped; false if it failed to stop
     * (unlikely, but perhaps possible if there's a permissions issue).
     */
    public static boolean stopCassandra(RemoteCommandDao command) throws ConnectionException, IOException, CassandraCrashedException
    {
        if (!isCassandraRunning(command))
        {
            return true;// don't try to stop cassandra; it is already stopped
        }
        //cassandra is currently running
        command.sendCommand(CASSANDRA_STOP_COMMAND);//make the call to stop cassandra
        try
        {
            Thread.sleep(1000);//Sleep to let cassandra finish stopping
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return !isCassandraRunning(command);//check to see if we succeeded
    }

    /**
     * Restarts an Cassandra service using the specified RemoteCommandDao.
     *
     * @param command RemoteCommandDao that is <b>already connected</b> to the
     * server you wish to restart the Cassandra instance on.
     * @throws ConnectionException If we can't connect or there is an connection
     * problem to the server.
     * @throws IOException If there is an IO issue talking to the server.
     * @throws CassandraCrashedException If Cassandra has crashed (probably
     * unrecoverable) during startup.
     * @return True if Cassandra was started; false if it failed to start.
     */
    public static boolean restartCassandra(RemoteCommandDao command) throws ConnectionException, IOException, CassandraCrashedException
    {
        command.sendCommand(CASSANDRA_RESTART_COMMAND);//make the call to restart cassandra
        try
        {
            Thread.sleep(30000);//Sleep to let cassandra finish starting up
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return isCassandraRunning(command);//check to see if we succeeded
    }

    /**
     * Checks to see if the Cassandra service is running using the
     * RemoteCommandDao.
     *
     * @param command RemoteCommandDao that is <b>already connected</b> to the
     * server you wish to start the Cassandra instance on.
     * @return True if Cassandra is running; false if it is not.
     * @throws ConnectionException If we can't connect or there is an connection
     * problem to the server.
     * @throws IOException If there is an IO issue talking to the server.
     * @throws CassandraCrashedException if Cassandra is neither running nor
     * stopped normally. Indicates that Cassandra has not successfully started,
     * and it probably in an unrecoverable state without some other change on
     * the node.
     */
    public static boolean isCassandraRunning(RemoteCommandDao command) throws ConnectionException, IOException, CassandraCrashedException
    {
        String statusResponse = command.sendCommand(CASSANDRA_STATUS_COMMAND);
        if (statusResponse.equals(EXPECTED_RUNNING_MESSAGE))
        {
            return true;
        }
        if (statusResponse.equals(CASSANDRA_CRASHED_MESSAGE))
        {
            throw new CassandraCrashedException(command.getHost());
        } else if (statusResponse.contains(CASSANDRA_PERMISSIONS_PROBLEM_MESSAGE))
        {
            throw new CassandraPermissionsException(command.getHost());
        } else
        {
            return false;
        }
    }
}

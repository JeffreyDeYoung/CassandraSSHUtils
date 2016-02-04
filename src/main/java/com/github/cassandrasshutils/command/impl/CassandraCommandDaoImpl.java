/*
 * Copyright 2016 Jeffrey DeYoung.
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
package com.github.cassandrasshutils.command.impl;

import com.github.cassandrasshutils.command.CassandraCommandDao;
import com.github.cassandrasshutils.command.domain.Server;

/**
 * Dao for executing remote Cassandra commands. Mostly nodetool related actions.
 *
 * @author jeffrey
 */
public class CassandraCommandDaoImpl implements CassandraCommandDao
{

    /**
     * Server that we will be executing our Cassandra commands against.
     */
    private Server server;

    /**
     * Constructor.
     *
     * @param server Server that we will be executing our Cassandra commands
     * against.
     */
    public CassandraCommandDaoImpl(Server server)
    {
        this.server = server;
    }

    /**
     * Gets the response from nodetool status.
     *
     * @return The nodetool status response as a string.
     */
    @Override
    public String getNodetoolStatus()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Gets the response from nodetool status for a particular keyspace.
     *
     * @param keyspace The keyspace you want to get the status for.
     * @return
     */
    @Override
    public String getNodetoolStatus(String keyspace)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

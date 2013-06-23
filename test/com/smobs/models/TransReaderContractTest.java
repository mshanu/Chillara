package com.smobs.models;


import junit.framework.TestCase;

public class TransReaderContractTest extends TestCase {

    public void testShouldTestTheCreateStatmentOfUserTransTable() {
        assertEquals("create table", TransReaderContract.createStatementForUserTrans());
    }
}

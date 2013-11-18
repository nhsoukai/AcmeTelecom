package com.acmetelecom;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: andreipetric
 * Date: 18/11/2013
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public interface CallInterface {
    String callee();

    int durationSeconds();

    // Needs to return following format: 11/15/13 4:21 PM
    String date();

    DateTime startTime();

    DateTime endTime();
}

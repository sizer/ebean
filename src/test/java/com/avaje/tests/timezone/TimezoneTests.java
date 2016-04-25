package com.avaje.tests.timezone;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

public class TimezoneTests {

  private final long now = 1460000000000L;

  private final Timestamp nowTs = new Timestamp(now);

  private final Calendar utcCalendar;

  public TimezoneTests() {
    utcCalendar = Calendar.getInstance();
    utcCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Ignore
  @Test
  public void rawJdbc() throws SQLException {

//    insert("Local");
//    insert("UTC");
//    insert("America/Los_Angeles");

    System.out.println("Local");
    fetch();

    System.out.println("UTC");
    setZone("UTC");
    fetch();

    System.out.println("LA");
    setZone("America/Los_Angeles");
    fetch();
  }

  private void fetch() throws SQLException {
    Transaction transaction = Ebean.beginTransaction();
    Connection connection = transaction.getConnection();
    PreparedStatement statement = connection.prepareStatement("select * from tztest");
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
      System.out.println(" zone:"+resultSet.getString("zone"));
      System.out.println("   ts:"+tsof(resultSet.getTimestamp("ts")));
      System.out.println(" tstz:"+tsof(resultSet.getTimestamp("tstz")));
      System.out.println("  ts1:"+tsof(resultSet.getTimestamp("ts1", utcCal())));
      System.out.println("tstz1:"+tsof(resultSet.getTimestamp("tstz1", utcCal())));
    }
    System.out.println("");
    resultSet.close();
    statement.close();
    transaction.end();
  }

  private String tsof(Timestamp timestamp) {
    return ""+timestamp.getTime()+","+timestamp.toString();
  }

  private void setZone(String zone) {
    TimeZone.setDefault(TimeZone.getTimeZone(zone));
  }

  private void insert(String zone) throws SQLException {

    String insert = "insert into tztest (zone, ts, tstz, ts1, tstz1) values (?,?,?,?,?)";

    if (!zone.equalsIgnoreCase("local")) {
      setZone(zone);
    }

    Transaction transaction = Ebean.beginTransaction();
    Connection connection = transaction.getConnection();
    PreparedStatement statement = connection.prepareStatement(insert);
    statement.setString(1, zone);
    statement.setTimestamp(2, nowTs);
    statement.setTimestamp(3, nowTs);
    statement.setTimestamp(4, nowTs, utcCal());
    statement.setTimestamp(5, nowTs, utcCal());
    statement.executeUpdate();

    transaction.commit();
  }

  private Calendar utcCal() {
    return (Calendar) utcCalendar.clone();
  }
}

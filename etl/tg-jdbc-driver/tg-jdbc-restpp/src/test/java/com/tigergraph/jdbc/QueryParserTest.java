package com.tigergraph.jdbc;

import com.tigergraph.jdbc.restpp.driver.QueryParser;

import static org.junit.Assert.assertTrue;
import org.apache.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import junit.framework.TestCase;

/**
 * Unit test for QueryParser
 * The corresponding TigerGraph demo could be found at:
 * https://docs.tigergraph.com/dev/gsql-examples/common-applications#example-2-page-rank
 */
public class QueryParserTest extends TestCase {

  public QueryParserTest(String name) {
    super(name);
  }

  public void testFormat() throws Exception {
    String query = "get Page(limit=?)";
    Map<Integer, Object> parameters = new HashMap<>(10);
    parameters.put(1, "3");
    StringBuilder sb = new StringBuilder();
    QueryParser parser = new QueryParser(null, query, parameters, 0, 0);
    sb.append(parser.getEndpoint()).append("\n");

    query = "get Page(filter=?)";
    parameters.clear();
    parameters.put(1, "page_id=1");
    parser = new QueryParser(null, query, parameters, 0, 0);
    sb.append(parser.getEndpoint()).append("\n");

    query = "get edges(Page, ?)";
    parameters.clear();
    parameters.put(1, "2");
    parser = new QueryParser(null, query, parameters, 0, 0);
    sb.append(parser.getEndpoint()).append("\n");

    query = "get edges(Page, ?, Linkto)";
    parameters.clear();
    parameters.put(1, "2");
    parser = new QueryParser(null, query, parameters, 0, 0);
    sb.append(parser.getEndpoint()).append("\n");

    query = "get edges(Page, ?, Linkto, Page)";
    parameters.clear();
    parameters.put(1, "2");
    parser = new QueryParser(null, query, parameters, 0, 0);
    sb.append(parser.getEndpoint()).append("\n");

    query = "get edge(Page, ?, Linkto, Page, ?)";
    parameters.clear();
    parameters.put(1, "2");
    parameters.put(2, "3");
    parser = new QueryParser(null, query, parameters, 0, 0);
    sb.append(parser.getEndpoint()).append("\n");

    query = "run pageRank(maxChange=?, maxIteration=?, dampingFactor=?)";
    parameters.clear();
    parameters.put(1, "0.001");
    parameters.put(2, 10);
    parameters.put(3, "0.15");
    parser = new QueryParser(null, query, parameters, 0, 0);
    sb.append(parser.getEndpoint()).append("\n");

    String formattedResult = sb.toString();
    InputStream expected =
      getClass().getClassLoader().getResourceAsStream("endpoint-expected.dat");
    assertEquals(IOUtils.toString(expected), formattedResult);
  }
}
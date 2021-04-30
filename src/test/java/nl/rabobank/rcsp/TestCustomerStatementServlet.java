package nl.rabobank.rcsp;

import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.*;

import org.junit.Test;
import org.mockito.Mockito;

public class TestCustomerStatementServlet extends Mockito {
	
    @Test
    public void testServlet_SUCCESFULL() throws Exception {
    	
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);  
        
        String json = "{\"Transaction reference\":\"77777\", \"Account number\":\"NL65RABO0123456789\", \"Start Balance\":\"100.00\", \"Mutation\":\"25.00\", \"Description\":\"Test\", \"End Balance\":\"125.00\"}";
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CustomerStatementServlet customerStatementServlet = new CustomerStatementServlet();
        customerStatementServlet.doPost(request, response);
        
        writer.flush(); 
        assertTrue(stringWriter.toString().contains("SUCCESFULL"));
    }

    @Test
    public void testServlet_DUPLICATE_REFERENCE() throws Exception {
    	
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);  
        
        String json = "{\"Transaction reference\":\"12345\", \"Account number\":\"NL65RABO0123456789\", \"Start Balance\":\"100.00\", \"Mutation\":\"25.00\", \"Description\":\"Test\", \"End Balance\":\"125.00\"}";
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CustomerStatementServlet customerStatementServlet = new CustomerStatementServlet();
        customerStatementServlet.doPost(request, response);
        
        writer.flush();
        assertTrue(stringWriter.toString().contains("DUPLICATE_REFERENCE"));
    }
    
    @Test
    public void testServlet_INCORRECT_END_BALANCE() throws Exception {
    	
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);  
        
        String json = "{\"Transaction reference\":\"88888\", \"Account number\":\"NL65RABO0123456789\", \"Start Balance\":\"100.00\", \"Mutation\":\"-25.00\", \"Description\":\"Test\", \"End Balance\":\"125.00\"}";
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CustomerStatementServlet customerStatementServlet = new CustomerStatementServlet();
        customerStatementServlet.doPost(request, response);
        
        writer.flush(); 
        assertTrue(stringWriter.toString().contains("INCORRECT_END_BALANCE"));
    }
    
    @Test
    public void testServlet_DUPLICATE_REFERENCE_INCORRECT_END_BALANCE() throws Exception {
    	
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);  
        
        String json = "{\"Transaction reference\":\"12345\", \"Account number\":\"NL65RABO0123456789\", \"Start Balance\":\"100.00\", \"Mutation\":\"-25.00\", \"Description\":\"Test\", \"End Balance\":\"125.00\"}";
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CustomerStatementServlet customerStatementServlet = new CustomerStatementServlet();
        customerStatementServlet.doPost(request, response);
        
        writer.flush(); 
        assertTrue(stringWriter.toString().contains("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE"));
    }
    
    @Test
    public void testServlet_BAD_REQUEST() throws Exception {
    	
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);  
        
        String json = "\"Transaction reference\":\"77777\", \"Account number\":\"NL65RABO0123456789\", \"Start Balance\":\"100.00\", \"Mutation\":\"25.00\", \"Description\":\"Test\", \"End Balance\":\"125.00\"}";
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CustomerStatementServlet customerStatementServlet = new CustomerStatementServlet();
        customerStatementServlet.doPost(request, response);
        
        writer.flush(); 
        assertTrue(stringWriter.toString().contains("BAD_REQUEST"));
    }
    
}
package com.rickletras.firstcrud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.rickletras.application.FirstcrudApplication;
import com.rickletras.application.firstcrud.model.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FirstcrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FirstcrudApplicationTests {
	@Autowired
    private TestRestTemplate restTemplate;
	
	@LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

	@Test
	public void contextLoads() {
	}
	
	@Test
    public void testGetTodosClientes() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/clientes",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }

   @Test
   public void testGetClienteId() {
       Cliente cliente = restTemplate.getForObject(getRootUrl() + "/clientes/1", Cliente.class);
       System.out.println(cliente.getNome());
       assertNotNull(cliente);
   }

   @Test
   public void testAdicionarCliente() throws ParseException {
       Cliente cliente = new Cliente();
       cliente.setCpf("123.456.789-01");
       cliente.setNome("teste");
       String s1 = "2019-07-20";
       Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(s1);
       cliente.setDataNascimento(d1);
       ResponseEntity<Cliente> postResponse = restTemplate.postForEntity(getRootUrl() + "/clientes", cliente, Cliente.class);
       assertNotNull(postResponse);
       assertNotNull(postResponse.getBody());
   }

   @Test
   public void testAtualizarCliente() throws ParseException {
       int id = 1;
       Cliente cliente = restTemplate.getForObject(getRootUrl() + "/clientes/" + id, Cliente.class);
       cliente.setNome("teste alterado");
       cliente.setCpf("987.654.321-10");
       String s1 = "2019-07-22";
       Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(s1);
       cliente.setDataNascimento(d1);
       restTemplate.put(getRootUrl() + "/clientes/" + id, cliente);
       Cliente atualizarCliente = restTemplate.getForObject(getRootUrl() + "/clientes/" + id, Cliente.class);
       assertNotNull(atualizarCliente);
   }

   @Test
   public void testRemoverCliente() {
        int id = 2;
        Cliente cliente = restTemplate.getForObject(getRootUrl() + "/clientes/" + id, Cliente.class);
        assertNotNull(cliente);
        restTemplate.delete(getRootUrl() + "/clientes/" + id);
        try {
             cliente = restTemplate.getForObject(getRootUrl() + "/clientes/" + id, Cliente.class);
        } catch (final HttpClientErrorException e) {
             assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
   }

}

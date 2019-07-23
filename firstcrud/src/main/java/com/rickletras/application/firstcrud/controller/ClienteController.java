package com.rickletras.application.firstcrud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rickletras.application.firstcrud.exception.ResourceNotFoundException;
import com.rickletras.application.firstcrud.model.Cliente;
import com.rickletras.application.firstcrud.repository.ClienteRepository;

@RestController
@RequestMapping("/zup")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public List<Cliente> getTodosClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getClienteId(@PathVariable(value = "id") Long clienteId)
        throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
          .orElseThrow(() -> new ResourceNotFoundException("Nenhum cliente encontrado para este id :: " + clienteId));
        return ResponseEntity.ok().body(cliente);
    }
    
    @PostMapping("/clientes")
    public Cliente adicionarCliente(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable(value = "id") Long clienteId,
         @Valid @RequestBody Cliente clienteDetails) throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
        .orElseThrow(() -> new ResourceNotFoundException("Nenhum cliente encontrado para este id :: " + clienteId));

        cliente.setNome(clienteDetails.getNome());
        cliente.setCpf(clienteDetails.getCpf());
        cliente.setDataNascimento(clienteDetails.getDataNascimento());
        
        final Cliente atualizarCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(atualizarCliente);
    }

    @DeleteMapping("/clientes/{id}")
    public Map<String, Boolean> removerCliente(@PathVariable(value = "id") Long clienteId)
         throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
       .orElseThrow(() -> new ResourceNotFoundException("Nenhum cliente encontrado para este id :: " + clienteId));

        clienteRepository.delete(cliente);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

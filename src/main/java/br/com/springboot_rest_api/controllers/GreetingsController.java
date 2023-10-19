package br.com.springboot_rest_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot_rest_api.model.Usuario;
import br.com.springboot_rest_api.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*   CDI*/
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
	/*
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Projeto SpringBoot RestApi: " + name + "!";
    }
    */
    
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String returnHelloWolrd (@PathVariable String name) {
    	Usuario usuario = new Usuario();
    	usuario.setNome(name);
    	
    	usuarioRepository.save(usuario); /* grava no BD*/
    	
    	return "Hello You: " + name;
    }
    
    @GetMapping(value="listatodos") /*Nosso primeiro metodo de API*/
    @ResponseBody /* Retorna os dados para o corpo da resposta*/
    public ResponseEntity<List<Usuario>> listUsuario() {
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); /*Executa a consulta no BD*/
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /*Retorna lista em JSON*/
    }
    
    @PostMapping(value="salvar") /*Mapea a url*/
    @ResponseBody /*Descrição da resposta*/
    /*Recebe os dados para salvar, injeta os dados na classe usuario*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {  
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
	@DeleteMapping(value = "delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para executar delete */

		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}
	
	@GetMapping(value = "buscaruserid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser) { /* Recebe os dados para consultar */

		Usuario usuario = usuarioRepository.findById(iduser).get();

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

	}
	
	@PutMapping(value = "atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { /* Recebe os dados para salvar */
		
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@GetMapping(value = "buscarPorNome") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { /* Recebe os dados para consultar */

		List<Usuario> list = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);

	}
	
	
	
    
}


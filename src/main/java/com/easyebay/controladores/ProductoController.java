package com.easyebay.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.easyebay.modelo.Producto;
import com.easyebay.modelo.Usuario;
import com.easyebay.servicios.ProductoServicio;
import com.easyebay.servicios.UsuarioServicio;
import com.easyebay.upload.FileSystemStorageService;
import com.easyebay.upload.StorageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/app")
public class ProductoController {
	
	@Autowired
	ProductoServicio productoServicio;
	@Autowired
	UsuarioServicio usuarioServicio;
	@Autowired
	FileSystemStorageService storageService;
	
	
	private Usuario usuario;
	
	@ModelAttribute("misproductos")
	public List<Producto> misProductos(){
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.err.println("el email es "+email);
		usuario = usuarioServicio.buscarPorEmail(email);
		
		return productoServicio.prodsDeUnProp(usuario);	
	}
	
	@GetMapping("/misproductos")
	public String list(Model model, @RequestParam(name="q", required=false)String query) {
		if(query!=null)
			model.addAttribute("misproductos",productoServicio.buscarMisProductos(query, usuario));
														//tb no se como pollas se rellena el usuario
		return "app/producto/lista";
		
	}
	
	@GetMapping("/misproductos/{id}/eliminar")
	public String eliminar(@PathVariable Long id) {
		Producto p = productoServicio.findById(id);
		if(p.getCompra()==null)
			productoServicio.borrar(id);
		if(p.getImagen()!=null) {
		
			storageService.delete(p.getImagen());
			System.err.println("boorrado");
		}
		
		return "redirect:/app/misproductos";
	}
	
	@GetMapping("/producto/nuevo")
	public String nuevoProductoForm(Model model) {
		model.addAttribute("producto", new Producto());
		return "app/producto/form";
		
		
	}
	
	@PostMapping("/producto/nuevo/submit")
	public String nuevoProductoSubmit(@ModelAttribute Producto producto,
			 @RequestParam("file")MultipartFile file) {
		if(!file.isEmpty()) {
			String imagen= storageService.store(file);
			producto.setImagen(MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "serveFile", imagen).build().toUriString());
			System.err.println(producto.getImagen());
		}
		producto.setPropietario(usuario);
		productoServicio.insertar(producto);
		
		
		return "redirect:/app/misproductos";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
//	
//	@RequestMapping("/hola")
//	public String locooo(Model model) {
//				
//		System.out.println("hola");
//		
//		return "redirect:loco";
//	}  ASI SE REDIRECCIONA DE UN COINTROLADOR A OTRO
//	
//	@RequestMapping("/loco")
//	public String locooo2(Model model) {
//				System.out.println("adios");
//		return "redirect:/";
//	}
}

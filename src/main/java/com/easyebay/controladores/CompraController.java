package com.easyebay.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easyebay.modelo.Compra;
import com.easyebay.modelo.Producto;
import com.easyebay.modelo.Usuario;
import com.easyebay.servicios.CompraServicio;
import com.easyebay.servicios.ProductoServicio;
import com.easyebay.servicios.UsuarioServicio;

@Controller
@RequestMapping("/app")
public class CompraController {
	@Autowired
	CompraServicio compraServicio;
	@Autowired
	ProductoServicio productoServicio;
	@Autowired
	UsuarioServicio usuarioServicio;
	@Autowired
	HttpSession session;

	private Usuario usuario;

	@ModelAttribute("carrito") // saber q hace esto
	public List<Producto> productosCarrito() {
		List<Long> contenido = (List<Long>) session.getAttribute("carrito");
		return (contenido == null) ? null : productoServicio.buscarProductosPorId(contenido);
	}

	@ModelAttribute("total_carrito")
	public double totalCarrito() {
		List<Producto> prodsCarrito = productosCarrito();

		if (prodsCarrito != null) {
			return prodsCarrito.stream().mapToDouble(p -> p.getPrecio()).sum();
		}

		return 0.0;

	}

	@ModelAttribute("mis_compras")
	public List<Compra> misCompras() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		// esto porque segun la config de seguridad(username pass) el username es el
		// email
		usuario = usuarioServicio.buscarPorEmail(email);
		return compraServicio.porPropietario(usuario);
	}

	@GetMapping("/carrito")
	public String verCarrito(Model model) {
		return "/app/compra/carrito";
	}

	@GetMapping("/carrito/add/{id}")
	public String addCarrito(Model model, @PathVariable Long id) {
		List<Long> contenido = (List<Long>) session.getAttribute("carrito");

		if (contenido == null)
			contenido = new ArrayList<Long>();
		if (!contenido.contains(id))
			contenido.add(id);
		session.setAttribute("carrito", contenido);
		return "redirect:/app/carrito";
	}

	@GetMapping("/carrito/eliminar/{id}")
	public String eliminarItem(Model model, @PathVariable Long id) {
		List<Long> contenido = (List<Long>) session.getAttribute("carrito");
		if (contenido == null)
			return "redirect:/public";
		contenido.remove(id);
		if (contenido.isEmpty())
			session.removeAttribute("carrito");
		else
			session.setAttribute("carrito", contenido);
		
		return "redirect:/app/carrito";
	}
	
	@GetMapping("/carrito/finalizar")
	public String checkout() {
		List<Long> contenido = (List<Long>) session.getAttribute("carrito");
		if (contenido == null)
			return "redirect:/public";
		List<Producto> productos= productosCarrito();
		
		Compra c = compraServicio.insertar(new Compra(),usuario);//? como sabe que se ha rellenado el usuarip¿
		
		productos.forEach(p->compraServicio.addProductoCompra(p, c));
		session.removeAttribute("carrito");
		
		return "redirect:/app/compra/factura/"+c.getId();
	}
	
	 @GetMapping("/compra/factura/{id}")
	 public String factura(Model model, @PathVariable Long id) {
		 
		 Compra c= compraServicio.buscarPorId(id);
		 
		 List<Producto> listaProdsList= productoServicio.productosDeUnaCompra(c);
		 
		 model.addAttribute("productos",listaProdsList);
		 model.addAttribute("compra",c);
		 model.addAttribute("total_compra",listaProdsList.stream().mapToDouble(x->x.getPrecio()).sum());
		 
		 return "/app/compra/factura";
		 
	 }
	 
	 @GetMapping("/miscompras")
	 public String verMisCompras(Model model) {
		 return "/app/compra/listado";
	 }
	 
	 
	 
	 
	
	
	
	
	
	

}

package com.easyebay.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easyebay.modelo.Producto;
import com.easyebay.servicios.ProductoServicio;

@Controller
@RequestMapping("/public") //así todo entrará aquí directo
public class ZonaPublicaController {

	@Autowired
	ProductoServicio productoServicio;
	
	@ModelAttribute("productos") //es como una variable de session o algo así
	public List<Producto> productosNoVendidos(){
		return productoServicio.productosSinVender();
	}
	
	@GetMapping({"","/","/index"})
	public String index(Model model, @RequestParam(name="q", required=false)String query) {
		if(query!=null)
			model.addAttribute("productos",productoServicio.buscar(query));
		return "index";
	}
	
	@GetMapping("/producto/{id}")
	public String showProudct(Model model, @PathVariable long id) {
		Producto result= productoServicio.findById(id);
		if(result!=null) {
			
			model.addAttribute("producto",result);
			return "producto";
		}
		return "redirect:/public";
	}
	
	
	
	
}

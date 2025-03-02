package com.docencia.tutorial09a.controllers;

import com.docencia.tutorial09a.models.Product;
import com.docencia.tutorial09a.repositories.ProductRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    // Simula la base de datos de productos
    private final ProductRepository productRepository;

    public CartController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "cart/index";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable Integer id, HttpSession session) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()){
            Map<Integer, Integer> cartProductData = (Map<Integer, Integer>)session.getAttribute("cart_product_data");
            if (cartProductData == null) {
                cartProductData = new HashMap<>();
            }
            cartProductData.put(id, cartProductData.getOrDefault(id, 0) + 1);
            session.setAttribute("cart_product_data", cartProductData);
        }
        return "redirect:/cart";
    }

    @GetMapping("/removeAll")
    public String removeAll(HttpSession session) {
        // Elimina el atributo del carrito de la sesión
        session.removeAttribute("cart_product_data");
        return "redirect:/cart";
    }
    
    
    

}

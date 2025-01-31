package org.hld.oceskow.controller;

import org.hld.oceskow.NotFoundException;
import org.hld.oceskow.entity.Order;
import org.hld.oceskow.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ViewController {
    private final OrderService orderService;

    public ViewController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/index")
    public String index(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("order", new Order());
        return "index";
    }

    @GetMapping(path ="/show/{id}")
    public String show(Model model, @PathVariable int id) {
        model.addAttribute("order", orderService.findOrderById(id));
        return "show";
    }

    @PostMapping(path ="/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String create(Order order) {
        orderService.addOrder(order);
        return "redirect:/index";
    }

    @DeleteMapping(path = "/delete/{id}")
    public String delete(@PathVariable int id) throws NotFoundException {
        Order order = orderService.findOrderById(id);
        orderService.removeOrder(order);
        return "redirect:/index";
    }
}

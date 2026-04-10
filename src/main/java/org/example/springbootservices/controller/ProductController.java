package org.example.springbootservices.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.springbootservices.dto.ProductResponseDTO;
import org.example.springbootservices.service.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
@Validated
public class ProductController {

    private ProductService service;

    @GetMapping("/busca/nome")
    public List<ProductResponseDTO> listProductsByName(@RequestParam String termo) throws IOException {
        return service.listProductsByName(termo);
    }

    @GetMapping("/busca/descricao")
    public List<ProductResponseDTO> listProductsByDescription(@RequestParam String termo) throws IOException {
        return service.listProductsByDescription(termo);
    }

    @GetMapping("/busca/frase")
    public List<ProductResponseDTO> listProductsByExactDescription(@RequestParam String termo) throws IOException{
        return service.listProductsByExactDescription(termo);
    }

    @GetMapping("/busca/fuzzy")
    public List<ProductResponseDTO> listProductsByFuzzName(@RequestParam String termo) throws IOException{
        return service.listProductsByFuzzName(termo);
    }

    @GetMapping("/busca/multicampos")
    public List<ProductResponseDTO> listProductsByMultiFields(@RequestParam String termo) throws IOException{
        return service.listProductsByMultiFields(termo);
    }

    @GetMapping("/busca/com-filtro")
    public List<ProductResponseDTO> listProductsByTextAndCategory(@RequestParam String termo, @RequestParam String categoria) throws IOException{
        return service.listProductsByTextAndCategory(termo,categoria);
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProductResponseDTO> listProductsByPriceRange(@RequestParam double min, @RequestParam double max) throws IOException{
        return service.listProductsByPriceRange(min,max);
    }

    @GetMapping("/busca/avancada")
    public List<ProductResponseDTO> listProductsAdvancedSearch(@RequestParam String categoria,@RequestParam double min, @RequestParam double max,@RequestParam String raridade) throws IOException {
        return service.listProductsAdvancedSearch(categoria,raridade,min,max);
    }

    @GetMapping("/agregacoes/por-categoria")
    public Map<String, Long> countByCategory() throws IOException {
        return service.countProductsByCategory();
    }

    @GetMapping("/agregacoes/por-raridade")
    public Map<String, Long> countByRarity() throws IOException {
        return service.countProductsByRarity();
    }
    @GetMapping("/agregacoes/preco-medio")
    public Double consultAveragePrice() throws IOException {
        return service.consultAveragePrice();
    }

    @GetMapping("/agregacoes/faixa-preco")
    public Map<String, Long> consultPricesRange() throws IOException {
        return service.consultPricesRange();
    }




}

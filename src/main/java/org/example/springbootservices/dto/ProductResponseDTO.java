package org.example.springbootservices.dto;

import co.elastic.clients.elasticsearch.core.search.Hit;
import org.example.springbootservices.model.ProductDocument;

public record ProductResponseDTO(
        String id,
        String nome,
        String descricao,
        String categoria,
        String raridade,
        Double preco,
        Double score
) {
    public static ProductResponseDTO toDTO(Hit<ProductDocument> hit) {
        ProductDocument doc = hit.source();
        return new ProductResponseDTO(
                hit.id(),
                doc.getNome(),
                doc.getDescricao(),
                doc.getCategoria(),
                doc.getRaridade(),
                doc.getPreco(),
                hit.score()
        );
    }
}

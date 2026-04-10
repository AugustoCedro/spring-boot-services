package org.example.springbootservices.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "guilda_loja")
public class ProductDocument {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private String categoria;
    private String raridade;
    private Double preco;
}

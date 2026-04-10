package org.example.springbootservices.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.ProductResponseDTO;
import org.example.springbootservices.model.ProductDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {

    private ElasticsearchClient client;

    public List<ProductResponseDTO> listProductsByName(String termo) throws IOException {
        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .match(m -> m
                                .field("nome")
                                .query(termo)
                        )
                ), ProductDocument.class
        );
        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }


    public List<ProductResponseDTO> listProductsByDescription(String termo) throws IOException {
        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .match(m -> m
                                .field("descricao")
                                .query(termo)
                        )
                ), ProductDocument.class
        );
        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> listProductsByExactDescription(String termo) throws IOException {
        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .matchPhrase(m -> m
                                .field("descricao")
                                .query(termo)
                        )
                ), ProductDocument.class
        );
        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> listProductsByFuzzName(String termo) throws IOException {
        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .fuzzy(f -> f
                                .field("nome")
                                .value(termo)
                                .fuzziness("AUTO")
                        )
                ), ProductDocument.class
        );
        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> listProductsByMultiFields(String termo) throws IOException {
        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .multiMatch(m -> m
                                .fields("nome", "descricao")
                                .query(termo)
                        )
                ), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> listProductsByTextAndCategory(String termo, String categoria) throws IOException {

        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q.bool(b -> b
                        .must(m -> m
                                .match(mm -> mm
                                        .field("descricao")
                                        .query(termo))
                        )
                        .filter(f -> f
                                .term(t -> t
                                        .field("categoria")
                                        .value(v -> v.stringValue(categoria)))
                        )
                )), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> listProductsByPriceRange(Double min, Double max) throws IOException {

        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .range(r -> r
                                .number(n -> n
                                        .field("preco")
                                        .gte(min)
                                        .lte(max)
                                )
                        )
                ), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }

    public List<ProductResponseDTO> listProductsAdvancedSearch(
            String categoria,
            String raridade,
            Double min,
            Double max
    ) throws IOException {

        SearchResponse<ProductDocument> search = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q.bool(b -> {
                    b.filter(f -> f.term(t -> t.field("categoria").value(v -> v.stringValue(categoria))));
                    b.filter(f -> f.term(t -> t.field("raridade").value(v -> v.stringValue(raridade))));
                    b.filter(f -> f.range(r -> r.number(n -> {
                                var number = n.field("preco");
                                number = number.gte(min);
                                number = number.lte(max);
                                return number;
                            })
                    ));

                    return b;
                })), ProductDocument.class);

        return search.hits().hits()
                .stream()
                .map(ProductResponseDTO::toDTO)
                .toList();
    }

    public Map<String, Long> countProductsByCategory() throws IOException {

        SearchResponse<ProductDocument> response = client.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("by_category", a -> a
                        .terms(t -> t
                                .field("categoria")
                        )
                ), ProductDocument.class
        );

        Aggregate agg = response.aggregations().get("by_category");

        List<StringTermsBucket> buckets = agg.sterms().buckets().array();

        Map<String, Long> result = new HashMap<>();

        for (StringTermsBucket bucket : buckets) {
            result.put(bucket.key().stringValue(), bucket.docCount());
        }

        return result;
    }

    public Map<String, Long> countProductsByRarity() throws IOException {

        SearchResponse<ProductDocument> response = client.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("by_rarity", a -> a
                        .terms(t -> t
                                .field("raridade")
                        )
                ), ProductDocument.class
        );

        Aggregate agg = response.aggregations().get("by_rarity");

        List<StringTermsBucket> buckets = agg.sterms().buckets().array();

        Map<String, Long> result = new HashMap<>();

        for (StringTermsBucket bucket : buckets) {
            result.put(bucket.key().stringValue(), bucket.docCount());
        }

        return result;
    }

    public Double consultAveragePrice() throws IOException {

        SearchResponse<ProductDocument> response = client.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("avg_price", a -> a
                        .avg(avg -> avg
                                .field("preco")
                        )
                ), ProductDocument.class
        );

        Aggregate agg = response.aggregations().get("avg_price");
        return agg.avg().value();
    }




    public Map<String, Long> consultPricesRange() throws IOException {

        SearchResponse<ProductDocument> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("prices-range", a -> a
                                .range(r -> r
                                        .field("preco")
                                        .ranges(
                                                AggregationRange.of(ar -> ar
                                                        .to(100.0)
                                                        .key("Abaixo de 100")
                                                ),
                                                AggregationRange.of(ar -> ar
                                                        .from(100.0)
                                                        .to(300.0)
                                                        .key("De 100 a 300")
                                                ),
                                                AggregationRange.of(ar -> ar
                                                        .from(300.0)
                                                        .to(700.0)
                                                        .key("De 300 a 700")
                                                ),
                                                AggregationRange.of(ar -> ar
                                                        .from(700.0)
                                                        .key("Acima de 700")
                                                )
                                        )
                                )
                        ),
                ProductDocument.class
        );

        RangeAggregate agg = response.aggregations()
                .get("prices-range")
                .range();

        Map<String, Long> result = new LinkedHashMap<>();

        for (RangeBucket bucket : agg.buckets().array()) {
            result.put(bucket.key(), bucket.docCount());
        }

        return result;
    }



}

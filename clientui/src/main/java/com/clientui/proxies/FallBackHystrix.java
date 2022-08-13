package com.clientui.proxies;

import com.clientui.beans.ProductBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class FallBackHystrix implements MicroserviceProduitsProxy {

    @Override
    public List<ProductBean> listeDesProduits() {
        List<ProductBean> collect = Stream.of(new ProductBean(1,
                        "MicroserviceProduitDown",
                        "Microservice Mproduits Down",
                        "https://img.freepik.com/vecteurs-libre/maintenance-conception-arriere-plan_1294-45.jpg?1?w=360",
                        0.0))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public ProductBean recupererUnProduit(int id) {
        return new ProductBean(1,
                "MicroserviceProduitDown",
                "Microservice Mproduits Down",
                "https://img.freepik.com/vecteurs-libre/maintenance-conception-arriere-plan_1294-45.jpg?1?w=360",
                0.0)
                ;
    }
}

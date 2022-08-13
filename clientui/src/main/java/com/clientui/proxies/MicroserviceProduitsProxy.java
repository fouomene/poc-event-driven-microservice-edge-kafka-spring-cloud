package com.clientui.proxies;

import com.clientui.beans.ProductBean;
import com.clientui.configuration.LoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "springcloudgateway-server1",url = "localhost:8004",fallback = FallBackHystrix.class) //Integrating Feign with Hystrix with fallback class or method
//@FeignClient(name = "microservice-produits", url = "localhost:8001",fallback = FallBackHystrix.class)
//@FeignClient(name = "microservice-produits")
//@RibbonClient(name = "microservice-produits") // avec Netflix ribbon
@LoadBalancerClient(name = "microservice-produits",
        configuration= LoadBalancerConfiguration.class) // Spring cloud Loadbalancer
public interface MicroserviceProduitsProxy {

    @GetMapping(value = "/Produits")
    List<ProductBean> listeDesProduits();

    /*
    * Notez ici la notation @PathVariable("id") qui est différente de celle qu'on utlise dans le contrôleur
    **/
    @GetMapping( value = "/Produits/{id}")
    ProductBean recupererUnProduit(@PathVariable("id") int id);

}

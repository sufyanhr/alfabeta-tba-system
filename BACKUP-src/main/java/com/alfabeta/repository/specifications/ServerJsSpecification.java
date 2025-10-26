package com.alfabeta.repository.specifications;


import com.alfabeta.model.component.ServerJs;
import org.springframework.data.jpa.domain.Specification;

public class ServerJsSpecification {
    public static Specification<ServerJs> getByName(String name) {

        return (root, query, cb) -> cb.equal(root.get("name"), name);

    }
}

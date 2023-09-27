package com.ti.tubeminer.global.domain.service;


import com.ti.tubeminer.global.domain.repository.IBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class BaseService<R extends IBaseRepository<E>, E> {

    @Autowired
    private R repository;

    public E save(E entity){
        return repository.save(entity);
    }
}

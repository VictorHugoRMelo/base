package com.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.model.HistoricoLogin;
import com.base.model.HistoricoLoginId;

public interface HistoricoLoginRepository extends JpaRepository<HistoricoLogin, HistoricoLoginId> {

}

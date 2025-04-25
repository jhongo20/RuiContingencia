package com.rui.basic.app.basic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rui.basic.app.basic.domain.entities.RuiGenerics;
import com.rui.basic.app.basic.repository.RuiGenericsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IntermediaryTypeService {

    private final RuiGenericsRepository genericsRepository;
    
    // Asumo que el ID padre para tipos de intermediario es diferente
    // Puedes ajustar este valor según tus datos reales
    private static final Long INTERMEDIARY_TYPE_FATHER_ID = 1L; // Cambia este valor al correcto

    /**
     * Obtiene todos los tipos de intermediarios activos
     * 
     * @return Lista de tipos de intermediarios
     */
    public List<RuiGenerics> getAllIntermediaryTypes() {
        // Buscar todos los tipos de intermediario que son hijos del padre INTERMEDIARY_TYPE_FATHER_ID
        // y que están activos (status = 1)
        return genericsRepository.findByFatherIdAndStatus(INTERMEDIARY_TYPE_FATHER_ID, 1);
    }
    
    /**
     * Alias para getAllIntermediaryTypes() para mantener coherencia con otros métodos
     */
    public List<RuiGenerics> getAllTypes() {
        return getAllIntermediaryTypes();
    }
    
    /**
     * Busca un tipo de intermediario por ID
     * 
     * @param id ID del tipo de intermediario
     * @return Optional con el tipo de intermediario si existe
     */
    public Optional<RuiGenerics> findById(Long id) {
        return genericsRepository.findById(id);
    }
}
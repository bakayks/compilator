package kg.inai.service;

import kg.inai.entity.Reg;

import java.util.List;

public interface RegService {
    Reg findById(Long id);

    List<Reg> findAll();

    Reg create(Reg reg);

    String deleteById(Long id);
}

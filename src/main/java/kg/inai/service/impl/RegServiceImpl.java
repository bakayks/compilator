package kg.inai.service.impl;

import kg.inai.entity.Reg;
import kg.inai.exception.RecordNotFoundException;
import kg.inai.repository.RegRepository;
import kg.inai.service.RegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegServiceImpl implements RegService {
    @Autowired
    private RegRepository regRepository;

    @Override
    public Reg findById(Long id) {
        return regRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public List<Reg> findAll() {
        return regRepository.findAll();
    }

    @Override
    public Reg create(Reg reg) {
        return regRepository.save(reg);
    }

    @Override
    public String deleteById(Long id) {
        regRepository.deleteById(id);
        return "Success deleting regulation with id "+id;
    }
}

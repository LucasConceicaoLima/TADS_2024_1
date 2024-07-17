package br.edu.ifsul.cstsi.lucastads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HiraganaService {

    @Autowired
    private HiraganaRepository hiraganaRepository;

    public List<Hiragana> findAll() {
        return hiraganaRepository.findAll();
    }

    public Optional<Hiragana> findById(Long id) {
        return hiraganaRepository.findById(id);
    }

    public Hiragana save(Hiragana hiragana) {
        return hiraganaRepository.save(hiragana);
    }

    public void deleteById(Long id) {
        hiraganaRepository.deleteById(id);
    }
}

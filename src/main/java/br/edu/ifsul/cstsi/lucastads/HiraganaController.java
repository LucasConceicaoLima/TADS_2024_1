package br.edu.ifsul.cstsi.lucastads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hiragana")
public class HiraganaController {

    private static final Logger logger = LoggerFactory.getLogger(HiraganaController.class);

    @Autowired
    private HiraganaService hiraganaService;

    @GetMapping
    public List<Hiragana> getAllHiragana() {
        return hiraganaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hiragana> getHiraganaById(@PathVariable Long id) {
        Optional<Hiragana> hiragana = hiraganaService.findById(id);
        return hiragana.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hiragana createHiragana(@RequestBody Hiragana hiragana) {
        logger.info("Received Hiragana: caractere={}, leitura={}, nroTracos={}", hiragana.getCaractere(), hiragana.getLeitura(), hiragana.getNroTracos());
        return hiraganaService.save(hiragana);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hiragana> updateHiragana(@PathVariable Long id, @RequestBody Hiragana hiragana) {
        if (!hiraganaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        hiragana.setId(id);
        logger.info("Updating Hiragana: id={}, caractere={}, leitura={}, nroTracos={}", id, hiragana.getCaractere(), hiragana.getLeitura(), hiragana.getNroTracos());
        return ResponseEntity.ok(hiraganaService.save(hiragana));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHiragana(@PathVariable Long id) {
        if (!hiraganaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        hiraganaService.deleteById(id);
        logger.info("Deleting Hiragana with id={}", id);
        return ResponseEntity.noContent().build();
    }
}

package sia.tacocloud.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.dao.TacoRepository;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacosOrder;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos",
                produces = "application/json")
public class TacoController {

    private TacoRepository tacoRepo;

    public TacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping()
    public Iterable<Taco> recentTacos(){
        PageRequest page = PageRequest.of(0, 12, Sort.by("createAt").descending());
        return tacoRepo.findAll(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> findById(@PathVariable("id") Long id) {
        Optional<Taco> taco = tacoRepo.findById(id);
        if(taco.isPresent()) {
            return new ResponseEntity<>(taco.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }



}

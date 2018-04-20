package com.kamal.khidma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kamal.khidma.service.EnseignantService;
import com.kamal.khidma.web.rest.errors.BadRequestAlertException;
import com.kamal.khidma.web.rest.util.HeaderUtil;
import com.kamal.khidma.service.dto.EnseignantDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Enseignant.
 */
@RestController
@RequestMapping("/api")
public class EnseignantResource {

    private final Logger log = LoggerFactory.getLogger(EnseignantResource.class);

    private static final String ENTITY_NAME = "enseignant";

    private final EnseignantService enseignantService;

    public EnseignantResource(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    /**
     * POST  /enseignants : Create a new enseignant.
     *
     * @param enseignantDTO the enseignantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enseignantDTO, or with status 400 (Bad Request) if the enseignant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enseignants")
    @Timed
    public ResponseEntity<EnseignantDTO> createEnseignant(@RequestBody EnseignantDTO enseignantDTO) throws URISyntaxException {
        log.debug("REST request to save Enseignant : {}", enseignantDTO);
        if (enseignantDTO.getId() != null) {
            throw new BadRequestAlertException("A new enseignant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnseignantDTO result = enseignantService.save(enseignantDTO);
        return ResponseEntity.created(new URI("/api/enseignants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enseignants : Updates an existing enseignant.
     *
     * @param enseignantDTO the enseignantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enseignantDTO,
     * or with status 400 (Bad Request) if the enseignantDTO is not valid,
     * or with status 500 (Internal Server Error) if the enseignantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enseignants")
    @Timed
    public ResponseEntity<EnseignantDTO> updateEnseignant(@RequestBody EnseignantDTO enseignantDTO) throws URISyntaxException {
        log.debug("REST request to update Enseignant : {}", enseignantDTO);
        if (enseignantDTO.getId() == null) {
            return createEnseignant(enseignantDTO);
        }
        EnseignantDTO result = enseignantService.save(enseignantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enseignantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enseignants : get all the enseignants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enseignants in body
     */
    @GetMapping("/enseignants")
    @Timed
    public List<EnseignantDTO> getAllEnseignants() {
        log.debug("REST request to get all Enseignants");
        return enseignantService.findAll();
        }

    /**
     * GET  /enseignants/:id : get the "id" enseignant.
     *
     * @param id the id of the enseignantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enseignantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enseignants/{id}")
    @Timed
    public ResponseEntity<EnseignantDTO> getEnseignant(@PathVariable Long id) {
        log.debug("REST request to get Enseignant : {}", id);
        EnseignantDTO enseignantDTO = enseignantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(enseignantDTO));
    }

    /**
     * DELETE  /enseignants/:id : delete the "id" enseignant.
     *
     * @param id the id of the enseignantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enseignants/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        log.debug("REST request to delete Enseignant : {}", id);
        enseignantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

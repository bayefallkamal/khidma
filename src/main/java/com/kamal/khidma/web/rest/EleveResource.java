package com.kamal.khidma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kamal.khidma.service.EleveService;
import com.kamal.khidma.web.rest.errors.BadRequestAlertException;
import com.kamal.khidma.web.rest.util.HeaderUtil;
import com.kamal.khidma.service.dto.EleveDTO;
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
 * REST controller for managing Eleve.
 */
@RestController
@RequestMapping("/api")
public class EleveResource {

    private final Logger log = LoggerFactory.getLogger(EleveResource.class);

    private static final String ENTITY_NAME = "eleve";

    private final EleveService eleveService;

    public EleveResource(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    /**
     * POST  /eleves : Create a new eleve.
     *
     * @param eleveDTO the eleveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eleveDTO, or with status 400 (Bad Request) if the eleve has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eleves")
    @Timed
    public ResponseEntity<EleveDTO> createEleve(@RequestBody EleveDTO eleveDTO) throws URISyntaxException {
        log.debug("REST request to save Eleve : {}", eleveDTO);
        if (eleveDTO.getId() != null) {
            throw new BadRequestAlertException("A new eleve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EleveDTO result = eleveService.save(eleveDTO);
        return ResponseEntity.created(new URI("/api/eleves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eleves : Updates an existing eleve.
     *
     * @param eleveDTO the eleveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eleveDTO,
     * or with status 400 (Bad Request) if the eleveDTO is not valid,
     * or with status 500 (Internal Server Error) if the eleveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eleves")
    @Timed
    public ResponseEntity<EleveDTO> updateEleve(@RequestBody EleveDTO eleveDTO) throws URISyntaxException {
        log.debug("REST request to update Eleve : {}", eleveDTO);
        if (eleveDTO.getId() == null) {
            return createEleve(eleveDTO);
        }
        EleveDTO result = eleveService.save(eleveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eleveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eleves : get all the eleves.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eleves in body
     */
    @GetMapping("/eleves")
    @Timed
    public List<EleveDTO> getAllEleves() {
        log.debug("REST request to get all Eleves");
        return eleveService.findAll();
        }

    /**
     * GET  /eleves/:id : get the "id" eleve.
     *
     * @param id the id of the eleveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eleveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/eleves/{id}")
    @Timed
    public ResponseEntity<EleveDTO> getEleve(@PathVariable Long id) {
        log.debug("REST request to get Eleve : {}", id);
        EleveDTO eleveDTO = eleveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eleveDTO));
    }

    /**
     * DELETE  /eleves/:id : delete the "id" eleve.
     *
     * @param id the id of the eleveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eleves/{id}")
    @Timed
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        log.debug("REST request to delete Eleve : {}", id);
        eleveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

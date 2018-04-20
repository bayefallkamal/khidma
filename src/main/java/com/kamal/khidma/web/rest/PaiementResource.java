package com.kamal.khidma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kamal.khidma.service.PaiementService;
import com.kamal.khidma.web.rest.errors.BadRequestAlertException;
import com.kamal.khidma.web.rest.util.HeaderUtil;
import com.kamal.khidma.service.dto.PaiementDTO;
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
 * REST controller for managing Paiement.
 */
@RestController
@RequestMapping("/api")
public class PaiementResource {

    private final Logger log = LoggerFactory.getLogger(PaiementResource.class);

    private static final String ENTITY_NAME = "paiement";

    private final PaiementService paiementService;

    public PaiementResource(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    /**
     * POST  /paiements : Create a new paiement.
     *
     * @param paiementDTO the paiementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paiementDTO, or with status 400 (Bad Request) if the paiement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paiements")
    @Timed
    public ResponseEntity<PaiementDTO> createPaiement(@RequestBody PaiementDTO paiementDTO) throws URISyntaxException {
        log.debug("REST request to save Paiement : {}", paiementDTO);
        if (paiementDTO.getId() != null) {
            throw new BadRequestAlertException("A new paiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaiementDTO result = paiementService.save(paiementDTO);
        return ResponseEntity.created(new URI("/api/paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paiements : Updates an existing paiement.
     *
     * @param paiementDTO the paiementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paiementDTO,
     * or with status 400 (Bad Request) if the paiementDTO is not valid,
     * or with status 500 (Internal Server Error) if the paiementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paiements")
    @Timed
    public ResponseEntity<PaiementDTO> updatePaiement(@RequestBody PaiementDTO paiementDTO) throws URISyntaxException {
        log.debug("REST request to update Paiement : {}", paiementDTO);
        if (paiementDTO.getId() == null) {
            return createPaiement(paiementDTO);
        }
        PaiementDTO result = paiementService.save(paiementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paiementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paiements : get all the paiements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paiements in body
     */
    @GetMapping("/paiements")
    @Timed
    public List<PaiementDTO> getAllPaiements() {
        log.debug("REST request to get all Paiements");
        return paiementService.findAll();
        }

    /**
     * GET  /paiements/:id : get the "id" paiement.
     *
     * @param id the id of the paiementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paiementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/paiements/{id}")
    @Timed
    public ResponseEntity<PaiementDTO> getPaiement(@PathVariable Long id) {
        log.debug("REST request to get Paiement : {}", id);
        PaiementDTO paiementDTO = paiementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paiementDTO));
    }

    /**
     * DELETE  /paiements/:id : delete the "id" paiement.
     *
     * @param id the id of the paiementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paiements/{id}")
    @Timed
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        log.debug("REST request to delete Paiement : {}", id);
        paiementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.kamal.khidma.web.rest;

import com.kamal.khidma.KhidmaApp;

import com.kamal.khidma.domain.Paiement;
import com.kamal.khidma.repository.PaiementRepository;
import com.kamal.khidma.service.PaiementService;
import com.kamal.khidma.service.dto.PaiementDTO;
import com.kamal.khidma.service.mapper.PaiementMapper;
import com.kamal.khidma.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.kamal.khidma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaiementResource REST controller.
 *
 * @see PaiementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhidmaApp.class)
public class PaiementResourceIntTest {

    private static final Instant DEFAULT_DATE_PAIEMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PAIEMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_TYPE_PAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_PAIEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_MOIS = "AAAAAAAAAA";
    private static final String UPDATED_MOIS = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PAYEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PAYEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_PAYEUR = "AAAAAAAAAA";
    private static final String UPDATED_TEL_PAYEUR = "BBBBBBBBBB";

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private PaiementMapper paiementMapper;

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaiementMockMvc;

    private Paiement paiement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaiementResource paiementResource = new PaiementResource(paiementService);
        this.restPaiementMockMvc = MockMvcBuilders.standaloneSetup(paiementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiement createEntity(EntityManager em) {
        Paiement paiement = new Paiement()
            .datePaiement(DEFAULT_DATE_PAIEMENT)
            .montant(DEFAULT_MONTANT)
            .typePaiement(DEFAULT_TYPE_PAIEMENT)
            .mois(DEFAULT_MOIS)
            .nomPayeur(DEFAULT_NOM_PAYEUR)
            .telPayeur(DEFAULT_TEL_PAYEUR);
        return paiement;
    }

    @Before
    public void initTest() {
        paiement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaiement() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isCreated());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate + 1);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getDatePaiement()).isEqualTo(DEFAULT_DATE_PAIEMENT);
        assertThat(testPaiement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testPaiement.getTypePaiement()).isEqualTo(DEFAULT_TYPE_PAIEMENT);
        assertThat(testPaiement.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testPaiement.getNomPayeur()).isEqualTo(DEFAULT_NOM_PAYEUR);
        assertThat(testPaiement.getTelPayeur()).isEqualTo(DEFAULT_TEL_PAYEUR);
    }

    @Test
    @Transactional
    public void createPaiementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();

        // Create the Paiement with an existing ID
        paiement.setId(1L);
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaiements() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        // Get all the paiementList
        restPaiementMockMvc.perform(get("/api/paiements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].datePaiement").value(hasItem(DEFAULT_DATE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].typePaiement").value(hasItem(DEFAULT_TYPE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS.toString())))
            .andExpect(jsonPath("$.[*].nomPayeur").value(hasItem(DEFAULT_NOM_PAYEUR.toString())))
            .andExpect(jsonPath("$.[*].telPayeur").value(hasItem(DEFAULT_TEL_PAYEUR.toString())));
    }

    @Test
    @Transactional
    public void getPaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", paiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paiement.getId().intValue()))
            .andExpect(jsonPath("$.datePaiement").value(DEFAULT_DATE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.typePaiement").value(DEFAULT_TYPE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS.toString()))
            .andExpect(jsonPath("$.nomPayeur").value(DEFAULT_NOM_PAYEUR.toString()))
            .andExpect(jsonPath("$.telPayeur").value(DEFAULT_TEL_PAYEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaiement() throws Exception {
        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);
        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Update the paiement
        Paiement updatedPaiement = paiementRepository.findOne(paiement.getId());
        // Disconnect from session so that the updates on updatedPaiement are not directly saved in db
        em.detach(updatedPaiement);
        updatedPaiement
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .typePaiement(UPDATED_TYPE_PAIEMENT)
            .mois(UPDATED_MOIS)
            .nomPayeur(UPDATED_NOM_PAYEUR)
            .telPayeur(UPDATED_TEL_PAYEUR);
        PaiementDTO paiementDTO = paiementMapper.toDto(updatedPaiement);

        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isOk());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getDatePaiement()).isEqualTo(UPDATED_DATE_PAIEMENT);
        assertThat(testPaiement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testPaiement.getTypePaiement()).isEqualTo(UPDATED_TYPE_PAIEMENT);
        assertThat(testPaiement.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testPaiement.getNomPayeur()).isEqualTo(UPDATED_NOM_PAYEUR);
        assertThat(testPaiement.getTelPayeur()).isEqualTo(UPDATED_TEL_PAYEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingPaiement() throws Exception {
        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isCreated());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);
        int databaseSizeBeforeDelete = paiementRepository.findAll().size();

        // Get the paiement
        restPaiementMockMvc.perform(delete("/api/paiements/{id}", paiement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paiement.class);
        Paiement paiement1 = new Paiement();
        paiement1.setId(1L);
        Paiement paiement2 = new Paiement();
        paiement2.setId(paiement1.getId());
        assertThat(paiement1).isEqualTo(paiement2);
        paiement2.setId(2L);
        assertThat(paiement1).isNotEqualTo(paiement2);
        paiement1.setId(null);
        assertThat(paiement1).isNotEqualTo(paiement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaiementDTO.class);
        PaiementDTO paiementDTO1 = new PaiementDTO();
        paiementDTO1.setId(1L);
        PaiementDTO paiementDTO2 = new PaiementDTO();
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO2.setId(paiementDTO1.getId());
        assertThat(paiementDTO1).isEqualTo(paiementDTO2);
        paiementDTO2.setId(2L);
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO1.setId(null);
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paiementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paiementMapper.fromId(null)).isNull();
    }
}

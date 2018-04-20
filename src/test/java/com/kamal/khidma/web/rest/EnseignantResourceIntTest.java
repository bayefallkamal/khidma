package com.kamal.khidma.web.rest;

import com.kamal.khidma.KhidmaApp;

import com.kamal.khidma.domain.Enseignant;
import com.kamal.khidma.repository.EnseignantRepository;
import com.kamal.khidma.service.EnseignantService;
import com.kamal.khidma.service.dto.EnseignantDTO;
import com.kamal.khidma.service.mapper.EnseignantMapper;
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
import java.util.List;

import static com.kamal.khidma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EnseignantResource REST controller.
 *
 * @see EnseignantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhidmaApp.class)
public class EnseignantResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EnseignantMapper enseignantMapper;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnseignantMockMvc;

    private Enseignant enseignant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnseignantResource enseignantResource = new EnseignantResource(enseignantService);
        this.restEnseignantMockMvc = MockMvcBuilders.standaloneSetup(enseignantResource)
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
    public static Enseignant createEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .adresse(DEFAULT_ADRESSE);
        return enseignant;
    }

    @Before
    public void initTest() {
        enseignant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignant() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate + 1);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEnseignant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEnseignant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEnseignant.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEnseignant.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createEnseignantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant with an existing ID
        enseignant.setId(1L);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEnseignants() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void getEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enseignant.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignant() throws Exception {
        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Update the enseignant
        Enseignant updatedEnseignant = enseignantRepository.findOne(enseignant.getId());
        // Disconnect from session so that the updates on updatedEnseignant are not directly saved in db
        em.detach(updatedEnseignant);
        updatedEnseignant
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .adresse(UPDATED_ADRESSE);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(updatedEnseignant);

        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isOk());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEnseignant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEnseignant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEnseignant.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEnseignant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnseignant() throws Exception {
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        int databaseSizeBeforeDelete = enseignantRepository.findAll().size();

        // Get the enseignant
        restEnseignantMockMvc.perform(delete("/api/enseignants/{id}", enseignant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enseignant.class);
        Enseignant enseignant1 = new Enseignant();
        enseignant1.setId(1L);
        Enseignant enseignant2 = new Enseignant();
        enseignant2.setId(enseignant1.getId());
        assertThat(enseignant1).isEqualTo(enseignant2);
        enseignant2.setId(2L);
        assertThat(enseignant1).isNotEqualTo(enseignant2);
        enseignant1.setId(null);
        assertThat(enseignant1).isNotEqualTo(enseignant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnseignantDTO.class);
        EnseignantDTO enseignantDTO1 = new EnseignantDTO();
        enseignantDTO1.setId(1L);
        EnseignantDTO enseignantDTO2 = new EnseignantDTO();
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
        enseignantDTO2.setId(enseignantDTO1.getId());
        assertThat(enseignantDTO1).isEqualTo(enseignantDTO2);
        enseignantDTO2.setId(2L);
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
        enseignantDTO1.setId(null);
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enseignantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enseignantMapper.fromId(null)).isNull();
    }
}

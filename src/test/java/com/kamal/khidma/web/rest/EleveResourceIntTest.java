package com.kamal.khidma.web.rest;

import com.kamal.khidma.KhidmaApp;

import com.kamal.khidma.domain.Eleve;
import com.kamal.khidma.repository.EleveRepository;
import com.kamal.khidma.service.EleveService;
import com.kamal.khidma.service.dto.EleveDTO;
import com.kamal.khidma.service.mapper.EleveMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the EleveResource REST controller.
 *
 * @see EleveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhidmaApp.class)
public class EleveResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ENTREE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ENTREE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private EleveMapper eleveMapper;

    @Autowired
    private EleveService eleveService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEleveMockMvc;

    private Eleve eleve;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EleveResource eleveResource = new EleveResource(eleveService);
        this.restEleveMockMvc = MockMvcBuilders.standaloneSetup(eleveResource)
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
    public static Eleve createEntity(EntityManager em) {
        Eleve eleve = new Eleve()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .lieuNaiss(DEFAULT_LIEU_NAISS)
            .dateEntree(DEFAULT_DATE_ENTREE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return eleve;
    }

    @Before
    public void initTest() {
        eleve = createEntity(em);
    }

    @Test
    @Transactional
    public void createEleve() throws Exception {
        int databaseSizeBeforeCreate = eleveRepository.findAll().size();

        // Create the Eleve
        EleveDTO eleveDTO = eleveMapper.toDto(eleve);
        restEleveMockMvc.perform(post("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isCreated());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeCreate + 1);
        Eleve testEleve = eleveList.get(eleveList.size() - 1);
        assertThat(testEleve.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEleve.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEleve.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testEleve.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testEleve.getDateEntree()).isEqualTo(DEFAULT_DATE_ENTREE);
        assertThat(testEleve.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEleve.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEleveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eleveRepository.findAll().size();

        // Create the Eleve with an existing ID
        eleve.setId(1L);
        EleveDTO eleveDTO = eleveMapper.toDto(eleve);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEleveMockMvc.perform(post("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEleves() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        // Get all the eleveList
        restEleveMockMvc.perform(get("/api/eleves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eleve.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].lieuNaiss").value(hasItem(DEFAULT_LIEU_NAISS.toString())))
            .andExpect(jsonPath("$.[*].dateEntree").value(hasItem(DEFAULT_DATE_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getEleve() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        // Get the eleve
        restEleveMockMvc.perform(get("/api/eleves/{id}", eleve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eleve.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.lieuNaiss").value(DEFAULT_LIEU_NAISS.toString()))
            .andExpect(jsonPath("$.dateEntree").value(DEFAULT_DATE_ENTREE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingEleve() throws Exception {
        // Get the eleve
        restEleveMockMvc.perform(get("/api/eleves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEleve() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);
        int databaseSizeBeforeUpdate = eleveRepository.findAll().size();

        // Update the eleve
        Eleve updatedEleve = eleveRepository.findOne(eleve.getId());
        // Disconnect from session so that the updates on updatedEleve are not directly saved in db
        em.detach(updatedEleve);
        updatedEleve
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .dateEntree(UPDATED_DATE_ENTREE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        EleveDTO eleveDTO = eleveMapper.toDto(updatedEleve);

        restEleveMockMvc.perform(put("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isOk());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeUpdate);
        Eleve testEleve = eleveList.get(eleveList.size() - 1);
        assertThat(testEleve.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEleve.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEleve.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testEleve.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testEleve.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testEleve.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEleve.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEleve() throws Exception {
        int databaseSizeBeforeUpdate = eleveRepository.findAll().size();

        // Create the Eleve
        EleveDTO eleveDTO = eleveMapper.toDto(eleve);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEleveMockMvc.perform(put("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isCreated());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEleve() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);
        int databaseSizeBeforeDelete = eleveRepository.findAll().size();

        // Get the eleve
        restEleveMockMvc.perform(delete("/api/eleves/{id}", eleve.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eleve.class);
        Eleve eleve1 = new Eleve();
        eleve1.setId(1L);
        Eleve eleve2 = new Eleve();
        eleve2.setId(eleve1.getId());
        assertThat(eleve1).isEqualTo(eleve2);
        eleve2.setId(2L);
        assertThat(eleve1).isNotEqualTo(eleve2);
        eleve1.setId(null);
        assertThat(eleve1).isNotEqualTo(eleve2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EleveDTO.class);
        EleveDTO eleveDTO1 = new EleveDTO();
        eleveDTO1.setId(1L);
        EleveDTO eleveDTO2 = new EleveDTO();
        assertThat(eleveDTO1).isNotEqualTo(eleveDTO2);
        eleveDTO2.setId(eleveDTO1.getId());
        assertThat(eleveDTO1).isEqualTo(eleveDTO2);
        eleveDTO2.setId(2L);
        assertThat(eleveDTO1).isNotEqualTo(eleveDTO2);
        eleveDTO1.setId(null);
        assertThat(eleveDTO1).isNotEqualTo(eleveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eleveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eleveMapper.fromId(null)).isNull();
    }
}

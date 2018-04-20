package com.kamal.khidma.web.rest;

import com.kamal.khidma.KhidmaApp;

import com.kamal.khidma.domain.Parent;
import com.kamal.khidma.repository.ParentRepository;
import com.kamal.khidma.service.ParentService;
import com.kamal.khidma.service.dto.ParentDTO;
import com.kamal.khidma.service.mapper.ParentMapper;
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
 * Test class for the ParentResource REST controller.
 *
 * @see ParentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KhidmaApp.class)
public class ParentResourceIntTest {

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
    private ParentRepository parentRepository;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private ParentService parentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParentMockMvc;

    private Parent parent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParentResource parentResource = new ParentResource(parentService);
        this.restParentMockMvc = MockMvcBuilders.standaloneSetup(parentResource)
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
    public static Parent createEntity(EntityManager em) {
        Parent parent = new Parent()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .adresse(DEFAULT_ADRESSE);
        return parent;
    }

    @Before
    public void initTest() {
        parent = createEntity(em);
    }

    @Test
    @Transactional
    public void createParent() throws Exception {
        int databaseSizeBeforeCreate = parentRepository.findAll().size();

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);
        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isCreated());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate + 1);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testParent.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testParent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParent.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testParent.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createParentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parentRepository.findAll().size();

        // Create the Parent with an existing ID
        parent.setId(1L);
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParents() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get all the parentList
        restParentMockMvc.perform(get("/api/parents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parent.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void getParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get the parent
        restParentMockMvc.perform(get("/api/parents/{id}", parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parent.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParent() throws Exception {
        // Get the parent
        restParentMockMvc.perform(get("/api/parents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Update the parent
        Parent updatedParent = parentRepository.findOne(parent.getId());
        // Disconnect from session so that the updates on updatedParent are not directly saved in db
        em.detach(updatedParent);
        updatedParent
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .adresse(UPDATED_ADRESSE);
        ParentDTO parentDTO = parentMapper.toDto(updatedParent);

        restParentMockMvc.perform(put("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isOk());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testParent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testParent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParent.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testParent.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParentMockMvc.perform(put("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isCreated());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);
        int databaseSizeBeforeDelete = parentRepository.findAll().size();

        // Get the parent
        restParentMockMvc.perform(delete("/api/parents/{id}", parent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parent.class);
        Parent parent1 = new Parent();
        parent1.setId(1L);
        Parent parent2 = new Parent();
        parent2.setId(parent1.getId());
        assertThat(parent1).isEqualTo(parent2);
        parent2.setId(2L);
        assertThat(parent1).isNotEqualTo(parent2);
        parent1.setId(null);
        assertThat(parent1).isNotEqualTo(parent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParentDTO.class);
        ParentDTO parentDTO1 = new ParentDTO();
        parentDTO1.setId(1L);
        ParentDTO parentDTO2 = new ParentDTO();
        assertThat(parentDTO1).isNotEqualTo(parentDTO2);
        parentDTO2.setId(parentDTO1.getId());
        assertThat(parentDTO1).isEqualTo(parentDTO2);
        parentDTO2.setId(2L);
        assertThat(parentDTO1).isNotEqualTo(parentDTO2);
        parentDTO1.setId(null);
        assertThat(parentDTO1).isNotEqualTo(parentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parentMapper.fromId(null)).isNull();
    }
}

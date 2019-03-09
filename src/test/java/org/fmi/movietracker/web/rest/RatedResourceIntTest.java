package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.Rated;
import org.fmi.movietracker.repository.RatedRepository;
import org.fmi.movietracker.service.RatedService;
import org.fmi.movietracker.service.dto.RatedDTO;
import org.fmi.movietracker.service.mapper.RatedMapper;
import org.fmi.movietracker.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static org.fmi.movietracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RatedResource REST controller.
 *
 * @see RatedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class RatedResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private RatedRepository ratedRepository;

    @Autowired
    private RatedMapper ratedMapper;

    @Autowired
    private RatedService ratedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRatedMockMvc;

    private Rated rated;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RatedResource ratedResource = new RatedResource(ratedService);
        this.restRatedMockMvc = MockMvcBuilders.standaloneSetup(ratedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rated createEntity(EntityManager em) {
        Rated rated = new Rated()
            .code(DEFAULT_CODE);
        return rated;
    }

    @Before
    public void initTest() {
        rated = createEntity(em);
    }

    @Test
    @Transactional
    public void createRated() throws Exception {
        int databaseSizeBeforeCreate = ratedRepository.findAll().size();

        // Create the Rated
        RatedDTO ratedDTO = ratedMapper.toDto(rated);
        restRatedMockMvc.perform(post("/api/rateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratedDTO)))
            .andExpect(status().isCreated());

        // Validate the Rated in the database
        List<Rated> ratedList = ratedRepository.findAll();
        assertThat(ratedList).hasSize(databaseSizeBeforeCreate + 1);
        Rated testRated = ratedList.get(ratedList.size() - 1);
        assertThat(testRated.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createRatedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ratedRepository.findAll().size();

        // Create the Rated with an existing ID
        rated.setId(1L);
        RatedDTO ratedDTO = ratedMapper.toDto(rated);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatedMockMvc.perform(post("/api/rateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rated in the database
        List<Rated> ratedList = ratedRepository.findAll();
        assertThat(ratedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratedRepository.findAll().size();
        // set the field null
        rated.setCode(null);

        // Create the Rated, which fails.
        RatedDTO ratedDTO = ratedMapper.toDto(rated);

        restRatedMockMvc.perform(post("/api/rateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratedDTO)))
            .andExpect(status().isBadRequest());

        List<Rated> ratedList = ratedRepository.findAll();
        assertThat(ratedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRateds() throws Exception {
        // Initialize the database
        ratedRepository.saveAndFlush(rated);

        // Get all the ratedList
        restRatedMockMvc.perform(get("/api/rateds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rated.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getRated() throws Exception {
        // Initialize the database
        ratedRepository.saveAndFlush(rated);

        // Get the rated
        restRatedMockMvc.perform(get("/api/rateds/{id}", rated.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rated.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRated() throws Exception {
        // Get the rated
        restRatedMockMvc.perform(get("/api/rateds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRated() throws Exception {
        // Initialize the database
        ratedRepository.saveAndFlush(rated);

        int databaseSizeBeforeUpdate = ratedRepository.findAll().size();

        // Update the rated
        Rated updatedRated = ratedRepository.findById(rated.getId()).get();
        // Disconnect from session so that the updates on updatedRated are not directly saved in db
        em.detach(updatedRated);
        updatedRated
            .code(UPDATED_CODE);
        RatedDTO ratedDTO = ratedMapper.toDto(updatedRated);

        restRatedMockMvc.perform(put("/api/rateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratedDTO)))
            .andExpect(status().isOk());

        // Validate the Rated in the database
        List<Rated> ratedList = ratedRepository.findAll();
        assertThat(ratedList).hasSize(databaseSizeBeforeUpdate);
        Rated testRated = ratedList.get(ratedList.size() - 1);
        assertThat(testRated.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRated() throws Exception {
        int databaseSizeBeforeUpdate = ratedRepository.findAll().size();

        // Create the Rated
        RatedDTO ratedDTO = ratedMapper.toDto(rated);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatedMockMvc.perform(put("/api/rateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rated in the database
        List<Rated> ratedList = ratedRepository.findAll();
        assertThat(ratedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRated() throws Exception {
        // Initialize the database
        ratedRepository.saveAndFlush(rated);

        int databaseSizeBeforeDelete = ratedRepository.findAll().size();

        // Delete the rated
        restRatedMockMvc.perform(delete("/api/rateds/{id}", rated.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rated> ratedList = ratedRepository.findAll();
        assertThat(ratedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rated.class);
        Rated rated1 = new Rated();
        rated1.setId(1L);
        Rated rated2 = new Rated();
        rated2.setId(rated1.getId());
        assertThat(rated1).isEqualTo(rated2);
        rated2.setId(2L);
        assertThat(rated1).isNotEqualTo(rated2);
        rated1.setId(null);
        assertThat(rated1).isNotEqualTo(rated2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatedDTO.class);
        RatedDTO ratedDTO1 = new RatedDTO();
        ratedDTO1.setId(1L);
        RatedDTO ratedDTO2 = new RatedDTO();
        assertThat(ratedDTO1).isNotEqualTo(ratedDTO2);
        ratedDTO2.setId(ratedDTO1.getId());
        assertThat(ratedDTO1).isEqualTo(ratedDTO2);
        ratedDTO2.setId(2L);
        assertThat(ratedDTO1).isNotEqualTo(ratedDTO2);
        ratedDTO1.setId(null);
        assertThat(ratedDTO1).isNotEqualTo(ratedDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ratedMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ratedMapper.fromId(null)).isNull();
    }
}

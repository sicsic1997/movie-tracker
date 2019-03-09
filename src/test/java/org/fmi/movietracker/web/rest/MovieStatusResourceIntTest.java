package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.MovieStatus;
import org.fmi.movietracker.repository.MovieStatusRepository;
import org.fmi.movietracker.service.MovieStatusService;
import org.fmi.movietracker.service.dto.MovieStatusDTO;
import org.fmi.movietracker.service.mapper.MovieStatusMapper;
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
 * Test class for the MovieStatusResource REST controller.
 *
 * @see MovieStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class MovieStatusResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private MovieStatusRepository movieStatusRepository;

    @Autowired
    private MovieStatusMapper movieStatusMapper;

    @Autowired
    private MovieStatusService movieStatusService;

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

    private MockMvc restMovieStatusMockMvc;

    private MovieStatus movieStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovieStatusResource movieStatusResource = new MovieStatusResource(movieStatusService);
        this.restMovieStatusMockMvc = MockMvcBuilders.standaloneSetup(movieStatusResource)
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
    public static MovieStatus createEntity(EntityManager em) {
        MovieStatus movieStatus = new MovieStatus()
            .code(DEFAULT_CODE);
        return movieStatus;
    }

    @Before
    public void initTest() {
        movieStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieStatus() throws Exception {
        int databaseSizeBeforeCreate = movieStatusRepository.findAll().size();

        // Create the MovieStatus
        MovieStatusDTO movieStatusDTO = movieStatusMapper.toDto(movieStatus);
        restMovieStatusMockMvc.perform(post("/api/movie-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieStatus in the database
        List<MovieStatus> movieStatusList = movieStatusRepository.findAll();
        assertThat(movieStatusList).hasSize(databaseSizeBeforeCreate + 1);
        MovieStatus testMovieStatus = movieStatusList.get(movieStatusList.size() - 1);
        assertThat(testMovieStatus.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createMovieStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieStatusRepository.findAll().size();

        // Create the MovieStatus with an existing ID
        movieStatus.setId(1L);
        MovieStatusDTO movieStatusDTO = movieStatusMapper.toDto(movieStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieStatusMockMvc.perform(post("/api/movie-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieStatus in the database
        List<MovieStatus> movieStatusList = movieStatusRepository.findAll();
        assertThat(movieStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieStatusRepository.findAll().size();
        // set the field null
        movieStatus.setCode(null);

        // Create the MovieStatus, which fails.
        MovieStatusDTO movieStatusDTO = movieStatusMapper.toDto(movieStatus);

        restMovieStatusMockMvc.perform(post("/api/movie-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStatusDTO)))
            .andExpect(status().isBadRequest());

        List<MovieStatus> movieStatusList = movieStatusRepository.findAll();
        assertThat(movieStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovieStatuses() throws Exception {
        // Initialize the database
        movieStatusRepository.saveAndFlush(movieStatus);

        // Get all the movieStatusList
        restMovieStatusMockMvc.perform(get("/api/movie-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getMovieStatus() throws Exception {
        // Initialize the database
        movieStatusRepository.saveAndFlush(movieStatus);

        // Get the movieStatus
        restMovieStatusMockMvc.perform(get("/api/movie-statuses/{id}", movieStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovieStatus() throws Exception {
        // Get the movieStatus
        restMovieStatusMockMvc.perform(get("/api/movie-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieStatus() throws Exception {
        // Initialize the database
        movieStatusRepository.saveAndFlush(movieStatus);

        int databaseSizeBeforeUpdate = movieStatusRepository.findAll().size();

        // Update the movieStatus
        MovieStatus updatedMovieStatus = movieStatusRepository.findById(movieStatus.getId()).get();
        // Disconnect from session so that the updates on updatedMovieStatus are not directly saved in db
        em.detach(updatedMovieStatus);
        updatedMovieStatus
            .code(UPDATED_CODE);
        MovieStatusDTO movieStatusDTO = movieStatusMapper.toDto(updatedMovieStatus);

        restMovieStatusMockMvc.perform(put("/api/movie-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStatusDTO)))
            .andExpect(status().isOk());

        // Validate the MovieStatus in the database
        List<MovieStatus> movieStatusList = movieStatusRepository.findAll();
        assertThat(movieStatusList).hasSize(databaseSizeBeforeUpdate);
        MovieStatus testMovieStatus = movieStatusList.get(movieStatusList.size() - 1);
        assertThat(testMovieStatus.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieStatus() throws Exception {
        int databaseSizeBeforeUpdate = movieStatusRepository.findAll().size();

        // Create the MovieStatus
        MovieStatusDTO movieStatusDTO = movieStatusMapper.toDto(movieStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieStatusMockMvc.perform(put("/api/movie-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieStatus in the database
        List<MovieStatus> movieStatusList = movieStatusRepository.findAll();
        assertThat(movieStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovieStatus() throws Exception {
        // Initialize the database
        movieStatusRepository.saveAndFlush(movieStatus);

        int databaseSizeBeforeDelete = movieStatusRepository.findAll().size();

        // Delete the movieStatus
        restMovieStatusMockMvc.perform(delete("/api/movie-statuses/{id}", movieStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieStatus> movieStatusList = movieStatusRepository.findAll();
        assertThat(movieStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieStatus.class);
        MovieStatus movieStatus1 = new MovieStatus();
        movieStatus1.setId(1L);
        MovieStatus movieStatus2 = new MovieStatus();
        movieStatus2.setId(movieStatus1.getId());
        assertThat(movieStatus1).isEqualTo(movieStatus2);
        movieStatus2.setId(2L);
        assertThat(movieStatus1).isNotEqualTo(movieStatus2);
        movieStatus1.setId(null);
        assertThat(movieStatus1).isNotEqualTo(movieStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieStatusDTO.class);
        MovieStatusDTO movieStatusDTO1 = new MovieStatusDTO();
        movieStatusDTO1.setId(1L);
        MovieStatusDTO movieStatusDTO2 = new MovieStatusDTO();
        assertThat(movieStatusDTO1).isNotEqualTo(movieStatusDTO2);
        movieStatusDTO2.setId(movieStatusDTO1.getId());
        assertThat(movieStatusDTO1).isEqualTo(movieStatusDTO2);
        movieStatusDTO2.setId(2L);
        assertThat(movieStatusDTO1).isNotEqualTo(movieStatusDTO2);
        movieStatusDTO1.setId(null);
        assertThat(movieStatusDTO1).isNotEqualTo(movieStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(movieStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(movieStatusMapper.fromId(null)).isNull();
    }
}

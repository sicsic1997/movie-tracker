package org.fmi.movietracker.web.rest;

import org.fmi.movietracker.MovieTrackerApp;

import org.fmi.movietracker.domain.MovieGenreMapping;
import org.fmi.movietracker.repository.MovieGenreMappingRepository;
import org.fmi.movietracker.service.MovieGenreMappingService;
import org.fmi.movietracker.service.dto.MovieGenreMappingDTO;
import org.fmi.movietracker.service.mapper.MovieGenreMappingMapper;
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
 * Test class for the MovieGenreMappingResource REST controller.
 *
 * @see MovieGenreMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieTrackerApp.class)
public class MovieGenreMappingResourceIntTest {

    @Autowired
    private MovieGenreMappingRepository movieGenreMappingRepository;

    @Autowired
    private MovieGenreMappingMapper movieGenreMappingMapper;

    @Autowired
    private MovieGenreMappingService movieGenreMappingService;

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

    private MockMvc restMovieGenreMappingMockMvc;

    private MovieGenreMapping movieGenreMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovieGenreMappingResource movieGenreMappingResource = new MovieGenreMappingResource(movieGenreMappingService);
        this.restMovieGenreMappingMockMvc = MockMvcBuilders.standaloneSetup(movieGenreMappingResource)
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
    public static MovieGenreMapping createEntity(EntityManager em) {
        MovieGenreMapping movieGenreMapping = new MovieGenreMapping();
        return movieGenreMapping;
    }

    @Before
    public void initTest() {
        movieGenreMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieGenreMapping() throws Exception {
        int databaseSizeBeforeCreate = movieGenreMappingRepository.findAll().size();

        // Create the MovieGenreMapping
        MovieGenreMappingDTO movieGenreMappingDTO = movieGenreMappingMapper.toDto(movieGenreMapping);
        restMovieGenreMappingMockMvc.perform(post("/api/movie-genre-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieGenreMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieGenreMapping in the database
        List<MovieGenreMapping> movieGenreMappingList = movieGenreMappingRepository.findAll();
        assertThat(movieGenreMappingList).hasSize(databaseSizeBeforeCreate + 1);
        MovieGenreMapping testMovieGenreMapping = movieGenreMappingList.get(movieGenreMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createMovieGenreMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieGenreMappingRepository.findAll().size();

        // Create the MovieGenreMapping with an existing ID
        movieGenreMapping.setId(1L);
        MovieGenreMappingDTO movieGenreMappingDTO = movieGenreMappingMapper.toDto(movieGenreMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieGenreMappingMockMvc.perform(post("/api/movie-genre-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieGenreMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieGenreMapping in the database
        List<MovieGenreMapping> movieGenreMappingList = movieGenreMappingRepository.findAll();
        assertThat(movieGenreMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieGenreMappings() throws Exception {
        // Initialize the database
        movieGenreMappingRepository.saveAndFlush(movieGenreMapping);

        // Get all the movieGenreMappingList
        restMovieGenreMappingMockMvc.perform(get("/api/movie-genre-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieGenreMapping.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMovieGenreMapping() throws Exception {
        // Initialize the database
        movieGenreMappingRepository.saveAndFlush(movieGenreMapping);

        // Get the movieGenreMapping
        restMovieGenreMappingMockMvc.perform(get("/api/movie-genre-mappings/{id}", movieGenreMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieGenreMapping.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMovieGenreMapping() throws Exception {
        // Get the movieGenreMapping
        restMovieGenreMappingMockMvc.perform(get("/api/movie-genre-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieGenreMapping() throws Exception {
        // Initialize the database
        movieGenreMappingRepository.saveAndFlush(movieGenreMapping);

        int databaseSizeBeforeUpdate = movieGenreMappingRepository.findAll().size();

        // Update the movieGenreMapping
        MovieGenreMapping updatedMovieGenreMapping = movieGenreMappingRepository.findById(movieGenreMapping.getId()).get();
        // Disconnect from session so that the updates on updatedMovieGenreMapping are not directly saved in db
        em.detach(updatedMovieGenreMapping);
        MovieGenreMappingDTO movieGenreMappingDTO = movieGenreMappingMapper.toDto(updatedMovieGenreMapping);

        restMovieGenreMappingMockMvc.perform(put("/api/movie-genre-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieGenreMappingDTO)))
            .andExpect(status().isOk());

        // Validate the MovieGenreMapping in the database
        List<MovieGenreMapping> movieGenreMappingList = movieGenreMappingRepository.findAll();
        assertThat(movieGenreMappingList).hasSize(databaseSizeBeforeUpdate);
        MovieGenreMapping testMovieGenreMapping = movieGenreMappingList.get(movieGenreMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieGenreMapping() throws Exception {
        int databaseSizeBeforeUpdate = movieGenreMappingRepository.findAll().size();

        // Create the MovieGenreMapping
        MovieGenreMappingDTO movieGenreMappingDTO = movieGenreMappingMapper.toDto(movieGenreMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieGenreMappingMockMvc.perform(put("/api/movie-genre-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieGenreMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieGenreMapping in the database
        List<MovieGenreMapping> movieGenreMappingList = movieGenreMappingRepository.findAll();
        assertThat(movieGenreMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovieGenreMapping() throws Exception {
        // Initialize the database
        movieGenreMappingRepository.saveAndFlush(movieGenreMapping);

        int databaseSizeBeforeDelete = movieGenreMappingRepository.findAll().size();

        // Delete the movieGenreMapping
        restMovieGenreMappingMockMvc.perform(delete("/api/movie-genre-mappings/{id}", movieGenreMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieGenreMapping> movieGenreMappingList = movieGenreMappingRepository.findAll();
        assertThat(movieGenreMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieGenreMapping.class);
        MovieGenreMapping movieGenreMapping1 = new MovieGenreMapping();
        movieGenreMapping1.setId(1L);
        MovieGenreMapping movieGenreMapping2 = new MovieGenreMapping();
        movieGenreMapping2.setId(movieGenreMapping1.getId());
        assertThat(movieGenreMapping1).isEqualTo(movieGenreMapping2);
        movieGenreMapping2.setId(2L);
        assertThat(movieGenreMapping1).isNotEqualTo(movieGenreMapping2);
        movieGenreMapping1.setId(null);
        assertThat(movieGenreMapping1).isNotEqualTo(movieGenreMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieGenreMappingDTO.class);
        MovieGenreMappingDTO movieGenreMappingDTO1 = new MovieGenreMappingDTO();
        movieGenreMappingDTO1.setId(1L);
        MovieGenreMappingDTO movieGenreMappingDTO2 = new MovieGenreMappingDTO();
        assertThat(movieGenreMappingDTO1).isNotEqualTo(movieGenreMappingDTO2);
        movieGenreMappingDTO2.setId(movieGenreMappingDTO1.getId());
        assertThat(movieGenreMappingDTO1).isEqualTo(movieGenreMappingDTO2);
        movieGenreMappingDTO2.setId(2L);
        assertThat(movieGenreMappingDTO1).isNotEqualTo(movieGenreMappingDTO2);
        movieGenreMappingDTO1.setId(null);
        assertThat(movieGenreMappingDTO1).isNotEqualTo(movieGenreMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(movieGenreMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(movieGenreMappingMapper.fromId(null)).isNull();
    }
}
